/*
 * DependentsFilter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.DependencyDecisionResultVO;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;


/**
 *      Filters award summaries so that we get a list of denial, grants, and removals.
 *      Since we start out with grants and denials only, we have to decide
 *      which denials are removals.
 */
public class DependentsFilter {


    private static Logger logger = Logger.getLogger(DependentsFilter.class);

    private CommonUtils                             utils;
    private List<DependencyDecisionResultVO>        approvals           = new ArrayList<DependencyDecisionResultVO>();
    private List<DependencyDecisionResultVO>        denials             = new ArrayList<DependencyDecisionResultVO>();
    private boolean                                 logit               = false;



    public List<DependencyDecisionResultVO> filter( final List<DependencyDecisionResultVO>     dependents ) {

        log( RbpsConstants.BAR_FORMAT + "\nDependentsFilter:\ninput:\n" + CommonUtils.stringBuilder( dependents ) );

        if ( CollectionUtils.isEmpty( dependents ) ) {

            return new ArrayList<DependencyDecisionResultVO>();
        }

        List<DependencyDecisionResultVO> tmp = processDependents( dependents );

        log( RbpsConstants.BAR_FORMAT + "\nDependentsFilter:\napprovals:\n" + CommonUtils.stringBuilder( approvals ) );
//             + "\n" + RbpsConstants.BAR_FORMAT + "\ndenials:\n" + utils.stringBuilder( denials ) );

        return tmp;
    }


    private List<DependencyDecisionResultVO> processDependents( final List<DependencyDecisionResultVO>     dependents ) {

        for ( DependencyDecisionResultVO dependent : dependents ) {

            addGrantToApprovals( dependent );
            addRemovalToApprovals( dependent );
        }

        return removeDenialsWithoutClaimId( dependents );
//        log( "DependentsFilter:\nProcessed dependents:\n" + utils.stringBuilder( dependents ) );
    }


    private List<DependencyDecisionResultVO> removeDenialsWithoutClaimId( final List<DependencyDecisionResultVO> dependents ) {

        List<DependencyDecisionResultVO>        nonDiscarded = new ArrayList<DependencyDecisionResultVO>();

        for ( DependencyDecisionResultVO    dependent : dependents ) {

            if ( isDenial( dependent ) && ! hasClaim( dependent ) ) {

                log( "DependentsFilter:\ndiscarding dependent: " + dependent );
                continue;
            }

            nonDiscarded.add( dependent );
        }

        return nonDiscarded;
    }


    private void addGrantToApprovals( final DependencyDecisionResultVO             dependent ) {

        if ( ! isGrant( dependent ) ) {

            return;
        }

        approvals.add( dependent );
    }


    private void addRemovalToApprovals( final DependencyDecisionResultVO           dependent ) {

        if ( isGrant( dependent ) ) {

            return;
        }

        if ( ! approvalsContainsPreviousGrantForDependent( dependent ) ) {

            return;
        }
        if(dependent.getDependencyDecisionType().toUpperCase().equals("RATNHEL") ||
        		dependent.getDependencyDecisionType().toUpperCase().equals("O18NISOH")){
        	return;
        			
        }

        String removalString = StringUtils.capitalize( ApprovalType.REMOVAL.toString().toLowerCase() );
        
        if ( dependent.getGrantDenial().toUpperCase().contains( "No matching Award Line/Reason".toUpperCase() )) {
        	
        	removalString = removalString + " - No matching Award Line/Reason";
        }
        
        dependent.setGrantDenial( removalString );
        
        approvals.add( dependent );
    }


    private boolean approvalsContainsPreviousGrantForDependent( final DependencyDecisionResultVO inQuestion ) {

        for ( DependencyDecisionResultVO dependent : approvals ) {

            if ( ! isGrant( dependent ) ) {

                continue;
            }

            if ( dependent.getFullName() == null ) {

                continue;
            }

            if ( ! dependent.getFullName().equals( inQuestion.getFullName() ) ) {

                continue;
            }

            return true;
        }

        return false;
    }


//    private void addToDenials( final DependencyDecisionResultVO dependent ) {
//
//        if ( isGrantOrRemoval( dependent ) ) {
//
//            return;
//        }
//
//        denials.add( dependent );
//    }


    public boolean isGrant( final DependencyDecisionResultVO dependent ) {

        validatePresenceOfGrantOrDenial( dependent );

        return dependent.getGrantDenial().toUpperCase().contains( ApprovalType.GRANT.toString() );
//        return dependent.getGrantDenial().equalsIgnoreCase( ApprovalType.GRANT.toString() );
    }


    public boolean isRemoval( final DependencyDecisionResultVO dependent ) {

        validatePresenceOfGrantOrDenial( dependent );

        return dependent.getGrantDenial().equalsIgnoreCase( ApprovalType.REMOVAL.toString() );
    }


    public boolean isDenial( final DependencyDecisionResultVO dependent ) {

        validatePresenceOfGrantOrDenial( dependent );

        return dependent.getGrantDenial().toUpperCase().contains( ApprovalType.DENIAL.toString() );
//        return dependent.getGrantDenial().equalsIgnoreCase( ApprovalType.DENIAL.toString() );
    }


    public boolean isGrantOrRemoval( final DependencyDecisionResultVO dependent ) {

        return isGrant( dependent ) || isRemoval( dependent );
    }


    private void validatePresenceOfGrantOrDenial( final DependencyDecisionResultVO dependent ) {

        if ( dependent.getGrantDenial() == null ) {

            dependent.setGrantDenial( "Denial" );
//            throw new IllegalArgumentException( String.format( "Dependent >%s< with participantId >%d< does not have a grant or denial.",
//                                                               dependent.getFullName(),
//                                                               dependent.getPersonID() ) );
        }
    }


    private boolean hasClaim( final DependencyDecisionResultVO dependent ) {

        return dependent.getClaimID() != null;
    }


    private void log( final String    msg ) {

        if ( ! logit ) {

            return;
        }

        utils.log( logger, RbpsConstants.BAR_FORMAT + "\n" + msg );
    }




    public boolean getHasApprovals() {

        return ! approvals.isEmpty();
    }


    public boolean getHasDenials() {

        return ! denials.isEmpty();
    }


    public List<DependencyDecisionResultVO> getApprovals() {

        return approvals;
    }


    public List<DependencyDecisionResultVO> getDenials() {

        return denials;
    }
}
