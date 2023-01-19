/*
 * SoapFaultPrinter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.util;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.CommonUtils;

import javax.xml.transform.dom.DOMSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.soap11.Soap11Fault;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;



/**
 *      Prints out the information in the DOM for the xml coming
 *      back as part of the soap exception.
 */
public class SoapFaultPrinter {

    private static 	Logger 		logger = Logger.getLogger(SoapFaultPrinter.class);

    
    public boolean logSoapFaultInfo( final Throwable ex ) {

        return logSoapFaultInfo( logger, ex );
    }


    public boolean logSoapFaultInfo( final Logger    logger, final Throwable ex ) {

        String  soapFaultInfo = printSoapFaultInfo( ex );

        if ( StringUtils.isBlank( soapFaultInfo ) ) {

            return false;
        }

        CommonUtils.log( getLogger( logger ), "Found soap fault error:\n" + soapFaultInfo );

        return true;
    }


    public String printSoapFaultInfo( final Throwable ex ) {

        SoapFaultClientException    soapFault = null;

        int soapFaultIndex = ExceptionUtils.indexOfType( ex, SoapFaultClientException.class );

        if ( notFound( soapFaultIndex ) ) {

            return "";
        }

        soapFault = (SoapFaultClientException) ExceptionUtils.getThrowables( ex )[soapFaultIndex];

//        if ( ex instanceof SoapFaultClientException ) {
//
//            soapFault = (SoapFaultClientException) ex;
//        }
//        else if ( ex.getCause() instanceof SoapFaultClientException ) {
//
//            soapFault = (SoapFaultClientException) ex.getCause();
//        }

        if ( soapFault == null ) {

            return "";
        }

//        buffer.append( "fault code: " + soapFault.getFaultCode() );
//        buffer.append( "soap fault: " + ToStringBuilder.reflectionToString( soapFault.getSoapFault() ) );
//        buffer.append( "fault string or reason: " + soapFault.getFaultStringOrReason() );
//
//        Iterator iter = soapFault.getSoapFault().getFaultDetail().getDetailEntries();
//        while ( iter.hasNext() ) {
//
//            SoapFaultDetailElement detail = (SoapFaultDetailElement) iter.next();
//            buffer.append( "result: " + detail.getResult() );
//            buffer.append( "source: " + detail.getSource() );
//            buffer.append( "name: " + detail.getName() );
//            buffer.append( ToStringBuilder.reflectionToString( detail ) );
//        }

        StringBuffer    buffer = new StringBuffer();
        Node node = ((DOMSource) ((Soap11Fault) soapFault.getSoapFault()).getSource()).getNode();
        printNodeTree( buffer, node, "" );

        return buffer.toString();
    }


    private boolean notFound( final int soapFaultIndex ) {

        return soapFaultIndex < 0;
    }


    public void printNodeTree( final StringBuffer       buffer,
                               final Node               node,
                               final String             indent ) {

        NodeList childNodes = node.getChildNodes();

        for (int ii = 0; ii < childNodes.getLength(); ii++) {

            Node currentNode = childNodes.item(ii);

            if ( currentNode.getNodeName().equals(  "ns2:frame" ) ) {

                printFrame( buffer, ii, currentNode.getAttributes(), indent );
            }
            else {

                printNodeInfo( buffer, indent, currentNode );
            }
        }
    }


    public void printNodeInfo( final StringBuffer       buffer,
                               final String             indent,
                               final Node               currentNode ) {

        if ( isSingleChildNode( currentNode ) ) {

            printSingleChildNode( buffer, indent, currentNode );
            return;
        }
//            buffer.append( indent + "node text: " + currentNode.getTextContent() );
        if ( ! currentNode.getNodeName().equals( "#text" ) ) {
            buffer.append( String.format( "%s%s (%d)\n",
                                          indent,
                                          currentNode.getNodeName(),
                                          currentNode.getChildNodes().getLength() ) );
        }

        if ( currentNode.getNodeValue() != null ) {

            buffer.append( indent + currentNode.getNodeValue() + "\n" );
        }

        printAttr( buffer, currentNode, indent );

        printNodeTree( buffer, currentNode, indent + "    " );
    }


    private void printSingleChildNode( final StringBuffer       buffer,
                                       final String             indent,
                                       final Node               node ) {

        Node child = node.getChildNodes().item(  0 );
        buffer.append( String.format( "%s%s: %s\n",
                                      indent,
                                      node.getNodeName(),
                                      child.getNodeValue() ) );
    }


    public void printFrame( final StringBuffer      buffer,
                            final int               stackIndex,
                            final NamedNodeMap      nodeMap,
                            final String            indent ) {

        if ( nodeMap == null || nodeMap.getLength() == 0 ) {

            return;
        }

        String  className   = getValueIfThere( nodeMap, 0 );
        String  fileName    = getValueIfThere( nodeMap, 1 );
        String  lineNumber  = getValueIfThere( nodeMap, 2 );
        String  state       = getValueIfThere( nodeMap, 3 );

        buffer.append( String.format( "%s(%d) %s%s%s%s\n",
                                      indent,
                                      stackIndex,
                                      className,
                                      fileName,
                                      lineNumber,
                                      state ) );
    }


    public void printAttr( final StringBuffer       buffer,
                           final Node               node,
                           final String             indent ) {

        if ( node.getAttributes() == null ) {
            return;
        }

        for ( int ii = 0; ii < node.getAttributes().getLength(); ii++ ) {
            printAttrInfo( buffer, node, ii, indent );
        }
    }


    public void printAttrInfo( final StringBuffer       buffer,
                               final Node               node,
                               final int                ii,
                               final String             indent ) {

        Node attr = node.getAttributes().item( ii );

        printNodeTree( buffer, attr, indent + "    " );
//        buffer.append( indent + node.getNodeValue() );
    }


    public String getValueIfThere( final NamedNodeMap nodeMap, final int index ) {

        if ( nodeMap.getLength() < index || nodeMap.item( index ) == null ) {
            return "";
        }

        String  colon = "";
        if ( index > 0 ) {

            colon = ":";
        }

        return colon + nodeMap.item( index ).getNodeValue();
    }


    private boolean isSingleChildNode( final Node node ) {

        int numChildren = node.getChildNodes().getLength();

        if ( numChildren != 1 ) {

            return false;
        }

        Node child = node.getChildNodes().item(  0 );
        if ( child.getNodeName().equals( "#text" ) ) {

            return true;
        }

        return false;
    }


    private Logger getLogger( final Logger logger ) {

        if ( logger != null ) {
            return logger;
        }

        return this.logger;
    }
}
