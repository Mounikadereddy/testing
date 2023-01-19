/*
 * SojSignatureWSHandlerTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindSignaturesByStationNumberResponse;

import org.junit.Before;
import org.junit.Test;


public class SojSignatureWSHandlerTest  extends RbpsAbstractTest {

    private SojSignatureWSHandler           wsHandler;
    private RbpsRepository                  repo;


    @Override
    @Before
    public void setup() {

        super.setup();
        wsHandler = (SojSignatureWSHandler)  getBean( "sojSignatureWSHandler" );

        repo = (RbpsRepository)  getBean( "repository" );
    }


    @Test
    public void shouldWorkFor335() {

        runTest( "335", "K. L. Anderson" );
    }


    private void runTest( final String    stationId,
                          final String    signature ) {

        FindSignaturesByStationNumberResponse      response    = wsHandler.getSignatures( repo, stationId );

//        new RatingDatesPopulator().populateRatingDates( repo, response );

        assertThat( "signature",
                    response.getStnPrfilDetailDTO().get(0).getLetterSigntrTxt(),
                    is(equalTo( signature ) ) );
    }
}
