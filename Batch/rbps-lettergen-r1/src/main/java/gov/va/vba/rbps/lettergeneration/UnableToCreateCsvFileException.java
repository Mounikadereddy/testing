/*
 * UnableToCreateCsvFileException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;


/**
 *      This exception is thrown when there is a problem generating
 *      the csv file, by {@link CsvFileGenerator}.
 *
 *      @author vafsccorbit
 */
public class UnableToCreateCsvFileException extends RbpsLetterGenException {

    private static final long serialVersionUID = -6051638287158838399L;



    public UnableToCreateCsvFileException( final String         pdfFileName,
                                           final Throwable      cause ) {

        super( String.format( "Unable to generate csv file for >%s<.", pdfFileName ), cause );
    }
}
