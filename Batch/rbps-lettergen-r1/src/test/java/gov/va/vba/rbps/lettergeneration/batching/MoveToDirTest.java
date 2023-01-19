/*
 * MoveToDirTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration.batching;


import static gov.va.vba.rbps.lettergeneration.hamcrest.files.FileMatchers.exists;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.lettergeneration.batching.fixtures.FileTestUtils;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;

import java.io.File;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class MoveToDirTest {


    public static final String          DEST_DIR        = "sending";
    public static final String          BOGUS_FILE_DIR  = "bogusFileDir";
    public static final String          TEST_TMP_DIR    = "testTmpDir";

    private List<File>                  createdFiles;
    private final FileTestUtils         fileUtils       = new FileTestUtils();


    @Before
	public void setUp() throws Exception {
	    createdFiles = fileUtils.createClaimsFiles( TEST_TMP_DIR, "one", "two", "three" );
	}


    @After
	public void tearDown() throws Exception {
//        fileUtils.removeClaimsFiles( "one", "two", "three" );
        fileUtils.removeDirectories( DEST_DIR, BOGUS_FILE_DIR, TEST_TMP_DIR );
	}


    @Test
	public void shouldListAllFilesToMove() {
		MoveToDir      mover   = new MoveToDir();
		List<File>     results = mover.listPdfAndCsvFilesInDir( TEST_TMP_DIR );

		assertThat( new HashSet<File>( results ), is( equalTo( new HashSet<File>( createdFiles ) ) ) );
	}


    @Test
    public void shouldMoveFile() {
        MoveToDir      mover           = new MoveToDir();
        File           fileToMove      = createdFiles.get(0);
        String         destinationDir  = DEST_DIR;

        mover.moveFile( fileToMove, destinationDir );

        assertThat( PathUtils.concatenateToFile( destinationDir, fileToMove.getName() ), exists() );
    }


    @Test
    public void shouldRemoveAlreadyExistingFileInDestinationDir() throws Throwable {
        MoveToDir      mover           = new MoveToDir();
        File           fileToMove      = createdFiles.get(0);
        String         destinationDir  = DEST_DIR;
        fileUtils.createFile( destinationDir, fileToMove.getName() );

        mover.moveFile( fileToMove, destinationDir );

        assertThat( PathUtils.concatenateToFile( destinationDir, fileToMove.getName() ), exists() );
    }


    @Test
    public void shouldCreateDestDir() throws Throwable {
        MoveToDir      mover           = new MoveToDir();
        File           fileToMove      = createdFiles.get(0);
        String         destinationDir  = BOGUS_FILE_DIR;

        FileUtils.deleteQuietly( new File( DEST_DIR ) );

        mover.moveFile( fileToMove, destinationDir );
    }


    @Test( expected = IllegalArgumentException.class )
//    @Test
    public void shouldCheckDestDirNotFile() throws Throwable {
        MoveToDir      mover           = new MoveToDir();
        File           fileToMove      = createdFiles.get(0);
        String         destinationDir  = BOGUS_FILE_DIR;

        new File( destinationDir ).createNewFile();

        mover.moveFile( fileToMove, destinationDir );
    }


    @Test( expected = IllegalArgumentException.class )
//    @Test
    public void shouldCheckFileNotNull() throws Throwable {
        MoveToDir      mover          = new MoveToDir();
        String         destinationDir = DEST_DIR;

        mover.moveFile( null, destinationDir );
    }


    @Test( expected = IllegalArgumentException.class )
//    @Test
    public void shouldCheckFileExists() throws Throwable {
        MoveToDir      mover           = new MoveToDir();
        String         destinationDir  = DEST_DIR;

        mover.moveFile( new File( "xyzzy" ), destinationDir );
    }


    @Test()
    public void shouldMoveFiles() throws Throwable {
        MoveToDir      mover           = new MoveToDir();
        String         destinationDir  = DEST_DIR;

        mover.moveFiles( ".", destinationDir );
    }
}
