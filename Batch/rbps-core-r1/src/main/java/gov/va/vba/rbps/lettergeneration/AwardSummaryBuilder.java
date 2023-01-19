/*
 * AwardSummaryBuilder.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.logging.Logger;
//import gov.va.vba.rbps.coreframework.util.CommonUtils;
//import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.vo.RelationshipType;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardLineSummaryVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardReasonSeqNbrVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.DependencyDecisionResultVO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;



/**
 *      This class is used to build rows in the "Your award amount" table in
 *      the approve and approve_deny notification letters.
 */
public class AwardSummaryBuilder {

    private static Logger logger = Logger.getLogger(AwardSummaryBuilder.class);

//    private LogUtils                logUtils    = new LogUtils( logger, true );
    private ReasonTranslator        translator  = new ReasonTranslator();
//    private boolean                 logit       = true;





    public List<AwardSummary> buildSummaryList( final AwardLineSummaryVO                  lineSummary,
                                                final List<DependencyDecisionResultVO>    dependentInfoList ) {

//        logUtils.log( String.format( "Building an award summary from: \n%s\n%s\n",
//                            utils.stringBuilder( lineSummary.getAwardLineReasons() ),
//                            utils.stringBuilder( dependentInfoList ) ) );

        List<AwardSummary>                      summaries                   = new ArrayList<AwardSummary>();
        
//        List<DependencyDecisionResultVO>        grantOrRemovalDependents    = new ArrayList<DependencyDecisionResultVO>();
//        List<DependencyDecisionResultVO>        denialDependents            = new ArrayList<DependencyDecisionResultVO>();
//        filterDependents( dependentInfoList, grantOrRemovalDependents, denialDependents );

        AwardSummary summary = buildSummary( lineSummary, dependentInfoList );
        summaries.addAll( summary.summaryToList() );

        return summaries;
    }


//    private void filterDependents( final List<DependencyDecisionResultVO> dependentInfoList,
//                                   final List<DependencyDecisionResultVO> grantOrRemovalDependents,
//                                   final List<DependencyDecisionResultVO> denialDependents ) {
//
//        // TODO Auto-generated method stub
//
//    }

    private AwardSummary buildSummary( final AwardLineSummaryVO                 lineSummary,
                                       final List<DependencyDecisionResultVO>   dependentInfoList ) {

        AwardSummary    summary = new AwardSummary();

        summary.setTotalVABenefit( lineSummary.getGrossAmount() );
        summary.setAmountWithheld( lineSummary.getTotalWithholdingAmount() );
        summary.setAmountPaid( lineSummary.getNetAmount() );
        setPaymentChangeDate( summary, lineSummary, dependentInfoList );
        summary.setReasons( constructReasons( lineSummary, dependentInfoList, summary ) );

//        logUtils.log( "Built award summary: " + utils.stringBuilder( summary ) );
        return summary;
    }


    private List<AwardReason> constructReasons( final AwardLineSummaryVO                    lineSummary,
                                                final List<DependencyDecisionResultVO>      dependentInfoList,
                                                final AwardSummary                          summary ) {

        List<AwardReason>    reasons = new ArrayList<AwardReason>();

        for ( AwardReasonSeqNbrVO reason : lineSummary.getAwardLineReasons() ) {

            for ( DependencyDecisionResultVO dependent : dependentsForReason( reason, dependentInfoList ) ) {

                AwardReason newReason = new AwardReason();

                newReason.setSequenceNumber( reason.getReasonSequenceNumber() );
                newReason.setTranslator( translator );
                assignAwardLineReasonType( reason, newReason );
                assignDependentInfo( reason, dependent, newReason ,dependentInfoList);

//                logUtils.log( "Built award reason: " + newReason );
                reasons.add( newReason );
            }
        }

        return reasons;
    }


    private List<DependencyDecisionResultVO> dependentsForReason( final AwardReasonSeqNbrVO                   reason,
                                                                  final List<DependencyDecisionResultVO>      dependentInfoList ) {

        List<DependencyDecisionResultVO>    results = new ArrayList<DependencyDecisionResultVO>();

        gatherMatchingReasons( reason, dependentInfoList, results );
        createListForReasonWithNoDependents( results );

        return results;
    }


    private void gatherMatchingReasons( final AwardReasonSeqNbrVO                   reason,
                                        final List<DependencyDecisionResultVO>      dependentInfoList,
                                        final List<DependencyDecisionResultVO>      results ) {

        for ( DependencyDecisionResultVO dependent : dependentInfoList ) {

            if ( ! dependent.getAlReasonSequenceNumber().equals( reason.getReasonSequenceNumber() ) ) {

                continue;
            }

            results.add( dependent );
        }
    }


    private void createListForReasonWithNoDependents( final List<DependencyDecisionResultVO> results ) {

        if ( ! CollectionUtils.isEmpty( results ) ) {

            return;
        }

        //
        //      If there are no matching dependents, create one - null object
        //      pattern.  Not sure it will work.  Or is necessary.
        //      But in the loop creating reasons, if we don't have this null object
        //      then a reason with no dependents, such as "compensation rating adjustment"
        //      won't get added to the award summary.  This way it will, but
        //      with no dependent name or such.
        //
        DependencyDecisionResultVO dependent = new DependencyDecisionResultVO();
        results.add( dependent );
    }


    private void assignAwardLineReasonType( final AwardReasonSeqNbrVO   reason,
                                            final AwardReason           newReason ) {

        newReason.setAwardLineReasonType( reason.getAwardLineReasonType() );
        newReason.setAwardLineReasonTypeDescription( reason.getAwardLineReasonTypeDescription() );
    }


    private void assignDependentInfo( final AwardReasonSeqNbrVO             reason,
                                      final DependencyDecisionResultVO      dependentInfo,
                                      final AwardReason                     newReason,
                                      List<DependencyDecisionResultVO>      dependentInfoList ) {

        if ( dependentInfo == null ) {

            return;
        }

        if ( dependentInfo.getAlReasonSequenceNumber() != reason.getReasonSequenceNumber() ) {

            return;
        }
        newReason.setEventDate(dependentInfo.getEventDate().toGregorianCalendar().getTime());
        newReason.setSequenceNumber( dependentInfo.getAlReasonSequenceNumber() );
        newReason.setDependentName( dependentInfo.getFirstName() );
        newReason.setDependencyDecisionType( dependentInfo.getDependencyDecisionType() );
        newReason.setDependencyDecisionTypeDescription( dependentInfo.getDependencyDecisionTypeDescription() );
        newReason.setEffectiveDate( dependentInfo.getAwardEffectiveDate().toGregorianCalendar().getTime() );
        newReason.setFirstName( dependentInfo.getFirstName() );
        newReason.setFullName( dependentInfo.getFullName() );
        newReason.setChild( isChild( dependentInfo ) );
        String grantDenial	= getGrantDenialValue( dependentInfo.getGrantDenial().toUpperCase() );
        newReason.setApprovalType( ApprovalType.valueOf( grantDenial ) );
        newReason.setClaimId( dependentInfo.getClaimID() );
        newReason.setCorpParticipantId( dependentInfo.getPersonID() );
        newReason.setCurrentClaim(dependentInfo.getCurrentClaim());
        newReason.setDepndentPersonID(dependentInfo.getPersonID());
      

        logger.debug(dependentInfo.getFirstName() + " isCurrentClaim: " + dependentInfo.getCurrentClaim());
    }


    private void setPaymentChangeDate( final AwardSummary                       summary,
                                       final AwardLineSummaryVO                 lineSummary,
                                       final List<DependencyDecisionResultVO>   dependentInfoList ) {

//        if ( ! CollectionUtils.isEmpty( dependentInfoList ) ) {
//
//            summary.setPaymentChangeDate( utils.xmlGregorianCalendarToDay( dependentInfoList.get(0).getEventDate() ) );
//
//            return;
//        }

        summary.setPaymentChangeDate( SimpleDateUtils.xmlGregorianCalendarToDay( lineSummary.getEffectiveDate() ) );
    }


    private boolean isChild( final DependencyDecisionResultVO dependentInfo ) {

        return dependentInfo.getRelationshipTypeDescription().equalsIgnoreCase( RelationshipType.CHILD.getValue() );
    }


    private String getGrantDenialValue( final String grantDenialStr ) {
    	
    	if ( grantDenialStr.toUpperCase().contains( "GRANT" ) ) {
    		
    		return "GRANT";
    	}
    	
    	if ( grantDenialStr.toUpperCase().contains( "DENIAL" ) ) {
    		
    		return "DENIAL";
    	}
    	
    	if ( grantDenialStr.toUpperCase().contains( "REMOVAL" ) ) {
    		
    		return "REMOVAL";
    	}
    	return "";
    }
    
    
//    private boolean isGrant( final DependencyDecisionResultVO dependentInfo ) {
//
//        return dependentInfo.getGrantDenial().equals( "Grant" );
//    }
}
