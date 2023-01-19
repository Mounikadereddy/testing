/*
 * WebServiceRunner.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;

import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;
import gov.va.vba.rbps.lettergeneration.virtualva.results.DocResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 *      Calls the Virtual VA web service and prepares the list of
 *      files that worked and those that failed.
 */
public class WebServiceRunner implements VirtualVaWebServiceRunner {

    private static final String             VALUE_FOR_SUCCESS = "S";


    private List<String>      failed        = new ArrayList<String>();
    private List<String>      succeeded     = new ArrayList<String>();



    @Override
    public void run() {

        String      results = "";

        //      TODO (MUCH LATER): actually call web service.

        parseResults( results );
    }


    public void parseResults( final String   xml ) {

        ResponseParser      parser  = new ResponseParser();
        List<DocResult>     results = parser.parse( xml );

        for ( DocResult docInfo : results ) {

            addToSuccess( docInfo );
            addtoFailed( docInfo );
        }
    }


    private void addtoFailed( final DocResult docInfo ) {

        if ( wasSuccessfull( docInfo ) ) {
            return;
        }

        failed.add( docInfo.getOriginalFileName() );
    }


    private void addToSuccess( final DocResult docInfo ) {

        if ( ! wasSuccessfull( docInfo ) ) {
            return;
        }

        succeeded.add( docInfo.getOriginalFileName() );
    }


    private boolean wasSuccessfull( final DocResult docInfo ) {

        return docInfo.getStatuscode().contains( VALUE_FOR_SUCCESS );
    }


    @Override
    public List<File> getSuccessfulClaimsFiles( final String waitingDir ) {

        return fileList( waitingDir, succeeded );
    }


    @Override
    public List<File> getFailedClaimsFiles( final String waitingDir ) {

        return fileList( waitingDir, failed );
    }


    private List<File> fileList( final String         waitingDir,
                                 final List<String>   fileNames ) {

        List<File>      files = new ArrayList<File>();

        for ( String fileName : fileNames ) {
            files.add( PathUtils.concatenateToFile( waitingDir, fileName ) );
        }

        return files;
    }
}
