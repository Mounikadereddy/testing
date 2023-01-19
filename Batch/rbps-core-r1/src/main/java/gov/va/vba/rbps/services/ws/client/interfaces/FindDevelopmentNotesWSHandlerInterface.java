/*
 * FindDevelopmentNotesWSHandlerInterface.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.mapd.notes.FindDevelopmentNotesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.mapd.notes.FindDevelopmentNotes;;

/**
 * This interface is a data access interface.
 * It is a web service client interface to the FindDevelopmentNotes operation
 * in the DevelopmentNotes web service provided by the MAPD team.
 *
 * @author Steve.Sager
 * @since 07/2011
 * @version 1.1
 *
 * Last change on 07/27/2011
 * Revision History
 * Date         Name        Description
 *  ------------------------------------------------------------
 *  07/27/2011  S.Sager    Created
 */
public interface FindDevelopmentNotesWSHandlerInterface {
	
    /**
     * This method calls the MAPD service DevelopmentNotes.findDevelopmentNotes
     * @author vafscsagers	
     * @return CreateNoteResponse
     * @throws RbpsWebServiceClientException
     */
	FindDevelopmentNotesResponse findDevelopmentNotes(FindDevelopmentNotes note) throws RbpsWebServiceClientException;
}
