/*
 * DecisionDetailsProcessor
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




/**
 *      Creates decision details list and makes sure they are grants/denials,
 *      that they all have event dates, etc.
 */
public class DecisionDetailsProcessor {

    private static Logger                               logger          = Logger.getLogger(DecisionDetailsProcessor.class);

    private LogUtils                        logUtils        = new LogUtils( logger, true );
    private DependencyDecisionUtils         decisionUtils   = new DependencyDecisionUtils();



    public List<DecisionDetails> createDecisionDetailsListForDependent( final List<DependencyDecisionVO> dependencyDecisionByIdList, final RbpsRepository repository ) {

        sortDependencyDecisionList( dependencyDecisionByIdList );

        List<DecisionDetails>   decisionDetailsList     = new ArrayList<DecisionDetails>();
        DecisionDetails         previousDetails         = null;

        for ( DependencyDecisionVO dependencyDecision   :  dependencyDecisionByIdList ) {

            if ( decisionUtils.isGrant( dependencyDecision ) ) {

                previousDetails = handleGrantDecision( decisionDetailsList,
                                                       dependencyDecision,
                                                       previousDetails,
                                                       repository );
            }
            else {

                previousDetails = handleDenialDecision( decisionDetailsList,
                                                        dependencyDecision,
                                                        previousDetails,
                                                        repository );
            }
        }

        addRemainingDetails( decisionDetailsList, previousDetails );
        makeSureAllDecisionsHaveEndDate( decisionDetailsList );

        return decisionDetailsList;
    }


    private DecisionDetails handleGrantDecision( final List<DecisionDetails>    decisionDetailsList,
                                                 final DependencyDecisionVO     dependencyDecision,
                                                 final DecisionDetails          previousDetails,
                                                 final RbpsRepository 			repository ) {

        //      Sometimes you might have two grants in a row.
        //      For example, if you have an EMC (eligible minor child)
        //      and then a SCHATTB (school attendance begins),
        //      there's no termination T18 (turns 18) for the EMC.
        //      Awards actually removes that because apparently it's
        //      ugly seeing a termination and a grant on the same day.
        //      My feeling is, "too bad, so sad".   Or, if it's confusing,
        //      let the gui take care of it.  The database data needs to be
        //      there and consistent.
        //
        if ( previousDetails != null ) {

            previousDetails.setComputedEventEndDate();
            decisionDetailsList.add( previousDetails );
        }

        DecisionDetails decisionDetails = new DecisionDetails();
        decisionDetails.populateDecisionDetails( dependencyDecision );

        logUtils.log( String.format( "Built grant details from:\n%s\nto produce\n%s",
        		CommonUtils.stringBuilder( dependencyDecision ),
        		CommonUtils.stringBuilder( decisionDetails ) ), repository );

        return decisionDetails;
    }


    private DecisionDetails handleDenialDecision( final List<DecisionDetails>    decisionDetailsList,
                                                  final DependencyDecisionVO     dependencyDecision,
                                                  final DecisionDetails          previousDetails,
                                                  final RbpsRepository 			repository ) {

        logUtils.log( "Building denial details from:\n" + CommonUtils.stringBuilder( dependencyDecision ), repository );

        if ( previousDetails != null ) {

            previousDetails.setEventEndDate( dependencyDecision );
            decisionDetailsList.add( previousDetails );

            return null;
        }

        logUtils.log( "Denial without a Grant, not adding to the list, not building dependency decision\n"
                      + CommonUtils.stringBuilder( dependencyDecision ), repository );

        return null;
    }


    /**
     *      When you have an odd number of dependency decisions, the last one will get
     *      left off if we don't add it.  It gets left off because there's no matching
     *      denial and it's in the denial processing where the decision gets added
     *      to the list.
     */
    public void addRemainingDetails( final List<DecisionDetails>    decisionDetailsList,
                                     final DecisionDetails          previousDetails ) {

        if ( previousDetails == null ) {

            return;
        }

        decisionDetailsList.add( previousDetails );
    }


    private void makeSureAllDecisionsHaveEndDate( final List<DecisionDetails> decisionDetailsList ) {

        for ( DecisionDetails decisionDetails : decisionDetailsList ) {

            decisionDetails.setComputedEventEndDate();
        }
    }

    public void sortDependencyDecisionList( final List<DependencyDecisionVO> dependencyDecisionList ) {

        Collections.sort( dependencyDecisionList, new DependencyDecisionComparator() );
    }
}
