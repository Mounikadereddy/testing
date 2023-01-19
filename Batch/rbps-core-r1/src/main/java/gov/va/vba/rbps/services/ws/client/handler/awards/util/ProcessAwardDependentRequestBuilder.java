/*
 * ProcessAwardDependentRequestBuilder
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimprocessor.util.ClaimProcessorHelper;
import gov.va.vba.rbps.claimprocessor.util.OmnibusFlagHelper;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.MarriageTerminationType;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendAwardDependencyInputVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendDependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependent;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static gov.va.vba.rbps.coreframework.util.RbpsConstants.AWARD_TYPE;



/**
 *      builds the request for the "amend awards" web service.
 */
public class ProcessAwardDependentRequestBuilder {


    private static Logger                   logger              = Logger.getLogger(ProcessAwardDependentRequestBuilder.class);

    private LogUtils                        logUtils            = new LogUtils( logger, true );

    private OmnibusFlagHelper omnibusFlagHelper = new OmnibusFlagHelper();
    /**
     *      Prepare a ProcessAwardDependentRequest object
     *
     *      @return ProcessAwardDependent
     */
    public ProcessAwardDependent buildProcessAwardDependentRequest(RbpsRepository repo)  {

        AmendAwardDependencyInputVO       amendAwardDependencyInputVO = new AmendAwardDependencyInputVO();
        ProcessAwardDependent             request                     = new ProcessAwardDependent();
        List<AmendDependencyDecisionVO>   dependencyList              = getDependencyDecisionList(repo);

        amendAwardDependencyInputVO.getDependencyList().addAll(dependencyList);
        amendAwardDependencyInputVO.setAwardType(AWARD_TYPE);
        amendAwardDependencyInputVO.setBeneficiaryID(repo.getVeteran().getCorpParticipantId());
        amendAwardDependencyInputVO.setVeteranID(repo.getVeteran().getCorpParticipantId());
        amendAwardDependencyInputVO.setClaimID(repo.getVeteran().getClaim().getClaimId());
        amendAwardDependencyInputVO.setNetWorthOverLimit(repo.getVeteran().isNetWorthOverLimit());
        //task 009311 add procId to input
        amendAwardDependencyInputVO.setProcId(repo.getVnpProcId());

        setNegativeAwardAmountExpected( amendAwardDependencyInputVO, repo );

        request.setAmendAwardDependencyInput(amendAwardDependencyInputVO);

        if ( amendAwardDependencyInputVO.getDependencyList().isEmpty() ) {

            return null;
        }

        logRequest( request, repo );

        return request;
    }



    public void setNegativeAwardAmountExpected( AmendAwardDependencyInputVO		amendAwardDependencyInputVO, RbpsRepository repo ) {


        amendAwardDependencyInputVO.setNegativeAwardAmountExpected( "N" );

//    	if ( repo.getVeteran().getCurrentMarriage() != null ) {
//    		return;
//    	}

        if ( repo.getVeteran().getLatestPreviousMarriage() == null ) {
            return;
        }

        MarriageTerminationType	terminationType =  repo.getVeteran().getLatestPreviousMarriage().getTerminationType();

        if ( terminationType.equals( MarriageTerminationType.DIVORCE ) || terminationType.equals( MarriageTerminationType.DEATH ) ) {
            amendAwardDependencyInputVO.setNegativeAwardAmountExpected( "Y" );
        }
    }


    public void logRequest( final ProcessAwardDependent request, RbpsRepository repo ) {

        if ( request == null ) {

            logUtils.log( "No request built, no dependents to send.", repo );
            return;
        }

        if ( request.getAmendAwardDependencyInput() == null ) {

            logUtils.log( "No request built, no dependents to send.", repo );
            return;
        }

        if ( CollectionUtils.isEmpty( request.getAmendAwardDependencyInput().getDependencyList() ) ) {

            logUtils.log( "No request built, no dependents to send.", repo );
            return;
        }

//        logUtils.log( "Request to amend awards:\n" + utils.stringBuilder( request.getAmendAwardDependencyInput().getDependencyList() ) );
    }


    /**
     *      Build a List of AmendDependencyDecisionVO's
     *
     *      @return List<AmendDependencyDecisionVO>
     */
    private List<AmendDependencyDecisionVO> getDependencyDecisionList( RbpsRepository repo){

        List<AmendDependencyDecisionVO>     depList = new ArrayList<AmendDependencyDecisionVO>();
        AmendDependencyDecisionVO           depDecision;
        AmendDependencyDecisionVO           secondaryChildDecision;

        // add decision for spouse if applicable
        if (repo.getVeteran().getCurrentMarriage() != null) {

            depDecision = buildDependentDecision(repo.getVeteran().getCurrentMarriage().getMarriedTo(), repo);

            if (depDecision != null) {

                depList.add(depDecision);
            }
        }

        // add decision for spouse to be removed if applicable
        if (repo.getVeteran().getLatestPreviousMarriage() != null) {

            depDecision = buildDependentDecision(repo.getVeteran().getLatestPreviousMarriage().getMarriedTo(), repo);

            if (depDecision != null) {

                depList.add(depDecision);
            }
        }

        // add decision for child if applicable
        for (Child child : repo.getVeteran().getChildren()) {
            depDecision = buildDependentDecision(child, repo);

            // build primary decision for Child
            if (depDecision != null) {

                depList.add(depDecision);
            }

            // build secondary decision for Child
            secondaryChildDecision = buildSecondaryChildDecision(child, repo);
            if (secondaryChildDecision != null) {

                depList.add(secondaryChildDecision);
            }

            // build prior school child decision for Child
            depDecision = buildPriorSchoolChildDecision(child, repo);
            if (depDecision != null) {
                depList.add(depDecision);
            }
        }

//        logUtils.log( " *** RBPS: [ depList ]" );

        return depList;
    }


    /**
     *      Build a secondary AmendDependencyDecisionVO for a Child
     *      Near duplicate of buildDepDecision.
     *      @param Dependent
     *      @return AmendDependencyDecisionVO
     */
    private AmendDependencyDecisionVO buildSecondaryChildDecision(final Child child, RbpsRepository repo)  {

        return buildDependentDecisionBasedOnAward( child, child.getMinorSchoolChildAward(), repo );
    }

    /**
     *      Build a AmendDependencyDecisionVO for prior school term
     *      Near duplicate of buildDepDecision.
     *      @param Child
     *      @return AmendDependencyDecisionVO
     */
    private AmendDependencyDecisionVO buildPriorSchoolChildDecision(final Child child, RbpsRepository repo)  {

        return buildDependentDecisionBasedOnAward( child, child.getPriorSchoolChildAward(), repo );
    }

    /**
     *      Build an AmendDependencyDecisionVO for a Dependent
     *
     *      @param Dependent
     *      @return AmendDependencyDecisionVO
     */
    private AmendDependencyDecisionVO buildDependentDecision(final Dependent dependent, RbpsRepository repo) {

        return buildDependentDecisionBasedOnAward( dependent, dependent.getAward(), repo );
    }


    private AmendDependencyDecisionVO buildDependentDecisionBasedOnAward( final Dependent     dependent,
                                                                          final Award         award,
                                                                          RbpsRepository repo)  {

        if ( award == null ) {

            return null;
        }

        if ( ClaimProcessorHelper.missingNeededEventDate( award, repo ) ) {

            return null;
        }

        if ( award.getDependencyDecisionType() == null ) {

            return null;
        }

        if ( award.getDependencyStatusType() == null ) {

            return null;
        }

        AmendDependencyDecisionVO depDecision = new AmendDependencyDecisionVO();

        depDecision.setBirthdayDate(RbpsUtil.dateToXMLGregorianCalendar(SimpleDateUtils.truncateToDay( dependent.getBirthDate() )));

        Date eventDate = ClaimProcessorHelper.getEventDate( award, repo );
        if (award.getEndDate() != null) {

            depDecision.setDecisionEndDate( RbpsUtil.dateToXMLGregorianCalendar(SimpleDateUtils.truncateToDay( getEndDate( award ) )));
        }

        depDecision.setDependencyDecisionType(award.getDependencyDecisionType().getCode());
        depDecision.setDependencyStatusType(award.getDependencyStatusType().getCode());
        depDecision.setEventDate( RbpsUtil.dateToXMLGregorianCalendar( eventDate ) );
        depDecision.setFirstName(dependent.getFirstName());
        depDecision.setLastName(dependent.getLastName());
        depDecision.setPersonID(dependent.getCorpParticipantId());
        depDecision.setSocialSecurityNumber(dependent.getSsn());
        setOmnibusFlag( dependent, award, depDecision, eventDate, repo );

        depDecision.setHasIncome(dependent.hasIncome());
        return depDecision;
    }

    private void setOmnibusFlag(final Dependent dependent, final Award award, final AmendDependencyDecisionVO depDecision, final Date eventDate, RbpsRepository repo) {

        depDecision.setOmnibusFlag(OmnibusFlagHelper.getOmnibusFlag(repo.getVeteran(), award, eventDate));

        logUtils.log( String.format( "Setting omnibus flag for %s to %s.\n\n        Event date:                          %s\n        rating effective date:               %s",
                CommonUtils.getStandardLogName( dependent ),
                depDecision.getOmnibusFlag(),
                eventDate,
                repo.getVeteran().getRatingEffectiveDate() ), repo );
    }


    public static final Date getEndDate( final Award award ) {

        return SimpleDateUtils.truncateToDay( award.getEndDate() );
    }

}