/*
 * UnableToInjectHeadersException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.util;





public class UnableToInjectHeadersException extends RuntimeException {


    private static final long serialVersionUID = -9167607357285949688L;


    private String        webService = null;


    public UnableToInjectHeadersException( final String     uri,
                                           final Throwable  cause ) {

        super( cause );

        if ( uri == null ) {
            return;
        }

        extractWebService( uri );
    }


    public void extractWebService( final String uri ) {

        int     index = uri.lastIndexOf( "/" );

        if ( index > 0 ) {
            webService = uri.substring( index + 1 );
        }
    }


    @Override
    public String getMessage() {

        if ( webService == null ) {

            return String.format( "Unable to inject security headers for web service\n%s",
                                  getCause().getMessage() );
        }

        return String.format( "Unable to inject security headers for web service >%s<\n%s",
                              webService,
                              getCause().getMessage() );
    }
}
