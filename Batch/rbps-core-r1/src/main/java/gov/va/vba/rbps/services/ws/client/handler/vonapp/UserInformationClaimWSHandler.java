/*
 * UserInformationWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;

import static gov.va.vba.rbps.coreframework.util.RbpsConstants.CLAIM_STATUS_READY_FOR_RBPS;
import static gov.va.vba.rbps.coreframework.util.RbpsConstants.USER_IDENTITY_TYPE;
import static gov.va.vba.rbps.coreframework.util.RbpsConstants.USER_IDENTITY_VALUE;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSupplied;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSuppliedResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.GetClaimInfo;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.GetClaimInfoResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.UserInformationInput;

import org.apache.commons.lang.StringUtils;

/**
 * Retrieves all the claim data from UserInformation Web Service
 */
public class UserInformationClaimWSHandler extends
		BaseWSHandler<GetClaimInfo, GetClaimInfoResponse> {

	private static Logger logger = Logger
			.getLogger(UserInformationClaimWSHandler.class);

	public GetClaimInfoResponse getCalaimInfoResponse(final RbpsRepository repo) {

		GetClaimInfo request = buildClaimInfoRequest(repo.getVeteran().getFileNumber(),repo.getVeteran().getCorpParticipantId());

		return call(repo, request);
	}

	private GetClaimInfo buildClaimInfoRequest(String fileNumber, long corpParticipantId) {

		GetClaimInfo getClaimInfo = new GetClaimInfo();
		getClaimInfo.setArg0(fileNumber);
		getClaimInfo.setArg1(corpParticipantId);

		return getClaimInfo;
	}

	@Override
	protected GetClaimInfo buildRequest(final RbpsRepository repo) {

		// TODO Auto-generated method stub
		return null;
	}

}
