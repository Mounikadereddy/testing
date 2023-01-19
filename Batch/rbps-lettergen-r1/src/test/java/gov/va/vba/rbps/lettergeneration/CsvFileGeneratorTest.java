/*
 * CsvFileGeneratorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration;


import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.junit.Before;
import org.junit.Test;


public class CsvFileGeneratorTest {


    private CsvFileGenerator            generator;
    private Veteran                     veteran;
    private GenericLetterGeneration     letterGenerator;
    private LetterFields                letterFields;
    private String                      pdfFileName;



    @Before
    public void setUp() throws Exception {

        LogUtils.setGlobalLogit( false );

        RbpsRepository          repo            = new RbpsRepository();

//        System.out.println( "runtags = " + System.getProperty( "org.junit.RunTags" ));

        generator           = new CsvFileGenerator();
        veteran             = CommonFactory.jeffersonNoMiddleNameVeteran();
        repo.setVeteran( veteran );
        letterGenerator     = new GenericLetterGeneration();
        letterGenerator.createLetterFields( repo);


        List<AwardSummary>      awardSummary    = new ArrayList<AwardSummary>();
        letterFields        = new LetterFields( repo, awardSummary );
        letterFields.setLogit( false );
        letterFields.init();

        pdfFileName         = letterGenerator.generateFileName( GenericLetterGeneration.PDF_EXTENSION );
    }


//    @RunTags(tags={"LetterGen", "Release"} )
    @Test
    public void shouldGenerateRightDateFormat() {

        String result = generator.buildCsvLine( letterFields.getClaimNumber(),
                                                pdfFileName,
                                                CsvFileGenerator.VIRTUAL_VA_DOC_TYPE_NOTIFICATION_LETTER );

        assertThat( result, containsString( generator.getFormattedDate() ) );
    }


    @Test
    public void shouldHavePdfFileNameInCsvContents() {

        String result = generator.buildCsvLine( letterFields.getClaimNumber(),
                                                pdfFileName,
                                                CsvFileGenerator.VIRTUAL_VA_DOC_TYPE_NOTIFICATION_LETTER );

        assertThat( result, containsString( FilenameUtils.getName( pdfFileName ) ) );
    }


    @Test( expected = UnableToCreateCsvFileException.class )
    public void shouldHaveCsvFileGeneratorException() {

        generator.generateCsvFile( letterFields.getClaimNumber(),
                                   pdfFileName,
                                   System.getProperty( "java.io.tmpdir" ),
                                   CsvFileGenerator.VIRTUAL_VA_DOC_TYPE_NOTIFICATION_LETTER );
    }


    @Test
    public void shouldGenerateCsvFile() {

        generator.generateCsvFile( letterFields.getClaimNumber(),
                                   pdfFileName,
                                   PathUtils.concatenateToPath( System.getProperty( "java.io.tmpdir" ),
                                                                letterGenerator.generateFileName( GenericLetterGeneration.CSV_EXTENSION ) ),
                                   CsvFileGenerator.VIRTUAL_VA_DOC_TYPE_NOTIFICATION_LETTER );
    }
}
