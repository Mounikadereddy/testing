/*
 * VelocityEventHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.framework.logging.Logger;

import org.apache.velocity.app.event.InvalidReferenceEventHandler;
import org.apache.velocity.app.event.MethodExceptionEventHandler;
import org.apache.velocity.app.event.NullSetEventHandler;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.context.Context;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.log.LogChute;
import org.apache.velocity.util.introspection.Info;


/**
 *      Velocity has a number of issues, the major one of which is that
 *      velocity is often silent in the face of errors.  The {@link Markup}
 *      class uses the event handler so that we can listen for issues
 *      procssing the template.
 *
 *      This <code>VelocityEventHandler</code> is useful for debugging
 *      problems with processing the template.
 *
 *      The event handler will listen as velocity processes the template
 *      for certain events, such as:
 *
 *      <ul>
 *          <li>velocity logging</li>
 *          <li>
 *              an exception in calling a method - on what I'm not sure, but
 *              I think it would be one of the parameters passed to velocity
 *              to fill out the template
 *          </li>
 *          <li>a value being set on a velocity template variable</li>
 *          <li>invalid get method</li>
 *          <li>invalid set method</li>
 *          <li>invalid method</li>
 *      </ul>
 *
 *      We turn most of the output of this class off, and only turn them
 *      on when we can't figure out what velocity is doing to our template,
 *      or when there's a broken test case we need to figure out.
 *
 *      @author vafsccorbit
 */
public class VelocityEventHandler
    implements
        ReferenceInsertionEventHandler,
        MethodExceptionEventHandler,
        LogChute,
        InvalidReferenceEventHandler,
        NullSetEventHandler {

    private static Logger log = Logger.getLogger( VelocityEventHandler.class );


    private boolean     logOutput               = false;
    private boolean     reportMissingMethods    = true;



    public VelocityEventHandler( final boolean    reportMissingMethods ) {
        this.reportMissingMethods = reportMissingMethods;
    }



    @Override
    public void init( final RuntimeServices arg0 ) throws Exception {

        //      Do nothing.
    }


    @Override
    public boolean isLevelEnabled( final int arg0 ) {

        return true;
    }


    @Override
    public void log( final int level, final String message ) {

        if ( ! logOutput )
        {
            return;
        }

        log.info("level : " + level + " msg : " + message);
    }


    @Override
    public void log( final int level, final String message, final Throwable ex ) {

        if ( ! logOutput )
        {
            return;
        }

        log.info("level : " + level + " msg : " + message );
        ex.printStackTrace();
    }


    @Override
    public Object methodException( final Class claz, final String method, final Exception ex )
        throws Exception {

        if ( true ) {
            throw ex;
        }

        return "fred:because nothing else will do";
    }


    @Override
    public boolean shouldLogOnNullSet( final String arg0, final String arg1 ) {

        return true;
    }


    @Override
    public Object referenceInsert( final String reference, final Object value ) {

        if ( value == null ) {
            log.info( String.format( "reference >%s< has a null value", reference ) );

            return "joe:because nothing else will do";
        }

        return value.toString();
    }


    @Override
    public Object invalidGetMethod( final Context     context,
                                    final String      reference,
                                    final Object      object,
                                    final String      property,
                                    final Info        info ) {

        if ( ! reportMissingMethods ) {
            return "";
        }

    	if ( object != null )
    	{
	        log.info( String.format( "invalidGetMethod: reference >%s< for object >%s< with property >%s< has failed. Info >%s<.",
	                                           reference,
	                                           object.getClass().getName(),
	                                           property,
	                                           info.toString() ) );
    	}

        log.info( String.format( "invalidGetMethod: reference >%s< for object >%s< with property >%s< has failed. Info >%s<.",
			                reference,
			                "dunnos",
			                property,
			                info.toString() ) );

        return String.format( "invalidGetMethod: >%s<", property ) ;
    }


    @Override
    public boolean invalidSetMethod( final Context    context,
                                     final String     leftreference,
                                     final String     rightreference,
                                     final Info       info ) {

        if ( ! reportMissingMethods ) {
            return false;
        }

        log.info( String.format( "invalidSetMethod: left reference >%s< cannot be set with right reference >%s<. Info >%s<.",
                                           leftreference,
                                           rightreference,
                                           info.toString() ) );

        return false;
    }


    @Override
    public Object invalidMethod( final Context    context,
                                 final String     reference,
                                 final Object     object,
                                 final String     method,
                                 final Info       info ) {

        if ( ! reportMissingMethods ) {
            return null;
        }

        log.info( String.format( "invalidMethod: reference >%s< for object >%s< with method >%s< has failed. Info >%s<.",
                                           reference,
                                           object.getClass().getName(),
                                           method,
                                           info.toString() ) );

        return null;
    }








    public void setReportMissingMethods( final boolean reportMissingMethods ) {

        this.reportMissingMethods = reportMissingMethods;
    }


    public void setLogOutput( final boolean logOutput ) {

        this.logOutput = logOutput;
    }
}
