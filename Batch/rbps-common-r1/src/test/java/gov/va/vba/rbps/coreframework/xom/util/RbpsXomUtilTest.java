/*
 * RbpsXomUtilTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.xom.util;


import org.junit.Test;




public class RbpsXomUtilTest {


    @Test
    public void fake() {

    }


//    @Test
//    public void shouldGiveFiftyYears() {
//
//        Calendar    startCal    = new GregorianCalendar( 1961, 1, 6 );
//        Calendar    endCal      = new GregorianCalendar( 2011, 11, 6 );
//
//        int years = RbpsXomUtil.yearsBetween( startCal.getTime(), endCal.getTime() );
//
//        System.out.println( "years = " + years );
//
//        assertThat( years, is(equalTo( 50 )));
//    }
//
//
//    @Test
//    public void shouldGiveTenYears() {
//
//        Calendar    startCal    = new GregorianCalendar( 1961, 1, 6 );
//        Calendar    endCal      = new GregorianCalendar( 1971, 1, 7 );
//
//        int years = RbpsXomUtil.yearsBetween( startCal.getTime(), endCal.getTime() );
//
//        System.out.println( "years = " + years );
//
//        assertThat( years, is(equalTo( 10 )));
//    }
//
//
//    @Test
//    public void shouldGiveNineYears() {
//
//        Calendar    startCal    = new GregorianCalendar( 1961, 1, 6 );
//        Calendar    endCal      = new GregorianCalendar( 1971, 1, 5 );
//
//        int years = RbpsXomUtil.yearsBetween( startCal.getTime(), endCal.getTime() );
//
//        System.out.println( "years = " + years );
//
//        assertThat( years, is(equalTo( 9 )));
//    }
//
//
//    @Test
//    public void shouldGiveTenYearsForExactDay() {
//
//        Calendar    startCal    = new GregorianCalendar( 1961, 1, 6 );
//        Calendar    endCal      = new GregorianCalendar( 1971, 1, 6 );
//
//        int years = RbpsXomUtil.yearsBetween( startCal.getTime(), endCal.getTime() );
//
//        System.out.println( "years = " + years );
//
//        assertThat( years, is(equalTo( 10 )));
//    }
//
//
//    @Test
//    public void shouldGiveNineYearsForLeap() {
//
//        Calendar    startCal    = new GregorianCalendar( 1961, 2, 30 );
//        Calendar    endCal      = new GregorianCalendar( 1971, 2, 29 );
//
//        int years = RbpsXomUtil.yearsBetween( startCal.getTime(), endCal.getTime() );
//
//        System.out.println( "years = " + years );
//
//        assertThat( years, is(equalTo( 9 )));
//    }
//
//
//    @Test
//    public void shouldGive18YearsForLeap() {
//
//        Calendar    startCal    = new GregorianCalendar( 1993, 8, 1 );
//        Calendar    endCal      = new GregorianCalendar();
//
//        int years = RbpsXomUtil.yearsBetween( startCal.getTime(), endCal.getTime() );
//
//        System.out.println( "years = " + years );
//
//        assertThat( years, is(equalTo( 18 )));
//    }
//
//
//    @Test
//    public void shouldAddOneDay() {
//
//        int         fieldVal            = 3;
//        int         fieldIncrement      = 1;
//        Date        now                 = new Date();
//        Calendar    nowCal              = Calendar.getInstance();
//        nowCal.setTime( now );
//        nowCal.set( Calendar.DAY_OF_MONTH, fieldVal );
//        now = nowCal.getTime();
//
//        Date        result  = RbpsXomUtil.addDaysToDate( fieldIncrement, now );
//        Calendar    resultCal = Calendar.getInstance();
//        resultCal.setTime( result );
//
//        assertThat( resultCal.get( Calendar.DAY_OF_MONTH), is(equalTo( fieldVal + fieldIncrement )));
//    }
//
//
//    @Test
//    public void shouldAddOneMonth() {
//
//        int         fieldVal            = 3;
//        int         fieldIncrement      = 1;
//        int         field               = Calendar.MONTH;
//        Date        now                 = new Date();
//        Calendar    nowCal              = Calendar.getInstance();
//        nowCal.setTime( now );
//        nowCal.set( field, fieldVal );
//        now = nowCal.getTime();
//
//        Date        result  = RbpsXomUtil.addMonthsToDate( fieldIncrement, now );
//        Calendar    resultCal = Calendar.getInstance();
//        resultCal.setTime( result );
//
//        assertThat( resultCal.get( field ), is(equalTo( fieldVal + fieldIncrement )));
//    }
//
//
//    @Test
//    public void shouldAddOneYear() {
//
//        int         fieldVal            = 3;
//        int         fieldIncrement      = 1;
//        int         field               = Calendar.YEAR;
//        Date        now                 = new Date();
//        Calendar    nowCal              = Calendar.getInstance();
//        nowCal.setTime( now );
//        nowCal.set( field, fieldVal );
//        now = nowCal.getTime();
//
//        Date        result  = RbpsXomUtil.addYearsToDate( fieldIncrement, now );
//        Calendar    resultCal = Calendar.getInstance();
//        resultCal.setTime( result );
//
//        assertThat( resultCal.get( field ), is(equalTo( fieldVal + fieldIncrement )));
//    }
//
//
//    @Test
//    public void shouldGetAge() {
//
//        Person  person = new Veteran();
//        person.setBirthDate( date( 1966, 8, 29 ) );
//
//        int    result = RbpsXomUtil.getAgeOn( person, date( 2011, 3, 9 ) );
//
//        assertThat( result, is(equalTo( 44 )));
//    }
//
//
//    @Test
//    public void shouldGetAgeOnSameDay() {
//
//        Person  person = new Veteran();
//        person.setBirthDate( date( 1966, 8, 29 ) );
//
//        int    result = RbpsXomUtil.getAgeOn( person, date( 2011, 8, 29 ) );
//
//        assertThat( result, is(equalTo( 45 )));
//    }
//
//
//    @Test
//    public void shouldGetCurrentAge() {
//
//        Person  person = new Veteran();
//        person.setBirthDate( date( 1966, 8, 29 ) );
//
//        int    result = RbpsXomUtil.getCurrentAge( person );
//
//        assertThat( result, is(equalTo( 45 )));
//    }
//
//
//    private Date date( final int  year, final int month, final int day ) {
//
//        return new GregorianCalendar( year, month, day ).getTime();
//    }
}
