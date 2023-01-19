/*
 * FindFiduciaryWSHandlerInterface
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFiduciaryResponse;



public interface FindFiduciaryWSHandlerInterface {

	/**
     * This method calls the SHARE service
     * FamilyTree.findDependents
     * @author vafscgopalk
     * @return FindFiduciary
     * @throws RbpsWebServiceClientException
     */
	FindFiduciaryResponse findFiduciary(RbpsRepository repository) throws RbpsWebServiceClientException;

}
