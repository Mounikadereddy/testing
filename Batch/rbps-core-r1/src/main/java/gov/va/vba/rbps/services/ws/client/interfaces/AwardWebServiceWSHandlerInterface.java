package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService.FindStationOfJurisdictionResponse;

public interface AwardWebServiceWSHandlerInterface {
    public FindStationOfJurisdictionResponse findStationOfJurisdiction(RbpsRepository repo);
}
