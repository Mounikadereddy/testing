package gov.va.vba.rbps.services.ws.client.handler.awards;

import org.junit.Before;
import org.junit.Test;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.populators.PersonServiceBySsnPopulator;
import gov.va.vba.rbps.services.ws.client.handler.awards.PersonServiceWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.personService.FindPersonsBySsnsResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;



public class PersonServiceWSHandlerTest   extends RbpsAbstractTest {
	
	
    private RbpsRepository                  repository;
    private PersonServiceWSHandler			personServiceWSHandler;
    
    
	   @Override
	    @Before
	    public void setup() {

	        super.setup();
	        repository = new RbpsRepository();
	        personServiceWSHandler = (PersonServiceWSHandler) getBean( "personServiceWSHandler" );
	    }
	   
	   
	    @Test
	    public void tesPersonServiceWS() {
	    	
	       TestUtils   testUtils   = new TestUtils();
	       testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/schoolBeginDate.userInfo.response.xml" ); 
	       FindPersonsBySsnsResponse response = personServiceWSHandler.call(repository);
	       
	       PersonServiceBySsnPopulator	populator = new PersonServiceBySsnPopulator();
	       populator.populateCorporateDependentsBySsns(response, repository);
	   }

}
