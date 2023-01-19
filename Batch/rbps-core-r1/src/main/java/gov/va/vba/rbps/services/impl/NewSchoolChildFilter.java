/*
 * NewChildWithPreviousTermFilter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;

import java.util.Date;



public class NewSchoolChildFilter implements ChildFilter{

    private static Logger       logger      =   Logger.getLogger(NewSchoolChildFilter.class);

//    private CommonUtils         utils;
//    private SimpleDateUtils     dateUtils   =   new SimpleDateUtils();
    private LogUtils            logUtils    =   new LogUtils( logger, true );


    @Override
    public void filter( final RbpsRepository   repository, final Child child ) {

        if ( ! child.isNewSchoolChild() ) {

            return;
        }

        //evaluatePreviousTerm( repository, child );
    }


    private void evaluatePreviousTerm( final RbpsRepository repository, final Child  child ) {

        Education lastTerm      =   child.getLastTerm();

        if( ! isLastTermWithInOneYearOfDOC( repository, lastTerm ) ) {

            return;
        }

        throw new RbpsRuntimeException( exceptionMessage( child, repository ) );
    }


    private boolean isLastTermWithInOneYearOfDOC( final RbpsRepository repository, final Education lastTerm ) {

        if ( lastTerm == null ) {

            return false;
        }

        Date    dateOfClaim                 =   repository.getVeteran().getClaim().getReceivedDate();
        Date    lastTermStartDate           =   lastTerm.getCourseStudentStartDate();
        Date    lastTermStartDatePlus1Yr    =   SimpleDateUtils.addYearsToDate(1, lastTermStartDate );

        //      Danielle's email on 05-10-2012
        return SimpleDateUtils.isOnOrBetween( lastTermStartDate, lastTermStartDatePlus1Yr, dateOfClaim );
    }


    private String exceptionMessage( final Child child, final RbpsRepository repository) {

        logUtils.log( String.format( "Throwing exception since New School Child %s has previous term with in one year of Date of Claim ",
        		CommonUtils.getStandardLogName( child ) ), repository );

        return String.format( "New School Child %s has previous term with in one year of Date of Claim ", CommonUtils.getStandardLogName( child ) ) ;
    }
}
