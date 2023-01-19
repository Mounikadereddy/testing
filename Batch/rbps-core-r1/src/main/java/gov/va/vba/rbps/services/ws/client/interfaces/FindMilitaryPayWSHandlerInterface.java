package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.awards.militarypay.FindMilitaryPayResponse;

public interface FindMilitaryPayWSHandlerInterface {
	
	FindMilitaryPayResponse findMilitaryPay( final RbpsRepository repository ) throws RbpsWebServiceClientException;


}
