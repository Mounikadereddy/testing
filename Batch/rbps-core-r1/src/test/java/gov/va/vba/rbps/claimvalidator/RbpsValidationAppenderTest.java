/*
 * RbpsValidationAppenderTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimvalidator;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.claimprocessor.impl.EP130ClaimPreProcessorTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



public class RbpsValidationAppenderTest  extends RbpsAbstractTest {

    private static Logger   logger = Logger.getLogger(EP130ClaimPreProcessorTest.class);

    RbpsValidationAppender  validationAppender;
    private RbpsRepository  repository;
    private final String    MESSAGE = "Child Type should be Biological or Stepchild.";


    @Override
    @Before
    public void setup() {

        super.setup();

        validationAppender  = (RbpsValidationAppender) getBean( "validationAppender" );
        repository          = (RbpsRepository) getBean( "repository" );
    }



    private void setupUserInfo(final String userInfoPath) {


        repository.setVeteran( null );
        repository.clearValidationMessages();

    }
    
    @Ignore
    @Test
    public void shouldFutureDateValidated() {
    	
    	GregorianCalendar cal = new GregorianCalendar(2012, 11, 3);
    	
    	RbpsValidationAppender.addNotFutureDateValidation(cal.getTime(), "Test", repository );
    	assertThat( repository.getValidationMessages().size(), is( equalTo( 1 ) ) );
    }
   
    @Ignore
    @Test
    public void shouldPreviousDateValidated() {
    	
    	try {
    		
    		Date date1 = new SimpleDateFormat("MM/dd/yy").parse("11/01/12");
    		RbpsValidationAppender.addNotFutureDateValidation( date1, "Test", repository );
    		
    	} catch ( ParseException e) {
    		
    	      e.printStackTrace();
        }
    	
    	
    	assertThat( repository.getValidationMessages().size(), is( equalTo( 0 ) ) );
    }
    
    
    @Test
    public void shouldnullDateValidated() {
    	
    		Date date1 = null;
    		RbpsValidationAppender.addNotFutureDateValidation( date1, "Test", repository );
    		
    	assertThat( repository.getValidationMessages().size(), is( equalTo( 0 ) ) );
    }
}
