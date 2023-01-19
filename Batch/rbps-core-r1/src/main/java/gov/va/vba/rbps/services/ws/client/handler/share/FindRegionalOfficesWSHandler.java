/*
 * FindRegionalOfficesWSHandler.java
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
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindRegionalOffices;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindRegionalOfficesResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 *      Grabs the full list of regional offices.
 */
public class FindRegionalOfficesWSHandler {


    private static Logger logger = Logger.getLogger(FindRegionalOfficesWSHandler.class);

    private final LogUtils              logUtils    = new LogUtils( logger, true );

    private WebServiceTemplate      webServiceTemplate;
    private String                  webServiceUri;


    public FindRegionalOfficesResponse findRegionalOffices( final RbpsRepository repository ) throws RbpsWebServiceClientException {

        logUtils.debugEnter( repository );

        FindRegionalOffices request = new FindRegionalOffices();

        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive( webServiceUri,
                                                                 request,
                                                                 new HeaderSetter( webServiceUri, repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = "Call to WS StandardDataWebServiceBean.findStations failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage( webServiceUri,
                                                                               request.getClass().getSimpleName(),
                                                                               rootCause.getMessage() ) );
            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit( repository );
        }

        return (FindRegionalOfficesResponse) response;
    }



    public void setWebServiceUri( final String findStationsDataUri ) {

    	String clusterAddr = CommonUtils.getClusterAddress();
        this.webServiceUri = clusterAddr + "/" + findStationsDataUri;
    }

    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
