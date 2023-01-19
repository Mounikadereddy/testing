/*
 * MoveToDir.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching;


import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;


/**
 *      Simplifies moving batches of csv and pdf files.
 */
public class MoveToDir {


    public void moveFiles( final String sourceDir, final String destinationDir ) {

        try {
            Collection<File>  files = listPdfAndCsvFilesInDir( sourceDir );

            for ( File    file : files ) {
                moveFile( file, destinationDir );
            }
        }
        catch ( Throwable ex )
        {
            throw new IllegalArgumentException( "Unable to move source files to directory >" + destinationDir + "<", ex );
        }
    }


    public void moveFile( final File  file, final String destinationDir ) {

        checkFileGiven( file, destinationDir );

        try {
            checkFileExists( file );
            removeAlreadyExistingFile( file.getName(), destinationDir );
            FileUtils.moveFileToDirectory( file, new File( destinationDir ), true );
        }
        catch ( Throwable ex )
        {
            throw new IllegalArgumentException( "Unable to move source file >" + file.getAbsolutePath() + "< to directory >" + destinationDir + "<", ex );
        }
    }


	public List<File> listPdfAndCsvFilesInDir( final String sourceDirectoryPath ) {

		try {
            List<File> files     = getListOfFilesInDirectory( sourceDirectoryPath );

            return files;
        } catch (Throwable ex) {
            throw new IllegalArgumentException( "Unable to get list of files in >" + sourceDirectoryPath + "<", ex );
        }
	}


    private List<File> getListOfFilesInDirectory( final String sourceDirectoryPath  ) {

        File    sourceDir = new File( sourceDirectoryPath );

        Collection<File> files = FileUtils.listFiles( sourceDir,
        		                                      new String[] { "pdf", "csv" },
        		                                      false );
        return new ArrayList<File>( files );
    }


//    private List<String> convertFilesToFileNames( final Collection<File> files ) throws IOException {
//        List<String> fileNames = new ArrayList<String>();
//
//        for ( File file : files )
//        {
//            fileNames.add( file.getName() );
//        }
//
//        return fileNames;
//    }


    private void checkFileGiven( final File file, final String destinationDir ) {

        if ( file != null ) {
            return;
        }

        throw new IllegalArgumentException( "Unable to move source file to directory >" + destinationDir + "<, since no file was specified." );
    }


    private void checkFileExists( final File file ) {

        if ( file.exists() ) {
            return;
        }

        throw new IllegalArgumentException( "File >" + file.getAbsolutePath() + "< does not exist." );
    }


    private void removeAlreadyExistingFile( final String fileName,
                                            final String destinationDir ) {
        new File( destinationDir + File.separator + fileName  ).delete();
    }
}
