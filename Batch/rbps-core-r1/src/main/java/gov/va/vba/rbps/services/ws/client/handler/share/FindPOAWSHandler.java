/*
 * FindPOAWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindPOA;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindPOAResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.ShrinqfPersonOrg;

import org.springframework.ws.soap.client.SoapFaultClientException;


/**
 *      This interface is a data access interface.
 *      It is a web service client interface to the FindPOA web
 *      service provided by SHARE team.
 */
public class FindPOAWSHandler extends BaseWSHandler<FindPOA,FindPOAResponse> {




    @Override
    protected FindPOAResponse handleSoapFaultException( final RbpsRepository               repo,
                                                        final FindPOA                      request,
                                                        final SoapFaultClientException     fault ) {

        if ( fault.getMessage().contains( "No records" ) ) {

            FindPOAResponse     faultResponse   = new FindPOAResponse();
            ShrinqfPersonOrg    returnVal       = new ShrinqfPersonOrg();

            returnVal.setPersonOrganizationName( "bogus" );
            faultResponse.setReturn( returnVal );

            return faultResponse;
        }

        handleException( repo, request, fault );

        return null;
    }


    @Override
    protected FindPOA buildRequest( final RbpsRepository               repo ) {

        FindPOA      data = new FindPOA();
        data.setFileNumber( repo.getVeteran().getFileNumber() );

        return data;
    }
}
