/*
 * VnpProcUpdateWSHandlerInterface.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimstatus.VnpProcUpdateResponse;

/**
 * This Interface provides methods of VnpProcService WS 
 * 
 * @since 07/2011
 * @version 1.1
 * Last change on 07/10/2010
 * Revision History
 * Date			Name			Description
 * 	------------------------------------------------------------
 * 	07/12/2011  K.Chowdhury		Re-refactoring
 */
public interface VnpProcUpdateWSHandlerInterface {

	/**
	 * This method updates the claim status
	 * 
	 * @return
	 * @throws RbpsWebServiceClientException
	 */
	public VnpProcUpdateResponse vnpProcUpdate(RbpsRepository repository)  throws RbpsWebServiceClientException;
}
