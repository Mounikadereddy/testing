/*
 * AwardsWebServicesHandlerInterface.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;

public interface ProcessAwardDependentWSHandlerInterface {

	ProcessAwardDependentResponse processAwardDependents( RbpsRepository  repository) throws RbpsWebServiceClientException;

}
