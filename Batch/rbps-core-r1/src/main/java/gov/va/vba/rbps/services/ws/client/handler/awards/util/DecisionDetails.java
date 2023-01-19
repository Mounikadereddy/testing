/*
 * DecisionDetails
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;


import static gov.va.vba.rbps.coreframework.util.RbpsConstants.BAR_FORMAT;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendDependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;



/**
 *      A simple class to store info about a grant/termination range
 *      so that RBPS can match up a particular decision from rules
 *      against a decision that's already been awarded by "amend awards".
 */
public class DecisionDetails {

    private static Logger   logger      = Logger.getLogger(DecisionDetails.class);

//    private CommonUtils     utils;
//    private LogUtils        logUtils    = new LogUtils( logger, true );
//    private SimpleDateUtils SimpleDateUtils   = new SimpleDateUtils();

    private Date            birthDate;
    private Date            decisionDate;
    private Date            eventStartDate;
    private Date            eventEndDate;
    private String          startDecisionType;
    private String          endDecisionType;
    private String          startStatusType;
    private String          endStatusType;




    public void populateDecisionDetails( final DependencyDecisionVO dependencyDecision ) {

        Date decisionDate     = SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecision.getDecisionDate() );
        Date eventDate        = SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecision.getEventDate() );
        Date birthDate        = SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecision.getBirthdayDate() );

        setBirthDate( birthDate );
        setDecisionDate( SimpleDateUtils.truncateToDay( decisionDate ) );
        setEventStartDate( SimpleDateUtils.truncateToDay( eventDate ) );
        setStartDecisionType( dependencyDecision.getDependencyDecisionType() );
        setStartStatusType( dependencyDecision.getDependencyStatusType() );
    }




    public void setComputedEventEndDate() {

        if ( getEventEndDate() != null ) {

            return;
        }

        if ( isMinorChildDecision() ) {

            setEventEndDate( SimpleDateUtils.truncateToDay( SimpleDateUtils.addYearsToDate( 18, getBirthDate() ) ) );
        }
        else if ( isDisabledChildDecision() ) {

            setEventEndDate( SimpleDateUtils.truncateToDay( SimpleDateUtils.addYearsToDate( 200, getBirthDate() ) ) );
        }
        else if ( isDependencyEstablished() ) {

            setEventEndDate( SimpleDateUtils.truncateToDay( SimpleDateUtils.addYearsToDate( 200, getBirthDate() ) ) );
        }
    }


    public boolean isSameTypeAndWithinDateRange( final AmendDependencyDecisionVO       requestDependencyDecision ) {
        boolean tmp = isSameTypeOfDecision( requestDependencyDecision )
                           && withinDateRange( requestDependencyDecision );
        
//        logUtils.log( String.format( "This decision detail %s within date range - %s overlap\n%s",
//                                     (tmp ? "is"    : "is NOT" ),
//                                     (tmp ? "does"  : "does NOT" ),
//                                     this ) );

        return tmp;
    }


    public boolean withinDateRange( final AmendDependencyDecisionVO         requestDependencyDecision ) {

    	CommonUtils.log( logger,"\n" + BAR_FORMAT + "BEGIN withinDateRange" + BAR_FORMAT + "\n");
        if ( isMinorToSchoolChild( requestDependencyDecision )
                || isContinuousSchoolCoverage( requestDependencyDecision ) ) {

            return withinDateRangeForContinuousCoverage( requestDependencyDecision );
        }

    	String decDetails = "withinDateRange:: EventDate: " + requestDependencyDecision == null ? "requestDependencyDecision == null" : 
    		requestDependencyDecision.getEventDate() == null ? "EventDate is null" : requestDependencyDecision.getEventDate().toString();

    	CommonUtils.log( logger,"\n" + BAR_FORMAT + decDetails + BAR_FORMAT + "\n");

        Date    omnibussedEventDate = SimpleDateUtils.getOmnibuzzedDate( SimpleDateUtils.xmlGregorianCalendarToDay( requestDependencyDecision.getEventDate() ) );
				
        decDetails = "withinDateRange:: omnibussedEventDate: " +omnibussedEventDate == null? "omnibussedEventDate is null" :omnibussedEventDate.toString();
        CommonUtils.log( logger,"\n" + BAR_FORMAT + decDetails + BAR_FORMAT + "\n");
        
        decDetails = "withinDateRange:: EventStartDate: " + getEventStartDate() == null? "EventStartDate is null" : getEventStartDate().toString();
        CommonUtils.log( logger,"\n" + BAR_FORMAT + decDetails + BAR_FORMAT + "\n");
        
        decDetails = "withinDateRange:: EventEndDate: " + getEventStartDate() == null? "EventEndDate is null" :getEventEndDate().toString() ;		
        CommonUtils.log( logger,"\n" + BAR_FORMAT + decDetails + BAR_FORMAT + "\n");      		      		
        
        boolean tmp = SimpleDateUtils.isOnOrBetween( SimpleDateUtils.truncateToDay( getEventStartDate() ),
        		SimpleDateUtils.truncateToDay( getEventEndDate() ),
        		SimpleDateUtils.truncateToDay( omnibussedEventDate ) );

//        logUtils.log( String.format( "\n\tIs %s <= %s <= %s? %s",
//        					SimpleDateUtils.standardShortLogDayFormat( getEventStartDate() ),
//        					SimpleDateUtils.standardShortLogDayFormat( omnibussedEventDate ),
//        					SimpleDateUtils.standardShortLogDayFormat( getEventEndDate() ),
//                                     ("" + tmp).toUpperCase() ) );

        CommonUtils.log( logger,"\n" + BAR_FORMAT + "END withinDateRange" + BAR_FORMAT + "\n");
        return tmp;
    }




    public boolean withinDateRangeForContinuousCoverage(  final AmendDependencyDecisionVO         requestDependencyDecision  ) {

    	CommonUtils.log( logger,"\n" + BAR_FORMAT + "BEGIN withinDateRangeForContinuousCoverage" + BAR_FORMAT + "\n");
    	
    	if (requestDependencyDecision == null){
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage:: requestDependencyDecision is null" + BAR_FORMAT + "\n");
    		return false;
    	}
    	if (getEventStartDate() == null){
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage:: EventStartDate is null" + BAR_FORMAT + "\n");
    		return false;
    	}else{
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage:: EventStartDate: " + getEventStartDate().toString() + BAR_FORMAT + "\n");
    	}
    	if (getEventEndDate() == null){
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage:: EventEndDate is null" + BAR_FORMAT + "\n");
    		 return false;
    	}
    	else{
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage:: EventEndDate: " + getEventEndDate().toString() + BAR_FORMAT + "\n");

    	}
    	if (requestDependencyDecision.getEventDate() == null){
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage:: requestDependencyDecision.EventDate is null" + BAR_FORMAT + "\n");
    		return false;
    	}
    	else{
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage:: EventDate: " + requestDependencyDecision.getEventDate().toString() + BAR_FORMAT + "\n");

    	}
    	Date    omnibussedEventDate = SimpleDateUtils.getOmnibuzzedDate( SimpleDateUtils.xmlGregorianCalendarToDay( requestDependencyDecision.getEventDate() ) );

    	if(omnibussedEventDate ==null){
    		
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage:: omnibussedEventDate is null" + BAR_FORMAT + "\n");
    		return false;
    	}
    	else{
    		CommonUtils.log( logger,"\n" + BAR_FORMAT + "withinDateRangeForContinuousCoverage::omnibussedEventDate: " +omnibussedEventDate.toString() + BAR_FORMAT + "\n");

    	}
        boolean tmp = SimpleDateUtils.isOnOrBetweenForOmnibussed( SimpleDateUtils.truncateToDay( getEventStartDate() ),
                                                            SimpleDateUtils.truncateToDay( getEventEndDate() ),
                                                            SimpleDateUtils.truncateToDay( omnibussedEventDate ) );

//        logUtils.log( String.format( "\n\tIs %s <= %s < %s? %s",
//                                     SimpleDateUtils.standardShortLogDayFormat( getEventStartDate() ),
//                                     SimpleDateUtils.standardShortLogDayFormat( omnibussedEventDate ),
//                                     SimpleDateUtils.standardShortLogDayFormat( getEventEndDate() ),
//                                     ("" + tmp).toUpperCase() ) );

        CommonUtils.log( logger,"\n" + BAR_FORMAT + "END withinDateRangeForContinuousCoverage" + BAR_FORMAT + "\n");
        return tmp;
    }


    private boolean isContinuousSchoolCoverage( final AmendDependencyDecisionVO requestDependencyDecision ) {

        return isSchoolDecision()
                && requestDependencyDecision.getDependencyDecisionType().equals( DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE.getCode() );
    }


    public boolean isMinorToSchoolChild( final AmendDependencyDecisionVO         requestDependencyDecision ) {

        return isMinorChildDecision()
                && requestDependencyDecision.getDependencyDecisionType().equals( DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE.getCode() );
    }


    public boolean isSameTypeOfDecision( final AmendDependencyDecisionVO    requestDependencyDecision ) {

//        logUtils.log( String.format( "Comparing: %s to %s      and      %s to %s",
//                                     getStartDecisionType(),
//                                     requestDependencyDecision.getDependencyDecisionType(),
//                                     getStartStatusType(),
//                                     requestDependencyDecision.getDependencyStatusType() ) );

        return getStartDecisionType().equals( requestDependencyDecision.getDependencyDecisionType() )
                && getStartStatusType().equals( requestDependencyDecision.getDependencyStatusType() );
    }


    public void setEventEndDate( final DependencyDecisionVO dependencyDecision ) {

        //      SCHATTT gets the omnibussed date, otherwise don't.
        if ( dependencyDecision.getDependencyDecisionType().equals( DependentDecisionType.SCHOOL_ATTENDENCE_TERMINATES.getCode() ) ) {

            setEventEndDate( SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecision.getAwardEffectiveDate() ) );
        }
        else {
            setEventEndDate( SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecision.getEventDate() ) );
        }

        setEndDecisionType( dependencyDecision.getDependencyDecisionType() );
        setEndStatusType( dependencyDecision.getDependencyStatusType() );
    }


    private boolean isMinorChildDecision() {

        return  ( getStartDecisionType().equals(DependentDecisionType.ELIGIBLE_MINOR_CHILD.getCode())
                    && getStartStatusType().equals( DependentStatusType.MINOR_CHILD.getCode() ) );
    }


    private boolean isSchoolDecision() {

        return  ( getStartDecisionType().equals(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE.getCode())
                    && getStartStatusType().equals( DependentStatusType.SCHOOL_CHILD.getCode() ) );
    }


    private boolean isDisabledChildDecision() {

        return  ( getStartDecisionType().equals( DependentDecisionType.RATED_SERIOUSLY_DISABLED.getCode() )
                    && getStartStatusType().equals( DependentStatusType.SERIOUSLY_DISABLED_CHILD.getCode() ) );
    }


    private boolean isDependencyEstablished() {

        return getStartDecisionType().equals( DependentDecisionType.DEPENDENCY_ESTABLISHED.getCode() );
    }






    public Date getBirthDate() {

        return birthDate;
    }
    public void setBirthDate(final Date birthDate) {

        this.birthDate = birthDate;
    }


    public Date getDecisionDate() {

        return decisionDate;
    }
    public void setDecisionDate(final Date decisionDate) {

        this.decisionDate = decisionDate;
    }


    public Date getEventStartDate() {

        return eventStartDate;
    }
    public void setEventStartDate(final Date eventStartDate) {

        this.eventStartDate = eventStartDate;
    }


    public Date getEventEndDate() {

        return eventEndDate;
    }
    public void setEventEndDate(final Date eventEndDate) {

        this.eventEndDate = eventEndDate;
    }


    public String getStartDecisionType() {

        return startDecisionType;
    }
    public void setStartDecisionType(final String startDecisionType) {

        this.startDecisionType = startDecisionType;
    }


    public String getEndDecisionType() {

        return endDecisionType;
    }
    public void setEndDecisionType(final String endDecisionType) {

        this.endDecisionType = endDecisionType;
    }


    public String getStartStatusType() {

        return startStatusType;
    }
    public void setStartStatusType(final String startStatusType) {

        this.startStatusType = startStatusType;
    }

    public String getEndStatusType() {

        return endStatusType;
    }
    public void setEndStatusType(final String endStatusType) {

        this.endStatusType = endStatusType;
    }


    @Override
    public String toString() {

        return new ToStringBuilder( this )
                .append( "birthDate",           birthDate )
                .append( "decisionDate",        decisionDate )
                .append( "eventStartDate",      eventStartDate )
                .append( "eventEndDate",        eventEndDate )
                .append( "startDecisionType",   startDecisionType )
                .append( "endDecisionType",     endDecisionType )
                .append( "startStatusType",     startStatusType )
                .append( "endStatusType",       endStatusType )
                .toString();
    }
}
