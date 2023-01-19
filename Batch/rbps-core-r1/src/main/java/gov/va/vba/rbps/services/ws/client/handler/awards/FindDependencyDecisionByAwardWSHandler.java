/*
 * FindDependencyDecisionByAwardWSHandler
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAward;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;


public class FindDependencyDecisionByAwardWSHandler extends BaseWSHandler<FindDependencyDecisionByAward,FindDependencyDecisionByAwardResponse> {




    @Override
    protected FindDependencyDecisionByAward buildRequest( final RbpsRepository        repo ) {

        FindDependencyDecisionByAward   findDependencyDecisionByAward   = new FindDependencyDecisionByAward();
        long                            vetCorpId                       = repo.getVeteran().getCorpParticipantId();

        findDependencyDecisionByAward.setVeteranId( vetCorpId );
        findDependencyDecisionByAward.setBeneficiaryId( vetCorpId );
        findDependencyDecisionByAward.setAwardType( RbpsConstants.AWARD_TYPE );

        return findDependencyDecisionByAward;
    }
}
