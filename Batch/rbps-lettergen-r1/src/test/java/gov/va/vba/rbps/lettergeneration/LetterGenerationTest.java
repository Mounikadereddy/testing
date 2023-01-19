/*
 * LetterGenerationTest.java
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
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;
import gov.va.vba.rbps.lettergeneration.util.LetterConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;



public class LetterGenerationTest {




    @Test
    public void shouldCreateFileNameWithClaimId() {

        GenericLetterGeneration letterGeneration    = new GenericLetterGeneration();
        Veteran                 george              = CommonFactory.georgeVeteran();
        RbpsRepository          repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( george );
        letterGeneration.createLetterFields( repo );

        String                  pdfFileName         = letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION );

        assertThat( pdfFileName, containsString( "" + george.getClaim().getClaimId() ) );
    }


    @Test
    public void shouldCreateFileNameWithPdf() {

        GenericLetterGeneration letterGeneration    = new GenericLetterGeneration();
        Veteran                 george              = CommonFactory.georgeVeteran();
        RbpsRepository          repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( george );
        letterGeneration.createLetterFields( repo );

        String                  pdfFileName         = letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION );

        assertThat( pdfFileName, containsString( GenericLetterGeneration.PDF_EXTENSION ) );
    }


    @Test
    public void shouldCreateFileNameWithCsv() {

        GenericLetterGeneration letterGeneration    = new GenericLetterGeneration();
        Veteran                 george              = CommonFactory.georgeVeteran();
        RbpsRepository          repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( george );
        letterGeneration.createLetterFields( repo );

        String                  pdfFileName         = letterGeneration.generateFileName( GenericLetterGeneration.CSV_EXTENSION );

        assertThat( pdfFileName, containsString( GenericLetterGeneration.CSV_EXTENSION ) );
    }


    @Test
    public void shouldGeneratePdfFileFromTemplate() throws Throwable {

        GenericLetterGeneration letterGeneration    = new GenericLetterGeneration();
        Veteran                 george              = CommonFactory.georgeVeteran();
        RbpsRepository          repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( george );
        letterGeneration.createLetterFields( repo );


        letterGeneration.generateLetter( LetterConstant.LETTER_TEMPLATE, "approval.pdf" );
    }


    @Test
    public void shouldAddVeteransLastNameToOutput() throws Throwable {

        GenericLetterGeneration letterGeneration    = new GenericLetterGeneration();
        Veteran                 george              = CommonFactory.georgeVeteran();
        RbpsRepository          repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( george );
        letterGeneration.createLetterFields( repo );

        String      output      = letterGeneration.interpolateTemplate( LetterConstant.LETTER_TEMPLATE );
//      System.out.println( "should add last name to output\n==============\n" + output + "\n==============\n");

        assertThat( output, containsString( CommonFactory.georgeVeteran().getLastName() ) );
    }


    @Test
    @SuppressWarnings( "unused" )
    public void shouldAddAwardTableToOutput() throws Throwable {

        GenericLetterGeneration letterGeneration = new GenericLetterGeneration();
        letterGeneration.setLogit( false );

        String      output      = letterGeneration.interpolateTemplate( LetterConstant.LETTER_TEMPLATE );
//      System.out.println( output );

//      assertThat( output, containsString( "" + veteran.getAward().getBeneficiaryId() ) );
    }


    @Test
    public void shouldCreatePdf() throws Throwable {

        GenericLetterGeneration letterGeneration = new GenericLetterGeneration();
        letterGeneration.setLogit( false );

//        System.out.println( "Current working dir: " + new File( "." ).getCanonicalPath() );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = dir + "outputLetter.pdf";
        letterGeneration.generateLetter( LetterConstant.LETTER_TEMPLATE,
                                          pdfFileName );
        assertThat( new File( pdfFileName ), exists() );

//        for ( File file : new File( dir ).listFiles() )
//        {
//            System.out.println( "\t" + file.getCanonicalPath() );
//        }
    }


    @Test
    public void shouldGeneratePdfNameAndCreatePdf() throws Throwable {

        List<AwardSummary>          awardSummary        = new ArrayList<AwardSummary>();
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                 george              = CommonFactory.georgeVeteran();
        RbpsRepository          repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( george );
        letterGeneration.createLetterFields( repo );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = PathUtils.concatenateToPath( dir,
                                                           letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION ) );
        letterGeneration.generateLetterAndCsv( repo,
                                               awardSummary );

        assertThat( new File( pdfFileName ), exists() );
    }


    @Test
    public void shouldCreatePdfNoMiddleName() throws Throwable {

        List<AwardSummary>          awardSummary        = new ArrayList<AwardSummary>();
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                     george              = CommonFactory.georgeVeteran();
        RbpsRepository              repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( george );
        letterGeneration.createLetterFields( repo );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = PathUtils.concatenateToPath( dir,
                                                           letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION ) );
        letterGeneration.generateLetterAndCsv( repo,
                                               awardSummary );
        assertThat( new File( pdfFileName ), exists() );
    }


    @Test
    public void shouldCreateAdamsWithPoaPdf() throws Throwable {

        List<AwardSummary>          awardSummary        = new ArrayList<AwardSummary>();
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                     veteran             = CommonFactory.adamsVeteran();
        RbpsRepository              repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( veteran );

        veteran.setHasPOA( true );
        veteran.getClaim().setClaimId( 263819301 );
        veteran.getClaim().setClaimId( 15211882 );
        veteran.getClaim().setClaimId( 17848322 );
        veteran.getClaim().setClaimId( 98798700 );

        letterGeneration.setAwardSummary( awardSummary );
        letterGeneration.createLetterFields( repo );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = PathUtils.concatenateToPath( dir,
                                                           letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION ) );
        letterGeneration.generateLetterAndCsv( repo, awardSummary );
        assertThat( new File( pdfFileName ), exists() );
    }


    @Test
    public void shouldCreateAdamsPdfWithPoaAndComplicatedAddress() throws Throwable {

        List<AwardSummary>          awardSummary        = new ArrayList<AwardSummary>();
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                     veteran             = CommonFactory.adamsVeteran();
        RbpsRepository              repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( veteran );

        veteran.setHasPOA( true );
        veteran.getMailingAddress().setLine2( "line2" );
        veteran.getMailingAddress().setLine3( "line3" );

        letterGeneration.setAwardSummary( awardSummary );
        letterGeneration.createLetterFields( repo );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = PathUtils.concatenateToPath( dir,
                                                           letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION ) );
        letterGeneration.generateLetterAndCsv( repo, awardSummary );
        assertThat( new File( pdfFileName ), exists() );
    }


    @Test
    public void shouldCreateLargeAdamsWithPoaPdf() throws Throwable {

        List<AwardSummary>          awardSummary        = getRandomSampleAwardSummaries( 20 );
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                     veteran             = CommonFactory.adamsVeteran();
        RbpsRepository              repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( veteran );

        veteran.setHasPOA( true );
        letterGeneration.setAwardSummary( awardSummary );
        letterGeneration.createLetterFields( repo );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = PathUtils.concatenateToPath( dir,
                                                           letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION ) );
        letterGeneration.generateLetterAndCsv( repo, awardSummary );
        assertThat( new File( pdfFileName ), exists() );
    }


    @Test
    public void shouldCreateAdamsWithoutPoaPdf() throws Throwable {

        List<AwardSummary>          awardSummary        = new ArrayList<AwardSummary>();
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                     veteran             = CommonFactory.adamsVeteran();
        RbpsRepository              repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( veteran );

        veteran.setHasPOA( false );
        letterGeneration.setAwardSummary( awardSummary );
        letterGeneration.createLetterFields( repo );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = PathUtils.concatenateToPath( dir,
                                                           letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION ) );
        letterGeneration.generateLetterAndCsv( repo, awardSummary );
        assertThat( new File( pdfFileName ), exists() );
    }


    @Test
    public void shouldCreateAdamsDenialPdf() throws Throwable {

        List<AwardSummary>          awardSummary        = new ArrayList<AwardSummary>();
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                     veteran             = CommonFactory.adamsVeteran();
        RbpsRepository              repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( veteran );

        letterGeneration.setAwardSummary( awardSummary );
        letterGeneration.createLetterFields( repo );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = PathUtils.concatenateToPath( dir,
                                                           letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION ) );
        letterGeneration.generateLetterAndCsv( repo, awardSummary );
        assertThat( new File( pdfFileName ), exists() );
    }


    @Test
    public void shouldCreateAdamsApproveDenyPdf() throws Throwable {

        List<AwardSummary>          awardSummary        = new ArrayList<AwardSummary>();
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                     veteran             = CommonFactory.adamsVeteran();
        RbpsRepository              repo                = new RbpsRepository();
        letterGeneration.setLogit( false );

        repo.setVeteran( veteran );

        letterGeneration.setAwardSummary( awardSummary );
        letterGeneration.createLetterFields( repo );

        String dir          = System.getProperty( "java.io.tmpdir" );
        String pdfFileName  = PathUtils.concatenateToPath( dir,
                                                           letterGeneration.generateFileName( GenericLetterGeneration.PDF_EXTENSION ) );
        letterGeneration.generateLetterAndCsv( repo, awardSummary );
        assertThat( new File( pdfFileName ), exists() );
    }


    private List<AwardSummary>  getSampleAwardSummaries() {

        List<AwardSummary> summaries = new ArrayList<AwardSummary>();

        AwardSummary    summary = new AwardSummary( 44.50, 24.50, 20.0,
                new Date(),
                "joe" );

		summaries.add( summary );
		
		
		
		summary = new AwardSummary( 5000.0, 3000.0, 2000.0,
		CommonFactory.getRandomDate(),
		"gone fishin" );
		summaries.add( summary );
		
		
		
		summary = new AwardSummary( 10.0, 5.0, 5.0,
		CommonFactory.getRandomDate(),
		"boondocks" );
		summaries.add( summary );
		
		return summaries;
    }


    private List<AwardSummary> getRandomSampleAwardSummaries( final int   numSummaries ) {

        List<AwardSummary> summaries = new ArrayList<AwardSummary>();

        for ( int ii = 0; ii < numSummaries; ii++ ) {

            AwardSummary summary = new AwardSummary( CommonFactory.getRandomDouble( 500.0 ),
								            		 CommonFactory.getRandomDouble( 300.0 ),
								            		 CommonFactory.getRandomDouble( 200.0 ),
                                                     CommonFactory.getRandomDate(),
                                                     getRandomReason() );

            summaries.add( summary );
        }

        return summaries;
    }


    public String getRandomReason() {

        return reasons.get( CommonFactory.getRandomInt( reasons.size() ) );
    }


    private List<String> reasons = Arrays.asList(
        "gone fishin'",
        "boondocks",
        "dog ate my homework",
        "sister ate my homework",
        "giant stomped on my house",
        "lizard bit my sister",
        "car landed in living room",
        "air conditioner snowed" );
}
