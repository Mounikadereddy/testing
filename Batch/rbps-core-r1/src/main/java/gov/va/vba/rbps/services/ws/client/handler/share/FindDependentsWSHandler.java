/*
 * FindDependentsWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependents;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.Shrinq3Person;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.Shrinq3Record;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;


/**
 *      It is a web service client interface to the FindDependents web
 *      service provided by SHARE team.
 */
public class FindDependentsWSHandler {

    private static Logger logger = Logger.getLogger(FindDependentsWSHandler.class);

    // Spring beans references/injection
    private WebServiceTemplate      webServiceTemplate;
    private String                  findDependentsUri;


    /**
     * This method calls the SHARE service
     * FamilyTree.findDependenets
     *
     * @return findDependentsResponse
     * @throws RbpsWebServiceClientException
     */
    @SuppressWarnings( "unchecked" )
    public FindDependentsResponse findDependents(RbpsRepository repository) throws RbpsWebServiceClientException {

        FindDependents request = buildFindDependentsRequest(repository );

        logger.debug(" ----> [ "+ this.getClass().getSimpleName() + "." + findDependentsUri + "]...");

        FindDependentsResponse  response;

        try {
            response = (FindDependentsResponse) webServiceTemplate.marshalSendAndReceive(
                    findDependentsUri,
                    request,
                    new HeaderSetter( findDependentsUri, repository.getClaimStationAddress() ) );
        }
        catch ( SoapFaultClientException  fault ) {

            if ( fault.getMessage().contains( "No records" ) ) {

                FindDependentsResponse  faultResponse   = new FindDependentsResponse();
                Shrinq3Record           record          = new Shrinq3Record();

                record.setNumberOfRecords( "0" );
                faultResponse.setReturn( record );

                return faultResponse;
            }

            String detailMessage = "Call to WS FamilyTree.findDependents failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage(findDependentsUri, request.getClass().getSimpleName(), fault.getMessage()) );

            logger.error(" ***RBPS: [" + detailMessage + "]");

            throw new RbpsWebServiceClientException(detailMessage, fault);
        }
        catch (Throwable rootCause) {
            String detailMessage = "Call to WS FamilyTree.findDependents failed";
            logger.error(" ***RBPS: [" + detailMessage + "]");

            repository.addValidationMessage( CommonUtils.getValidationMessage(findDependentsUri, request.getClass().getSimpleName(), rootCause.getMessage()) );

            System.out.println( "type of exception: " + rootCause.getClass() );

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }

        logger.debug(" <---- [ "+ this.getClass().getSimpleName() + ".findDependents()]...");

/*        utils.log( logger, "FindDependentsWSHandler: dependents are:" );
        for ( Shrinq3Person person : response.getReturn().getPersons() ) {
            utils.log( logger, utils.stringBuilder( person ) );
        }*/

        return response;
    }


    private FindDependents buildFindDependentsRequest(RbpsRepository repository) {

        FindDependents      data = new FindDependents();
        data.setFileNumber( repository.getVeteran().getFileNumber() );

        return data;
    }


    public void setFindDependentsUri( final String findDependentsUri ) {

	String clusterAddr = CommonUtils.getClusterAddress();
        this.findDependentsUri = clusterAddr + "/" + findDependentsUri;
    }

    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
