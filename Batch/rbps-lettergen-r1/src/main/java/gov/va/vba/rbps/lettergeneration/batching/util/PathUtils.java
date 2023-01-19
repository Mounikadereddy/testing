/*
 * PathUtils.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.util;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;


/**
 *      Some utils for adding working with paths.
 *      Can add pieces of paths together as well as
 *      get a csv file or path from a path to a pdf file.
 *
 *      @author vafsccorbit
 */
public class PathUtils {



    public static String concatenateToPath( final String...   fileNames ) {

        List<String>    names   = convertToNamesList( fileNames );

        return joinAll( names );
    }


    public static File concatenateToFile( final String...   fileNames ) {

        List<String>    names   = convertToNamesList( fileNames );

        return new File( joinAll( names ) );
    }


    public static String joinAll( final List<String>      names ) {

        String      total = names.get( 0 );
        for ( int ii = 1; ii < names.size(); ii++ ) {
            total = FilenameUtils.concat( total, names.get( ii ) );
        }

        return total;
    }


    private static List<String> convertToNamesList( final String... fileNames ) {

        List<String>    names   = new ArrayList<String>();

        for ( String  fileName : fileNames ) {
            names.add( fileName );
        }

        return names;
    }


    public static File csvFileFor( final File pdfFile ) {

        String      csvFileName = pdfFile.getAbsolutePath().replace( ".pdf", "" ) + ".csv";

        return new File( csvFileName );
    }


    public static String csvPathFor( final String pdfFile ) {

        return pdfFile.replace( ".pdf", "" ).replace( ".rtf", "" ) + ".csv";
    }
}
