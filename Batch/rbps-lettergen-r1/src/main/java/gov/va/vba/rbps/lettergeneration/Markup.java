/*
 * Markup.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolContext;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.config.EasyFactoryConfiguration;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.LoopTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.SortTool;


/**
 *      This class does the actual conversion from a set of data and
 *      a velocity template file to output.  The output is a string
 *      with all the data formatted per the template.  The actual
 *      setup and the call to velocity is done here.
 *
 *      Performance later might suggest that the velocity engine
 *      be set up once, instead of for each notification letter,
 *      but until we have experience, we can't know if that will
 *      make much of a difference.
 *
 *      @author vafsccorbit
 */
public class Markup {

    private boolean         raiseErrorOnMissingReferences	= false;
    private boolean         logit                           = false;



	/**
	 *         Take the given velocity param map, the path to the template file,
	 *         and run velocity to plug the param map into the template file
	 *         so as to return a <code>String</code> containing the processed
	 *         template file.
	 *
	 *         @param templateName
	 *         @param param
	 *         @param reportMissingMethods
	 *         @return
	 */
    public String mergeTemplate( final String                 templateName,
                                 final Map<String,Object>     param,
                                 final boolean                reportMissingMethods ) {

        try {
            Properties properties = new Properties();
            properties.setProperty( Velocity.RESOURCE_LOADER,               "class" );
            properties.setProperty( "class.resource.loader.description",    "Velocity Classpath Resource Loader" );
            properties.setProperty( "class.resource.loader.class",          ClasspathResourceLoader.class.getName() );
            properties.setProperty( "runtime.references.strict",            "" + raiseErrorOnMissingReferences );
//            properties.setProperty( "velocimacro.library",                  null );

            VelocityEngine              engine          = new VelocityEngine();
            VelocityEventHandler        eventHandler    = new VelocityEventHandler( reportMissingMethods );

            engine.setProperty( Velocity.RUNTIME_LOG_LOGSYSTEM, eventHandler );
            engine.init( properties );

            StringWriter            writer  = new StringWriter();
            VelocityContext         context = addParamToContext( param, eventHandler );

            Template template = engine.getTemplate( templateName );
            template.merge( context, writer );

            String result = writer.toString();

            logResultToFile( result );

            return result;
        }
        catch ( Throwable ex ) {
            throw new TemplateMergeException( String.format( "Unable to merge template >%s<", templateName ),
                                              ex );
        }
    }


    /**
     *      Take the given param map from the client of this api and put all
     *      the values in the velocity context.
     *
     *      @param param
     *      @param eventHandler
     *      @return
     */
    private VelocityContext addParamToContext( final Map<String, Object>    param,
                                               final VelocityEventHandler   eventHandler ) {

        VelocityContext context = createVelocityContext( eventHandler );

       for ( Map.Entry<String, Object> entry : param.entrySet() ) {
            context.put( entry.getKey(), entry.getValue() );
        }

        return context;
    }


    /**
     *      Create the velocity context and inject the event handler.
     *
     *      @param eventHandler
     *      @return
     */
    private VelocityContext createVelocityContext( final VelocityEventHandler   eventHandler ) {

        VelocityContext     context         = initializeContextWithTools();
        EventCartridge      eventCartridge  = new EventCartridge();
        eventCartridge.addEventHandler( eventHandler );
        eventCartridge.attachToContext( context );

        return context;
    }


    /**
     *      Make several of the auxiliary velocity tools available to the
     *      template by getting them added to an "easy config" object
     *      and adding them to the velocity context.
     *
     *      @return the modified velocity context.
     */
    private VelocityContext initializeContextWithTools() {

        EasyFactoryConfiguration config = new EasyFactoryConfiguration();

        addToolsToConfig( config );

        ToolboxFactory      factory     = config.createFactory();
        ToolContext         toolContext = new ToolContext();
        toolContext.addToolbox( factory.createToolbox( Scope.APPLICATION  ) );

        VelocityContext     context     = new VelocityContext( toolContext );

        return context;
    }


    /**
     *      Add several of the useful auxiliary velocity tools to the
     *      velocity config so that later they can be added to the velocity
     *      context.   Velocity is kind of confusing to configure.  Normally
     *      it's all done as part of a web application framework.  We're sort
     *      of the odd man out here.
     *
     *      @param config the factory config into which these tools will be injected
     */
    @SuppressWarnings( { "rawtypes", "unchecked" } )
    private void addToolsToConfig( final EasyFactoryConfiguration config ) {

        List     tools = Arrays.asList( DateTool.class,
                                        NumberTool.class,
                                        EscapeTool.class,
                                        SortTool.class,
                                        LoopTool.class );

        for ( Class  toolClass : (List<Class>) tools ) {

            config.toolbox( Scope.APPLICATION ).property( "locale", Locale.US )
            .tool( toolClass );
        }
    }


    private void logResultToFile( final String result ) {

        if ( ! logit ) {

            return;
        }
        BufferedWriter  out = null;
        try {

            FileWriter      fstream     = new FileWriter( String.format( "%s/%s", System.getProperty( "java.io.tmpdir" ), "processed_template.html" ) );
            out         = new BufferedWriter(fstream);
            //Fortify issue note: the below line our.write(result) is never reachable, because the logit attribute
            //is set as false as a private parameter and there is not set/get method for it. The logit is false always.
            out.write( result );
            out.flush();
            out.close();
        }
        catch ( Throwable ex ) {

            System.out.println( "Unable to write processed template file." );
            ex.printStackTrace();
        } finally { //Fortify Unreleased Resource: Stream fix
        	if (out != null) 
        		try {out.close();} catch (Exception e) {
        			//do nothing
        		}
        }
    }






	public void setRaiseErrorOnMissingReferences( final boolean raiseErrorOnMissingReferences) {
		this.raiseErrorOnMissingReferences = raiseErrorOnMissingReferences;
	}
}
