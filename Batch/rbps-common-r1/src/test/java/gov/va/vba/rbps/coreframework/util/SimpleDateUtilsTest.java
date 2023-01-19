/*
 * SimpleDateUtilsTest.java
 *
 * Copyright 2012 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.fixtures.CommonFactory;

import java.util.Calendar;
import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

public class SimpleDateUtilsTest {

    private SimpleDateUtils     dateUtils   = new SimpleDateUtils();


    

    @Test
    public void shouldGetDifferenceBetweenReverseDatesInDays() {
    	
        Calendar    cal1    = Calendar.getInstance();
        cal1.set( Calendar.DATE, 15 );
        cal1.set( Calendar.MONTH, Calendar.MAY );
        cal1.set( Calendar.YEAR, 2012 );
        
        Calendar    cal2    = Calendar.getInstance();
        cal2.set( Calendar.DATE, 10 );
        cal2.set( Calendar.MONTH, Calendar.MAY );
        cal2.set( Calendar.YEAR, 2012 );   
        
        long days = dateUtils.getDifferenceBetweenDatesInDays( cal1.getTime(), cal2.getTime() );
        assertThat( days, is(equalTo( 5L ) ) );
    }
    
    @Ignore
    @Test
    public void shouldGetDifferenceBetweenDatesInDays() {
    	
        Calendar    cal1    = Calendar.getInstance();
        cal1.set( Calendar.DATE, 10 );
        cal1.set( Calendar.MONTH, Calendar.MAY );
        cal1.set( Calendar.YEAR, 2012 );
        
        Calendar    cal2    = Calendar.getInstance();
        cal2.set( Calendar.DATE, 15 );
        cal2.set( Calendar.MONTH, Calendar.MAY );
        cal2.set( Calendar.YEAR, 2012 );   
        
        long days = dateUtils.getDifferenceBetweenDatesInDays( cal1.getTime(), cal2.getTime() );
        assertThat( days, is(equalTo( 5L ) ) );
    }
    
    
    @Ignore
    @Test
    public void shouldGetOmnibuzzedDate() {

        Calendar    cal    = Calendar.getInstance();
        cal.set( Calendar.DATE, 10 );
        cal.set( Calendar.MONTH, Calendar.MAY );
        cal.set( Calendar.YEAR, 2012 );

        Date omnibuzzedDate         = dateUtils.getOmnibuzzedDate( cal.getTime() );

        Calendar    omnibuzzedCal   = Calendar.getInstance();

        omnibuzzedCal.setTime( omnibuzzedDate );

        String omnibussedDateString =   Integer.toString( omnibuzzedCal.get( Calendar.YEAR ) ) +
                                        Integer.toString( omnibuzzedCal.get( Calendar.MONTH ) ) +
                                        Integer.toString( omnibuzzedCal.get( Calendar.DATE ) );

        cal.set( Calendar.MONTH, Calendar.JUNE );
        cal.set( Calendar.DATE, 1 );

        String dateString =     Integer.toString( cal.get( Calendar.YEAR ) ) +
                                Integer.toString( cal.get( Calendar.MONTH ) ) +
                                Integer.toString( cal.get( Calendar.DATE ) );

        assertThat( omnibussedDateString, is(equalTo( dateString ) ) );
    }

    @Ignore
    @Test
    public void shouldGetOmnibuzzedDateForLastDayOfMonth() {

        Calendar    newCal    = Calendar.getInstance();

        newCal.set(2012, Calendar.MAY, 31);

        Date omnibuzzedDate         = dateUtils.getOmnibuzzedDate( newCal.getTime() );

        Calendar    omnibuzzedCal   = Calendar.getInstance();

        omnibuzzedCal.setTime( omnibuzzedDate );

        String omnibussedDateString =   Integer.toString( omnibuzzedCal.get( Calendar.YEAR ) ) +
                                        Integer.toString( omnibuzzedCal.get( Calendar.MONTH ) ) +
                                        Integer.toString( omnibuzzedCal.get( Calendar.DATE ) );

        newCal.set( Calendar.MONTH, Calendar.JUNE );
        newCal.set( Calendar.DATE, 1 );

        String dateString =     Integer.toString( newCal.get( Calendar.YEAR ) ) +
                                Integer.toString( newCal.get( Calendar.MONTH ) ) +
                                Integer.toString( newCal.get( Calendar.DATE ) );

        assertThat( omnibussedDateString, is(equalTo( dateString ) ) );
    }

    @Ignore
    @Test
    public void shouldGetOmnibuzzedDateForLastDayOfYear() {

        Calendar    newCal    = Calendar.getInstance();

        newCal.set(2012, Calendar.DECEMBER, 31);

        Date omnibuzzedDate         = dateUtils.getOmnibuzzedDate( newCal.getTime() );

        Calendar    omnibuzzedCal   = Calendar.getInstance();

        omnibuzzedCal.setTime( omnibuzzedDate );

        String omnibussedDateString =   Integer.toString( omnibuzzedCal.get( Calendar.YEAR ) ) +
                                        Integer.toString( omnibuzzedCal.get( Calendar.MONTH ) ) +
                                        Integer.toString( omnibuzzedCal.get( Calendar.DATE ) );

        System.out.println( "omnibussedDateString" + omnibussedDateString );

        newCal.set(2013, Calendar.JANUARY, 1);

        String dateString =     Integer.toString( newCal.get( Calendar.YEAR ) ) +
                                Integer.toString( newCal.get( Calendar.MONTH ) ) +
                                Integer.toString( newCal.get( Calendar.DATE ) );

        System.out.println( "dateString" + dateString );

        assertThat( omnibussedDateString, is(equalTo( dateString ) ) );
    }


    @Ignore
    @Test
    public void shouldGetIsOnOrBetweenForOmnibussed() {

        Calendar    startCal    = Calendar.getInstance();
        Calendar    endCal      = Calendar.getInstance();
        Calendar    testCal     = Calendar.getInstance();

        startCal.set(2012, Calendar.JUNE, 10);
        endCal.set(2012, Calendar.JULY, 31);
        testCal.set(2012, Calendar.JULY, 10);

        boolean isOnOrBetween = dateUtils.isOnOrBetweenForOmnibussed( startCal.getTime(), endCal.getTime(), testCal.getTime() );
        assertThat( isOnOrBetween, is(equalTo( true ) ) );
    }


    @Ignore
    @Test
    public void shouldGetIsOnOrBetweenForOmnibussedAfterEndDate() {

        Calendar    startCal    = Calendar.getInstance();
        Calendar    endCal      = Calendar.getInstance();
        Calendar    testCal     = Calendar.getInstance();

        startCal.set(2012, Calendar.JUNE, 10);
        endCal.set(2012, Calendar.JULY, 31);
        testCal.set(2012, Calendar.AUGUST, 10);

        boolean isOnOrBetween = dateUtils.isOnOrBetweenForOmnibussed( startCal.getTime(), endCal.getTime(), testCal.getTime() );
        assertThat( isOnOrBetween, is(equalTo( false ) ) );
    }


    @Ignore
    @Test
    public void shouldGetIsOnOrBetweenForOmnibussedOnEndDate() {

        Calendar    startCal    = Calendar.getInstance();
        Calendar    endCal      = Calendar.getInstance();
        Calendar    testCal     = Calendar.getInstance();

        startCal.set(2012, Calendar.JUNE, 10);
        endCal.set(2012, Calendar.JULY, 31);
        testCal.set(2012, Calendar.JULY, 31);

        boolean isOnOrBetween = dateUtils.isOnOrBetweenForOmnibussed( startCal.getTime(), endCal.getTime(), testCal.getTime() );
        assertThat( isOnOrBetween, is(equalTo( false ) ) );
    }


    @Ignore
    @Test
    public void shouldConvertAmericanStandardFormat() {

        Date result = dateUtils.convertDate( "international format", "05-02-2012" );

        assertThat( result, is(equalTo( new CommonFactory().createDay( 2012, 05, 02 ))));
    }
}
