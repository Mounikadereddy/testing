/*
 * UnableToGenerateLetterException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;


/**
 *      This exception is thrown when there is a problem generating
 *      the notification letter by {@link GenericLetterGeneration}.
 *
 *      @author vafsccorbit
 */
public class UnableToGenerateLetterException extends RbpsLetterGenException {

    private static final long serialVersionUID = 12676894557817235L;


    private String      template;


    public UnableToGenerateLetterException( final String template, final Throwable cause ) {

        super( cause );

        this.template = template;
    }


    //  TODO: handle template type
    @Override
    public String getMessage() {

        String  typeOfLetter = null;

        return String.format(  "Unable to generate pdf file.\n%s", getCause().getMessage() );
    }
}
