/*
 * SojSignatureWSHandler
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindSignaturesByStationNumber;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindSignaturesByStationNumberResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;



/**
 *      Given an SOJ id, find out all the people who can sign letters for that SOJ.
 */
public class SojSignatureWSHandler {

    private static Logger logger = Logger.getLogger(SojSignatureWSHandler.class);

    private final LogUtils              logUtils    = new LogUtils( logger, true );

    // Spring beans references/injection
    private WebServiceTemplate          webServiceTemplate;
    private String                      webServiceUri;


    public FindSignaturesByStationNumberResponse getSignatures( final RbpsRepository        repo,
                                                                final String                stationId ) throws RbpsWebServiceClientException {

        logUtils.debugEnter( repo );

        FindSignaturesByStationNumber request = getRequestObject( stationId );

        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive( webServiceUri,
                                                                 request,
                                                                 new HeaderSetter( webServiceUri, repo.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = "Call to WS ShareStandardDataWebService.getSignatures failed";

            repo.addValidationMessage( CommonUtils.getValidationMessage( webServiceUri,
                                                                         request.getClass().getSimpleName(),
                                                                         rootCause.getMessage() ) );

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit( repo );
        }

        return (FindSignaturesByStationNumberResponse) response;
    }




    private FindSignaturesByStationNumber getRequestObject( final String stationId ) {

        FindSignaturesByStationNumber  request = new FindSignaturesByStationNumber();
        request.setStationNumber( stationId );

        return request;
    }


    public void setWebServiceUri( final String webServiceUri ) {

    	String clusterAddr = CommonUtils.getClusterAddress();
        this.webServiceUri = clusterAddr + "/" + webServiceUri;
    }

    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
    public void setLogit( final boolean logit ) {

        logUtils.setLogit( logit );
    }
}
