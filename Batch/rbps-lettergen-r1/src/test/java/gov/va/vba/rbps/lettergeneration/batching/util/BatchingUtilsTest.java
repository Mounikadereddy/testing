/*
 * BatchingUtilsTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;



/**
 * Test case for the <code>BatchingUtils</code> class
 *
 * @author vafscchowdk
 *
 */
public class BatchingUtilsTest {


    @Test
    public void shouldJoinWithCorrectSeparator() {

        BatchingUtils fields = new BatchingUtils();

        List<String> foo = Arrays.asList( "one", "two", "three" );

        assertThat( fields.join( foo, ", " ), is(equalTo( "one, two, three" ) ) );
    }
}
