package gov.va.vba.rbps.services.ws.client.handler.awards;



import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.checkerService.CheckServicesByAppName;
import gov.va.vba.rbps.services.ws.client.mapping.awards.checkerService.CheckServicesByAppNameResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.checkerService.ServiceCheckInputVO;



public class CheckerServiceWSHandler extends BaseWSHandler<CheckServicesByAppName, CheckServicesByAppNameResponse>{
	

	@Override
	    protected CheckServicesByAppName buildRequest( final RbpsRepository	repository ) {
		 
		 CheckServicesByAppName	checkServices	=	new CheckServicesByAppName();
		 ServiceCheckInputVO	inputVO			= new ServiceCheckInputVO();
		 
		 inputVO.setAppName("rbps_appcompchecker");
		 //CCR2557 set ShowDetails to Y
		 inputVO.setShowDetails("Y");
		 checkServices.setServiceCheckInput(inputVO);
		 
		 return checkServices;
	 }
}
