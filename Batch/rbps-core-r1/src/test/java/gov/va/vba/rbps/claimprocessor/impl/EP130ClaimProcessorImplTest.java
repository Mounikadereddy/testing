/*
 * EP130ClaimProcessorImplTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.claimprocessor.impl;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsClaimDataException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuleExecutionException;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;

import org.junit.Before;
import org.junit.Test;



//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/applicationxml", "classpath:/applicationContext-test.xml"})
public class EP130ClaimProcessorImplTest extends RbpsAbstractTest {

    private EP130ClaimProcessorImpl     processor;
    private RbpsRepository              repository;


    @Override
    @Before
    public void setup() {

        super.setup();

        processor  = (EP130ClaimProcessorImpl)  getBean( "ep130ClaimProcessorImpl" );
        repository = (RbpsRepository)  getBean( "repository" );
//        CommonUtils.setLogToStdout( false );
    }


    @Test
    public void shouldRunAllPopulators() throws RbpsRuleExecutionException, RbpsClaimDataException {

        try {
            repository.setVeteran( CommonFactory.adamsVeteran() );

//            processor.processClaim();
        }
        catch ( Throwable ex ) {

            System.out.println( new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            ex.printStackTrace();
        }
    }
}
