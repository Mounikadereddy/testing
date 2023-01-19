/*
 * FontTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration;

import static gov.va.vba.rbps.lettergeneration.hamcrest.files.FileMatchers.exists;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.lettergeneration.batching.util.PathUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class FontTest {


    @Test
    public void shouldCreateFileNameWithFont() {

        GenericLetterGeneration letterGeneration    = new GenericLetterGeneration();
        String                  fontpath            = letterGeneration.getFontPath();

        System.out.println( fontpath );

    }


    @Test
    public void shouldCreateAdamsDenialPdf() throws Throwable {

        List<AwardSummary>          awardSummary        = new ArrayList<AwardSummary>();
        GenericLetterGeneration     letterGeneration    = new GenericLetterGeneration();
        Veteran                     veteran             = CommonFactory.adamsVeteran();
        RbpsRepository              repo                = new RbpsRepository();

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
}
