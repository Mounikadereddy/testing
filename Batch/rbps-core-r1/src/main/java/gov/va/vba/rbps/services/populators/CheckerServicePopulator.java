package gov.va.vba.rbps.services.populators;



import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.RbpsWsException;
import gov.va.vba.rbps.services.ws.client.RbpsFaultBean;
import gov.va.vba.rbps.services.ws.client.handler.awards.CheckerServiceWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.checkerService.CheckServicesByAppNameResponse;






public class CheckerServicePopulator {
	
	
	private	CheckerServiceWSHandler	checkerServiceWSHandler;
	
	
	public boolean ifAllServicesAreUp( RbpsRepository repository ) throws RbpsWsException {
		

		CheckServicesByAppNameResponse response	= checkerServiceWSHandler.call( repository );
		
		RbpsFaultBean faultInfo = new RbpsFaultBean();
        faultInfo.setMessage("CheckServicesByAppNameResponse returned null");
        
		if ( response == null ) {

			throw new RbpsWsException( "RbpsWS runtime exception: ", faultInfo  );
		}
		
		if ( response.getServiceCheckStatusResponse() == null ) {
			
			throw new RbpsWsException( "RbpsWS runtime exception: ", faultInfo  );
		}
		
		String	status	=	response.getServiceCheckStatusResponse().getServiceStatus(); 
		
		if ( status == null ) {
			
			return false;
		}
		
		if ( status.equalsIgnoreCase("success") ){
			
			return true;
		}
		
		return false;
	}
	
	
	
	public void setCheckerServiceWSHandler( CheckerServiceWSHandler checkerServiceWSHandler ) {
		
		this.checkerServiceWSHandler = checkerServiceWSHandler;
	}

}
