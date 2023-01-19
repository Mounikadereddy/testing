/*
 * ClaimProcessorHelper
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimprocessor.util;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.ClaimLabelType;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.MarriageTerminationType;
import gov.va.vba.rbps.services.ws.client.handler.awards.UpdateBnftClaimLocationToPMCWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.mapd.CreateNoteWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.vonapp.UpdateBenefitClaimWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.vonapp.VnpProcUpdateWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.UpdateBnftClaimLocationToPMCResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static gov.va.vba.rbps.coreframework.util.RbpsConstants.CLAIM_STATUS_REQUIRES_MANUAL_PROCESSING;


/**
 *      Title: ClaimProcessorHelper
 *
 *      Utility/helper class to:
 *          1. update claim status
 *          2. update claim label
 *          3. create MAP-D note
 */
public final class ClaimProcessorHelper {

    public static final List<DependentDecisionType> denialDecisionTypes = Arrays.asList( DependentDecisionType.RATED_NOT_HELPLESS,
            DependentDecisionType.OVER_18_NOT_IN_SCHOOL_OR_HELPLESS );
    public static final List<DependentStatusType>       denialStatustypes   = Arrays.asList( DependentStatusType.NOT_AN_AWARD_DEPENDENT );

    private static Logger logger = Logger.getLogger(ClaimProcessorHelper.class);

    private final LogUtils                      logUtils            = new LogUtils( logger, true );

    private CreateNoteWSHandler                         createNoteWSHandler;
    private VnpProcUpdateWSHandler                      vnpProcUpdateWSHandler;
    private UpdateBenefitClaimWSHandler                 updateBenefitClaimWSHandler;
    private UpdateBnftClaimLocationToPMCWSHandler		updateBnftClaimLocationToPMCWSHandler;

    private static String pensionClaimError = "Veteran in receipt of Pension after the dependency effective date";

    /**
     * This method tags a Claim for Manual Processing:
     *
     *  1. update claim status
     *  2. update claim label
     *  3. create MAP-D note
     */
    public void sendClaimForManualProcessing(RbpsRepository repo) {

        updateClaimStatus(CLAIM_STATUS_REQUIRES_MANUAL_PROCESSING, repo);

        // TODO change claim label and location
        if(repo.isProcessPensions() && repo.getVeteran().isPensionAward()) {
            updateClaimLabel(repo);
            updateClaimLocationToPMC(repo);
        }
        
        String validataionMsg = CommonUtils.stringBuilder(repo.getValidationMessages());
        if (validataionMsg.contains(pensionClaimError)) {
        	logUtils.log("pension claim error received, validation errors = " + validataionMsg, repo);
        } else {
        	updateClaimLabelToRejectVersion(repo);
        }

        updateMapdWithValidationMessages(repo);
    }

    /*
     *      Should be on the Award class, but we're afraid to modify the xom.
     */
    public static final boolean isDenial( final Award award ) {

        if ( ( award.getDependencyDecisionType().equals(DependentDecisionType.MARRIAGE_TERMINATED) )
                || ( award.getDependencyDecisionType().equals(DependentDecisionType.DEATH ) ) ) {

            return false;
        }

        return denialDecisionTypes.contains( award.getDependencyDecisionType() )
                || denialStatustypes.contains( award.getDependencyStatusType() );
    }





    public void updateMapdWithValidationMessages(RbpsRepository repo) {

        logUtils.log( "the unformatted set of notes:\n" + CommonUtils.stringBuilder(repo.getValidationMessages()), repo);

        updateMapd(repo.getFormattedValidationMessages(), repo);
    }


    /**
     *      call the VNPprocUPdate WS to update a Claim status matching the String
     */
    public void updateClaimStatus(final String claimStatus, RbpsRepository repo) {

    	repo.setVnpProcStateType(claimStatus);
    	//vnpProcUpdateWSHandler.setRepository( repo );
        vnpProcUpdateWSHandler.vnpProcUpdate(repo);
    }


	/**
	 * updates the claim label for the claim with the associated reject claim
	 * label
	 */
	public void updateClaimLabelToRejectVersion(RbpsRepository repo) {
		logger.debug("repository.getVeteran().updateClaimLabelToRejectVersion() )****************************: ");

		if (repo.getVeteran() == null || repo.getVeteran().getClaim() == null
				|| repo.getVeteran().getClaim().getClaimLabel() == null) {

			return;
		}
		ClaimLabelType oldClaimLabel;
		ClaimLabelType newClaimLabel = null;
		oldClaimLabel = repo.getVeteran().getClaim().getClaimLabel();
		if (repo.getVeteran().getLatestPreviousMarriage() != null) {

			logger.debug("repository.getVeteran().getLatestPreviousMarriage() )****************************: ");

			MarriageTerminationType terminationType = repo.getVeteran().getLatestPreviousMarriage()
					.getTerminationType();

			if (terminationType.equals(MarriageTerminationType.DIVORCE)
					|| terminationType.equals(MarriageTerminationType.DEATH)) {
				// it is a spouse removal
				if (repo.isProcessPensions() && repo.getVeteran().isPensionAward()){
					newClaimLabel = (ClaimLabelType.NEW_686C_PMC_DEPENDENCY_ADJ_REMOVAL_EXCEPTION);
				}
				else{
					newClaimLabel = (ClaimLabelType.NEW_686C_DEPENDENCY_ADJ_REMOVAL_EXCEPTION);
				}
				repo.getVeteran().getClaim().setClaimLabel(newClaimLabel);

				logUtils.log("\n\tOld claimLabel: " + oldClaimLabel + "\n\tNew claimLabel: " + newClaimLabel, repo);
				try {
					updateBenefitClaimWSHandler.updateBenefitClaim(repo);

				} catch (Exception ex) {

					String message = (ex.getMessage() == null) ? "" : ex.getMessage();
					repo.addValidationMessage(message);
					updateMapdWithValidationMessages(repo);
				}
			}
		}

	}
    /**
     *      updates the claim label for the spouse removal
     */
	public void updateClaimLabelForSpouseRemoval(RbpsRepository repo) {

		logger.debug("updateClaimLabelForSpouseRemoval****************************");

		if (repo.getVeteran() == null || repo.getVeteran().getClaim() == null
				|| repo.getVeteran().getClaim().getClaimLabel() == null) {

			return;
		}
		ClaimLabelType oldClaimLabel;
		ClaimLabelType newClaimLabel = null;
		oldClaimLabel = repo.getVeteran().getClaim().getClaimLabel();
		if (repo.getVeteran().getLatestPreviousMarriage() != null) {

			logger.debug("repository.getVeteran().getLatestPreviousMarriage() )****************************: ");

			MarriageTerminationType terminationType = repo.getVeteran().getLatestPreviousMarriage()
					.getTerminationType();

			if (terminationType.equals(MarriageTerminationType.DIVORCE)
					|| terminationType.equals(MarriageTerminationType.DEATH)) {
				// it is a spouse removal
				if (repo.isProcessPensions() && repo.getVeteran().isPensionAward()){
					newClaimLabel=ClaimLabelType.NEW_686C_PMC_DEPENDENCY_ADJ_REMOVAL;
				}
				else{
					newClaimLabel=ClaimLabelType.NEW_686C_DEPENDENCY_ADJ_REMOVAL;
				}
				repo.getVeteran().getClaim().setClaimLabel(newClaimLabel);
				logUtils.log("\n\tOld claimLabel: " + oldClaimLabel + "\n\tNew claimLabel: " + newClaimLabel, repo);
				try {
					updateBenefitClaimWSHandler.updateBenefitClaim(repo);

				} catch (Exception ex) {

					String message = (ex.getMessage() == null) ? "" : ex.getMessage();
					repo.addValidationMessage(message);
					updateMapdWithValidationMessages(repo);
				}
			}
		}
	}
    public void updateClaimLabel(RbpsRepository repository) {
    	 logger.debug("updateClaimLabel  ****************************: "); 
         
    	 
    	if (repository.getVeteran() == null
                || repository.getVeteran().getClaim() == null
                || repository.getVeteran().getClaim().getClaimLabel() == null) {

            return;
    	}
        else if ( repository.getVeteran().getLatestPreviousMarriage() != null ) {
        //  changes for updating claim label for spouse removal
        	 logger.debug("repository.getVeteran().getLatestPreviousMarriage() )****************************: "); 
             
        MarriageTerminationType	terminationType =  repository.getVeteran().getLatestPreviousMarriage().getTerminationType();
        
			if (terminationType.equals(MarriageTerminationType.DIVORCE)
					|| terminationType.equals(MarriageTerminationType.DEATH)) {
				// it is a spouse removal
				// it is a spouse removal
				if (repository.isProcessPensions() && repository.getVeteran().isPensionAward()){
					 repository.getVeteran().getClaim().setClaimLabel(ClaimLabelType.NEW_686C_PMC_DEPENDENCY_ADJ_REMOVAL_EXCEPTION);
				}
				else{
					 repository.getVeteran().getClaim().setClaimLabel(ClaimLabelType.NEW_686C_DEPENDENCY_ADJ_REMOVAL_EXCEPTION);
				}
			}
		}//  end of changes for updating claim label for spouse removal
        else{

        ClaimLabelType oldClaimLabel = repository.getVeteran().getClaim().getClaimLabel();
        
       
        // if claim type is auto 686c, ebenefits 686c, phone depend adjust/adjustment exception,
        // then set to "PMC Automated Dependency 686c" (686c Pension)
        if(oldClaimLabel.equals(ClaimLabelType.NEW_686C) || oldClaimLabel.equals(ClaimLabelType.NEW_EBENEFITS_686C) ||
                oldClaimLabel.equals(ClaimLabelType.NEW_PHONE_DEPENDENCY_ADJUSTMENT) || oldClaimLabel.equals(ClaimLabelType.NEW_PHONE_DEPENDENCY_ADJUSTMENT_EXCEPTION)) {

            repository.getVeteran().getClaim().setClaimLabel(ClaimLabelType.NEW_PMC_686C);
        }

        // if claim type is auto 674, ebenefits 674, phone school attendence/attendence exception
        // then set to "PMC Automated School Attendance 674" (674 Pension)
        if(oldClaimLabel.equals(ClaimLabelType.NEW_674) || oldClaimLabel.equals(ClaimLabelType.NEW_EBENEFITS_674)||
                oldClaimLabel.equals(ClaimLabelType.NEW_PHONE_SCHOOL_ATTENDANCE) || oldClaimLabel.equals(ClaimLabelType.NEW_PHONE_SCHOOL_ATTENDANCE_EXCEPTION)) {

            repository.getVeteran().getClaim().setClaimLabel(ClaimLabelType.NEW_PMC_674);
        }

        // if claim type is D2D dependency adjustment
        // then set to "D2D-Pension Dependency Adjustment" (D2D pension)
        if(oldClaimLabel.equals(ClaimLabelType.NEW_D2D_DEPNDANCY_ADJUSTMENT)) {
            repository.getVeteran().getClaim().setClaimLabel(ClaimLabelType.NEW_D2D_PENSION_DEPNDANCY_ADJUSTMENT);
        }

        // if claim type is D2D school attendance
        // then set to "D2D-Pension School Attendence" (D2D pension school)
        if(oldClaimLabel.equals(ClaimLabelType.NEW_D2D_SCHOOL_ATTENDANCE)) {
            repository.getVeteran().getClaim().setClaimLabel(ClaimLabelType.NEW_D2D_PENSION_SCHOOL_ATTENDANCE);
        }
        logger.debug("claim label set to ****************************: "+repository.getVeteran().getClaim().getClaimLabel().getValue()); 
        }
      
        
    }

    /**
     * @param repo
     */
    public void updateClaimLocationToPMC(RbpsRepository repo) {
        logUtils.log( "calling updateBenefitClaimToPMC: " + repo.getVeteran().getClaim().getClaimId(), repo);

        UpdateBnftClaimLocationToPMCResponse response        = updateBnftClaimLocationToPMCWSHandler.call( repo );

        logUtils.log( "updateBenefitClaimToPMC returned: " + repo.getVeteran().getClaim().getClaimId(), repo);
    }

    /**
     *      Adds a note containing all rule exception messages to the current claim
     */
    public void updateMapd(RbpsRepository repo) {

        updateMapd(null, repo);
    }


    public static final Date getEventDate( final Award award, RbpsRepository repo ) {

        if ( isDenial( award ) ) {

            return SimpleDateUtils.truncateToDay( repo.getVeteran().getClaim().getReceivedDate() );
        }

        if ( award.getEventDate() == null ) {

            return null;
        }

        return SimpleDateUtils.truncateToDay( award.getEventDate() );
    }

    /**
     *      Adds a note with the String passed in to the current claim
     *      If the string is longer than 2000 characters, it is split
     *      based on \n and sent
     *      \n because each validationMessage in RbpsRepository ends with a \n
     *      @param String
     */
    private void updateMapd( final String text, RbpsRepository repo ) {

        try {
            boolean         repeat          =   true;
            String          fullText        =   text;
            String          subText         =   "";
            final int       STRLEN          =   2000;

            while (repeat) {

                if ((fullText != null) && (fullText.trim().length() > STRLEN)) {
                    subText = fullText.substring(0,STRLEN);

                    if ( ! subText.endsWith("\n") ) {

                        int pos     = subText.lastIndexOf("\n");

                        if (pos > 0) {

                            fullText = createFullTextNote(fullText, pos, repo);

                        }
                        else {
                            //      if first 2000 characters does not contain \n

                            fullText = createSubTextNote(fullText, subText, STRLEN, repo );
                        }

                    }
                    else {
                        //      if subText ends with \n

                        fullText = createSubTextNote(fullText, subText, STRLEN, repo );
                    }

                }
                else {
                    //      if fullText less than equal STRLEN
                	//createNoteWSHandler.setRepository(repo);
                    createNoteWSHandler.createNote(fullText, repo);
                    System.out.println( fullText );

                    repeat = false;
                }
            }
        }
        catch (Throwable ex) {

            logUtils.log( "***RBPS: MAP-D CreateNote Exception: ", ex, repo );
            throw new RbpsRuntimeException( "Unable to create the mapd notes", ex );
        }
    }


    private String createSubTextNote(String fullText, final String subText, final int STRLEN, RbpsRepository repo ) {

    	//createNoteWSHandler.setRepository( repo);
        createNoteWSHandler.createNote(subText, repo);

        fullText = fullText.substring(STRLEN);
        return fullText;
    }


    private String createFullTextNote(String fullText, final int pos, RbpsRepository repo) {

        String subText;
        subText     = fullText.substring(0, pos);
        fullText    = fullText.substring(pos + 1);

        //createNoteWSHandler.setRepository(repo);
        createNoteWSHandler.createNote(subText, repo);
        System.out.println( subText + " ");
        return fullText;
    }




    public static final boolean missingNeededEventDate( final Award award, RbpsRepository repo ) {
        return getEventDate( award, repo ) == null;
    }

    public void setCreateNoteWSHandler(final CreateNoteWSHandler createNoteWSHandler) {

        this.createNoteWSHandler = createNoteWSHandler;
    }

    /* EK undesirable
    
    public void setRepository(final RbpsRepository repository) {

        this.repository = repository;
    }
    */

    public void setUpdateBenefitClaimWSHandler( final UpdateBenefitClaimWSHandler updateBenefitClaimWSHandler) {

        this.updateBenefitClaimWSHandler = updateBenefitClaimWSHandler;
    }

    public void setVnpProcUpdateWSHandler( final VnpProcUpdateWSHandler vnpProcUpdateWSHandler) {

        this.vnpProcUpdateWSHandler = vnpProcUpdateWSHandler;
    }

    /**
     * @param updateBnftClaimLocationToPMCWSHandler
     */
    public void setUpdateBnftClaimLocationToPMCWSHandler( final UpdateBnftClaimLocationToPMCWSHandler updateBnftClaimLocationToPMCWSHandler) {
        this.updateBnftClaimLocationToPMCWSHandler = updateBnftClaimLocationToPMCWSHandler;
    }

}
