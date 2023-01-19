/*
 * ProcessWebServiceResults.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;


import gov.va.vba.rbps.lettergeneration.batching.MoveToDir;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;


/**
 *      Call the Virtual VA web service runner and process
 *      the claims files according to the info
 *      we get from the web service.
 */
public class ProcessWebServiceResults {


    private VirtualVaWebServiceRunner           webServiceRunner;
    private String                              waitingDir;



    public void processWebServiceResults( final String      waitingDir,
                                          final String      failureDir ) {

        this.waitingDir = waitingDir;

        runWebService();
        success( webServiceRunner.getSuccessfulClaimsFiles( waitingDir ) );
        failed( webServiceRunner.getFailedClaimsFiles( waitingDir ), failureDir );
    }


    public void runWebService() {
        checkForWebService();
        webServiceRunner.run();
    }


    public void success( final List<File>       goodFiles ) {
        deleteSuccessfulFiles( goodFiles );
    }


    public void failed( final List<File>        failedFiles,
                        final String            failureDir ) {
        moveFilesToFailureDir( failedFiles, failureDir );
    }


    public void deleteSuccessfulFiles( final List<File> goodFiles ) {
        if ( goodFiles == null )
        {
            return;
        }

        for ( File file : goodFiles ) {
            File    waitingFile = waitingFileFor( file );

            FileUtils.deleteQuietly( waitingFile );
            FileUtils.deleteQuietly( csvFileFor( waitingFile ) );
        }
    }


    public void moveFilesToFailureDir( final List<File>        failedFiles,
                                       final String            failureDir ) {
        if ( failedFiles == null )
        {
            return;
        }

        MoveToDir   mover = new MoveToDir();

        for ( File file : failedFiles ) {
            File    waitingFile = waitingFileFor( file );

            mover.moveFile( waitingFile, failureDir );
            mover.moveFile( csvFileFor( waitingFile ), failureDir );
        }
    }


    public File csvFileFor( final File pdfFile ) {
        String      csvFileName = pdfFile.getAbsolutePath().replace( ".pdf", "" ) + ".csv";

        return new File( csvFileName );
    }


    private void checkForWebService() {
        if ( webServiceRunner != null ) {
            return;
        }

        throw new IllegalArgumentException( "A web service runner must be provided." );
    }


    public File waitingFileFor( final File        inputFile ) {

        return new File( waitingDir
                         + File.separator
                         + inputFile.getName() );
    }









    public void setWebServiceRunner( final VirtualVaWebServiceRunner webServiceRunner ) {
        this.webServiceRunner = webServiceRunner;
    }


    public void setWaitingDir( final String waitingDir ) {
        this.waitingDir = waitingDir;
    }
}
