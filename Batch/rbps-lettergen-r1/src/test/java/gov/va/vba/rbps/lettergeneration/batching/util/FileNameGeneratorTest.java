/*
 * FileNameGeneratorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.util;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;

import org.junit.Test;



/**
 * Test case for the <code>FileNameGeneratorTest</code> class
 *
 * @author vafscchowdk
 *
 */
public class FileNameGeneratorTest {


//    @RunTags(tags={"LetterGen", "Release"} )
    @Test
    public void shouldCreateBaseNameWithClaimId() {

        FileNameGenerator       generator       = new FileNameGenerator();
        Veteran                 george          = CommonFactory.georgeVeteran();
        String                  baseFileName    = generator.generateBaseFileName( george.getFileNumber() );

        assertThat( baseFileName, containsString( "" + george.getFileNumber() ) );
    }


//    @RunTags(tags={"LetterGen", "Release"} )
    @Test
    public void shouldCreateBaseNameWithDate() {

        FileNameGenerator       generator       = new FileNameGenerator();
        Veteran                 george          = CommonFactory.georgeVeteran();
        String                  baseFileName    = generator.generateBaseFileName( george.getFileNumber() );

        assertThat( baseFileName, containsString( generator.getFormattedDate() ) );
    }


//    @RunTags(tags={"LetterGen", "Release"} )
    @Test
    public void shouldCreateFileNameWithRightExtension() {

        FileNameGenerator       generator           = new FileNameGenerator();
        Veteran                 george              = CommonFactory.georgeVeteran();
        String                  desiredExtension    = "jojo";
        String                  baseFileName        = generator.generateFileName( george.getFileNumber(),
                                                                                  desiredExtension );

        assertThat( baseFileName, containsString( desiredExtension ) );
    }


//    @RunTags(tags={"LetterGen", "Release"} )
    @Test
    public void shouldCreateFileNameWithClaimId() {

        FileNameGenerator       generator       = new FileNameGenerator();
        Veteran                 george          = CommonFactory.georgeVeteran();
        String                  baseFileName    = generator.generateFileName( george.getFileNumber(),
                                                                              "fred" );

        assertThat( baseFileName, containsString( "" + george.getFileNumber() ) );
    }


//    @RunTags(tags={"LetterGen", "Release"} )
    @Test
    public void shouldCreateFileNameWithDate() {

        FileNameGenerator       generator       = new FileNameGenerator();
        Veteran                 george          = CommonFactory.georgeVeteran();
        String                  baseFileName    = generator.generateFileName( george.getFileNumber(),
                                                                              "karma" );

        assertThat( baseFileName, containsString( generator.getFormattedDate() ) );
    }


//    @RunTags(tags={"LetterGen", "Release"} )
    @Test
    public void shouldCreateDesiredFile() {

        FileNameGenerator       generator       = new FileNameGenerator();
        Veteran                 george          = CommonFactory.georgeVeteran();
        String                  baseFileName    = generator.generateFileName( george.getFileNumber(),
                                                                              "karma" );

        String      desiredFileName = String.format( "%s_%s.%s",
                                                     george.getFileNumber(),
                                                     generator.getFormattedDate(),
                                                     "karma" );

        assertThat( baseFileName, containsString( desiredFileName ) );
    }
}
