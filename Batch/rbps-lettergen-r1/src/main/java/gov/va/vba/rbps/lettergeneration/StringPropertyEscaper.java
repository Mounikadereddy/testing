/*
 * StringPropertyEscaper
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.PropertyProcessor;

import org.apache.commons.lang.StringEscapeUtils;



/**
 *      Modifies a string so that it's html safe.
 */
public final class StringPropertyEscaper implements PropertyProcessor {

    private static Logger logger = Logger.getLogger( StringPropertyEscaper.class );



    @Override
    public Object modifyProperty( final Object value ) {

        if ( value == null ) {

            return null;
        }

        if ( ! (value instanceof String) ) {

            return value;
        }

        String      stringValue = (String) value;

        //      Could also use Spring's HtmlUtils.htmlEscape( stringValue )
        return StringEscapeUtils.escapeHtml( stringValue );
    }
}
