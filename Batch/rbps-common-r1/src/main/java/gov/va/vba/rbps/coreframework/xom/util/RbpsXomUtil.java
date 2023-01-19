/*
 * RbpsXomUtil.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom.util;


import gov.va.vba.rbps.coreframework.xom.Person;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


/**
 *      Utils needed by the rules.
 */
public class RbpsXomUtil implements Serializable {

    private static final long serialVersionUID = 8976382572478606710L;


    public static Date addDaysToDate( final int          numDays,
                                      final Date         startDate ) {
        int numOfYear;

        if (Math.abs(numDays) == 365) {
            if (numDays > 0) {
                numOfYear = 1;
            } else {
                numOfYear = -1;
            }
            return addYearsToDate(numOfYear, startDate);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        cal.add( Calendar.DAY_OF_YEAR, numDays );

        return cal.getTime();
    }


    public static Date addMonthsToDate( final int        numMonths,
                                        final Date       startDate ) {


        Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        cal.add( Calendar.MONTH, numMonths );

        return cal.getTime();
    }


    public static Date addYearsToDate( final int         numYears,
                                       final Date        startDate ) {

        if ( startDate == null ) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        //it should return 03/01 if a child was born at leap year day 02/29
        if (dateIsLeapYearDay(cal) && !(numYears % 4 == 0)){
            cal.add( Calendar.DAY_OF_YEAR, 1 );
        }
        cal.add( Calendar.YEAR, numYears );

        return cal.getTime();

    }

    // check if it is a leap year day
    private static boolean dateIsLeapYearDay(Calendar cal) {
        return (cal.get(Calendar.MONTH) == 1 && cal.get(Calendar.DAY_OF_MONTH) == 29);
    }

    public static final Date getOmnibusedDate( final Date date ) {

        Calendar    cal    = Calendar.getInstance();

        cal.setTime( date );
        cal.add( Calendar.MONTH, 1 );
        cal.set( Calendar.DATE, 1);

        return cal.getTime();
    }
    
/*
    private static Date addFieldToDate( final int       fieldId,
                                        final int       numToAdd,
                                        final Date      startDate ) {

        Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        cal.add( fieldId, numToAdd );

        return cal.getTime();
    }*/


    public static int getAgeOn( final Person    person,
                                final Date      endDate ) {

        Calendar startCal    = Calendar.getInstance();
        Calendar endCal      = Calendar.getInstance();

        startCal.setTime(person.getBirthDate());
        endCal.setTime(endDate);

        int year1 = endCal.get(Calendar.YEAR);
        int year2 = startCal.get(Calendar.YEAR);
        int age = year1 - year2;
        int month1 = endCal.get(Calendar.MONTH);
        int month2 = startCal.get(Calendar.MONTH);
        if (month2 > month1) {
            age--;
        } else if (month1 == month2) {
            int day1 = endCal.get(Calendar.DAY_OF_MONTH);
            int day2 = startCal.get(Calendar.DAY_OF_MONTH);
            if (day2 > day1) {
                age--;
            }
        }

        if ( age < 0 ){

            return 0;
        }

        return age;
    }


    public static int getCurrentAge( final Person person ) {

        return getAge( person.getBirthDate() );
    }


    public static int getAge( final Date birthDate ) {

        return yearsBetween( birthDate, new Date() );
    }


    /**
     *      This Method is unit tested properly for very different cases ,
     *      taking care of Leap Year days difference in a year,
     *      and date cases month and Year boundary cases (12/31/1980, 01/01/1980 etc)
     **/
    public static int yearsBetween( final Date startDate,
                                    final Date endDate ) {

        Calendar    startCal    = Calendar.getInstance();
        Calendar    endCal      = Calendar.getInstance();
        int         age         = 0;

        startCal.setTime( startDate );
        endCal.setTime( endDate );

        if ( startCal.after( endCal) ) {

            throw new IllegalArgumentException( String.format( "Start date %s must be before end date %s",
                    startDate,
                    endDate ) );
        }

        age = endCal.get(Calendar.YEAR) - startCal.get(Calendar.YEAR);

        //
        //      If birth date is greater than todays date
        //      (after 2 days adjustment of leap year) then decrement age one year
        //
        if ( (startCal.get(Calendar.DAY_OF_YEAR) - endCal.get(Calendar.DAY_OF_YEAR) > 3)
                || (startCal.get(Calendar.MONTH) > endCal.get(Calendar.MONTH ))) {

            age--;
        }
        //
        //      If birth date and todays date are of same month and birth day of month is
        //      greater than todays day of month then decrement age
        //
        else if ((startCal.get(Calendar.MONTH) == endCal.get(Calendar.MONTH ))
                && (startCal.get(Calendar.DAY_OF_MONTH) > endCal.get(Calendar.DAY_OF_MONTH ))) {

            age--;
        }

        return age;
    }


    public static boolean isPresent(final Object obj) {

        return obj != null;
    }


    public static boolean isNotPresent(final Object obj) {

        return obj == null;
    }
}
