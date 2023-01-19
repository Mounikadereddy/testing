/*
 * StepChildNotAlreadyOnAwardFilter
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;

//import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
//import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;

import java.util.Date;



/**
 *      Filters the following types of children:
 *
 *          * Step-school child not already on award
 *          * Already 18 on the date of claim
 *          * Minor child at marriage date
 */
public class NewStepChildNotAlreadyOnAwardFilter implements ChildFilter {

//    private static Logger       logger      =   Logger.getLogger(NewStepChildNotAlreadyOnAwardFilter.class);

//    private CommonUtils         utils       =   new CommonUtils( new RbpsRepository() );
//    private LogUtils            logUtils    =   new LogUtils( logger, true );
//    private SimpleDateUtils     dateUtils   =   new SimpleDateUtils();


    @Override
    public void filter( final RbpsRepository   repo, final Child child ) {

        if ( ! isStepChild( child ) ) {

//            logUtils.log( String.format( "%s is a %s child and not a step child, don't need to filter.",
//            		CommonUtils.getStandardLogName( child ),
//                                         child.getChildType().getValue() ) );
            return;
        }

        if ( child.isOnCurrentAward() ) {

//            logUtils.log( String.format( "%s is already on award, don't need to filter.", CommonUtils.getStandardLogName( child )));
            return;
        }

        if ( ! isAge18AtDateOfClaim( repo, child ) ) {

////            logUtils.log( String.format( "%s is a NOT 18 at date of claim, not triggering exception",
//            		CommonUtils.getStandardLogName( child ) ) );
            return;
        }

        if ( ! isMinorChildAtDateOfMarriage( repo, child ) ) {

//            logUtils.log( String.format( "%s is a NOT minor child at date of marriage, not triggering exception",
//            		CommonUtils.getStandardLogName( child ) ) );
            return;
        }

        throw new RbpsRuntimeException( exceptionMessage( child ) );
    }


    private boolean isAge18AtDateOfClaim( final RbpsRepository repo, final Child  child ) {

        int     age     = SimpleDateUtils.getAgeOn( child, repo.getVeteran().getClaim().getReceivedDate() );

        return ! isMinorChild( age );
    }


    private boolean isMinorChildAtDateOfMarriage( final RbpsRepository repo, final Child  child ) {

//        NullSafeGetter  getter      = new NullSafeGetter();
        Date            marriedDate = (Date) NullSafeGetter.getAttribute( repo, "veteran.currentMarriage.startDate" );

        if ( marriedDate == null ) {

            return false;
        }

        int             age         = SimpleDateUtils.getAgeOn( child, marriedDate );

        return isMinorChild( age );
    }


    public boolean isStepChild( final Child child ) {

        return child.getChildType() == ChildType.STEPCHILD;
    }


    public boolean isMinorChild( final int age ) {

        return age > -1 && age < 18;
    }


    private String exceptionMessage( final Child child) {

        String msg = String.format( "Step child %s is not already on award, is 18 at date of claim, and was a minor on date of marriage",
        							CommonUtils.getStandardLogName( child ) );

//        logUtils.log( msg );
        return msg;
    }
}
