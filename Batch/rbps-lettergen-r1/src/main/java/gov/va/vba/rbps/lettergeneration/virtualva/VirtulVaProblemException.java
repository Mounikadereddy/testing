/*
 * VirtulVaProblemException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;

import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;


/**
 *      This exception is thrown when there is a problem returned
 *      from the Virtual VA web service,
 *      by {@link ResponseParser}.
 *
 *      @author vafsccorbit
 */
public class VirtulVaProblemException extends RbpsRuntimeException {


    private static final long serialVersionUID = -8311695664294388500L;


    public VirtulVaProblemException( final String message ) {

        super( "Virtual VA has encountered this problem: " + message );
    }
}
