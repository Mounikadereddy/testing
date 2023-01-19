/*
 * CommonUtils.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;

import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.xom.Person;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;


/**
 *      Date utilities, such as whether a date is in the future, or if it's
 *      on or before another date, or how old someone is.
 */
public class SimpleDateUtils {


    public static final String SIMPLE_DATE_FORMAT = "MM/dd/yyyy";

    public static final double getDifferenceBetweenDatesInYears( Date date1, Date date2 ) {
    	
    	long numberOfDays = getDifferenceBetweenDatesInDays(date1, date2 );
    	
    	double numberOfYears = numberOfDays / 365.25;
    	
    	return numberOfYears;
    }


    public static final long getDifferenceBetweenDatesInDays( Date date1, Date date2 ) {
    	
    	long ONE_DAY = 24 * 60 * 60 * 1000L;
    	
    	Date earlier = null;
    	Date later   = null;
    	
    	if ( date1.before( date2 ) ){
    		earlier = date1;
    		later   = date2;
    	}
    	else {
    		earlier = date2;
    		later   = date1;
    	}
    	long days = ( ( later.getTime() - earlier.getTime() ) /  ONE_DAY  ) ;
    	
    	return days;
    }


    public static final boolean isInFuture( final Date input ) {

        Date    now = new Date();

        return input.after( now );
    }


    public static final String standardLogDayFormat( final Date   date ) {

        if ( date == null ) {

            return null;
        }

        DateFormat  formatter   = new SimpleDateFormat( "yyyy-MM-dd     HH:mm        z Z" );
        return formatter.format( date );
    }


    public static final String standardShortLogDayFormat( final Date   date ) {

        if ( date == null ) {

            return null;
        }

        DateFormat  formatter   = new SimpleDateFormat( "yyyy-MM-dd" );
        return formatter.format( date );
    }


//  public Date roundToDay( final Date    date ) {
//
//      return DateUtils.round( date, Calendar.DAY_OF_MONTH );
//  }


    public static final Date truncateToDay( final Date    date ) {

        return DateUtils.truncate( date, Calendar.DAY_OF_MONTH );
    }




    /**
     *      tests whether a testDate is in the given closed interval.
     *
     *      Could be "isInClosedInterval".
     *
     *      @param startDate
     *      @param endDate
     *      @param testDate
     *      @return
     */
    public static final boolean isOnOrBetween( final Date startDate, final Date endDate, final Date testDate ) {

        return isOnOrAfter( testDate, startDate )
                && isOnOrBefore( testDate, endDate );
    }


    public static final boolean isOnOrBetweenForOmnibussed( final Date startDate, final Date endDate, final Date testDate ) {

        return isOnOrAfter( testDate, startDate )
                && testDate.before( endDate );
    }


    public static final boolean isOnOrBefore( final Date isOnOrBeforeDate, final Date testAgainstDate ) {

        return isOnOrBeforeDate.equals( testAgainstDate ) || isOnOrBeforeDate.before( testAgainstDate );
    }


    public static final boolean isOnOrAfter( final Date isOnOrAfterDate, final Date testAgainstDate  ) {

        return isOnOrAfterDate.equals( testAgainstDate ) || isOnOrAfterDate.after(testAgainstDate);
    }


    /**
     * Converts a date from a string.  Many of the web services use
     * the same simple data format, so this method just handles that
     * one simple case.
     *
     * @param fieldName - for error purposes, tells us which field
     *              is having the formatting issue.
     * @param input - the string date.
     * @return a converted date.
     */
    public static final Date convertDate( final String      fieldName,
                             final String      input ) {

        if ( StringUtils.isBlank(  input ) ) {

            return null;
        }

        try {

            return parseInputDate( input );
        }
        catch ( Throwable ex ) {

            throw new RbpsRuntimeException( String.format( "Failed to convert >%s< to a date", input ), ex );
        }
    }


    public static final Date parseInputDate( final String     dateString ) {

        List<String> formats = Arrays.asList( SIMPLE_DATE_FORMAT,
                                              "MM-dd-yyyy",
                                              "MM/dd/yyyy" );

        for ( String format : formats ) {

            try {

                return new SimpleDateFormat( format ).parse( dateString );
            } catch ( ParseException ignore ) {
                //     Ignore, try again with new format
            }
        }

        throw new UnableToParseDateInputException( dateString );
    }


    /**
     * Converts a date from a string.  Many of the web services use
     * the same simple data format, so this method just handles that
     * one simple case.
     *
     * @param fieldName - for error purposes, tells us which field
     *              is having the formatting issue.
     * @param input - the string date.
     * @return a converted date.
     */
    public static final Date convertDate( final String      fieldName,
                             final String      format,
                             final String      input ) {

        if ( StringUtils.isBlank(  input ) ) {

            return null;
        }

        try {

            DateFormat dateFormatter = new SimpleDateFormat( format );
            return dateFormatter.parse( input );
        }
        catch ( Throwable ex ) {

            throw new RbpsRuntimeException( String.format( "Failed to convert >%s< to a date", input ), ex );
        }
    }


    /**
     * Convert a date to a string for when you want to send a date to a web
     * service.  This would probably be in a claim post processor.  This
     * uses the same format that web services send to us, so they should
     * be able to swallow it.
     *
     * @param fieldName - for error purposes, tells us which field
     *              is having the formatting issue.
     * @param input - the date we're converting to a string
     * @return the stringified date
     */
    public static final String convertDate( final String     fieldName,
                               final String     format,
                               final Date       input ) {

        if ( input == null ) {

            return null;
        }

        // good to see new one created each time, SDF is not thread safe.
        DateFormat dateFormatter = new SimpleDateFormat( format );
        return dateFormatter.format( input );
    }


    /**
     *      Convert the <code>XMLGregorianCalendar</code> to a date in a null
     *      safe manner.
     *
     *      @param xmlGregorianCalendar
     *      @return the converted date or null if the input is null
     */
//    public Date xmlGregorianCalendarToDate(final XMLGregorianCalendar xmlGregorianCalendar) {
//
//        return xmlGregorianCalendar != null ? getCalendarWithoutTimezone( xmlGregorianCalendar ) : null;
//    }


    /**
     *      Convert the <code>XMLGregorianCalendar</code> to date which has had
     *      the hours, minutes, seconds, etc. truncated off so that it just represents
     *      the day.
     *
     *      @param xmlGregorianCalendar
     *      @return a Date which represents a day only.
     */
    public static final Date xmlGregorianCalendarToDay(final XMLGregorianCalendar xmlGregorianCalendar) {

        if ( xmlGregorianCalendar == null ) {

            return null;
        }

        Date result     = getCalendarWithoutTimezone( xmlGregorianCalendar );
             result     = truncateToDay( result );

        return result;
    }


    public static final Date getCalendarWithoutTimezone( final XMLGregorianCalendar xmlGregorianCalendar ) {

        return getCalendarWithoutTimezone( xmlGregorianCalendar.toGregorianCalendar() );
    }
    
    /**
     *      Convert the <code>XMLGregorianCalendar</code> to date
     *
     *      @param xmlGregorianCalendar
     *      @return Date.
     */
    public static final Date xmlGregorianCalendarToDateAndTime(final XMLGregorianCalendar xmlGregorianCalendar) {

        if ( xmlGregorianCalendar == null ) {

            return null;
        }

        Date result     = getCalendarWithTime( xmlGregorianCalendar );

        return result;
    }
    
    public static final Date getCalendarWithTime( final XMLGregorianCalendar xmlGregorianCalendar ) {

        return getCalendarWithTime( xmlGregorianCalendar.toGregorianCalendar() );
    }

    public static final XMLGregorianCalendar dateToXMLGregorianCalendar(final Date date) {

        if ( date == null ) {

            return null;
        }

        XMLGregorianCalendar xmlGregorianCalendar 	= null;
        GregorianCalendar gregorianCalendar			= new GregorianCalendar();
        gregorianCalendar.setTime(date);
        
        try {
        	
        	xmlGregorianCalendar 	= DatatypeFactory.newInstance().newXMLGregorianCalendar( gregorianCalendar );
        	
        } catch( Exception ex ) {
        	
        	throw new RbpsRuntimeException( String.format( "Failed to convert date >%s< to a XMLGregorianCalendar", date.toString() ), ex );
        }

        return xmlGregorianCalendar;
    }

    public static final Date getCalendarWithoutTimezone( final Calendar input ) {
    	// name is deceiving.  It HAS a timezone, most likely CST since server is in Austin
    	
        Calendar cal = input;
        Calendar cleaned = Calendar.getInstance();

        cleaned.set( Calendar.YEAR,             cal.get( Calendar.YEAR ) );
        cleaned.set( Calendar.MONTH,            cal.get( Calendar.MONTH ) );
        cleaned.set( Calendar.DAY_OF_MONTH,     cal.get( Calendar.DAY_OF_MONTH ) );

//        System.out.println( "CAL: " + cleaned );
//        System.out.println( "CAL: " + cleaned.getTime() );

        return cleaned.getTime();
    }
    
    public static final Date getCalendarWithTime( final Calendar input ) {
    	
        Calendar cal = input;
        Calendar cleaned = Calendar.getInstance();

        cleaned.set( Calendar.YEAR,             cal.get( Calendar.YEAR ) );
        cleaned.set( Calendar.MONTH,            cal.get( Calendar.MONTH ) );
        cleaned.set( Calendar.DAY_OF_MONTH,     cal.get( Calendar.DAY_OF_MONTH ) );
        cleaned.set( Calendar.HOUR_OF_DAY,     	cal.get( Calendar.HOUR_OF_DAY ) );
        cleaned.set( Calendar.MINUTE,    	 	cal.get( Calendar.MINUTE ) );
        cleaned.set( Calendar.SECOND,     		cal.get( Calendar.SECOND ) );

        return cleaned.getTime();
    }


    public static final Date addDaysToDate( final int          numDays,
                                      final Date         startDate ) {

        return addFieldToDate( Calendar.DAY_OF_YEAR, numDays, startDate );
    }


    public static final Date addMonthsToDate( final int        numMonths,
                                        final Date       startDate ) {

        return addFieldToDate( Calendar.MONTH, numMonths, startDate );
    }


    public static final Date addYearsToDate( final int         numYears,
                                       final Date        startDate ) {

        return addFieldToDate( Calendar.YEAR, numYears, startDate );
    }


    private static final Date addFieldToDate( final int       fieldId,
                                        final int       numToAdd,
                                        final Date      startDate ) {

        if ( startDate == null ) {

            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime( startDate );
        cal.add( fieldId, numToAdd );

        return cal.getTime();
    }


    public static final int getAgeOn( final Person    person,
                                final Date      endDate ) {

        return yearsBetween( person.getBirthDate(), endDate );
    }


    public static final int getCurrentAge( final Person person ) {

        if ( person == null || person.getBirthDate() == null ) {

            return -1;
        }

        return getAge( person.getBirthDate() );
    }


    public static final Date getDate18( final Person person ) {

        if ( person == null || person.getBirthDate() == null ) {

            return null;
        }

        return addYearsToDate( 18, person.getBirthDate() );
    }


    public static final Date get5MonthsAfter18thBirthday( final Person person ) {

        if ( person == null || person.getBirthDate() == null ) {

            return null;
        }

        Date    birthday18th        =   getDate18( person );
        Date    fiveMonthsLater     =   addMonthsToDate( 5, birthday18th );

        return fiveMonthsLater;
    }


    public static final Date getDate23( final Person person ) {

        if ( person == null || person.getBirthDate() == null ) {

            return null;
        }

        return addYearsToDate( 23, person.getBirthDate() );
    }


    public static final int getAge( final Date date ) {

        return yearsBetween( date, new Date() );
    }


    public static final Date getOmnibuzzedDate( final Date date ) {

        Calendar    cal    = Calendar.getInstance();

        cal.setTime( date );
        cal.add( Calendar.MONTH, 1 );
        cal.set( Calendar.DATE, 1);

        return cal.getTime();
    }


    /**
     *      This Method is unit tested properly for very different cases ,
     *      taking care of Leap Year days difference in a year,
     *      and date cases month and Year boundary cases (12/31/1980, 01/01/1980 etc)
     */
    public static final int yearsBetween( final Date startDate,
                                     final Date endDate ) {

        if ( startDate == null ) {

            return -1;
        }

        // not needed
        //SimpleDateUtils     dateUtils       = new SimpleDateUtils();
        
        Calendar            startCal        = Calendar.getInstance();
        Calendar            endCal          = Calendar.getInstance();
        int                 age             = 0;

        startCal.setTime( startDate );
        endCal.setTime( endDate );

        if ( startCal.after( endCal ) ) {

            throw new IllegalArgumentException( String.format( "Start date %s must be before end date %s",
                                                               SimpleDateUtils.standardLogDayFormat( startDate ),
                                                               SimpleDateUtils.standardLogDayFormat( endDate ) ) );
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
}
