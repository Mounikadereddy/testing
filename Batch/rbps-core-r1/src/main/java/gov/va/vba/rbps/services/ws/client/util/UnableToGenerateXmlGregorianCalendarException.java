package gov.va.vba.rbps.services.ws.client.util;


public class UnableToGenerateXmlGregorianCalendarException extends RuntimeException {


    private static final long serialVersionUID = 5534910482225286296L;


    public UnableToGenerateXmlGregorianCalendarException( final String        givenDate,
                                                          final Throwable     cause ) {

        super( String.format( "Unable to generate an XmlGregorian from >%s<", givenDate ),
               cause );
    }

}
