/*
 * CommonUtilsTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class CommonUtilsTest {

    @Before
    public void setup() {

    }

    
    
    @Test
    public void shouldgetLogger() {
    	
    	String currentProcess = "1";
    	String processorStr = " \""+ "rbps_processor" + currentProcess + "\"" ;
    	System.out.println( processorStr );
    	
    }
    
    
    @Ignore
    @Test
    public void shouldSplitAndConcatenateOnSpaces() {
    	
    	String string = "abcd      efgh      hijk      a         lnm";
    	
    	String returnString = CommonUtils.removeExtraSpacesFromString( string );
    	
    	assertThat( returnString.length(), is(equalTo( 20 ) ) );
    }
   
    
    @Ignore
    @Test
    public void shouldWorkWithUnreasonableStackLevel() {

        CommonUtils.getStackLevel( 100 );
    }

    @Ignore
    @Test
    public void shouldWorkWithExactUnreasonableStackLevel() {

        //      The actual number of stack frames is around 26, so I want to check off by ones.
        for ( int ii = 20; ii < 30; ii++ ) {

            CommonUtils.getStackLevel( ii );
        }
    }


    @Ignore
    @Test
    public void shouldGetNumericStringForNull(){

        String returnString =   CommonUtils.getNumericString(null);
        assertThat( returnString, is(equalTo( null ) ) );
    }


    @Ignore
    @Test
    public void shouldGetNumericStringForEmptyString(){

        String returnString =   CommonUtils.getNumericString("");

        assertThat( returnString.length(), is(equalTo( 0 ) ) );
    }


    @Ignore
    @Test
    public void shouldGetNumericStringFromNonNumericString(){

        String returnString =   CommonUtils.getNumericString("ab12cd45");

        assertThat( Integer.parseInt(returnString), is(equalTo( 1245 ) ) );
    }


    @Ignore
    @Test
    public void shouldGetNumericStringForAlphaString(){

        String returnString =   CommonUtils.getNumericString("abcd");

        assertThat( returnString.length(), is(equalTo( 0 ) ) );
    }
}
