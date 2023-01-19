/*
 * LoggingInterceptor.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.util;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.CommonUtils;

import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;


/**
 *      Used to log web service request, responses, and faults for
 *      web service requests that go through spring.
 */
public class LoggingInterceptor implements ClientInterceptor {

    private static Logger logger = Logger.getLogger(LoggingInterceptor.class);


    private TransformerFactory  transformerFactory  = TransformerFactory.newInstance();
    private boolean             logit               = true;


    @Override
    public boolean handleFault( final MessageContext messageContext ) throws WebServiceClientException {

        logMessageSource( "\nFault:\n", getSource( messageContext.getResponse() ) );
        return true;
    }


    @Override
    public boolean handleRequest( final MessageContext messageContext ) {

        logMessageSource( "\nRequest:\n", getSource( messageContext.getRequest() ) );
        return true;
    }


    @Override
    public boolean handleResponse( final MessageContext messageContext ) {

        logMessageSource( "\nResponse:\n", getSource( messageContext.getResponse() ) );
        return true;
    }


    private Transformer createNonIndentingTransformer() throws TransformerConfigurationException {

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        transformer.setOutputProperty(OutputKeys.INDENT, "no");

        return transformer;
    }


    /**
     * Logs the given {@link Source source} to the {@link #logger}, using the message as a prefix.
     * <p/>
     * By default, this message creates a string representation of the given source, and delegates to {@link
     * #logMessage(String)}.
     *
     * @param logMessage the log message
     * @param source     the source to be logged
     * @throws TransformerException in case of errors
     */
    private void logMessageSource(final String logMessage, final Source source) {

        if (source == null) {

            return;
        }

        if ( ! logit ) {

            return;
        }

        try {
            Transformer     transformer     = createNonIndentingTransformer();
            StringWriter    writer          = new StringWriter();
            transformer.transform(source, new StreamResult(writer));

            String          message         = logMessage + writer.toString();

            CommonUtils.log( logger, message );
        }
        catch ( Throwable ignore ) {

            // ignore, just logging
            System.out.println( "Unable to log " + logMessage + " because of " + ignore.getMessage() );
        }
    }



    private Source getSource(final WebServiceMessage message) {

        return message.getPayloadSource();
    }

    public void setLogit( final boolean logit ) {

        this.logit = logit;
    }
}
