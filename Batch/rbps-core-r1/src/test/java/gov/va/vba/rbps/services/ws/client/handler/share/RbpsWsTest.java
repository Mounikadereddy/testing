package gov.va.vba.rbps.services.ws.client.handler.share;

import org.junit.Before;
import org.junit.Test;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.services.ws.RbpsWS;
import gov.va.vba.rbps.services.ws.RbpsWsException;





public class RbpsWsTest   extends RbpsAbstractTest {

	private RbpsWS				rbpsWs;
	
	
	
    @Override
    @Before
    public void setup() {

        super.setup();

        rbpsWs = (RbpsWS) getBean( "rbpsWS" );

    }
    
	   @Test
	    public void shouldRbpsWs() throws RbpsWsException{
		   
		   rbpsWs.processRbpsAmendDependency("2", "1");
	   }
	
}
