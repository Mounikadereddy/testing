/*
 * FindMilitaryPayWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.services.ws.client.handler.awards;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.interfaces.FindMilitaryPayWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.awards.militarypay.FindMilitaryPay;
import gov.va.vba.rbps.services.ws.client.mapping.awards.militarypay.FindMilitaryPayResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;

/**
 * This class is a data access class.
 * It is a web service client class to the services
 * provided by AWARD team.
 */
public class FindMilitaryPayWSHandler implements FindMilitaryPayWSHandlerInterface{

    private static Logger logger = Logger.getLogger(FindMilitaryPayWSHandler.class);

    private LogUtils                    logUtils                = new LogUtils( logger, true );

    // Spring beans references/injection
    private WebServiceTemplate          webServiceTemplate;
    private String                      findMilitaryPayUri;


    /**
     *     This method calls the AWARDS service
     *     ReadMilitaryPay.findMilitaryPay
     *
     *     @return FindDependentOnAwardResponse
     *     @throws RbpsWebServiceClientException
     */
    @Override
    public FindMilitaryPayResponse findMilitaryPay( final RbpsRepository repository ) throws RbpsWebServiceClientException {

        logUtils.debugEnter( repository );

        FindMilitaryPay request = buildFindMilitaryPayRequest( repository );


        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive( findMilitaryPayUri,
                                                                 request,
                                                                 new HeaderSetter( findMilitaryPayUri,
                                                                                   repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = "Call to WS " + findMilitaryPayUri + " failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage(findMilitaryPayUri, request.getClass().getSimpleName(), rootCause.getMessage()) );
            logger.error(" ***RBPS: [" + detailMessage + "]");

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit( repository );
        }

        return (FindMilitaryPayResponse) response;
    }


    /**
     *      prepare a FindMilitaryPay request object
     *
     *      @return FindMilitaryPay
     */
    private FindMilitaryPay buildFindMilitaryPayRequest( final RbpsRepository repository ) {

        FindMilitaryPay findMilitaryPay = new FindMilitaryPay();
        findMilitaryPay.setVeteranID(Long.toString(repository.getVeteran()
                .getCorpParticipantId()));

        return findMilitaryPay;
    }


    public void setFindMilitaryPayUri(final String findMilitaryPayUri) {
	String clusterAddr = CommonUtils.getClusterAddress();
        this.findMilitaryPayUri = clusterAddr + "/" + findMilitaryPayUri;
    }

    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }
}
