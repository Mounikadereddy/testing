/*
 * UpdateBenefitClaimWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.coreframework.xom.EndProductType;
import gov.va.vba.rbps.services.ws.client.interfaces.UpdateBenefitClaimWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaim;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaim.BenefitClaimUpdateInput;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * This class calls BenefitClaimWebService to update the claim label.
 */
public class UpdateBenefitClaimWSHandler implements
		UpdateBenefitClaimWSHandlerInterface {

	private static Logger logger = Logger
			.getLogger(UpdateBenefitClaimWSHandler.class);

	// protected CommonUtils utils = new CommonUtils();
	private LogUtils logUtils = new LogUtils(logger, true);

	private WebServiceTemplate webServiceTemplate;
	private String updateBenefitClaimUri;

	//private RbpsRepository repository;

	/**
	 * Calls the AWARD UpdateBenefitClaim web service, gets the result and
	 * returns it
	 */
	@Override
	public UpdateBenefitClaimResponse updateBenefitClaim(RbpsRepository repository)
			throws RbpsWebServiceClientException {

		logUtils.debugEnter(repository);

		UpdateBenefitClaim request = buildUpdateBenefitClaimRequest(repository);
	 	 logger.debug("updateBenefitClaim)***************************request*: "+request); 
	     
		UpdateBenefitClaimResponse response;

		try {

			response = (UpdateBenefitClaimResponse) webServiceTemplate
					.marshalSendAndReceive(
							updateBenefitClaimUri,
							request,
							new HeaderSetter(updateBenefitClaimUri, repository
									.getClaimStationAddress()));
			 logger.debug("updateBenefitClaim)***************************response*: "+response); 
			   
		} catch (Throwable rootCause) {

			String detailMessage = "Call to WS BenefitClaimWebService.updateBenefitClaim failed";
			repository.addValidationMessage(CommonUtils.getValidationMessage(
					updateBenefitClaimUri, request.getClass().getSimpleName(),
					rootCause.getMessage()));

			logger.error(" ***RBPS: [" + detailMessage + "]");
			logger.debug(rootCause.getMessage());

			rootCause.printStackTrace();
			throw new RbpsWebServiceClientException(detailMessage, rootCause);
		} finally {
			logUtils.debugExit(repository);
		}

		logger.debug("Label Return Message:"
				+ response.getReturn().getReturnMessage());

		return response;
	}

	/**
	 * Sets parameters for UpdateBenefitClaim
	 * 
	 * @return UpdateBenefitClaim
	 */
	private UpdateBenefitClaim buildUpdateBenefitClaimRequest(RbpsRepository repository) {

		UpdateBenefitClaim updateBenefitClaim = new UpdateBenefitClaim();

		updateBenefitClaim
				.setBenefitClaimUpdateInput(setBenefitClaimUpdateInput(repository));

		return updateBenefitClaim;
	}

	/**
	 * Sets BenefitClaimUpdateInput with hardcoded values
	 * 
	 * @return BenefitClaimUpdateInput
	 */
	private BenefitClaimUpdateInput setBenefitClaimUpdateInput(RbpsRepository repository) {

		BenefitClaimUpdateInput benefitClaimUpdateInput = new BenefitClaimUpdateInput();

		EndProductType endProductCode = repository.getVeteran().getClaim()
				.getEndProductCode();

		if (endProductCode != null) {

			benefitClaimUpdateInput.setOldEndProductCode(endProductCode
					.getValue());
		}

		Date receivedDate = repository.getVeteran().getClaim()
				.getReceivedDate();

		if (receivedDate != null) {

			benefitClaimUpdateInput.setDateOfClaim(new SimpleDateFormat(
					RbpsConstants.DATE_FORMAT).format(receivedDate));
			benefitClaimUpdateInput.setOldDateOfClaim(new SimpleDateFormat(
					RbpsConstants.DATE_FORMAT).format(receivedDate));
		}

		//
		// Per Cory email Nov 9th, updateBenefitClaim wants ssn file numbers,
		// not non-ssn file numbers,
		// so we're sending ssn for file number.
		//
		benefitClaimUpdateInput.setFileNumber(repository.getVeteran().getSsn());
		benefitClaimUpdateInput.setNewEndProductLabel(repository.getVeteran()
				.getClaim().getClaimLabel().getCode());

		benefitClaimUpdateInput.setPayeeCode(RbpsConstants.PAYEE_CODE);
		benefitClaimUpdateInput.setSectionUnitNo(RbpsConstants.SECTION_UNIT_NO);
		benefitClaimUpdateInput
				.setBenefitClaimType(RbpsConstants.BENEFIT_CLAIM_TYPE);
		benefitClaimUpdateInput.setDisposition(RbpsConstants.DISPOSITION);
		benefitClaimUpdateInput
				.setFolderWithClaim(RbpsConstants.FOLDER_WITH_CLAIM);

		return benefitClaimUpdateInput;
	}

	public void setWebServiceTemplate(
			final WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}

	public void setUpdateBenefitClaimUri(final String updateBenefitClaimUri) {
		String clusterAddr = CommonUtils.getClusterAddress();
		this.updateBenefitClaimUri = clusterAddr + "/" + updateBenefitClaimUri;
	}

	/*
	 public void setRepository(final RbpsRepository repository) {
	  this.repository = repository; }
	 */
}
