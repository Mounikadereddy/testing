/*
 * WebServiceRunnerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.lettergeneration.batching.SendToVirtualVa;
import gov.va.vba.rbps.lettergeneration.batching.fixtures.FileTestUtils;

import org.apache.commons.io.FilenameUtils;
import org.junit.Ignore;
import org.junit.Test;


@Ignore
public class WebServiceRunnerTest {


    private static final String         MIXED_RESULTS_PATH   = "gov/va/vba/rbps/lettergeneration/virtual/bfiSomeFailed.xml";


    @Test
    public void shouldGetRightNumberOfFailures() throws Throwable {

        WebServiceRunner    runner  = new WebServiceRunner();
        String              xml     = new FileTestUtils().getFileContents( MIXED_RESULTS_PATH );

        runner.parseResults( xml );

//        System.out.println( String.format( "results size: %d", results.size() ));

        assertThat( runner.getFailedClaimsFiles( SendToVirtualVa.WAITING_DIR ).size(), is(equalTo( 2 ) ) );
    }


    @Test
    public void shouldGetRightNumberOfSuccesses() throws Throwable {

        WebServiceRunner    runner  = new WebServiceRunner();
        String              xml     = new FileTestUtils().getFileContents( MIXED_RESULTS_PATH );

        runner.parseResults( xml );

//        System.out.println( String.format( "results size: %d", results.size() ));

        assertThat( runner.getSuccessfulClaimsFiles( SendToVirtualVa.WAITING_DIR ).size(), is(equalTo( 1 ) ) );
    }


    @Test
    public void shouldGetFileNameRight() throws Throwable {

        WebServiceRunner    runner  = new WebServiceRunner();
        String              xml     = new FileTestUtils().getFileContents( MIXED_RESULTS_PATH );

        runner.parseResults( xml );

//        System.out.println( String.format( "results size: %d", results.size() ));
        String path = runner.getFailedClaimsFiles( SendToVirtualVa.WAITING_DIR ).get(0).getPath();

        assertThat( FilenameUtils.separatorsToUnix( path ),
                    is(equalTo( "waiting/459_2661772911016419-00GEN_S.pdf" ) ) );
    }
}
