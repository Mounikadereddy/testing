/*
 * FindFlashesWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFlashes;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFlashesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FlashInquiryRecord;

import org.springframework.ws.soap.client.SoapFaultClientException;


/**
 *      Get the Flashes so tha RBPS can find out if the Veteran has
 *      an Attorney Fee Agreement.
 */
public class FindFlashesWSHandler extends BaseWSHandler<FindFlashes,FindFlashesResponse> {




    @Override
    protected FindFlashesResponse handleSoapFaultException( final RbpsRepository                repo,
                                                            final FindFlashes                   request,
                                                            final SoapFaultClientException      fault ) {

        if ( fault.getMessage().contains( "String index out of range" ) ) {

            //
            //      We don't do anything with this data structure.
            //      The important thing is if we update something.
            //
            FindFlashesResponse     faultResponse   = new FindFlashesResponse();
            FlashInquiryRecord      record          = new FlashInquiryRecord();

            record.setNumberOfFlashes( "0" );
            faultResponse.setReturn( record );

            return faultResponse;
        }

        handleException( repo, request, fault );

        return null;
    }


    @Override
    protected FindFlashes buildRequest( final RbpsRepository    repo ) {

        FindFlashes      data = new FindFlashes();
        data.setFileNumber( repo.getVeteran().getFileNumber() );

        return data;
    }
}
