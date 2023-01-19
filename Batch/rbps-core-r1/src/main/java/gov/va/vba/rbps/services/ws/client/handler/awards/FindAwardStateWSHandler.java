/*
 * FindAwardsStateWebServicesHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.state.FindAwardState;
import gov.va.vba.rbps.services.ws.client.mapping.awards.state.FindAwardStateResponse;


/**
 *      This class is a data access class.
 *      It is a web service client class to the services
 *      provided by AWARD team.
 */
public class FindAwardStateWSHandler extends BaseWSHandler<FindAwardState,FindAwardStateResponse> {



    /**
     *      Helper method, prepare a FindAwardState request object
     *      @return FindAwardState
     */
    @Override
    protected FindAwardState buildRequest( final RbpsRepository   repo ) {

        FindAwardState request = new FindAwardState();

        request.setAwardType( RbpsConstants.AWARD_TYPE );
        request.setBeneficiaryID( repo.getVeteran().getCorpParticipantId() );
        request.setVeteranID( repo.getVeteran().getCorpParticipantId() );

        return request;
    }
}
