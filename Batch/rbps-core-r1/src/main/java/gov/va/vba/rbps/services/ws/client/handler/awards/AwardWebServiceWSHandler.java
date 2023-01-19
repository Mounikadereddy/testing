package gov.va.vba.rbps.services.ws.client.handler.awards;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;

import gov.va.vba.rbps.services.ws.client.handler.RbpsWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService.FindStationOfJurisdiction;
import gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService.FindStationOfJurisdictionResponse;


public class AwardWebServiceWSHandler extends RbpsWSHandler {

    public FindStationOfJurisdictionResponse findStationOfJurisdiction(final RbpsRepository repo) throws RbpsWebServiceClientException  {
        FindStationOfJurisdiction request = new FindStationOfJurisdiction();

        request.setPtcpntBeneId( repo.getVeteran().getCorpParticipantId() );
        request.setPtcpntVetId( repo.getVeteran().getCorpParticipantId() );


        return (FindStationOfJurisdictionResponse) getResponse(request, repo);
    }
}
