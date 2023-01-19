/*
 * ClaimValidatorInterface.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.claimvalidator;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;




/**
 *      The role of the a Validator is to validate all data
 *      related to a new eClaim from VONAPP.
 */
public abstract class ClaimValidatorInterface {

    
    /**
     *         RbpsServiceFacade.execute method is the entry point of the
     *         ClaimValidatorInterface: the validate call from facade comes here.
     */
    public abstract void validate(RbpsRepository repo);
}
