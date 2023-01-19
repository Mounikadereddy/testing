/*
 * UnableToParseResultsException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;

import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;


/**
 *      This exception is thrown when there is a problem parsing
 *      the results of the Virtual VA web service,
 *      by {@link ResponseParser}.
 *
 *      @author vafsccorbit
 */
public class UnableToParseResultsException extends RbpsLetterGenException {

    private static final long serialVersionUID = 1449944537567559153L;



    public UnableToParseResultsException( final Throwable cause ) {

        super( cause );
    }



    @Override
    public String getMessage() {

        return "Unable to parse results from Virtual VA web service.\n" + getCause().getMessage();
    }
}
