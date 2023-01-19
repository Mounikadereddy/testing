/*
 * ProcessWebServiceResultsTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;


import static gov.va.vba.rbps.lettergeneration.hamcrest.files.FileMatchers.exists;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.lettergeneration.batching.SendToVirtualVa;
import gov.va.vba.rbps.lettergeneration.batching.fixtures.FileTestUtils;
import gov.va.vba.rbps.lettergeneration.batching.fixtures.TestingWebServiceRunner;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class ProcessWebServiceResultsTest {


    private List<File>                  createdFiles;
    private FileTestUtils               fileUtils       = new FileTestUtils();
//    private ProcessWebServiceResults    processor;
//    private TestingWebServiceRunner     runner;



    @Before
    public void setUp() throws Exception {
        createdFiles = fileUtils.createClaimsFiles( SendToVirtualVa.WAITING_DIR, "one", "two", "three" );

//        processor   = new ProcessWebServiceResults();
//        runner      = new TestingWebServiceRunner();
//        processor.setWebServiceRunner( runner );
    }


    @After
    public void tearDown() throws Exception {
        fileUtils.removeClaimsFiles( "one", "two", "three" );
        fileUtils.removeDirectories( SendToVirtualVa.WAITING_DIR, SendToVirtualVa.FAILURE_DIR );
    }


    @Ignore
    @Test
    public void shouldReturnCsvFileForPdf() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        result      = processor.csvFileFor( createdFiles.get( 0 ) );

        assertThat( result.getName(), is(equalTo( "one.csv" )));
    }


    @Ignore
    @Test
    public void shouldReturnWaitingForFile() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );

        File                        result      = processor.waitingFileFor( createdFiles.get( 0 ) );

        assertThat( result.getPath(), is(equalTo( waitingFileFor( new File( "one.pdf" )).getPath() )));
    }


    @Ignore
    @Test
    public void shouldRemoveFailedClaimsPdfFilesFromWaiting() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        oneFile     = createdFiles.get( 0 );

        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );
        processor.failed( Arrays.asList( oneFile ), SendToVirtualVa.FAILURE_DIR );

        assertThat( oneFile, not( exists() ) );
    }


    @Ignore
    @Test
    public void shouldRemoveFailedClaimsCsvFilesFromWaiting() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        oneFile     = createdFiles.get( 0 );

        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );
        processor.failed( Arrays.asList( oneFile ), SendToVirtualVa.FAILURE_DIR );

        assertThat( processor.csvFileFor( oneFile ), not( exists() ) );
    }


    @Ignore
    @Test
    public void shouldMoveFailedClaimsPdfFilesToFailure() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        failedFile  = createdFiles.get( 0 );
        File                        oneFile     = failedFileFor( failedFile );

        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );
        processor.failed( Arrays.asList( failedFile ), SendToVirtualVa.FAILURE_DIR );

        assertThat( oneFile, exists() );
    }


    @Ignore
    @Test
    public void shouldMoveFailedClaimsCsvFilesToFailure() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        failedFile  = noPathFileFor( createdFiles.get( 0 ) );
        File                        oneFile     = failedFileFor( failedFile );

        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );
        processor.failed( Arrays.asList( failedFile ), SendToVirtualVa.FAILURE_DIR );

        assertThat( processor.csvFileFor( oneFile ), exists() );
    }


    @Ignore
    @Test
    public void shouldRemoveSuccessfulClaimsPdfFilesFromWaiting() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        oneFile     = createdFiles.get( 0 );

        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );
        processor.success( Arrays.asList( oneFile ) );

        assertThat( oneFile, not( exists() ) );
    }


    @Ignore
    @Test
    public void shouldRemoveSuccessfulClaimsCsvFilesFromWaiting() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        oneFile     = createdFiles.get( 0 );

        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );
        processor.success( Arrays.asList( oneFile ) );

        assertThat( processor.csvFileFor( oneFile ), not( exists() ) );
    }


    @Ignore
    @Test
    public void shouldRemoveSuccessfulClaimsPdfFilesWithNoPathFromWaiting() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        oneFile     = noPathFileFor( createdFiles.get( 0 ) );

        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );
        processor.success( Arrays.asList( oneFile ) );

        assertThat( waitingFileFor( oneFile ), not( exists() ) );
    }


    @Ignore
    @Test
    public void shouldRemoveSuccessfulClaimsCsvFilesWithNoPathFromWaiting() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        File                        oneFile     = noPathFileFor( createdFiles.get( 0 ) );

        processor.setWaitingDir( SendToVirtualVa.WAITING_DIR );
        processor.success( Arrays.asList( oneFile ) );

        assertThat( processor.csvFileFor( waitingFileFor( oneFile ) ), not( exists() ) );
    }


    @Ignore
    @Test( expected = IllegalArgumentException.class )
    public void shouldCheckForWebService() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();

        processor.setWebServiceRunner( null );
        processor.runWebService();
    }


    public void shouldCheckForNullOrEmptySuccessfulList() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();

        processor.deleteSuccessfulFiles( null );
    }


    public void shouldCheckForNullOrEmptyFailureList() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();

        processor.moveFilesToFailureDir( null, SendToVirtualVa.FAILURE_DIR );
    }


    @Ignore
    @Test
    public void shouldRunWebService() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        TestingWebServiceRunner     runner      = new TestingWebServiceRunner();

        processor.setWebServiceRunner( runner );
        processor.runWebService();

        assertThat( runner.hasBeenRun, is( true ) );
    }


    @Ignore
    @Test
    public void shouldRunWebServiceViaProcessing() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        TestingWebServiceRunner     runner      = new TestingWebServiceRunner();

        processor.setWebServiceRunner( runner );
        processor.processWebServiceResults( SendToVirtualVa.WAITING_DIR, SendToVirtualVa.FAILURE_DIR );

        assertThat( runner.hasBeenRun, is( true ) );
    }


    @Ignore
    @Test
    public void shouldMoveFailedPdfToFailedViaProcessing() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        TestingWebServiceRunner     runner      = new TestingWebServiceRunner();
        File                        oneFile     = failedFileFor( createdFiles.get( 0 ) );

        runner.setFailedClaimsFiles( Arrays.asList( createdFiles.get( 0 ) ) );
        processor.setWebServiceRunner( runner );
        processor.processWebServiceResults( SendToVirtualVa.WAITING_DIR, SendToVirtualVa.FAILURE_DIR );


        assertThat( oneFile, exists() );
    }


    @Ignore
    @Test
    public void shouldMoveFailedCsvToFailedViaProcessing() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        TestingWebServiceRunner     runner      = new TestingWebServiceRunner();
        File                        oneFile     = failedFileFor( createdFiles.get( 0 ) );

        runner.setFailedClaimsFiles( Arrays.asList( createdFiles.get( 0 ) ) );
        processor.setWebServiceRunner( runner );
        processor.processWebServiceResults( SendToVirtualVa.WAITING_DIR, SendToVirtualVa.FAILURE_DIR );


        assertThat( processor.csvFileFor( oneFile ), exists() );
    }


    @Ignore
    @Test
    public void shouldRemoveSuccessfulPdfFileViaProcessing() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        TestingWebServiceRunner     runner      = new TestingWebServiceRunner();
        File                        oneFile     = createdFiles.get( 0 );

        runner.setSuccessfulClaimsFiles( Arrays.asList( oneFile ) );
        processor.setWebServiceRunner( runner );
        processor.processWebServiceResults( SendToVirtualVa.WAITING_DIR, SendToVirtualVa.FAILURE_DIR );


        assertThat( oneFile, not( exists() ) );
    }


    @Ignore
    @Test
    public void shouldRemoveSuccessfulCsvFileViaProcessing() {
        ProcessWebServiceResults    processor   = new ProcessWebServiceResults();
        TestingWebServiceRunner     runner      = new TestingWebServiceRunner();
        File                        oneFile     = createdFiles.get( 0 );

        runner.setSuccessfulClaimsFiles( Arrays.asList( oneFile ) );
        processor.setWebServiceRunner( runner );
        processor.processWebServiceResults( SendToVirtualVa.WAITING_DIR, SendToVirtualVa.FAILURE_DIR );


        assertThat( processor.csvFileFor( oneFile ), not( exists() ) );
    }






    private File waitingFileFor( final File        inputFile ) {

        return PathUtils.concatenateToFile( SendToVirtualVa.WAITING_DIR,
                                            inputFile.getName() );
    }


    private File failedFileFor( final File        inputFile ) {

        return PathUtils.concatenateToFile( SendToVirtualVa.FAILURE_DIR,
                                            inputFile.getName() );
    }


    private File noPathFileFor( final File file ) {
        return new File( file.getName() );
    }
}
