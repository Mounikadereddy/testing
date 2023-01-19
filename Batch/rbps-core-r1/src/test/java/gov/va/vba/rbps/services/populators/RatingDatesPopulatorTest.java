/*
 * RatingDatesPopulatorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.claimvalidator.GenericUserInformationValidatorImpl;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class RatingDatesPopulatorTest extends RbpsAbstractTest {

    private static Logger logger = Logger.getLogger(RatingDatesPopulatorTest.class);

    private RatingDatesPopulator        populator;
    private TestUtils                   testUtils   = new TestUtils();
    private LogUtils                    logUtils    = new LogUtils( logger, true );
    private CommonFactory               factory     = new CommonFactory();


    @Override
    @Before
    public void setup() {

        super.setup();

        populator   = new RatingDatesPopulator();
    }

    @Test
    public void shouldCertIssueOn102312() {
    	
    	 CompareByDateRangeResponse      response    = 
    		 			testUtils.getRatingComparisonResponseFromXml( "gov/va/vba/rbps/services/populators/102312.compareByDateRange.response.xml" );
    	 
    	 RbpsRepository                  repo        =  new RbpsRepository();
         repo.setVeteran( CommonFactory.adamsVeteran() );

         populator.populateRatingDates( repo, response );
    	 
    }

//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Ignore
    @Test
    public void shouldPopulateSimpleCase() {

        runTest( "simple",
                 2010, 8, 12,
                 2012, 1, 1 );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldHandleLowPercentagesCase() {

        runTest( "lowPercentage",
                 2010, 8, 12,
                 2012, 1, 1 );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldFindEarliestCase() {

        runTest( "findEarliest",
                 2010, 8, 12,
                 2011, 12, 20 );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldHandleNoRatings() {

        runFailingTest( "noRatings",
                        "Auto Dependency Processing Validation Reject Reason - No rating details over 30% available." );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldHandleRejectReason() {

        runFailingTest( "hasRejectReason", GenericUserInformationValidatorImpl.FAIL + "Missing stuff" );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldHandleMissingRatingDate() {

        runFailingTest( "missingRatingDate", GenericUserInformationValidatorImpl.FAIL + "No rating date available." );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldHandleNoEffectiveDateInDetails() {

        runFailingTest( "noEffectiveDateInDetails", GenericUserInformationValidatorImpl.FAIL + "Rating details missing effective date." );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldNotBeInFuture() {

        Date    ago = factory.createDay( 1961, 1, 6 );

        assertThat( "ago",
                    new SimpleDateUtils().isInFuture( ago ),
                    is(equalTo( false ) ) );
        ;
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldBeInFuture() {

        Date    nextCentury = factory.createDay( 2100, 1, 6 );

        assertThat( "next century",
                    new SimpleDateUtils().isInFuture( nextCentury ),
                    is(equalTo( true ) ) );
        ;
    }


    private void runFailingTest( final String     label,
                                 final String     expectedMessage ) {


        try {
            runTest( label,
                     2010, 8, 12,
                     2011, 12, 20 );
        }
        catch ( Throwable ex ) {

//            logUtils.log( "err message: " + ex.getMessage() );
            assertThat( ex.getMessage(), is(equalTo( expectedMessage ) ) );
        }
    }


    private void runTest( final String        label,
                          final int           ratingYear,
                          final int           ratingMonth,
                          final int           ratingDay,
                          final int           effectiveYear,
                          final int           effectiveMonth,
                          final int           effectiveDay ) {

        String                          xmlPath     = getXmlPath( label );
        CompareByDateRangeResponse      response    = testUtils.getRatingComparisonResponseFromXml( xmlPath );
        RbpsRepository                  repo        =  new RbpsRepository();
        repo.setVeteran( CommonFactory.adamsVeteran() );

        populator.populateRatingDates( repo, response );

        assertThat( "rating date",
                    repo.getVeteran().getRatingDate(),
                    is(equalTo( factory.createDay( ratingYear, ratingMonth, ratingDay )) ) );

        assertThat( "rating effective date",
                    repo.getVeteran().getRatingEffectiveDate(),
                    is(equalTo( factory.createDay( effectiveYear, effectiveMonth, effectiveDay )) ) );
    }


    public String getXmlPath( final String    label ) {

        return String.format( "gov/va/vba/rbps/services/populators/ratingComparison/%s.ratingComparison.xml", label );
    }
}
