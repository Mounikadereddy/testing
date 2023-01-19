/*
 * TemplateMergeException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;


/**
 *      This exception is thrown when there is a problem using
 *      velocity to merge the input data into the velocity
 *      template, by {@link Markup}.
 *
 *      @author vafsccorbit
 */
public class TemplateMergeException extends RbpsLetterGenException {


    private static final long serialVersionUID = -4774965349732851937L;


    public TemplateMergeException( final String message, final Throwable rootCause ) {

        super( message, rootCause );
    }
}
