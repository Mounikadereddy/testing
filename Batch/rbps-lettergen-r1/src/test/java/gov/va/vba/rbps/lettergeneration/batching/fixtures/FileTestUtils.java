/*
 * FileTestUtils.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.fixtures;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;


public class FileTestUtils {


    public List<File> createClaimsFiles( final String     directory,
                                         final String...   prefixes ) throws IOException {
        List<File>  files = new ArrayList<File>();

        for ( String prefix : prefixes ) {
            String fileName = prefix + ".pdf";
            files.add( createFile( directory, fileName ) );

            fileName = prefix + ".csv";
            files.add( createFile( directory, fileName ) );
        }

        return files;
    }


//  private File createFile( final String fileName ) throws IOException {
//      File newFile = new File( fileName );
//
//      newFile.createNewFile();
//
//      return newFile;
//  }


    public File createFile( final String destinationDir, final String file ) throws IOException {
        new File( destinationDir ).mkdir();

        File        destinationFile = new File( destinationDir + File.separator + file );
        destinationFile.createNewFile();

        return destinationFile;
    }


    public void removeClaimsFiles( final String...   fileNames ) {
        for ( String name : fileNames ) {
            new File( name + ".pdf" ).delete();
            new File( name + ".csv" ).delete();
        }
    }


    public void removeDirectories( final String...   dirNames ) {
        for ( String name : dirNames ) {
            FileUtils.deleteQuietly( new File( name ) );
        }
    }


    public int numFilesIn( final String   directoryPath ) {
        File        dir     = new File( directoryPath );
        String[]    files   = dir.list();

        if ( files == null ) {
            return 0;
        }

        return files.length;
    }


    public String getFileContents( final String   path ) throws IOException {

        ClassPathResource   finder  = new ClassPathResource( path );
        String              xml     = FileUtils.readFileToString( finder.getFile() );
        return xml;
    }
}
