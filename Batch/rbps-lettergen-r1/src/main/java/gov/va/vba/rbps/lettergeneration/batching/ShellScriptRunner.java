/*
 * ShellScriptRunner.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.lang.time.DateUtils;


/**
 *      A runner which takes a path to a shell script
 *      and uses apache commons exec to run it.
 */
public class ShellScriptRunner
    implements
        ScriptRunner {

    private static final long        TIMEOUT = 120 * DateUtils.MILLIS_PER_SECOND;

    private long        timeout         = TIMEOUT;
    private String      pathToScript;
    private String      sendingDir;



    @Override
    public boolean run() {
        try {
            DefaultExecutor     executor    = new DefaultExecutor();
            ExecuteWatchdog     watchdog    = new ExecuteWatchdog( timeout );

            executor.setWatchdog( watchdog );

            int exitValue = executor.execute( buildCommandLine() );

            return exitValue == 0;
        }
        catch ( ExecuteException ex ) {
            return false;
        }
        catch ( Throwable ex ) {
            throw new RuntimeException( "Unable to run shell script to use Connect:Direct to send to Virtual VA.", ex );
        }
    }


    public CommandLine buildCommandLine() {
        checkPathToScript();

        CommandLine commandLine = new CommandLine( pathToScript );

        commandLine.addArgument( "${sendingDir}" );
        addSendingDirProtectingSpaces( commandLine );


        return commandLine;
    }


    private void addSendingDirProtectingSpaces( final CommandLine commandLine ) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put( "sendingDir", new File( sendingDir ) );

        commandLine.setSubstitutionMap( map );
    }


    private void checkPathToScript() {
        if ( new File( pathToScript).exists() ) {
            return;
        }

        throw new IllegalArgumentException( "Script >" + pathToScript + "< does not exist." );
    }











    public void setPathToScript( final String pathToScript ) {
        this.pathToScript = pathToScript;
    }


    public void setSendingDir( final String sendingDir ) {
        this.sendingDir = sendingDir;
    }


    public void setTimeoutInSeconds( final long timeoutInSeconds ) {
        this.timeout = timeoutInSeconds * DateUtils.MILLIS_PER_SECOND;
    }
}
