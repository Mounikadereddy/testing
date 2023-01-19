/*
 * CommonUtils.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import gov.va.vba.framework.common.SystemUtils;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Person;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;



/**
 *      Common tools used by all the populators.
 */
public class CommonUtils {

	
    //private RbpsRepository   repository              = null;
    //private boolean          logToStdout             = true;
    //private boolean          accumulateLogMessages   = false;
    //private List<String>     logMessages;
    //private String			 currentProcess;
    
    // no arg constructor not allowed
    private CommonUtils(){}

   public static final String getStandardLogName( final Person     person ) {

        return String.format( ">%s, %s<", person.getLastName(), person.getFirstName() );
    }


    /**
     *      We were using <code>StringUtils.join</code> from apache commons, but
     *      when we were testing via weblogic deployment, it turns out that
     *      that method wasn't available because the version of <code>StringUtils</code>
     *      that was available in deployment didn't have this method available,
     *      so we wrote our own.
     *
     *      @param list - the list of items we wish to join into a string
     *      @param separator - the separator we want to use to separate items
     *          in the string.
     *      @return a string with all the items joined together.
     */
    public static final String join( final List<String> list, final String separator ) {

        String      safeSeparator = separator;

        if ( separator == null ) {
            safeSeparator = "";
        }

        Separator       sep     = new Separator( safeSeparator );
        StringBuffer    buffer  = new StringBuffer();

        for ( String item : list ) {

            buffer.append( sep );
            buffer.append( item );
        }

        return buffer.toString();
    }

    public static final String removeExtraSpacesFromString( String string ) {
    	
    	if ( StringUtils.isEmpty( string ) ) {
    		
    		return "";
    	}
    	
    	String str[] =  string.split("\\s+") ;
    	
    	String returnStr = "";
    	
    	for (String element : str ) {
            
    		returnStr = returnStr + element + " ";
        }
    	return returnStr.trim();
    }
    
    
    
    
    
    

    public static final String getValidationMessage( final String     webServiceUri,
                                        final String     methodName,
                                        final String     errorMessage ) {

        String uriString       = simplifyUri( webServiceUri );
        String detailMessage   = String.format( "Exception occurred in RBPS while communicating with external service: %s.%s.\n",
                                                uriString,
                                                methodName  );
        // RTC 976601 to save error message for email
        String emailMessage   = String.format( "Reporting Unexpected error in RBPS: %s.%s. ",
                								uriString,
                								methodName);
        EmailSender.addErrorMsg(emailMessage, errorMessage);

        return detailMessage;
    }


    public static final String simplifyUri( final String   webServiceUri ) {

        String uriString       = webServiceUri;

        if ( webServiceUri.lastIndexOf( "/" ) > 0 ) {

            uriString       = webServiceUri.substring( webServiceUri.lastIndexOf( "/" ) + 1 );
        }

        return uriString;
    }


    public static final String getNumericString(final String str) {

        if (str == null) {

            return null;
        }

        StringBuffer    strBuff = new StringBuffer();
        char            charAt;

        for (int ii = 0; ii < str.length() ; ii++) {
            charAt = str.charAt(ii);

            if (Character.isDigit(charAt)) {
                strBuff.append(charAt);
            }
        }

        return strBuff.toString();
    }



    /**
     * Check to see if an indicator is positive.  A number of web services
     * return an indicator for a particular field.  An example would be
     * a <code>veteranIndicator</code>.  This method checks to see
     * if the value is not null and if it starts with Y.  If so, then
     * a <code>true</code> is returned
     *
     * @param indicator - the value we're checking to see if it's a
     *          positive indicator.
     * @return true if the value is not null and starts with a "y".
     */
    public static final boolean indicatorIsPositive( final String indicator ) {

        return ( ! StringUtils.isBlank( indicator ) )
                 && indicator != null
                 && indicator.toLowerCase().startsWith( "y" );
    }


    public static final long participantIdFor( final Person    person ) {

        return person.getCorpParticipantId();
    }


    public static final String listProp( final List<?>      stuff,
                            final String    propToPrint ) {

        String      accumulated = "";
        Separator   sep         = new Separator( ", " );

        for ( Object    item : stuff ) {

            accumulated += sep + showProp( item, propToPrint );
        }

        return accumulated;
    }


    public static final String showProp( final Object  obj, final String propToPrint ) {

        Object val = NullSafeGetter.getAttribute( obj, propToPrint );

        if ( val != null ) {

            return val.toString();
        }

        return null;
    }


    public static final String stringBuilder( final Collection<?>   stuff ) {

        if ( CollectionUtils.isEmpty( stuff ) ) {

            return "";
        }

        String      accumulated = "";
        Separator   sep         = new Separator( ",\n" );

        for ( Object    item : stuff ) {

            accumulated += sep + stringBuilder( item );
        }

        return accumulated;
    }


    public static final String stringBuilder( final Map<?,?> stuff ) {

        if ( CollectionUtils.isEmpty( stuff ) ) {

            return "";
        }

        String      accumulated = "";
        Separator   sep         = new Separator( ",\n" ); //unused?

        for ( Object    item : stuff.keySet() ) {

            accumulated += stringBuilder( item ) + "\n\t" + stringBuilder( stuff.get( item ) ) + "\n=====================\n";
        }

        return accumulated;
    }


    public static final String stringBuilder( final Object item ) {

        if ( item == null ) {

            return "<empty>";
        }

        if ( item instanceof String ) {

            return "String >" + item + "<";
        }

        return ToStringBuilder.reflectionToString( item, RbpsConstants.RBPS_TO_STRING_STYLE ).replace( ",", ",\n\t" );
    }


    public static final boolean convertBoolean(final String inputString) {

        if ("true".equalsIgnoreCase( inputString )) {
            return true;
        }
        else if ("t".equalsIgnoreCase( inputString )) {
            return true;
        }
        else if ("yes".equalsIgnoreCase( inputString )) {
            return true;
        }
        else if ("y".equalsIgnoreCase( inputString  )) {
            return true;
        }

        return false;
    }


    public static final String getStackLocation() {

        StackTraceElement stackLevel = getStackLevel( 2 );

        return printStackLevel( stackLevel );
    }


    public static final String getParentStackLocation() {

        StackTraceElement stackLevel = getStackLevel( 3 );

        return printStackLevel( stackLevel );
    }


    public static final String getParent2StackLocation() {

        StackTraceElement stackLevel = getStackLevel( 4 );

        return printStackLevel( stackLevel );
    }


    public static final String getParent3StackLocation() {

        StackTraceElement stackLevel = getStackLevel( 5 );

        return printStackLevel( stackLevel );
    }


    public static final String getStackLocation( final int       levelsUp ) {

        StackTraceElement stackLevel = getStackLevel( levelsUp + 1 );

        return printStackLevel( stackLevel );
    }


    public static final String printStackLevel( final StackTraceElement stackLevel ) {

        return simpleNameFor( stackLevel.getClassName() ) + "." + stackLevel.getMethodName() + "()";
    }


    public static final StackTraceElement getStackLevel( final int levelsUp ) {

        Exception               ex          = new Exception();
        StackTraceElement[]     stackTrace  = ex.getStackTrace();
        StackTraceElement       stackLevel  = null;

        if ( stackTrace.length <= levelsUp ) {

            stackLevel  = stackTrace[stackTrace.length - 1];
        }
        else {
            stackLevel  = stackTrace[levelsUp];
        }

        return stackLevel;
    }


    public static final String simpleNameFor( final String  className ) {

        int     dotLocation = className.lastIndexOf( "." );
        String  simpleName  = className;

        if ( dotLocation > 0 ) {

            // strip the package name
            return simpleName.substring(simpleName.lastIndexOf(".") + 1 );
        }

        return simpleName;
    }


    public final static void log( final Logger       logger,
                     final String       msg) {

    	String message	=  "[ currentThread: " + Thread.currentThread().getName() + "] " + msg;
    	
        if ( logger != null ) {

            logger.debug( message );
        }

/*        if ( logToStdout ) {
            System.out.println( msg );
        }

        if ( accumulateLogMessages ) {

            getLogMessages().add(  msg  );
        }

        addToRepository( msg );
*/        
    }
    
	 public static final void log( final Logger       logger,  final String       msg, boolean logToStdout) {
	
		 String message	=  "[ currentThread: " + Thread.currentThread().getName() + "] " + msg;
		 
		if ( logger != null ) {
		
		   logger.debug( message );
		}
		
//		if ( logToStdout ) {
//		   System.out.println( message );
//		}
	
		/*if ( accumulateLogMessages ) {
		
		   getLogMessages().add(  msg  );
		}
		
		addToRepository( msg );
	*/
	 }


    public static void log( final Logger       logger,
                     final String       msg,
                     final Throwable    ex, boolean logToStdout) {
    	
    	String message	=  "[ currentThread: " + Thread.currentThread().getName() + "] " + msg;

        if ( logger != null ) {

            logger.debug( message, ex );
        }

//        if ( logToStdout ) {
//            System.out.println( message );
//            ex.printStackTrace();
//        }

/*        if ( accumulateLogMessages ) {

            getLogMessages().add(  msg  );
            getLogMessages().add( ExceptionUtils.getFullStackTrace( ex ) );
        }

        addToRepository( msg );
        addToRepository( ExceptionUtils.getFullStackTrace( ex ) );
        */
    }

    // Double-check that logging to a possibly DIFFERENT "logMessages" is really what you want to expose publicly.  
    // Document in any case that this is intended to avoid developer confusion
    public static final void log( final Logger           logger,
                     final List<String>     logMessages,
                     final String           msg, boolean logToStdout) {

    	String message	=  "[ currentThread: " + Thread.currentThread().getName() + "] " + msg;
    	
        logger.debug( message );

//        if ( logToStdout ) {
//            System.out.println( message );
//        }

        if ( logMessages != null ) {

            logMessages.add(  message  );
        }

/*        if ( accumulateLogMessages ) {

            getLogMessages().add(  msg  );
        }

        addToRepository( msg);
        */
    }

/*
    private final void addToRepository( final String    msg) {

        if ( repository == null ) {

            return;
        }

        repository.addJournalStr( msg + "\n" );
    }
*/

    public static final List<String> getExceptionMessages( final Throwable ex ) {

        List<String>        output  = new ArrayList<String>();
        Throwable           subject = ex;

        output.add( ex.getMessage() );

        while ( subject.getCause() != null ) {

            output.add( subject.getCause().getMessage() );
            subject = subject.getCause();
        }

        return output;
    }


    /*private List<String> getLogMessages() {

        if ( logMessages == null ) {

            logMessages = new ArrayList<String>();
        }

        return logMessages;
    }


    public String getFormattedLogMessages() {

        return join( getLogMessages(), "\n" );
    }


    public void clearLogMessages() {

        logMessages = null;
    }


    public final void setAccumulateLogMessages( final boolean    accumulate ) {

        accumulateLogMessages = accumulate;
    }


    public final boolean getAccumulateLogmessages() {

        return accumulateLogMessages;
    }



    public void setLogToStdout( boolean logToStdout ) {

        this.logToStdout = logToStdout;
    }
*/

    /* removed EK - allow access to repo only through constructor 
    public static void setRepository( final RbpsRepository  repo ) {

        CommonUtils.repository = repo;
    }
    */
    
    /* added EK - allow access to repo */
/*    public RbpsRepository getRepository() {

        return repository;
    }
    
    public void setCurrentProcess( String currentProcess ) {
    	
    	this.currentProcess = currentProcess;
    }
    
    public Logger getLogger() {
    	
    	String processorStr = "rbps_processor" + currentProcess;
    	return Logger.getLogger(processorStr);
    }
    */

	/**
	 * Returns the current WebLogic Cluster address and port portion of an SEI URL.
	 * @return The Address:port portion of an SEI URL
	 * 
	 * @note If no Cluster is detected (likely running on a local desktop), then the
	 * Cluster address for the DEV system (http://vbawbtappdev2.vba.va.gov:9015) is
	 * returned. 
	 */
	@SuppressWarnings("deprecation")
	public static String getClusterAddress() {
		String addr = "http://vbawbtappdev2.vba.va.gov:9015";
		String sslPort = "443";
		String http;

		if (SystemUtils.getLoadBalancingHost() != null && SystemUtils.getLoadBalancingHost().trim().length() > 0) {
			if (SystemUtils.getLoadBalancingPort().contains(sslPort)) {
				http="https://";
			}else {
				http="http://";
			} 
			addr = http + SystemUtils.getLoadBalancingHost() + ":" + SystemUtils.getLoadBalancingPort();
		}
		return addr;
	}
}
