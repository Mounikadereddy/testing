/*
 * RbpsServiceInterface.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.services.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsServiceException;

/**
 * 
 * @since March 1, 2011
 * @version 1.1
 * @author Omar.Gaye
 * 
 */
public interface RbpsServiceInterface{
	
	/**
	 * This method is called from RbpsWS to execute all the claims that are ready for RBPS.
	 * 
	 * @throws RbpsServiceException
	 */
	public void executeAll() throws RbpsServiceException;
	
	
	/**
	 * This method is called from RbpsWS to execute all the claims that are ready for RBPS.
	 * 
	 * @throws RbpsServiceException
	 */
	public void executeAll(final String currentProcess, final String totalProcesses, RbpsRepository repo) throws RbpsServiceException;
	
	/**
	 * This method is called from rbps-execute.jsp to process claims one by one 
	 * clicking on 'Submit' button by the user.
	 * 
	 * @throws RbpsServiceException
	 */
	public	String execute(RbpsRepository repo) throws RbpsServiceException;

}
