/*
 * HeaderSetter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.util;


import gov.va.vba.rbps.coreframework.dto.ClaimStationAddress;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.soap.SoapMessage;


/**
 *      When called by the soap transport, inject the security
 *      headers into the xml.
 */
public class HeaderSetter implements WebServiceMessageCallback {


    private String        uri;
    private String        stationId       = null;


    public HeaderSetter( final String     uri ) {

        this.uri = uri;
    }


    public HeaderSetter( final String                   uri,
                         final ClaimStationAddress      claimStationAddress ) {

        this.uri        = uri;
        this.stationId  = claimStationAddress.getStationId();
    }


    public HeaderSetter( final String       uri,
                         final String       stationId ) {

        this.uri        = uri;
        this.stationId  = stationId;
    }


    @Override
    public void doWithMessage(final WebServiceMessage message) {

        try {
            SoapMessage soapMessage = (SoapMessage) message;

            //       set the BEP CSS security header before sending request
            RbpsWebServiceClientUtil.setHeader( soapMessage, stationId );
        } catch (Exception ex) {

            throw new UnableToInjectHeadersException( uri, ex );
        }
    }
}
