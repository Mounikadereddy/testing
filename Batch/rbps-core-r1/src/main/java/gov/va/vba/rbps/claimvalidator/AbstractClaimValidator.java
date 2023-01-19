/*
 * AbstractClaimValidator.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.claimvalidator;


/**
 *All the Validators should simply extends this AbstractClaimValidator class.
 *
 *@author Omar.Gaye
 *@since July 13 2011
 *
 */
public abstract class AbstractClaimValidator extends ClaimValidatorInterface{

	public abstract void validate();		
	
}
