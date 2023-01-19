package gov.va.vba.rbps.services.ws.client.handler.awards;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.lettergeneration.ProcessMilitaryPay;
import gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision.ReadRetiredPayDecnResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;
import gov.va.vba.rbps.services.ws.client.util.XmlUnmarshaller;




public class ProcessMilitaryPayWSHandlerTest extends RbpsAbstractTest{
	
	
	private	ProcessMilitaryPayWSHandler		processMilitaryPayWSHandler;
	private	ProcessMilitaryPay				processMilitaryPay;
	private	RbpsRepository 					repository = new RbpsRepository();
	private TestUtils       				testUtils   = new TestUtils();

    @Override
    @Before
    public void setup() {
        super.setup();

//        repository = (RbpsRepository) getBean( "repository" );
        processMilitaryPayWSHandler = (ProcessMilitaryPayWSHandler)getBean( "processMilitaryPayWSHandler" );
        processMilitaryPay = (ProcessMilitaryPay)getBean("processMilitaryPay");
    }
	
	
	@Test
	 public void testProcessMilitaryPayWSHandler() {
		
		testUtils.populateUserInfo( repository,  "gov/va/vba/rbps/lettergeneration/militaryPay.userInfo.response.xml" );
		ProcessAwardDependentResponse   response    =	(ProcessAwardDependentResponse) new XmlUnmarshaller().loadResponse( 
														"gov/va/vba/rbps/lettergeneration/militaryPay.awards.response.xml" ,
                										ProcessAwardDependentResponse.class );
//		ReadRetiredPayDecnResponse	response	=	processMilitaryPayWSHandler.call( repository );
		processMilitaryPay.processMilitaryPayInformation(repository, response);
		
	}
	
	
	

}
