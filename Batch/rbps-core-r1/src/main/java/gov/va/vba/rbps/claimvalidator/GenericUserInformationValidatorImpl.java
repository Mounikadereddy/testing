/*
 * GenericUserInformationValidatorImpl.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimvalidator;


import static gov.va.vba.rbps.coreframework.util.RbpsAssertion.assertIsReadyStatus;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsClaimDataException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;


/**
 *      Validates the data required by RBPS from UserInformationService
 */
public class GenericUserInformationValidatorImpl extends ClaimValidatorInterface {


    private static Logger logger = Logger.getLogger(GenericUserInformationValidatorImpl.class);

    public static final String         FAIL = "Auto Dependency Processing Validation Reject Reason - ";

    private LogUtils                    logUtils            = new LogUtils( logger, true );
    


    /**
     * validate() invokes the following methods to validate a Veteran
     * Step 1: validateVeteran() (Validate the Veteran itself)
     * Step 2: validateVeteranHasClaim() (Validate the Claim and associated fields)
     * Step 3: validateCurrentMarriage686c() (Validations for 686c - Marriage)
     * Step 4: validatePreviousMarriages686c() (Validations for 686c - Marriage)
     * Step 5: validateChild686c() (Validations for 686c - Child)
     * Step 6: validateChild674() (Validations for 674 - Child)
     *
     * @throws RbpsRuntimeException, RbpsClaimDataException
     * @return void
     */
    @Override
    public void validate(RbpsRepository repo) {

//        logUtils.debugEnter();

        // validate general Veteran and Claim data
        validateVeteran(repo);
        if ( repo.getVeteran() == null ){
        	return;
        }
        validateVeteranMailingAddress(repo);
        validateVeteranHasClaim(repo);

        // validate 686c data
        validateChild686c(repo);
        validateCurrentMarriage686c(repo);
        validatePreviousMarriages686c(repo);

        // validate 674 data
        validateChild674(repo);

        // 'temporary' validations
        //validate674With686c();
        validateMultiple674(repo);

        if ( repo.hasValidationMessages() ) {

            logUtils.log("Validation Messages : "+repo.getFormattedValidationMessages(), repo);
//            throw new RbpsClaimDataException();
            throw new RbpsClaimDataException("Claims Data is not valid for processing by RBPS. Reasons : " + repo.getFormattedValidationMessages() );
        }

//        logUtils.debugExit();
    }


    /**
     *      Performs the validation for multiple 674 submitted for a single child
     *
     *      @return void
     *      @throws RbpsClaimDataException
     */
    private void validateMultiple674(RbpsRepository repo) throws RbpsClaimDataException {

        int formCount;

        for (final Child child : repo.getVeteran().getChildren()) {

            formCount = 0;
            for (final FormType form : child.getForms()) {

                if (form == FormType.FORM_21_674) {
                    formCount++;
                }
            }

            if (formCount > 1) {
                //throw new RbpsClaimDataException(FAIL + "Claims with a Child on multiple 674s are not in scope for RBPS.");
            	RbpsValidationAppender.addCustomMessage(FAIL + "Claims with a Child on multiple 674s are not in scope for RBPS.", repo);
            }
        }
    }


    /**
     * Performs the validation for a 674 submitted with no 686c for a child
     *
     * @return void
     * @throws RbpsClaimDataException
     */
    private void validate674With686c(RbpsRepository repo) throws RbpsClaimDataException {

        for (final Child child : repo.getVeteran().getChildren()) {

            if (child.getForms().contains(FormType.FORM_21_674)
                 && ! child.getForms().contains(FormType.FORM_21_686C)) {

                //throw new RbpsClaimDataException(FAIL + "Claims with a Child on a 674 but not on a 686c are not in scope for RBPS.");
            	RbpsValidationAppender.addCustomMessage(FAIL + "Claims with a Child on a 674 but not on a 686c are not in scope for RBPS.", repo);
            }
        }
    }

    /**
     * performs 674-level validations for Children
     *
     * 1 - Ensure for a particular child they have 674 data to validate
     * 2 - Birth Date
     * 3 - Current Term
     * 4 - Expected Graduation Date of Current Term
     * 5 - Education Type : part time/full time
     * 6 - Education Level Type: Homeschool, Highschool, College
     *
     * @return void
     */
    private void validateChild674(RbpsRepository repo) {

        for (final Child child : repo.getVeteran().getChildren()) {
        	
        	//ccr 2116 - RBPS to set 674s with previous school term to manual
        	// lastTerm contains the  previousTerms data
        	/*
        	  if(child.getLastTerm() !=null){
            	// we are not going to validate any more RbpsRuntimeException and set the case to manual. 
          		throw new RbpsRuntimeException( "Auto Dependency Processing Reject Reason -RBPS is currently not processing dependency claims with a previous school term. Please review." );
          	  }
          	*/
        	  // end of changes for ccr 2116.
          	   if (child.getForms().contains(FormType.FORM_21_674)) {
            	RbpsValidationAppender.addNotNullValidation(child.getBirthDate(), FAIL + "Child must have a Birth Date.", repo);
            	RbpsValidationAppender.addNotNullValidation(child.getCurrentTerm(), FAIL + "Child must have a current Education term.", repo );
            	RbpsValidationAppender.addNotNullValidation(child.getCurrentTerm().getExpectedGraduationDate(), FAIL + "Child must have an expected Graduation Date for the current Education term.", repo);
            	RbpsValidationAppender.addNotNullValidation(child.getCurrentTerm().getEducationType(), FAIL + "Child must be attending part-time or full-time.", repo);
            	RbpsValidationAppender.addNotNullValidation(child.getCurrentTerm().getEducationLevelType(), FAIL + "Child must be attending a Home School, High School or College.", repo);
            	
             }
          	if(child.getCurrentTerm()!=null && child.getCurrentTerm().getOfficialCourseStartDate()!=null ){
          		Date child18Birthday=RbpsXomUtil.addYearsToDate(18, child.getBirthDate());
          		Date child18BirthdayPlus3mo=RbpsXomUtil.addMonthsToDate(3, child18Birthday);
          		logger.debug("child child18Birthday is:"+child18Birthday);
          		logger.debug("child child18BirthdayPlus3mo is:"+child18BirthdayPlus3mo);
          		logger.debug("child child.getCurrentTerm().getOfficialCourseStartDate():"+child.getCurrentTerm().getOfficialCourseStartDate());
          		if(child.getCurrentTerm().getOfficialCourseStartDate().after(child18Birthday) && child.getCurrentTerm().getOfficialCourseStartDate().before(child18BirthdayPlus3mo)){
          			RbpsValidationAppender.addCustomMessage(FAIL + "Continuous school term could not be determined as school start date is near 18th birthdate. Please Review and send 674b if needed.", repo);
          			
          		}
          	}
        }
    }

    /**
     * performs 686c-level validations for previous marriages
     *
     * 1 - Iterate over previous marriages
     * 2 - Each Marriage must have a spouse
     * 3 - Marriage must have an end date
     *
     * @return void
     */
    private void validatePreviousMarriages686c(RbpsRepository repo) {

        for (final Marriage marriage : repo.getVeteran().getPreviousMarriages()) {
        	RbpsValidationAppender.addNotNullValidation(marriage.getMarriedTo(), FAIL + "Previous Marriage must have a Spouse.", repo);
            //addNotNullValidation(m.getMarriedTo().getBirthDate(), FAIL + "Previous Spouse must have a Birth Date.");
        	RbpsValidationAppender.addNotNullValidation(marriage.getEndDate(), FAIL + "Previous Marriage must have an end date.", repo);
        }
    }

    /**
     * performs 686c-level validations for the current marriage
     *
     * 1 - Check if the Veteran is Married
     * 2 - Married to a Person
     * 3 - Marriage has a start date
     * 4 - Marriage does NOT have an end date
     *
     * @return void
     */
    private void validateCurrentMarriage686c(RbpsRepository repo) {

        if (repo.getVeteran().getCurrentMarriage() == null) {

            return;
        }

        RbpsValidationAppender.addNotNullValidation(repo.getVeteran().getCurrentMarriage().getMarriedTo(), FAIL + "Veteran must be Married to a person.", repo);
//        addNotNullValidation(repository.getVeteran().getCurrentMarriage().getMarriedTo().getBirthDate(), FAIL + "Spouse must have a Birth Date.");
//        TODO: wrap the not null validation with the condition for the step child when uncommented
//        RbpsValidationAppender.addNotNullValidation(repository.getVeteran().getCurrentMarriage().getStartDate(), FAIL + "Current Marriage must have a start date.", repo);
        RbpsValidationAppender.addNullValidation(repo.getVeteran().getCurrentMarriage().getEndDate(), FAIL + "Current Marriage must not have an end date.", repo);
        RbpsValidationAppender.addNotFutureDateValidation( repo.getVeteran().getCurrentMarriage().getStartDate(), FAIL + "Marriage date is in the future. Please review.", repo );
    }

    /**
     * performs 686c-level validations for Children
     *
     * 1 - Ensure for a particular child they have 686c data to validate
     * 2 - Birth Date
     * 3 - Child Type
     *
     * @return void
     */
    private void validateChild686c(RbpsRepository repo) {

        for (final Child c : repo.getVeteran().getChildren()) {

            if (c.getForms().contains(FormType.FORM_21_686C)) {

                RbpsValidationAppender.addNotNullValidation(c.getBirthDate(), FAIL + "Child must have a Birth Date.", repo);
                RbpsValidationAppender.addNotNullValidation(c.getSsn(), FAIL + "Dependent " + c.getFirstName() + " " + c.getLastName() + " SSN was not provided. Please Review.", repo);
                RbpsValidationAppender.addNotNullValidation(c.getChildType(), FAIL + "Child must have a relationship type.", repo);
                RbpsValidationAppender.addNotFutureDateValidation( c.getBirthDate(), FAIL + "Dependent date of birth is in the future. Please review.",repo );
            }
        }
    }


    /**
     * performs basic Claim-level validations
     *
     * 1 - Proc State = Ready
     * 2 - End Product Code
     * 3 - Claim Label
     * 4 - Claim received Date
     * 5 - List of forms is non-empty
     * 6 - Claim ID
     *
     * @author vafscpeterj
     * @return void
     */
    private void validateVeteranHasClaim(RbpsRepository repo) {

        assertIsReadyStatus(repo.getVnpProcStateType(), FAIL + "Claim must be in 'Ready' status.");
        RbpsValidationAppender.addNotNullValidation(repo.getVeteran().getClaim(), FAIL + "The Veteran must have an associated Claim.", repo);
        RbpsValidationAppender.addNotNullValidation(repo.getVeteran().getClaim().getEndProductCode(), FAIL + "End Product not in EP 130 series. Please review.", repo);
        //assertIsValidClaimLabel(repository.getVeteran().getClaim().getClaimLabel(), FAIL + "Claim must have a valid Claim Label.");
        RbpsValidationAppender.addIsValidClaimLabel(repo.getVeteran().getClaim().getClaimLabel(), FAIL + "Claim must have a valid Claim Label.", repo);
        RbpsValidationAppender.addNotNullValidation(repo.getVeteran().getClaim().getReceivedDate(), FAIL + "Claim must have a date.", repo);
        //assertNonEmptyList(repository.getVeteran().getClaim().getForms(), FAIL + "Claim must have forms associated with it.");
        RbpsValidationAppender.addNonEmptyListValidation(repo.getVeteran().getClaim().getForms(), FAIL + "Claim must have forms associated with it.", repo);
        //assertNumberNonZero(repository.getVeteran().getClaim().getClaimId(), FAIL + "Claim must have an ID.");
        RbpsValidationAppender.addNonZeroNumberValidation(repo.getVeteran().getClaim().getClaimId(), FAIL + "Claim must have an ID.", repo);
    }


    /**
     * performs basic Veteran validations
     *
     * 1 - File Number
     *
     * @author vafscpeterj
     * @return void
     */
    private void validateVeteran(RbpsRepository repo) {

        RbpsValidationAppender.addNotNullValidation(repo.getVeteran(), FAIL + "A Veteran must be specified.", repo);
        if (repo.getVeteran() == null ) {
        	return;
        }
        RbpsValidationAppender.addNonEmptyStringValidation(repo.getVeteran().getFileNumber(), FAIL + "Veteran must have a File Number.", repo);
        RbpsValidationAppender.addNonZeroNumberValidation(repo.getVeteran().getCorpParticipantId(), FAIL + "Veteran must have a Corporate Participant Id.", repo);
    }


    private void validateVeteranMailingAddress(RbpsRepository repo) {
    	
    	if (repo.getVeteran().getMailingAddress() == null ) {
    		
    		RbpsValidationAppender.addCustomMessage(FAIL + "Veteran address is missing. Please review.", repo);
    		return;
    	}
    	
    	if (repo.getVeteran().getMailingAddress().getCountry() == null ) {
    		
    		RbpsValidationAppender.addCustomMessage(FAIL + "Veteran mailing address country is missing. Please review.", repo);
    		return;
    	}
    	
	    	if (repo.getVeteran().getMailingAddress().getCountry().equalsIgnoreCase("USA") &&
	    			repo.getVeteran().getMailingAddress().getMltyPostOfficeTypeCd() ==null ) {
	    		
	    		if ( StringUtils.isEmpty(repo.getVeteran().getMailingAddress().getLine1() )
	    				|| StringUtils.isEmpty( repo.getVeteran().getMailingAddress().getCity() )
	    				|| StringUtils.isEmpty( repo.getVeteran().getMailingAddress().getState() )
	    				|| StringUtils.isEmpty( repo.getVeteran().getMailingAddress().getZipPrefix() )  ) {
	    		
	    			RbpsValidationAppender.addCustomMessage(FAIL + "Veteran address is incomplete. Please review.", repo);
	    		}
	    	}// ccr2192 changes
	    	else if (!repo.getVeteran().getMailingAddress().getCountry().equalsIgnoreCase("USA") &&
	    			repo.getVeteran().getClaim().getJrnObjId().contains("shrinq") ) {
	    		
	    		if ( StringUtils.isEmpty(repo.getVeteran().getMailingAddress().getLine1() )
	    				|| StringUtils.isEmpty( repo.getVeteran().getMailingAddress().getCity())
	    				|| StringUtils.isEmpty(repo.getVeteran().getMailingAddress().getCountry())) {
	    		
	    			RbpsValidationAppender.addCustomMessage(FAIL + "Veteran international address is incomplete. Please review.", repo);
	    		}
	    	}// end of 2192 changes
	    	else { 
	    			    		
	    		/*
	    		 * Changes for CCR2032 
	    		 * dependency claims with foreign addresses should be off-ramed
	    		 *  APO, FPO, and DPO addresses should be off-ramped as well as any dependency claims with a country other than USA. 
                  *if  getVeteran().getMailingAddress().getMltyPostOfficeTypeCd() !=null means it is one of APO, FPO, and DPO addresses.
	    		 * if ( StringUtils.isEmpty( repo.getVeteran().getMailingAddress().getLine1() )
	    				|| StringUtils.isEmpty( repo.getVeteran().getMailingAddress().getCity() )
	    				|| StringUtils.isEmpty( repo.getVeteran().getMailingAddress().getCountry() )  ) {
	    			
	    			RbpsValidationAppender.addCustomMessage(FAIL + "Veteran address is incomplete. Please review.", repo);
	    		*/
	    		RbpsValidationAppender.addCustomMessage(FAIL + "Veteran address is foreign, APO, FPO, or DPO type. Please review.", repo);
	    		}
	    	}
    }

