package gov.va.vba.rbps.services.ws.client.handler.org;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.handler.RbpsWSHandler;
import gov.va.vba.rbps.services.ws.client.interfaces.OrgWebServiceWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbers;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbersResponse;

public class OrgWebServiceWSHandler extends RbpsWSHandler implements OrgWebServiceWSHandlerInterface {
    @Override
    public FindPOAsByFileNumbersResponse findPOAsByFileNumber(final RbpsRepository repo) throws RbpsWebServiceClientException {
        FindPOAsByFileNumbers request = new FindPOAsByFileNumbers();
        request.getFileNumbers().add(repo.getVeteran().getFileNumber());
        return (FindPOAsByFileNumbersResponse) getResponse(request, repo);
    }


}
