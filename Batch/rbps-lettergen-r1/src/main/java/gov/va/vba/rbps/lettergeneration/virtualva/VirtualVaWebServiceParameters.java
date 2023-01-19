/*
 * VirtualVaWebServiceParameters.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 *      This class figures out the parameters we need to feed
 *      to the virtual va web service.
 */
public class VirtualVaWebServiceParameters {

	private static final String USER_ID        = "26";
	private static final String FEED_TYPE      = "RBPS";
	private static final String DATE_FORMAT    = "yyyy-MM-dd HH:mm:ss";


//	XXX (MUCH LATER): It should be the earliest file date
	public static String getBeginDate() {

        return new SimpleDateFormat( DATE_FORMAT ).format( getDayBefore( new Date(), 2 ) );
	}


	public static String getEndDate() {

		return new SimpleDateFormat( DATE_FORMAT ).format( yesterday() );
	}


    public static Date yesterday() {

        return getDayBefore( new Date(), 1 );
    }


	private static Date getDayBefore( final Date date, final int daysPrevious ) {

        Calendar cal = Calendar.getInstance();

        cal.add( Calendar.DATE, -daysPrevious );

        return cal.getTime();
    }


	public static String getFeedType() {

		return FEED_TYPE;
	}


	public static String getUserID() {

		return USER_ID;
	}
}
