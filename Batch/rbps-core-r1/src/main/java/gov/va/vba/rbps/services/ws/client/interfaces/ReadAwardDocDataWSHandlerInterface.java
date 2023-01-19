package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service.ReadAwardDocDataResponse;

public interface ReadAwardDocDataWSHandlerInterface {
	
	ReadAwardDocDataResponse readAwardDocData(RbpsRepository repository) throws RbpsWebServiceClientException;
}
