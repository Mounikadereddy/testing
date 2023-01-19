/*
 * PopulatorUtilsTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators.utils;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;

import java.util.Date;

import org.apache.commons.lang.time.FastDateFormat;
import org.junit.Test;



/**
 * Test case for the <code>CommonUtils</code> class
 *
 * @author vafscchowdk
 *
 */
public class PopulatorUtilsTest {


//    private SimpleDateUtils       dateUtils     = new SimpleDateUtils();



    @Test
    public void shouldConvertADate() {

        String                  inputDate   = "01/06/1961";
        Date                    result      = SimpleDateUtils.convertDate( "test", inputDate );

        assertThat( FastDateFormat.getInstance( "MM/dd/yyyy" ).format( result ), is( equalTo( inputDate ) ) );
    }


    @Test
    public void shouldConvertANullDate() {

        Date                  result        = SimpleDateUtils.convertDate( "test", (String) null );

        assertThat( result, is( nullValue() ) );
    }


    @Test
    public void shouldConvertABlankDate() {

        Date            result  = SimpleDateUtils.convertDate( "test", "" );

        assertThat( result, is( nullValue() ) );
    }


    @Test( expected = RbpsRuntimeException.class )
    public void shouldFailToConvertANameIntoDate() {

    	SimpleDateUtils.convertDate( "test", "Joe" );
    }


    @Test
    public void shouldConvertTrueToBoolean() {


        assertThat( CommonUtils.convertBoolean( "true" ), is(equalTo(true) ) );
    }


    @Test
    public void shouldConvertFalseToBoolean() {


        assertThat( CommonUtils.convertBoolean( "false" ), is(equalTo(false) ) );
    }


    @Test
    public void shouldConvertTToBoolean() {


        assertThat( CommonUtils.convertBoolean( "t" ), is(equalTo(true) ) );
    }


    @Test
    public void shouldConvertFToBoolean() {


        assertThat( CommonUtils.convertBoolean( "f" ), is(equalTo(false) ) );
    }


    @Test
    public void shouldConvertYesToBoolean() {


        assertThat( CommonUtils.convertBoolean( "YES" ), is(equalTo(true) ) );
    }


    @Test
    public void shouldConvertNoToBoolean() {


        assertThat( CommonUtils.convertBoolean( "nO" ), is(equalTo(false) ) );
    }


    @Test
    public void shouldConvertYToBoolean() {


        assertThat( CommonUtils.convertBoolean( "Y" ), is(equalTo(true) ) );
    }


    @Test
    public void shouldConvertNToBoolean() {


        assertThat( CommonUtils.convertBoolean( "N" ), is(equalTo(false) ) );
    }


    @Test
    public void shouldConvertFredToBoolean() {


        assertThat( CommonUtils.convertBoolean( "Fred" ), is(equalTo(false) ) );
    }


    @Test
    public void shouldConvertNullToBoolean() {


        assertThat( CommonUtils.convertBoolean( null ), is(equalTo(false) ) );
    }
}
