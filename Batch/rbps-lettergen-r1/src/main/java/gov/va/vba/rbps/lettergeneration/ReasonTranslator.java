/*
 * ReasonTranslator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.LogUtils;

import java.util.Arrays;
import java.util.List;


public class ReasonTranslator {

    private static Logger logger = Logger.getLogger(ReasonTranslator.class);

    private LogUtils    logUtils            = new LogUtils( logger, true );


    private List<ReasonTranslation>    translations = Arrays.asList(

        new ReasonTranslation( "CONVAWD29",
                               "CONV – Dependent Added",
                               "%s was added to your award" ),

        new ReasonTranslation( "02",
                               "02 – Cost of Living Adjustment",
                               "Cost of Living Adjustment" ),

        new ReasonTranslation( "CHSC",
                               "Child Status Change",
                               "%s was added to your award",
                               "%s was removed from your award" ),

        new ReasonTranslation( "ESC",
                               "Eligible School Child",
                               "%s was added to your award" ),

        new ReasonTranslation( "GD",
                               "Gain of Dependent",
                               "%s was added to your award" ),

        new ReasonTranslation( "LD",
                               "Loss of Dependent",
                               "%s was removed from your award" ),

        new ReasonTranslation( "21",
                               "Minor Child Adjustment",
                               "%s was added to your award",
                               "%s was removed from your award." ),

        new ReasonTranslation( "22",
                               "School Child Adjustment",
                               "%s was added to your award due to school attendance",
                               "%s was removed from your award due to no longer attending school" ),

        new ReasonTranslation( "23",
                               "Change in Spouse Status",
                               "%s was added to your award",
                               "%s was removed from your award" ),

        new ReasonTranslation( "TA18",
                               "Terminated – Age 18",
                               "%s turned 18 and has been removed from your award" ),

        new ReasonTranslation( "TA23",
                               "Terminated – Age 23",
                               "%s turned 23 and has been removed from your award" ),

        new ReasonTranslation( "T18",
                               "Terminated – Age 18",
                               "%s turned 18 and has been removed from your award" ),

        new ReasonTranslation( "T23",
                               "Terminated – Age 23",
                               "%s turned 23 and has been removed from your award" ),

        new ReasonTranslation( "TNLD",
                               "Terminated – No Longer Dependent",
                               "%s has been removed from your award because they are no longer considered a dependent" ),

        new ReasonTranslation( "TST",
                               "Terminated – School Termination",
                               "%s has been removed from your award since they are not attending school" ),

        //      Use text string #1 when dependencyDecisionType =O18NISOH
        new ReasonTranslation( "O18NISOH",
                               "Over 18-Not in School or Helpless",
                               "%s was reported as being between the ages of 18 and 23, was not reported as currently pursuing a course of instruction at an approved educational institution, and was not reported as being seriously disabled.",
                               "%s was reported as being between the ages of 18 and 23, was not reported as currently pursuing a course of instruction at an approved educational institution, and was not reported as being seriously disabled." ),

        //      Rated Not Helpless
        new ReasonTranslation( "RATNHEL",
                               "Rated Not Helpless",
                               "%s was reported as being 23 years of age or older and not reported as being seriously disabled." ),

        //      Rated Seriously Disabled
        new ReasonTranslation( "RATHEL",
                               "Rated Seriously Disabled",
                               "%s was added to your award",
                               "%s was removed from your award" ),

        //      Use text string #4 when dependencyDecisionType =MARR
        new ReasonTranslation( "MARR",
                               null,
                               "%s was reported as being married" ),

        //      Eligible minor child
        new ReasonTranslation( "EMC",
                               "Eligible Minor Child",
                               "%s was added to your award",
                               "%s was removed from your award" ),

        //      School attendance begins
        new ReasonTranslation( "SCHATTB",
                               "School Attendance Begins",
                               "%s was added to your award as a school child",
                               "%s was removed from your award as a school child" ),

        //      School attendance terminated
        new ReasonTranslation( "SCHATTT",
                               "School Attendance Terminates",
                               "%s has been removed from your award since they are not attending school",
                               "%s has been removed from your award since they are not attending school" ),
        new ReasonTranslation( "SCHATT",
                              "School Attendance Terminates",
                              "%s has been removed from your award since they are not attending school",
                              "%s has been removed from your award since they are not attending school" ),

        //      Dependency Established
        new ReasonTranslation( "DEPEST",
                               "Dependency Established",
                               "%s was added to your award",
                               "%s was removed from your award" ) );



//    public ReasonTranslator() {
//
//    }


    public ReasonTranslation translate( final AwardReason   awardReason,
                                        final String        key,
                                        final String        putativeTranslation ) {

        ReasonTranslation translation = getTranslation( key );

        if ( translation != null ) {

            return translation;
        }

//        logUtils.log( "\n\nAwardReason: " + awardReason );
//        logUtils.log( String.format( "Unable to find translation for: >%s< and >%s<", key, putativeTranslation ), new Throwable( "stack" ) );
        return new ReasonTranslation( key, "", putativeTranslation );
    }


    public boolean hasTranslation( final String     key ) {

        return getTranslation( key ) != null;
    }


    private ReasonTranslation getTranslation( final String key ) {

        for ( ReasonTranslation translation : translations ) {

            if ( translation.getType().equalsIgnoreCase( key ) ) {

                return translation;
            }
        }

        return null;
    }
}
