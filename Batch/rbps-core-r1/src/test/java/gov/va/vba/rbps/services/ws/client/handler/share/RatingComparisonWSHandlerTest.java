/*
 * RatingComparisonWSHandlerTest
 *
 * Copyright 2012 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.populators.RatingDatesPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class RatingComparisonWSHandlerTest  extends RbpsAbstractTest {

    private RatingComparisonWSHandler       ratingComparisonWSHandler;
    private RbpsRepository                  repo;
    private CommonFactory                   factory     = new CommonFactory();


    @Override
    @Before
    public void setup() {

        super.setup();
        ratingComparisonWSHandler = (RatingComparisonWSHandler) getBean( "ratingComparisonWSHandler" );

        repo = (RbpsRepository) getBean( "repository" );
        repo.setVeteran( CommonFactory.georgeVeteran() );
    }


    @Test
    public void shouldWorkFor30842484() {

        runTest( 30842484,
                 2012, 3, 9,
                 2010, 7, 1 );
    }


    @Test
    public void shouldWorkFor30842324() {

        runTest( 30842324,
                 2012, 3, 9,
                 2011, 6, 1 );
    }


    @Test
    public void shouldWorkFor30843911() {

        runTest( 30843911,
                 2011, 10, 14,
                 2011, 1, 1 );
    }


    @Test
    public void shouldWorkFor30843525() {

        runTest( 30843525,
                 2012, 3, 9,
                 2010, 9, 1 );
    }

    //      SWETA ===================================

    @Test
    public void shouldWorkFor124060() {

        runTest( 124060,
                 2012, 3, 9,
                 2010, 9, 1 );
    }


    private void runTest( final long        partId,
                          final int         ratingYear,
                          final int         ratingMonth,
                          final int         ratingDay,
                          final int         effectiveYear,
                          final int         effectiveMonth,
                          final int         effectiveDay ) {

        repo.getVeteran().setCorpParticipantId( partId );
        repo.getVeteran().getClaim().setReceivedDate( new Date() );
        CompareByDateRangeResponse      response    = ratingComparisonWSHandler.findApplicableRatingsData( repo );

        new RatingDatesPopulator().populateRatingDates( repo, response );

        assertThat( "rating date",
                    repo.getVeteran().getRatingDate(),
                    is(equalTo( factory.createDay( ratingYear, ratingMonth, ratingDay )) ) );

        assertThat( "rating effective date",
                    repo.getVeteran().getRatingEffectiveDate(),
                    is(equalTo( factory.createDay( effectiveYear, effectiveMonth, effectiveDay )) ) );
    }
}
