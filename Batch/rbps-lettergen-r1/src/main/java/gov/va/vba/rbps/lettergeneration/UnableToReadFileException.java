/*
 * UnableToReadFileException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;



public class UnableToReadFileException extends RbpsLetterGenException {


    private static final long serialVersionUID = -4522094409626799689L;


    public UnableToReadFileException( final String classPath, final Throwable rootCause ) {

        super( String.format( "Unable to read the contents of the file >%s<.", classPath ), rootCause );
    }
}
