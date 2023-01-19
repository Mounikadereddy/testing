/*
 * ResponseParser.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;


import gov.va.vba.rbps.lettergeneration.virtualva.results.BatchFileInterface;
import gov.va.vba.rbps.lettergeneration.virtualva.results.DocResult;

import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.StringUtils;


/**
 *      Takes the xml response from the Virtual VA web service
 *      and produces a list of DocResults so that the {@link WebServiceRunner}
 *      can provide a list of failed and successful files.
 *
 *      @author vafsccorbit
 */
public class ResponseParser {


    public List<DocResult> parse( final String    xml ) {

        try {
            JAXBContext             context         = JAXBContext.newInstance( BatchFileInterface.class );
            Unmarshaller            unmarshaller    = context.createUnmarshaller();
            BatchFileInterface      results         = (BatchFileInterface) unmarshaller.unmarshal( new StringReader( xml ) );

            checkForError( results );

//            log.info( "got a doc: " + results.getJobList().getJob().getDocs() );
            return results.getJobList().getJob().getDocs().getDocs();
        }
        catch ( Throwable ex )
        {
            throw new UnableToParseResultsException( ex );
        }
    }


    private void checkForError( final BatchFileInterface results ) {

        if ( StringUtils.isBlank( results.getErrorCode() ) )
        {
            return;
        }

        throw new VirtulVaProblemException( results.getDescription() );
    }
}
