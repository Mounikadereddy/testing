/*
 * UnableToOutputPdfFileException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;


/**
 *      This exception is thrown when there is a problem saving the
 *      content to the pdf file, by {@link GenericLetterGeneration}.
 *
 *      @author vafsccorbit
 */
public class UnableToOutputPdfFileException extends RbpsLetterGenException {


    private static final long serialVersionUID = 6412565755732991390L;


    public UnableToOutputPdfFileException( final String         pdfFileName,
                                           final Throwable      cause ) {

        super( String.format( "Unable to save the pdf output to the file >%s<.", pdfFileName ), cause );
    }
}
