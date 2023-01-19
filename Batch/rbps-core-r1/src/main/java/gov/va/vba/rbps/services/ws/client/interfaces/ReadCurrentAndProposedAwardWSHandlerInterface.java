package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.ReadCurrentAndProposedAwardResponse;

public interface ReadCurrentAndProposedAwardWSHandlerInterface {

    /**
     * This method calls the SHARE service
     * FamilyTree.findDependents
     * @author Shane Shahamat
     * @return findCurrentAndProposedAward
     * @throws RbpsWebServiceClientException
     */
    ReadCurrentAndProposedAwardResponse readCurrentAndProposedAward(RbpsRepository repository) throws RbpsWebServiceClientException;

}
