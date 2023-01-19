/*
 * FindMailingAddressWSHandlerInterface.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.interfaces;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindMailingAddressResponse;


/**
 * This interface is a data access interface.
 * It is a web service client interface to the FindMailingAddress web
 * service provided by SHARE team.
 *
 * @author Tom.Corbin
 * @since 07/2011
 * @version 1.1
 *
 * Last change on 07/10/2010
 * Revision History
 * Date         Name        Description
 *  ------------------------------------------------------------
 *  07/19/2011  T.Corbin    Created
 */
public interface FindMailingAddressWSHandlerInterface {

    /**
     * This method calls the SHARE service
     * FamilyTree.findMailingAddress
     * @author vafsccorbit
     * @param participanId - since you may want to find mailing addresses for
     *          people other than the veteran, this parameter is used to
     *          specify which person's mailing address we're looking for.
     *          As an example, at some point we'll need to find the POA's
     *          mailing address.
     * @return FindMailingAddressResponse
     * @throws RbpsWebServiceClientException
     */
    FindMailingAddressResponse findMailingAddress( long participantId,  		RbpsRepository repo) throws RbpsWebServiceClientException;
}
