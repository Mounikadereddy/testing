/*
 * RbpsUtil.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;

import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang.time.DateUtils;


/**
 * RbpsUtil provides some generic utility methods for working with or
 * transforming different types of data.
 */
public class RbpsUtil {

    /**
     *      Converts a List of Strings to a Single formatted String
     *
     *      @param StringList
     *      @return String
     */
    public static String stringListToStringBuilder(final List<String> stringList) {

        StringBuilder stringBuilder = new StringBuilder();

        for (String string : stringList) {

            stringBuilder.append(string.trim()).append("\n");
        }

        return stringBuilder.toString();
    }


    /**
     *      Converts a String to a Long
     *
     *      @param String
     *      @return Long
     */
    public static Long getExceptionSafeLongValue(final String stringValue) {

        try {

            return Long.valueOf(stringValue);
        }
        catch(Exception ex) {

            throw new RbpsRuntimeException( String.format( "Unable to parse >%s< into a long value.",
                                                           stringValue ), ex );
        }
    }



    /**
     *      Converts a Date to XMLGregorianCalendar
     *
     *      @param Date
     *      @return XMLGregorianCalendar
     *      @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar dateToXMLGregorianCalendar(final Date date) {

        if ( date == null ) {

            throw new IllegalArgumentException( "You must specify a date to be translated to an XmlGregorianCalendar" );
        }

        try {

            GregorianCalendar       gc      = new GregorianCalendar();
            gc.setTime( date );

            return DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
        }
        catch (DatatypeConfigurationException ex) {

            throw new RbpsRuntimeException( String.format( "Unable to convert >%s< to an XmlGregorianCalendar",
                                                           date ), ex );
        }
    }



    /**
     *      Converts a Date to XMLGregorianCalendar
     *
     *      @param Date
     *      @return XMLGregorianCalendar
     *      @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar dayToXMLGregorianCalendar(final Date date) {

        if ( date == null ) {

            throw new IllegalArgumentException( "You must specify a date to be translated to an XmlGregorianCalendar" );
        }

        try {

            XMLGregorianCalendar    xmlDate = null;
            Calendar                cal     = Calendar.getInstance( DateUtils.UTC_TIME_ZONE );

            Date truncatedDate = DateUtils.truncate( date, Calendar.DAY_OF_MONTH );
            cal.setTime( truncatedDate );
            cal.setTimeZone( DateUtils.UTC_TIME_ZONE );
//            cal.set( Calendar.)

//            System.out.println( "dayToXMLGreg: input date         >" + date + "<" );
//            System.out.println( "dayToXMLGreg: processed date     >" + cal.getTime() + "<" );
//            System.out.println( "dayToXMLGreg: cal year           >" + cal.get( Calendar.YEAR ) + "<" );
//            System.out.println( "dayToXMLGreg: cal month          >" + cal.get( Calendar.MONTH ) + "<" );
//            System.out.println( "dayToXMLGreg: cal day of month   >" + cal.get( Calendar.DAY_OF_MONTH ) + "<" );
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar( BigInteger.valueOf( cal.get( Calendar.YEAR ) ),
                                                                             cal.get( Calendar.MONTH ) + 1,
                                                                             cal.get( Calendar.DAY_OF_MONTH ),
                                                                             0,
                                                                             0,
                                                                             0,
                                                                             BigDecimal.valueOf(0),
                                                                             0 );

            return xmlDate;
        }
        catch (DatatypeConfigurationException ex) {

            throw new RbpsRuntimeException( String.format( "Unable to convert >%s< to an XmlGregorianCalendar",
                                                           date ), ex );
        }
    }
}
