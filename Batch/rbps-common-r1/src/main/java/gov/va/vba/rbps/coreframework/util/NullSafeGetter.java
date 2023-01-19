/*
 * NullSafeGetter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


/**
 *      Class used to get a chained set of attributes from an object
 *      hierarchy w/o  having to do boatloads of null checks.
 *
 *      Example:
 *      <pre>
 *      NullSafeGetter  grabber = new NullSafeGetter();
 *
 *      Spouse spouse = (Spouse) grabber.getAttribute( veteran, "currentMarriage.marriedTo" );
 *      </pre>
 */
public final class NullSafeGetter {
 
	/* FUTURE */
	//private NullSafeGetter(){} // don't instantiate

    public static final Object getAttribute( final Object  object, final String  attributePath ) {

        Object      input   = object;
        Object      result  = null;
        String[]    path    = attributePath.split( "\\." );

        for ( String element : path ) {

            BeanWrapper wrapper = new BeanWrapperImpl( input );
            result = wrapper.getPropertyValue( element );

            if ( result == null ) {

                return result;
            }

            input = result;
        }

        return result;
    }


    public static final void setAttribute( final Object      object,
                              final String      attributePath,
                              final Object      newValue ) {

        String  finalAttributeName  = getFinalAttributeName( attributePath );
        String  pathToFinalObject   = getPathToFinalObject( attributePath );
        Object  finalObject         = getFinalObject( object, pathToFinalObject );

        setAttributeOnFinalObject( finalObject, finalAttributeName, newValue );
    }


    private static final Object getFinalObject( final Object object, final String pathToFinalObject ) {

        if ( StringUtils.isBlank( pathToFinalObject ) ) {

            return object;
        }

        return getAttribute( object, pathToFinalObject );
    }


    private static final void setAttributeOnFinalObject( final Object    finalObject,
                                            final String    finalAttributeName,
                                            final Object    newValue ) {

        BeanWrapper wrapper = new BeanWrapperImpl( finalObject );
        wrapper.setPropertyValue( finalAttributeName, newValue );
    }


    public static final String getPathToFinalObject( final String attributePath ) {

        int indexOfLastPeriod = getIndexOfLastPeriod( attributePath );

        if ( indexOfLastPeriod < 0 ) {

            return null;
        }

        return attributePath.substring( 0, indexOfLastPeriod );
    }


    public static final String getFinalAttributeName( final String attributePath ) {

        int indexOfLastPeriod = getIndexOfLastPeriod( attributePath );

        if ( indexOfLastPeriod < 0 ) {

            return attributePath;
        }

        return attributePath.substring( indexOfLastPeriod + 1 );
    }


    private static final int getIndexOfLastPeriod( final String attributePath ) {

        return attributePath.lastIndexOf( "." );
    }
}
