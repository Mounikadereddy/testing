/*
 * AwardPrintSaver.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.bip.docgen.service.plugin.awards.api.edoc.EdocDocument;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsServiceException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.lettergeneration.batching.util.FileNameGenerator;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.DocumentVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 *      Save the award print data returned from the <code>AmendAward</code>
 *      web service to a pdf file and create the corresponding csv file.
 *      The file names will contain the claim # and an abbreviated
 *      form of the date: yyyyMMdd.
 */
public class AwardPrintSaver {

    private static Logger logger = Logger.getLogger(AwardPrintSaver.class);

    private LogUtils            logUtils = new LogUtils( logger, true );

    //  Fed from spring water
    //private RbpsRepository      repository;
    private String              pathToPdfOutputDirectory    = System.getProperty( "java.io.tmpdir" );


    public AwardPrintSaver() {

        // Just for spring
    }


    /**
     *      Save the repository for later use.
     *
     *      @param repository - the rbps repository
     
    public AwardPrintSaver( final RbpsRepository repository ) {

        this.repository = repository;
    }
    */


    /**
     *      Take the byte array from the response from adding (a) dependent(s) to an
     *      award and save the file and output a matching csv file.  Actually, the
     *      response contains a list of documents.  We save each document in the list
     *      into a file containing the document index in the list.  The index starts
     *      with 1.
     *
     *      @param response - this is the response from adding (a) dependent(s) to
     *                  an award.
     */
    public void saveAwardPrintAndCsv( final ProcessAwardDependentResponse     response, RbpsRepository repo ) {

        saveAwardPrintFiles( response, repo );
    }


    /**
     *      Take the byte array from the response from adding (a) dependent(s) to an
     *      award and save the file and output a matching csv file.  Actually, the
     *      response contains a list of documents.  We save each document in the list
     *      into a file containing the document index in the list.  The index starts
     *      with 1.
     *
     *      @param response - this is the response from adding (a) dependent(s) to
     *                  an award.
     */
    private void saveAwardPrintFiles( final ProcessAwardDependentResponse response, RbpsRepository repo ) {

        int counter = 1;
        //The DocumentReturnVO object, the DocumentSummary has removed from Award_EJB and RBPS interface.
        //it has no useful values.
//        for ( DocumentVO doc : response.getReturn().getDocumentSummary().getDocumentList() ) {
//
//            saveAwardPrintFile( doc, counter, repo );
//            saveCsvFile( counter, repo );
//
//            counter++;
//        }
    }


    private void saveAwardPrintFile( final DocumentVO   doc,
                                     final int          counter,
                                     RbpsRepository repo) {

        String fileName = "<unkown>";

        try {
            fileName = generateFileName( counter, repo );

            saveToPdf( doc, fileName, repo );
        }
        catch ( Throwable ex ) {

            throw new RbpsServiceException( String.format( "Unable to save pdf file to >%s<",
                                                           fileName ),
                                            ex );
        }
    }


    public void saveCsvFile( final int         counter, RbpsRepository repo ) {

        String pdfFileName = generateFileName( counter, repo );

        new CsvFileGenerator().generateCsvFile( repo.getVeteran().getFileNumber(),
                                                pdfFileName,
                                                PathUtils.csvPathFor( pdfFileName ),
                                                CsvFileGenerator.VIRTUAL_VA_DOC_TYPE_AWARD_PRINT );
    }


    private void saveToPdf( final DocumentVO doc, final String fileName, RbpsRepository repo )
        throws FileNotFoundException,
               IOException {

        if ( doc == null || doc.getLetterText() == null ) {

            logUtils.log( "Awards did not send an award print, so nothing can be saved to pdf.", repo );
            return;
        }

        FileOutputStream    output = null;

        try {
            output = new FileOutputStream( new File( fileName ) );
            output.write( doc.getLetterText() );
        }
        finally {

            if ( output != null ) {

                output.close();
            }
        }
    }



    public String generateFileName( final int counter, RbpsRepository repo ) {

        String  fileNumber = repo.getVeteran().getFileNumber();
        return new FileNameGenerator( pathToPdfOutputDirectory )
                    .generateFileName( fileNumber, "_ap" + counter, "pdf" );
    }
    
    /**
     * generate file name without path
     * @param counter
     * @param repo
     * @return
     */
    public String generateFileNameWithoutPath( final int counter, RbpsRepository repo ) {

        String  fileNumber = repo.getVeteran().getFileNumber();
        return new FileNameGenerator("")
                    .generateFileName( fileNumber, "_ap" + counter, "pdf" );
    }


/*
    public void setRepository( final RbpsRepository repository ) {

        this.repository = repository;
    }
*/

    public void setPathToPdfOutputDirectory( final String pathToPdfOutputDirectory ) {

        this.pathToPdfOutputDirectory = pathToPdfOutputDirectory;
    }
}
