package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service.GetPersonProfileResponse;

/**
 * @author VBACOShahP
 *
 */
public interface GetPersonProfileWSHandlerInterface {
	GetPersonProfileResponse getPersonProfile(RbpsRepository repository) throws RbpsWebServiceClientException;
}
