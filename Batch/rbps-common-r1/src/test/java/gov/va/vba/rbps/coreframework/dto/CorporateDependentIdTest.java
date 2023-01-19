/*
 * CorporateDependentIdTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.dto;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



/**
 *      Test case for the <code>CorporateDependentId</code> class
 */
public class CorporateDependentIdTest {


    @Before
    public void setup() {

        LogUtils.setGlobalLogit( false );
    }


    @Test
    public void shouldNotBeEqualIfDifferentParticipantId() {

        CorporateDependentId left   = new CorporateDependentId( 3L, null, null, null );
        CorporateDependentId right  = new CorporateDependentId( 4L, null, null, null );

        assertThat( left, is( not(equalTo( right ) ) ) );
    }


    @Test
    public void shouldNotBeEqualWithoutParticipantId() {

        CorporateDependentId left   = new CorporateDependentId( 3L,     null, null, null );
        CorporateDependentId right  = new CorporateDependentId( null,   null, null, null );

        assertThat( left, is( not(equalTo( right ) ) ) );
    }


    @Ignore
    @Test
    public void shouldBeEqualWithFirstName() {

        CorporateDependentId left   = new CorporateDependentId( null, "Tom", null, null );
        CorporateDependentId right  = new CorporateDependentId( null, "Tom", null, null );

        assertThat( left, is( equalTo( right ) ) );
    }


    @Test
    public void shouldBeEqualWithSameNameBirthDateSameDayDifferentTime() {

        Date    now     = new Date();
        Date    then    = aMinuteFromNow();

        CorporateDependentId left   = new CorporateDependentId( null, "Tom", now, "1234" );
        CorporateDependentId right  = new CorporateDependentId( null, "Tom", then, "1234" );

        assertThat( left, is( equalTo( right ) ) );
    }


    @Test
    public void shouldBeEqualIfNoParticipantIdSameName() {

        Date    now     = new Date();

        CorporateDependentId left   = new CorporateDependentId( 3L, "Tom", now, "1234" );
        CorporateDependentId right  = new CorporateDependentId( null, "TOM", now, "1234" );

        assertThat( left, is( equalTo( right ) ) );
    }


    @Test
    public void shouldNotBeEqualIfDifferentName() {

        Date    now     = new Date();

        CorporateDependentId left   = new CorporateDependentId( 3L, "Tom", now, "1234" );
        CorporateDependentId right  = new CorporateDependentId( null, "FRED", now, "1234" );

        assertThat( left, not( is( equalTo( right ) ) ) );
    }


    @Test
    public void shouldNotBeEqualIfNoName() {

        Date    now     = new Date();

        CorporateDependentId left   = new CorporateDependentId( 3L, "Tom", now, "1234" );
        CorporateDependentId right  = new CorporateDependentId( null, null, now, "1234" );

        assertThat( left, is( not( equalTo( right ) ) ) );
    }


    @Test
    public void shouldBeEqualIfNoParticipantIdSameNameAndBirthDate() {

        Date    now = new Date();
        CorporateDependentId left   = new CorporateDependentId( 3L, "Tom", now, null );
        CorporateDependentId right  = new CorporateDependentId( null, "TOM", now, null );

        assertThat( left, is( equalTo( right ) ) );
    }


    @Test
    public void shouldNotBeEqualIfNoParticipantIdSameBirthDate() {

        Date    now = new Date();
        CorporateDependentId left   = new CorporateDependentId( 3L,     null, now, null );
        CorporateDependentId right  = new CorporateDependentId( null,   null, now, null );

        assertThat( left, is( not( equalTo( right ) ) ) );
    }


    @Test
    public void shouldBeEqualIfNoParticipantIdSameSocial() {

        Date    now = new Date();

        CorporateDependentId left   = new CorporateDependentId( 3L,     "Fred", now, "123456789" );
        CorporateDependentId right  = new CorporateDependentId( null,   "FRED", now, "123456789" );

        assertThat( left, is( equalTo( right ) ) );
    }


    @Test
    public void shouldBeEqualIfSameSocial() {

        Date    now = new Date();
        CorporateDependentId left   = new CorporateDependentId( null,   "Tom", now, "123456789" );
        CorporateDependentId right  = new CorporateDependentId( null,   "TOM", now, "123456789" );

        assertThat( left, is( equalTo( right ) ) );
    }


    @Test
    public void shouldNotBeEqualIfDifferentSocial() {

        Date    now = new Date();
        CorporateDependentId left   = new CorporateDependentId( null,   "Tom", now, "123456789" );
        CorporateDependentId right  = new CorporateDependentId( null,   "TOM", now, "12345" );

        assertThat( left, is( not( equalTo( right ) ) ) );
    }


    @Test
    public void shouldFindKeyaInList() {

        List<CorporateDependentId>      ids         = createListOfDependentIds();
        CorporateDependentId            searchFor   = new CorporateDependentId( null, "keya", aBirthDate(), null );
        int index = ids.indexOf( searchFor );

        assertThat( index, is( equalTo( 2 ) ) );
    }


    @Test
    public void shouldFindKarmaInList() {

        List<CorporateDependentId>      ids         = createListOfDependentIds();
        CorporateDependentId            searchFor   = new CorporateDependentId( 123456789L, "Karma", aBirthDate(), "123456789" );
        int index = ids.indexOf( searchFor );

        assertThat( index, is( equalTo( 6 ) ) );
    }


    @Test
    public void shouldFindJoeInListBySocial() {

        List<CorporateDependentId>      ids         = createListOfDependentIds();
        CorporateDependentId            searchFor   = new CorporateDependentId( null, "joe", aBirthDate(), "555555555" );
        int index = ids.indexOf( searchFor );

        assertThat( index, is( equalTo( 1 ) ) );
    }


//    @Ignore
//    @Test
//    public void shouldBeEqualIfTheSameParticipantId() {
//
//        CorporateDependentId left   = new CorporateDependentId( 3L, null, null, null );
//        CorporateDependentId right  = new CorporateDependentId( 3L, null, null, null );
//
//        assertThat( left, is( equalTo( right ) ) );
//    }


    public List<CorporateDependentId> createListOfDependentIds() {

        Date now = aBirthDate();

        return Arrays.asList( new CorporateDependentId( 123456789L,     "fred",     now, "123456789" ),
                              new CorporateDependentId( 987654321L,     "joe",      now, "555555555" ),
                              new CorporateDependentId( null,           "keya",     now, null ),
                              new CorporateDependentId( null,           "raja",     now, "111111111" ),
                              new CorporateDependentId( null,           null,       now, "222222222" ),
                              new CorporateDependentId( null,           "steve",     now, "123456789" ),
                              new CorporateDependentId( null,           "karma",     now, "123456789" ) );
    }


    private Date aBirthDate() {

        return new SimpleDateUtils().convertDate( "birthDate", "03/11/1958" );
    }


    private Date aMinuteFromNow() {

        Calendar tool = Calendar.getInstance();
                        tool.add( Calendar.MINUTE, 8 );
        Date     then = tool.getTime();
        return then;
    }
}
