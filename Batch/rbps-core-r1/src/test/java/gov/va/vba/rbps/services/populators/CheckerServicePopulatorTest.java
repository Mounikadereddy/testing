package gov.va.vba.rbps.services.populators;

import org.junit.Before;
import org.junit.Test;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;




public class CheckerServicePopulatorTest  extends RbpsAbstractTest {

	
	CheckerServicePopulator	populator;	
	
    @Override
    @Before
    public void setup() {

        super.setup();

        populator = (CheckerServicePopulator) getBean( "checkerServicePopulator" );

    }
    
    
    @Test
    public void shouldAllServicesAreUp() {

    	 RbpsRepository	repo	= new RbpsRepository();
    	 try {
    		 
    		 populator.ifAllServicesAreUp( repo );
    		 
    	 } catch ( Exception ex ) {
    		 
    	 }
    }
	
}
