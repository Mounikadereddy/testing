package gov.va.vba.rbps.services.ws.client.util;


public class UnableToSetSoapHeaderException extends RuntimeException {


    private static final long serialVersionUID = 7106745529750702175L;


    public UnableToSetSoapHeaderException( final Throwable cause ) {

        super( "Unable to set soap security headers", cause );
    }
}
