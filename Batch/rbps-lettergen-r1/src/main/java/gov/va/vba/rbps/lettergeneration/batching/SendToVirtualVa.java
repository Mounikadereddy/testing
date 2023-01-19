/*
 * SendToVirtualVa.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching;


import java.io.File;

import org.apache.commons.lang.StringUtils;


/**
 *      Take all the files in the pending dir, move them to sending,
 *      call the shell script to send to connect:direct, and move the
 *      files to waiting if the connect:direct script works.
 *
 *      Otherwise, send them back to waiting.
 */
public class SendToVirtualVa {

    public static final String      PENDING_DIR     = "pending";
    public static final String      SENDING_DIR     = "sending";
    public static final String      WAITING_DIR     = "waiting";
    public static final String      FAILURE_DIR     = "failure";



    private String          rootDir             = null;
    private String          pendingDir          = PENDING_DIR;
    private String          sendingDir          = SENDING_DIR;
    private String          waitingDir          = WAITING_DIR;
    private String          failureDir          = FAILURE_DIR;
    private ScriptRunner    scriptRunner;


    public void send() {
        initDirectoryPaths();
        moveToSendingDir();

        sendToVirtualVa();
    }


    public void sendToVirtualVa() {
        if ( scriptRunner ==  null  ) {
            throw new IllegalArgumentException( "A ScriptRunner must be specified." );
        }

        boolean worked = scriptRunner.run();

        if ( worked )
            moveToWaitingDir();
        else
            moveToFailureDir();
    }


    private void moveToSendingDir() {
        MoveToDir   mover = new MoveToDir();
        mover.moveFiles( pendingDir, sendingDir );
    }


    private void moveToWaitingDir() {
        MoveToDir   mover = new MoveToDir();
        mover.moveFiles( sendingDir, waitingDir );
    }


    private void moveToFailureDir() {
        MoveToDir   mover = new MoveToDir();
        mover.moveFiles( sendingDir, failureDir );
    }


//    private void moveBackToPendingDir() {
//        MoveToDir   mover = new MoveToDir();
//        mover.moveFiles( sendingDir, pendingDir );
//    }


    public void initDirectoryPaths() {
        if ( StringUtils.isBlank( rootDir ) ) {
            return;
        }

        pendingDir = prependRootDir( pendingDir );
        sendingDir = prependRootDir( sendingDir );
        waitingDir = prependRootDir( waitingDir );
        failureDir = prependRootDir( failureDir );
    }


    private String prependRootDir( final String seed ) {
        return rootDir + File.separator + seed;
    }








    public String getRootDir() {
        return rootDir;
    }


    public void setRootDir( final String rootDir ) {
        this.rootDir = rootDir;
    }



    public String getPendingDir() {
        return pendingDir;
    }


    public void setPendingDir( final String pendingDir ) {
        this.pendingDir = pendingDir;
    }



    public String getSendingDir() {
        return sendingDir;
    }


    public void setSendingDir( final String sendingDir ) {
        this.sendingDir = sendingDir;
    }



    public String getWaitingDir() {
        return waitingDir;
    }


    public void setWaitingDir( final String waitingDir ) {
        this.waitingDir = waitingDir;
    }



    public ScriptRunner getScriptRunner() {
        return scriptRunner;
    }


    public void setScriptRunner( final ScriptRunner scriptRunner ) {
        this.scriptRunner = scriptRunner;
    }



    public String getFailureDir() {
        return failureDir;
    }


    public void setFailureDir( final String failureDir ) {
        this.failureDir = failureDir;
    }
}
