/*
 * FindMailingAddressWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.services.ws.client.interfaces.FindMailingAddressWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindMailingAddress;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindMailingAddressResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 * This interface is a data access interface.
 * It is a web service client interface to the FindMailingAddress web
 * service provided by SHARE team.
 */
public class FindMailingAddressWSHandler implements FindMailingAddressWSHandlerInterface {

    private static Logger logger = Logger.getLogger(FindMailingAddressWSHandler.class);

//    protected CommonUtils           utils                   = new CommonUtils();

    // Spring beans references/injection
    private WebServiceTemplate      webServiceTemplate;
    private String                  findMailingAddressUri;
    //private RbpsRepository          repository;



    /**
     * This method calls the SHARE service
     * FamilyTree.findMailingAdress
     * @author vafsccorbit
     * @param participanId - since you may want to find mailing addresses for
     *          people other than the veteran, this parameter is used to
     *          specify which person's mailing address we're looking for.
     *          As an example, at some point we'll need to find the POA's
     *          mailing address.
     * @return findMailingAddressResponse
     * @throws RbpsWebServiceClientException
     */
    @Override
    @SuppressWarnings( "unchecked" )
    public FindMailingAddressResponse findMailingAddress( final long participantId, RbpsRepository repository ) throws RbpsWebServiceClientException {

        FindMailingAddress request = buildFindMailingAddressRequest( participantId, repository );

        logger.debug(" ----> [ "+ this.getClass().getSimpleName() + "." + findMailingAddressUri + "]...");

        FindMailingAddressResponse response;
        try {
            response = (FindMailingAddressResponse) webServiceTemplate.marshalSendAndReceive(
                    findMailingAddressUri,
                    request,
                    new HeaderSetter( findMailingAddressUri, repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {
            String detailMessage = "Call to WS FamilyTree.findMailingAddress failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage(findMailingAddressUri, request.getClass().getSimpleName(), rootCause.getMessage()) );
            logger.error(" ***RBPS: [" + detailMessage + "]");

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }

        logger.debug(" <---- [ "+ this.getClass().getSimpleName() + ".findMailingAddress()]...");

        return response;
    }


    private static final FindMailingAddress buildFindMailingAddressRequest( final long participantId, RbpsRepository repository ) {

//        setupFakeDataIfNecessary();

        FindMailingAddress      data = new FindMailingAddress();
        data.setFileNumber( repository.getVeteran().getFileNumber() );

        return data;
    }







    /***
     * Setter injection
     * @param findMailingAddressUri
     */
    public void setFindMailingAddressUri( final String findMailingAddressUri ) {

    	String clusterAddr = CommonUtils.getClusterAddress();
        this.findMailingAddressUri = clusterAddr + "/" + findMailingAddressUri;
    }


    /***
     * Setter injection
     * @param rbpsRepository
     */
//    public void setRepository( final RbpsRepository repository ) {
//
//        this.repository = repository;
//    }

    /***
     * Setter injection
     * @param webServiceTemplate
     */
    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
