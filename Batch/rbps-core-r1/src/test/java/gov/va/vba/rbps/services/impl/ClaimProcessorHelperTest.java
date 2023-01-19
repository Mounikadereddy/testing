package gov.va.vba.rbps.services.impl;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.claimprocessor.util.ClaimProcessorHelper;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




public class ClaimProcessorHelperTest extends RbpsAbstractTest {

    private ClaimProcessorHelper    claimProcessorHelper;
    private RbpsRepository          repository;
    private TestUtils               testUtils   = new TestUtils();

    @Override
    @Before
    public void setup() {
        super.setup();

        claimProcessorHelper	= (ClaimProcessorHelper) getBean("claimProcessorHelper");
        repository				= (RbpsRepository) getBean ( "repository" );
    }
    
    
    
    @Test
    public void testClaimProcessorHelper() {

//    	testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/militaryPay.userInfo.response.xml" );
//    	claimProcessorHelper.updateClaimLabelToRejectVersion();
    } 
}
