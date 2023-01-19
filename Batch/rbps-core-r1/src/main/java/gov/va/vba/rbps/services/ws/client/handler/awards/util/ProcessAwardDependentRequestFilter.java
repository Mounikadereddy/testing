/*
 * ProcessAwardDependentRequestFilter
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendDependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependent;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;


/*
 * This class filters the request object for ProcessAwardDependent based on the following criteria.
 *
 *      If Granted and Decision Type is SCHATTB, the dependent  will be added to the request.
 *
 *      If Granted and Decision Type not SCHATTB and if Decision Type and Dependency Status Type match
 *      with an object in the DependenyDecisionAward list, then the dependent will be removed.
 *
 *      If the event date of the request object falls between the event date for grant and removal, then the object will be removed.
*/
public class ProcessAwardDependentRequestFilter {

    private static Logger                               logger          = Logger.getLogger(ProcessAwardDependentRequestFilter.class);

    private LogUtils                                    logUtils        = new LogUtils( logger, true );

    private DependencyDecisionByAwardProducer           dependencyDecisionByAwardProducer;
    private ProcessAwardDependentRequestBuilder         processAwardDependentRequestBuilder;



    public ProcessAwardDependent getFilteredProcessAwardDependentRequest( final RbpsRepository repository) {

        ProcessAwardDependent awardDependentRequest = processAwardDependentRequestBuilder.buildProcessAwardDependentRequest( repository );

        buildNewAwardDependentRequestList( repository, awardDependentRequest );

        throwExceptionIfNoDependent( repository, awardDependentRequest );

        return awardDependentRequest;
    }


    private void buildNewAwardDependentRequestList( final RbpsRepository                    repository,
                                                    final ProcessAwardDependent             awardDependentRequest ) {

        ProcessAwardDependentFacade requestFacade = new ProcessAwardDependentFacade( awardDependentRequest );

        if ( requestFacade.noAwardsRequestProvided() ) {

            logUtils.log( "awardDependentRequest is null", repository );
            return;
        }

        List <AmendDependencyDecisionVO> requestDependencyList  = requestFacade.getCurrentAwardsDependentRequests();
        List <AmendDependencyDecisionVO> newDependencyList;

        newDependencyList = buildNewAwardDependentRequestForEachOldRequest( repository, requestDependencyList );

        requestFacade.setNewAwardsDependentRequests( newDependencyList );
    }


    public List <AmendDependencyDecisionVO> buildNewAwardDependentRequestForEachOldRequest( final RbpsRepository                    repository,
                                                                                            final List<AmendDependencyDecisionVO>   requestDependencyList ) {

        List <AmendDependencyDecisionVO> newDependencyList      = new ArrayList<AmendDependencyDecisionVO>();

        for ( AmendDependencyDecisionVO requestDependencyDecision : requestDependencyList ) {

            buildNewAwardDependentRequest( repository, newDependencyList, requestDependencyDecision );
        }

        return newDependencyList;
    }


    private void buildNewAwardDependentRequest( final RbpsRepository                        repository,
                                                final List <AmendDependencyDecisionVO>      newDependencyList,
                                                final AmendDependencyDecisionVO             requestDependencyDecision ) {

        DecisionDetailsProcessor detailsProcessor = new DecisionDetailsProcessor();

        List<DependencyDecisionVO>  dependencyDecisionByIdList = getDependencyDecisionList( repository,
                                                                                            requestDependencyDecision );

        logUtils.log( String.format( "List from map by participant Id: >%s< :\n", requestDependencyDecision.getPersonID() )
                             + CommonUtils.stringBuilder( dependencyDecisionByIdList ), repository );

        List<DecisionDetails> decisionDetailsList = detailsProcessor.createDecisionDetailsListForDependent( dependencyDecisionByIdList, repository );

        addRequestToDependencyListForDependent( requestDependencyDecision, newDependencyList, decisionDetailsList, repository );
    }


    private void addRequestToDependencyListForDependent( final AmendDependencyDecisionVO               requestDependencyDecision,
                                                         final List <AmendDependencyDecisionVO>        newDependencyList,
                                                         final List<DecisionDetails>                   decisionDetailsList,
                                                         final RbpsRepository                          repository ) {
        boolean okAddFlag = true;

        for ( DecisionDetails decisionDetail : decisionDetailsList ) {

//            Date eventDate              = SimpleDateUtils.xmlGregorianCalendarToDay( requestDependencyDecision.getEventDate() );
//            Date omnibussedEventDate    = SimpleDateUtils.getOmnibuzzedDate( eventDate );

            if ( doesSameDecisionDetailWithInDateRange( requestDependencyDecision,
                                                        decisionDetail ) ) {

                okAddFlag = false;
                break;
            }
        }

        if ( okAddFlag ) {

            newDependencyList.add( requestDependencyDecision );
            logUtils.log( "dependency decision added to the filtered request list - so it will be sent to amend awards\n"
                    + CommonUtils.stringBuilder( requestDependencyDecision ), repository );
        }
        else {

            logUtils.log( "dependency decision NOT added to the filtered request list - so it will NOT be sent to amend awards\n"
                 + CommonUtils.stringBuilder( requestDependencyDecision ), repository );
        }
    }


    public boolean doesSameDecisionDetailWithInDateRange( final AmendDependencyDecisionVO       requestDependencyDecision,
                                                          final DecisionDetails                 decisionDetail ) {

        return decisionDetail.isSameTypeAndWithinDateRange( requestDependencyDecision );
    }


    private List<DependencyDecisionVO> getDependencyDecisionList( final RbpsRepository repository,
                                                                  final AmendDependencyDecisionVO requestDependencyDecision) {

        return dependencyDecisionByAwardProducer.getDependencyDecisionListForDependent( repository,
                                                                                        repository.getVeteran().getCorpParticipantId(),
                                                                                        requestDependencyDecision.getPersonID() );
    }


    public void throwExceptionIfNoDependent( final RbpsRepository              repository,
                                             final ProcessAwardDependent       awardDependentRequest ) {

        if ( hasDependent( awardDependentRequest ) ) {

            return;
        }

        throwExceptionForNoDependent( repository );
    }


    private boolean hasDependent( final ProcessAwardDependent       awardDependentRequest ) {

        if ( awardDependentRequest == null ) {

            return false;
        }

        if ( awardDependentRequest.getAmendAwardDependencyInput() == null ) {

            return false;
        }

        List <AmendDependencyDecisionVO> dependencyList = awardDependentRequest.getAmendAwardDependencyInput().getDependencyList();

        if ( CollectionUtils.isEmpty( dependencyList ) ) {

            return false;
        }

        return true;
    }


    private void throwExceptionForNoDependent( final RbpsRepository repository ) {

        logUtils.log( "No dependents to send to awards, returning.", repository );

        throw new RbpsRuntimeException( "Auto Dependency Processing Reject Reason: No dependents to send to awards.");
    }


    public void setProcessAwardDependentRequestBuilder( final ProcessAwardDependentRequestBuilder processAwardDependentRequestBuilder ) {

        this.processAwardDependentRequestBuilder = processAwardDependentRequestBuilder;
    }

    public void setDependencyDecisionByAwardProducer( final DependencyDecisionByAwardProducer dependencyDecisionByAwardProducer ) {

        this.dependencyDecisionByAwardProducer = dependencyDecisionByAwardProducer;
    }
}
