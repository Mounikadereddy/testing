/*
 * AwardPrintSaverTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import static gov.va.vba.rbps.lettergeneration.hamcrest.files.FileMatchers.exists;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.lettergeneration.batching.util.FileNameGenerator;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.DocumentVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.io.File;

import org.junit.Before;
import org.junit.Test;


/**
 *      Test case for the <code>AwardPrintSaver</code> class
 */
public class AwardPrintSaverTest {


    @Before
    public void setUp() throws Exception {

    }


    /**
     *      The award print saver should generate the
     *      right name for the file sent back from
     *      amending an award.
     */
    @Test
    public void shouldChooseRightFileName() {

        //      GIVEN
        RbpsRepository      repository      = new RbpsRepository();
        repository.setVeteran( CommonFactory.adamsVeteran() );

        AwardPrintSaver     saver           = new AwardPrintSaver();
        long                claimId         = repository.getVeteran().getClaim().getClaimId();
        String              formattedDate   = new FileNameGenerator().getFormattedDate();

        String              desiredFileName = String.format( "%d_%sap3.%s",
                                                             claimId,
                                                             formattedDate,
                                                             "pdf" );


        //      WHEN
        String              actualFileName  = saver.generateFileName(3, repository );


        //      THEN
        assertThat( actualFileName, containsString( desiredFileName ) );
    }


    /**
     *      Should actually generate a pdf when given enough info.
     */
    @SuppressWarnings( "unused" )
    @Test
    public void shouldGeneratePdfFile() {

        boolean     shouldHaveAwardPrint;
        String desiredFileName = setupFileGeneration( shouldHaveAwardPrint = true );


        //      THEN
        assertThat( new File( desiredFileName ), exists() );
    }


    /**
     *      A csv file should be generated when given enough info.
     */
    @SuppressWarnings( "unused" )
    @Test
    public void shouldGenerateCsvFile() {

        boolean     shouldHaveAwardPrint;
        String desiredFileName = setupFileGeneration( shouldHaveAwardPrint = true );


        //      THEN
        assertThat( new File( PathUtils.csvPathFor( desiredFileName ) ), exists() );
    }


    /**
     *      There shouldn't be an exception (CQ 263) if no award print is sent.
     */
    @SuppressWarnings( "unused" )
    @Test
    public void shouldNotFailIfNoAwardPrint() {

        boolean     shouldHaveAwardPrint;
        setupFileGeneration(  shouldHaveAwardPrint = false );
    }



    private String setupFileGeneration( final boolean shouldHaveAwardPrint ) {

        //      GIVEN
        RbpsRepository      repository      = new RbpsRepository();
        repository.setVeteran( CommonFactory.adamsVeteran() );

        AwardPrintSaver     saver           = new AwardPrintSaver();
        String              desiredFileName = saver.generateFileName( 1,repository );

        ProcessAwardDependentResponse   response;
        String                          xmlPath = "gov/va/vba/rbps/services/populators/470141119/awards.response.xml";

        response = new TestUtils().getAmendAwardResponseFromXml( xmlPath );

        if ( ! shouldHaveAwardPrint ) {

            for ( DocumentVO doc : response.getReturn().getDocumentSummary().getDocumentList() ) {

                doc.setLetterText( null );
            }
        }


        //      WHEN
        saver.saveAwardPrintAndCsv( response, repository );

        return desiredFileName;
    }
}
