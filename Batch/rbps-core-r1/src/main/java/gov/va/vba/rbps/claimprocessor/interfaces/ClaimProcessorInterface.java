/*
 * ClaimProcessorInterface.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.claimprocessor.interfaces;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsClaimDataException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuleExecutionException;

/**
 * 
 * @since March 1, 2011
 * @version 1.0
 * @author Omar.Gaye
 * 
 * */
public interface ClaimProcessorInterface {
	public void processClaim(RbpsRepository repo)throws RbpsRuleExecutionException, RbpsClaimDataException;
}
