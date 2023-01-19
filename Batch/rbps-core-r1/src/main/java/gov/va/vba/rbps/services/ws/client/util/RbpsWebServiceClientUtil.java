/*
 * RbpsWebServiceClientUtil.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.util;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.dom.DOMResult;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;


/**
 * <p>
 * Title: RbpsWebServiceClientUtil
 * </p>
 *
 * Utility/helper class for web service client
 * Since Spring does not handle very well static
 * and injection: using @Component and @Autowired
 * work around...
 */
@Component
public final class RbpsWebServiceClientUtil {

    private static Logger logger = Logger.getLogger(RbpsWebServiceClientUtil.class);

    //-------------------------------------------
    /**
     * Fields needed for the BEP WS Security header
     * Using fake credentials for now(Thanks Cory)
     * Later will need to use RBPS CSS account credentials.
     */
    private static String username;
    private static String password;
    public static String stationid;
    private static String clientmachine;
    private static String appname;
    //------------------------------------------


    /**
     *      The method is called by all web services
     *      client to set the VBA BEP WSS
     *      SOAP security header
     */
    public static void setHeader( final SoapMessage     soapMessage,
                                  final String          specificStationId ) {
        try {
            SoapHeader       soapHeader      = soapMessage.getSoapHeader();
            DOMResult        result          = (DOMResult) soapHeader.getResult();
            JAXBContext      jaxbContext     = JAXBContext.newInstance(SecurityBean.class);
            Marshaller       marshaller      = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            SecurityBean     securityheader  = new SecurityBean();
            UserTokenBean    usertoken       = new UserTokenBean();
            usertoken.setUserName(username);
            securityheader.setUsertoken(usertoken);
            usertoken.setPassword(password);

            ServiceHeaderBean serviceheader = new ServiceHeaderBean();
            serviceheader.setAppName(appname);
            serviceheader.setClientMachine(clientmachine);

            setStationIdOnServiceHeader( specificStationId, serviceheader );
            securityheader.setServiceheader(serviceheader);

//          System.out.println("****************************** Name is: " + username);
            marshaller.marshal(securityheader, result);

//            recordRequest( soapMessage );
        }
        catch (Exception ex) {

            throw new UnableToSetSoapHeaderException( ex );
        }
    }


    public static void setStationIdOnServiceHeader( final String                specificStationId,
                                                    final ServiceHeaderBean     serviceheader ) {

        if ( ! StringUtils.isBlank( specificStationId ) ) {

//            for ( int ii = 3; ii < 20; ii++ ) {
//
//                new LogUtils( logger, true ).log( String.format( "Sending station id >%s<, %d", specificStationId, ii ), ii );
//            }

//            new LogUtils( logger, true ).log( String.format( "Sending station id >%s<", specificStationId ), 10 );
            serviceheader.setStationId( specificStationId );
        }
        else {

            serviceheader.setStationId( stationid );
        }
    }


    public static XMLGregorianCalendar asXMLGregoriancalendar(final String dateString) {

        try {
            Date date = SimpleDateUtils.parseInputDate( dateString );
            GregorianCalendar gregorianCalendar = new GregorianCalendar();

//            System.out.println( String.format( "from source >%s< to date: %s",
//                                               dateString,
//                                               date.toString() ) );

            gregorianCalendar.setTimeInMillis(date.getTime());

            return df.newXMLGregorianCalendar(gregorianCalendar);
        } catch ( Throwable ex ) {

            throw new UnableToGenerateXmlGregorianCalendarException( dateString, ex );
        }
    }



    /**
     * Create XMLGregorianCalendar instances
     */
    private static DatatypeFactory df = null;
    static {
        try {
            df = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException dce) {
            throw new IllegalStateException(
                    "Exception occurred while obtaining DatatypeFactory instance",
                    dce);
        }
    }




    @Autowired(required = true)
    public void setUsername(final String username) {
        RbpsWebServiceClientUtil.username = username;
    }

    @Autowired(required = true)
    public void setPassword(final String password) {
        RbpsWebServiceClientUtil.password = password;
    }

    @Autowired(required = true)
    public void setClientmachine(final String clientmachine) {
        RbpsWebServiceClientUtil.clientmachine = clientmachine;
    }

    @Autowired(required = true)
    public void setAppname(final String appname) {
        RbpsWebServiceClientUtil.appname = appname;
    }

    public void setDf(final DatatypeFactory df) {
        RbpsWebServiceClientUtil.df = df;
    }

    @Autowired(required = true)
    public String getStationid() {
        return stationid;
    }

    @Autowired(required = true)
    public void setStationid(final String stationid) {
        RbpsWebServiceClientUtil.stationid = stationid;
    }
}
