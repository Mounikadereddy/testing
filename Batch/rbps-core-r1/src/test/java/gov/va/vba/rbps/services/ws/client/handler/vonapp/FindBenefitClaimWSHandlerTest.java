/*
 * FindBenefitClaimWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Claim;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.populators.ClaimIdPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.BenefitClaimRecord;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.FindBenefitClaimResponse;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


/**
 *      Test case for FindBenefitClaimWSHandler class
 */
public class FindBenefitClaimWSHandlerTest extends RbpsAbstractTest {

    private FindBenefitClaimWSHandler   findBenefitClaimWSHandler;
    private RbpsRepository              repo;
    private TestUtils					testUtils	= new TestUtils();


    @Override
    @Before
    public void setup() {

        super.setup();

        findBenefitClaimWSHandler = (FindBenefitClaimWSHandler)  getBean( "findBenefitClaimWSHandler" );
        repo = ( RbpsRepository)  getBean("repository");
    }


    @Ignore
    @Test
    public void shouldUserInfoIdMatchWithBenifitClaimId() {

    	//testUtils.populateUserInfo( repo, "gov/va/vba/rbps/services/populators/benifitClaim.userInfo.response.xml" ); 
        FindBenefitClaimResponse	response	= 
        						testUtils.getFindBenefitClaimResponseFromXml( "gov/va/vba/rbps/services/populators/findBenefitClaimResponse.xml" );

        ClaimIdPopulator  populator = new ClaimIdPopulator( );
//        populator.setRepository( repo );
        populator.populateClaimId( response.getReturn(), repo );

        assertThat("Records found.", is(equalTo(response.getReturn().getReturnMessage()) ) );
        assertThat( repo.getVeteran().getClaim().getClaimId(), is(equalTo( 231728L ) ) );
    }

    
    @Test
    public void shouldUserInfoIdNotMatchWithBenifitClaimId() {

    	//testUtils.populateUserInfo( repo, "gov/va/vba/rbps/services/populators/benifitClaim1.userInfo.response.xml" ); 
        FindBenefitClaimResponse	response	= 
        						testUtils.getFindBenefitClaimResponseFromXml( "gov/va/vba/rbps/services/populators/findBenifitClaim1.response.xml" );

        ClaimIdPopulator  populator = new ClaimIdPopulator();
//      populator.setRepository( repo );
        populator.populateClaimId( response.getReturn(), repo );

        assertThat( repo.getVeteran().getClaim().getClaimId(), is(equalTo( 30449078L ) ) );
    }   

    
    
    @Ignore
    @Test
    public void testFindBenefitClaimWithInvalidClaimId() {

        try { 
        	
            repo.setVeteran( CommonFactory.adamsVeteran() );
            repo.getVeteran().setFileNumber( CommonFactory.getRandomFileNumber() );
            BenefitClaimRecord response = findBenefitClaimWSHandler.findBenefitClaim( repo ).getReturn();

            assertThat("Records found.", is(equalTo(response.getReturnMessage()) ) );
        }
        catch ( Throwable ex ) {

            new SoapFaultPrinter().logSoapFaultInfo( ex );
        }
    }
}
