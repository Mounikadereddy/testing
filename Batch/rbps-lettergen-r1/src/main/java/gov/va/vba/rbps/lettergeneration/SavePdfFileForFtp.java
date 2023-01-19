/*
 * SavePdfFileForFtp.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsLetterGenException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;



/**
 *      Copies the pdf files as they are created up to Louay's file sharing machine.
 *      Uses sftp (on top of ssh, not ssl) to send the file.
 *
 *      The reason we copy these files to that machine is so that they can get
 *      printed.
 */
public class SavePdfFileForFtp {

    private static Logger logger = Logger.getLogger(SavePdfFileForFtp.class);

    //private CommonUtils utils;
    private LogUtils    logUtils        = new LogUtils( logger, true );


    /**
     *      Copy the given pdf file to Louay's file sharing machine
     *      so that it can get printed.
     */
    /*public void saveFileForFtp( final String        pathToPdfOutputDir,
                                final String        localFilePath,
                                final String        poaOrganizationName,
                                RbpsRepository repo) {

        String      ftpDir          = ftpPathFor( pathToPdfOutputDir );
        int         numberOfPages   = getNumberOfPages( localFilePath );
        String      ftpPath         = String.format( "%s/pages%d",
                                                     ftpDir,
                                                     numberOfPages );

        copyFile( localFilePath, ftpPath, repo );

        if ( ! StringUtils.isBlank( poaOrganizationName ) ) {

            ftpPath = String.format( "%s/%s/pages%d",
                                     ftpDir,
                                     poaOrganizationName,
                                     numberOfPages );

            copyFile( localFilePath, ftpPath, repo );
        }
    }*/


    /**
     *      Copy the given pdf file to Louay's file sharing machine
     *      so that it can get printed.
     */
    
/*    private void copyFile( final String   localFilePath,
                           final String   ftpPath,
                           RbpsRepository repo) {

        try {
            FileUtils.copyFileToDirectory( new File( localFilePath ), new File( ftpPath ) );
            logUtils.log( String.format( "`Copied file `%s to %s for ftp", localFilePath, ftpPath ), repo );
        }
        catch ( Throwable ex ) {

            throw new RbpsLetterGenException( "Unable to create printing pdf file", ex );
        }
    }*/

    
    
    public void saveFileForPrinting( final String       pathToPdfOutputDir,
                                	 final String       fileName,
                                	 final String       veteranFileName,
                                	 final String		poaFileName,
                                	 final boolean 		isInternational,
                                	 RbpsRepository repo) {

    	String		localFilePath	= String.format( "%s/%s", pathToPdfOutputDir, fileName );
        
        String      copyPath     	= "";
        
        if ( isInternational ) {
        	
        	copyPath	= String.format( "%s/INT/%s", pathToPdfOutputDir, veteranFileName );
        }
        else {
        	
        	copyPath	= String.format( "%s/VET/%s", pathToPdfOutputDir, veteranFileName );
        }

        // debug for PDF files
        logUtils.log( String.format( "Copied file for Printing from %s to %s ", localFilePath, copyPath ), repo );
        copyFileForPrinting( localFilePath, copyPath, repo );

        if ( ! StringUtils.isBlank( poaFileName ) ) {

        	copyPath = String.format( "%s/POA/%s", pathToPdfOutputDir, poaFileName );

            copyFileForPrinting( localFilePath, copyPath, repo );
        }
    }

    
    
    private void copyFileForPrinting( final String   localFilePath,
                           			  final String   ftpPath,
                           			  RbpsRepository repo) {

        try {
            FileUtils.copyFile( new File( localFilePath ), new File( ftpPath ) );
            logUtils.log( String.format( "Copied file %s to %s for printing", localFilePath, ftpPath ),repo );
        }
        catch ( Throwable ex ) {

            throw new RbpsLetterGenException( "Unable to create printing pdf file", ex );
        }
    }


    private String ftpPathFor( final String pathToPdfOutputDir ) {

        return String.format( "%s/ftp", pathToPdfOutputDir );
    }
    public void setLogit( final boolean logit ) {

        logUtils.setLogit( logit );
    }
    
    /*

    public void setCommonUtils( final CommonUtils utils ) {

        this.utils = utils;
    }
    */
}
