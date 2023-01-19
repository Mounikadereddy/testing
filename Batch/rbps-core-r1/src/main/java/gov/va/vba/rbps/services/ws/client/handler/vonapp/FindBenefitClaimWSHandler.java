/*
 * FindBenefitClaimWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.FindBenefitClaim;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.FindBenefitClaimResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 *      This class calls BenefitClaimWebService to get a list of claims that match the file number.
 */
public class FindBenefitClaimWSHandler {

    private static Logger logger = Logger.getLogger(FindBenefitClaimWSHandler.class);

    // EK - removed must be passed in 
    //protected CommonUtils           utils;

    private WebServiceTemplate      webServiceTemplate;
    private String                  findBenefitClaimUri;
    
    // EK - part of new CommonUtils
    //private RbpsRepository          repository;

    
    public FindBenefitClaimWSHandler(){}
    
      /**
     *     Calls the AWARD FindBenefitClaim web service, gets the result and returns it
     */
    public FindBenefitClaimResponse findBenefitClaim(RbpsRepository repo) throws RbpsWebServiceClientException{

        logger.debug(" ----> [ "+ this.getClass().getSimpleName() + ".findBenefitClaim()]...");
        FindBenefitClaim              request = buildFindBenefitClaimRequest(repo);
        FindBenefitClaimResponse      response;

        try{

            response = (FindBenefitClaimResponse) webServiceTemplate.marshalSendAndReceive(
                                        findBenefitClaimUri,
                                        request,
                                        new HeaderSetter( findBenefitClaimUri, repo.getClaimStationAddress() ) );
        }
        catch(Throwable rootCause) {

            String detailMessage = "Call to WS BenefitClaimWebService.findBenefitClaim failed";
            repo.addValidationMessage( CommonUtils.getValidationMessage(findBenefitClaimUri, request.getClass().getSimpleName(), rootCause.getMessage()) );

            logger.error(" ***RBPS: [" + detailMessage + "]");
            logger.debug(rootCause.getMessage());

            rootCause.printStackTrace();
            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }

        logger.debug(" *** RBPS find benefit claim Return Message:"
                + response.getReturn().getReturnMessage());
        logger.debug(" <---- [ "+ this.getClass().getSimpleName() + ".findBenefitClaim()]...");

        return  response;
    }


    /**
     * Sets parameters for FindBenefitClaim
     *
     * @return FindBenefitClaim
     */
    private static final FindBenefitClaim buildFindBenefitClaimRequest(RbpsRepository repo) {

        FindBenefitClaim findBenefitClaim = new FindBenefitClaim();

        findBenefitClaim.setFileNumber( repo.getVeteran().getFileNumber() );

        return findBenefitClaim;
    }


    public void setWebServiceTemplate(final WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public void setfindBenefitClaimUri(final String findBenefitClaimUri) {
    	String clusterAddr = CommonUtils.getClusterAddress();
        this.findBenefitClaimUri = clusterAddr + "/" + findBenefitClaimUri;
    }

    /* EK removed
    public void setRepository(final RbpsRepository repository) {
        this.repository = repository;
    }
    */
}
