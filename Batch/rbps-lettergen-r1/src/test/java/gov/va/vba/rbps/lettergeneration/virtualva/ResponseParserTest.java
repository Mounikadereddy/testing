/*
 * ResponseParserTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.lettergeneration.batching.fixtures.FileTestUtils;
import gov.va.vba.rbps.lettergeneration.virtualva.results.DocResult;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;



@Ignore
public class ResponseParserTest {

    private static final String         GOOD_RESULTS_PATH   = "gov/va/vba/rbps/lettergeneration/virtual/bfi.xml";
    private static final String         BAD_RESULTS_PATH    = "gov/va/vba/rbps/lettergeneration/virtual/bfiError.xml";



    @Test
    public void shouldGetFileNameRight() throws Throwable {

        ResponseParser      parser  = new ResponseParser();
        String              xml     = new FileTestUtils().getFileContents( GOOD_RESULTS_PATH );

        List<DocResult>     results = parser.parse( xml );

//        System.out.println( String.format( "results size: %d", results.size() ));

        assertThat( results.get(0).getOriginalFileName(), is(equalTo( "459_2661772911016419-00GEN_S.pdf" ) ) );
    }


    @Test
    public void shouldNumDocsRight() throws Throwable {

        ResponseParser      parser  = new ResponseParser();
        String              xml     = new FileTestUtils().getFileContents( GOOD_RESULTS_PATH );

        List<DocResult>     results = parser.parse( xml );

//        System.out.println( String.format( "results size: %d", results.size() ));

        assertThat( results.size(), is(equalTo( 3 ) ) );
    }


    @Test( expected = UnableToParseResultsException.class )
    public void shouldBeAbleToTellWhenVirtualVaReportsError() throws Throwable {

        ResponseParser      parser  = new ResponseParser();
        String              xml     = new FileTestUtils().getFileContents( BAD_RESULTS_PATH );

        List<DocResult>     results = parser.parse( xml );

//        System.out.println( String.format( "results size: %d", results.size() ));

        assertThat( results.size(), is(equalTo( 3 ) ) );
    }
}
