/*
 * XmlUnmarshaller.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.springframework.core.io.ClassPathResource;


/**
 *      Reads an xml file saved from soapUI that represents a response
 *      from a web service.  Unmarshalls the xml into an object
 *      of the specified class.
 *
 * @author vafscchowdk
 *
 */
public class XmlUnmarshaller {


    public Object loadResponse( final String      xmlPath,
                                final Class       responseClass ) {

        try {
            ClassPathResource   resource        = new ClassPathResource( xmlPath );
            JAXBContext         jaxbContext     = JAXBContext.newInstance( responseClass );
            Unmarshaller        unmarshaller    = jaxbContext.createUnmarshaller();

            return unmarshaller.unmarshal( resource.getURL() );
        }
        catch ( Throwable ex ) {

            throw new RuntimeException( String.format( "Unable to unmarshal >%s<",  xmlPath ), ex );
        }
    }


//    public Object loadResponseFromFile( final String      xmlPath,
//                                        final Class       responseClass ) {
//
//        try {
//            ClassPathResource   resource        = new ClassPathResource( xmlPath );
//            JAXBContext         jaxbContext     = JAXBContext.newInstance( responseClass );
//            Unmarshaller        unmarshaller    = jaxbContext.createUnmarshaller();
//
//            return unmarshaller.unmarshal( resource.getFile() );
//        }
//        catch ( Throwable ex ) {
//
//            throw new RuntimeException( String.format( "Unable to unmarshal >%s<",  xmlPath ), ex );
//        }
//    }
}
