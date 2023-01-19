/*
 * UnableToParseDateInputException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.util;


public class UnableToParseDateInputException extends RuntimeException {


    private static final long serialVersionUID = -5372698413185654249L;


    public UnableToParseDateInputException( final String dateString ) {

        super( String.format( "Unable to parse >%s< into an accepted date format.", dateString ) );
    }
}
