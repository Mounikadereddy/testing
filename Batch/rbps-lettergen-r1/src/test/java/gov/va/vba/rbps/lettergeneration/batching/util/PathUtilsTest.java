/*
 * PathUtilsTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.util;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PathUtilsTest {


    @Before
    public void setUp() throws Exception {

    }


    @After
    public void tearDown() throws Exception {

    }


    @Test
    public void shouldConcatenateWithOnePath() {
        File    foo = PathUtils.concatenateToFile( "foo" );

        assertThat( "foo", is( equalTo( foo.getPath() ) ) );
    }


    @Test
    public void shouldConcatenateWithSystemSeparator() {
        File    foo = PathUtils.concatenateToFile( "foo", "bar" );

        assertThat( foo.getPath(), containsString( File.separator ) );
    }


    @Test
    public void shouldConcatenateWithTwoPaths() {
        File    foo = PathUtils.concatenateToFile( "foo", "bar" );

        assertThat( "foo/bar", is( equalTo( FilenameUtils.separatorsToUnix( foo.getPath() ) ) ) );
    }


    @Test
    public void shouldConcatenateWithFourPaths() {
        File    foo = PathUtils.concatenateToFile( "foo", "bar", "baz", "jojo" );

        assertThat( "foo/bar/baz/jojo", is( equalTo( FilenameUtils.separatorsToUnix( foo.getPath() ) ) ) );
    }
}
