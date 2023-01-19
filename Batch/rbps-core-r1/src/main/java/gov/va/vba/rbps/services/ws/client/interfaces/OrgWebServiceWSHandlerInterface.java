package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbersResponse;

public interface OrgWebServiceWSHandlerInterface {
    FindPOAsByFileNumbersResponse findPOAsByFileNumber (final RbpsRepository repo) throws RbpsWebServiceClientException;
}
