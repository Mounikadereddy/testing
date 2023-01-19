/*
 * UpdateBenefitClaimWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;


import static org.junit.Assert.assertEquals;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.lettergeneration.GeneratePdfTest;
import gov.va.vba.rbps.services.populators.RbpsRepositoryPopulator;
import gov.va.vba.rbps.services.ws.client.WSOutputXMLReader;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.BenefitClaimRecord;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.UserInformation;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Test;


/**
 *      Test case for UpdateBenefitClaimWSHandler class
 */
public class UpdateBenefitClaimWSHandlerTest extends RbpsAbstractTest {

	
    private UpdateBenefitClaimWSHandler updateBenefitClaimWSHandler;
    private RbpsRepository              repository;
    private TestUtils       			testUtils   = new TestUtils();

    @Override
    @Before
    public void setup() {

        super.setup();
        repository = ( RbpsRepository ) getBean ( "repository" );
        updateBenefitClaimWSHandler = (UpdateBenefitClaimWSHandler) getBean( "updateBenefitClaimWSHandler" );
        
    }


    @Test
    public void testUpdateBenefitClaim() {

//    	testUtils.populateUserInfo( repository, "gov/va/vba/rbps/services/impl/endPrdctType131.userInfo.response.xml" );

        updateBenefitClaimWSHandler.updateBenefitClaim( repository ).getReturn();

    }


    @Test
    public void testUpdateBenefitClaimWithInvalidClaimId() {

        try {
            RbpsRepositoryPopulator rbpsRepositoryPopulator = new RbpsRepositoryPopulator();
            RbpsRepository repo = ( RbpsRepository) getBean("repository");
//            rbpsRepositoryPopulator.setRepository( repo );

            UserInformation userInformation = WSOutputXMLReader.readUserInformationResponseWithInvalidClaimId().getReturn().getUserInformation().get(0);
//            rbpsRepositoryPopulator.populateFromVonapp(userInformation);
            repo.getVeteran().setFileNumber( CommonFactory.getRandomFileNumber() );

            BenefitClaimRecord response = updateBenefitClaimWSHandler.updateBenefitClaim( repository ).getReturn();

            assertEquals("GUIE02214", response.getReturnCode());
        }
        catch ( Throwable ex ) {

            new SoapFaultPrinter().logSoapFaultInfo( ex );
        }
    }
}
