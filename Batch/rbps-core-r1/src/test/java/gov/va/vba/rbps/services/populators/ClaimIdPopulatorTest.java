package gov.va.vba.rbps.services.populators;

import org.junit.Before;
import org.junit.Test;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.FindBenefitClaimResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;






public class ClaimIdPopulatorTest   extends RbpsAbstractTest {
	
	
	private ClaimIdPopulator	populator 	= new ClaimIdPopulator();
	private RbpsRepository   	repo 		= new RbpsRepository();
	private TestUtils       	testUtils   = new TestUtils();
	
	
	
    @Override
    @Before
    public void setup() {

        super.setup();
        
    }

    @Test
    public void shouldPopulateClaimId(){
    	
    	testUtils.populateUserInfo( repo, "gov/va/vba/rbps/services/impl/userInfo.response.xml" );
    	FindBenefitClaimResponse response = testUtils.getFindBenefitClaimResponseFromXml("gov/va/vba/rbps/services/populators/findBenefitClaimResponse.xml");
    	populator.populateClaimId(response.getReturn(), repo);
    	
    }
}
