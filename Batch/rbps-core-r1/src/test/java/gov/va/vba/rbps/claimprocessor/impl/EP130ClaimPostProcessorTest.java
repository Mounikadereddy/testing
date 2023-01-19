/*
 * EP130ClaimPostProcessorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimprocessor.impl;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.lettergeneration.AwardPrintSaver;
import gov.va.vba.rbps.lettergeneration.GeneratePdf;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/applicationxml", "classpath:/applicationContext-test.xml"})
public class EP130ClaimPostProcessorTest extends RbpsAbstractTest {

    private EP130ClaimPostProcessor     postProcessor;
    private RbpsRepository              repository = new RbpsRepository();
    private TestUtils                   testUtils   = new TestUtils();

    @Override
    @Before
    public void setup() {

        super.setup();

        postProcessor   = (EP130ClaimPostProcessor) getBean( "ep130ClaimPostProcessor" );
        testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/fiduciary.userInfo.response.xml" );
    	repository.setClaimStationLocationId( 335L );
    	repository.getVeteran().setServiceConnectedDisabilityRating( 40.0 );
    	repository.getVeteran().setRatingDate( new Date() );
    	repository.getVeteran().setRatingEffectiveDate( new Date() );
    }


    @Test
    public void shouldSavePdfFiles() throws Throwable {

        try {

            postProcessor.postProcess( repository );
        }
        catch ( Throwable ex ) {

            System.out.println( new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            ex.printStackTrace();

            throw ex;
        }
    }


    @Test
    public void shouldProcessAward() throws Throwable {

        try {

            postProcessor.processAward( repository );
        }
        catch ( Throwable ex ) {

            System.out.println( new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            ex.printStackTrace();

            throw ex;
        }
    }


    @Test
    public void shouldProcessAwardAndSavepdf() throws Throwable {

        try {

            ProcessAwardDependentResponse response = postProcessor.processAward( repository );
            postProcessor.generatePdfAndCsv( response, repository );
        }
        catch ( Throwable ex ) {

            System.out.println( new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            ex.printStackTrace();

            throw ex;
        }
    }
}
