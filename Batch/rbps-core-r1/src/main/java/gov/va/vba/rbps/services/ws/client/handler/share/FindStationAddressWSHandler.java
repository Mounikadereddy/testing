/*
 * FindStationAddressWSHandler.java
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
import gov.va.vba.rbps.services.ws.client.mapping.share.findstations.FindStationAddress;
import gov.va.vba.rbps.services.ws.client.mapping.share.findstations.FindStationAddressResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 *      Given an SOJ id, call a ws to get the address.
 */
public class FindStationAddressWSHandler {


    private static Logger logger = Logger.getLogger(FindStationAddressWSHandler.class);

    private final LogUtils              logUtils    = new LogUtils( logger, true );

    private WebServiceTemplate      webServiceTemplate;
    private String                  findStationsDataUri;


    public FindStationAddressResponse findStationAddress( final String stationId, final RbpsRepository 	repository ) throws RbpsWebServiceClientException {

        logUtils.debugEnter( repository );

        FindStationAddress request = getRequestObject( stationId );

        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive( findStationsDataUri,
                                                                 request,
                                                                 new HeaderSetter( findStationsDataUri, repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = "Call to WS ShareStandardDataWebService.findStationAdderess failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage( findStationsDataUri,
                                                                                request.getClass().getSimpleName(),
                                                                                rootCause.getMessage() ) );
            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit( repository );
        }

        return (FindStationAddressResponse) response;
    }


    private FindStationAddress  getRequestObject( final String stationId ) {

        FindStationAddress address     = new FindStationAddress();

        address.setStationNumber( stationId );

        return address;
    }


    public void setFindStationsDataUri( final String findStationsDataUri ) {

    	String clusterAddr = CommonUtils.getClusterAddress();
        this.findStationsDataUri = clusterAddr + "/" + findStationsDataUri;
    }

    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
