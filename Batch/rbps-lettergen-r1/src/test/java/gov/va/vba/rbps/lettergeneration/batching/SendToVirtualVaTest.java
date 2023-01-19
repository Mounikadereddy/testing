/*
 * SendToVirtualVaTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration.batching;


import static gov.va.vba.rbps.lettergeneration.hamcrest.files.FileMatchers.exists;
import static gov.va.vba.rbps.lettergeneration.hamcrest.files.FileMatchers.isDirectory;
import static gov.va.vba.rbps.lettergeneration.hamcrest.files.FileMatchers.isDirectoryEmpty;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.lettergeneration.batching.fixtures.FileTestUtils;
import gov.va.vba.rbps.lettergeneration.batching.fixtures.TestingScriptRunner;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


@Ignore
public class SendToVirtualVaTest {

    private List<File>          createdFiles;
    private FileTestUtils       fileUtils       = new FileTestUtils();


    @Before
    public void setUp() throws Exception {
        createdFiles = fileUtils.createClaimsFiles( SendToVirtualVa.SENDING_DIR,
                                                    "one",
                                                    "two",
                                                    "three" );
    }


    @After
    public void tearDown() throws Exception {
        fileUtils.removeDirectories( SendToVirtualVa.PENDING_DIR,
                                     SendToVirtualVa.SENDING_DIR,
                                     SendToVirtualVa.WAITING_DIR,
                                     SendToVirtualVa.FAILURE_DIR );
    }


    @Test
    public void shouldPrefixRootDir() {
        SendToVirtualVa     sender = new SendToVirtualVa();

        sender.initDirectoryPaths();

        assertThat( sender.getPendingDir(), is(equalTo( SendToVirtualVa.PENDING_DIR )));
    }


    @Test
    public void shouldPrefixDifferentRootDir() {
        SendToVirtualVa     sender = new SendToVirtualVa();

        String fred = "fred";
        sender.setRootDir( fred );
        sender.initDirectoryPaths();

        assertThat( sender.getPendingDir(), is(equalTo( PathUtils.concatenateToPath( fred, SendToVirtualVa.PENDING_DIR ))));
    }


    @Test( expected = IllegalArgumentException.class )
    public void shouldFailIfNoScriptRunner() throws Throwable {
        SendToVirtualVa         sender          = new SendToVirtualVa();

        sender.sendToVirtualVa();
    }


    @Test
    public void shouldMoveClaimsFilesToFailureOnFailure() throws Throwable {
        SendToVirtualVa         sender          = new SendToVirtualVa();
        TestingScriptRunner     scriptRunner    = new TestingScriptRunner( false );

        sender.setScriptRunner( scriptRunner );
        sender.sendToVirtualVa();

        assertThat( PathUtils.concatenateToFile( SendToVirtualVa.FAILURE_DIR, createdFiles.get(0).getName() ),
                    exists() );
    }


    @Test
    public void shouldMoveAllClaimsFilesToFailureOnFailure() throws Throwable {
        SendToVirtualVa         sender              = new SendToVirtualVa();
        TestingScriptRunner     scriptRunner        = new TestingScriptRunner( false );
        int                     numFilesInSending   = fileUtils.numFilesIn( sender.getSendingDir() );

        sender.setScriptRunner( scriptRunner );
        sender.sendToVirtualVa();

        assertThat( fileUtils.numFilesIn( sender.getFailureDir() ),
                    is(equalTo( numFilesInSending )) );
    }


    @SuppressWarnings( "unchecked" )
    @Test
    public void shouldSendingBeEmptyOnFailure() throws Throwable {
        SendToVirtualVa         sender          = new SendToVirtualVa();
        TestingScriptRunner     scriptRunner    = new TestingScriptRunner( false );

        sender.setScriptRunner( scriptRunner );
        sender.sendToVirtualVa();

        assertThat( new File( SendToVirtualVa.SENDING_DIR ),
                    allOf( isDirectory(), isDirectoryEmpty() ) );
    }


    @Test
    public void shouldMoveClaimsFilesToWaitingOnSuccess() throws Throwable {
        SendToVirtualVa         sender          = new SendToVirtualVa();
        TestingScriptRunner     scriptRunner    = new TestingScriptRunner( true );

        sender.setScriptRunner( scriptRunner );
        sender.sendToVirtualVa();

        assertThat( PathUtils.concatenateToFile( SendToVirtualVa.WAITING_DIR, createdFiles.get(0).getName() ),
                    exists() );
    }


    @Test
    public void shouldMoveAllClaimsFilesToWaitingOnSuccess() throws Throwable {
        SendToVirtualVa         sender              = new SendToVirtualVa();
        TestingScriptRunner     scriptRunner        = new TestingScriptRunner( true );
        int                     numFilesInSending   = fileUtils.numFilesIn( sender.getSendingDir() );

        sender.setScriptRunner( scriptRunner );
        sender.sendToVirtualVa();

        assertThat( fileUtils.numFilesIn( sender.getWaitingDir() ),
                    is(equalTo( numFilesInSending )) );
    }


    @SuppressWarnings( "unchecked" )
    @Test
    public void shouldSendingBeEmptyOnSuccess() throws Throwable {
        SendToVirtualVa         sender          = new SendToVirtualVa();
        TestingScriptRunner     scriptRunner    = new TestingScriptRunner( true );

        sender.setScriptRunner( scriptRunner );
        sender.sendToVirtualVa();

        assertThat( new File( SendToVirtualVa.SENDING_DIR ),
                    allOf( isDirectory(), isDirectoryEmpty() ) );
    }
}
