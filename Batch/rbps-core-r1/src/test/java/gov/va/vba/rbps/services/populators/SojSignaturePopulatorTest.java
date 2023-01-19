/*
 * SojSignaturePopulatorTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.handler.share.FindRegionalOfficesWSHandlerTest;
import gov.va.vba.rbps.services.ws.client.handler.share.SojSignatureWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindSignaturesByStationNumberResponse;
import gov.va.vba.rbps.services.ws.client.util.LoggingInterceptor;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class SojSignaturePopulatorTest extends RbpsAbstractTest {

    private SojSignaturePopulator       populator;
    private TestUtils                   testUtils   = new TestUtils();


    @Override
    @Before
    public void setup() {

        super.setup();

        ToStringBuilder.setDefaultStyle( RbpsConstants.RBPS_TO_STRING_STYLE );

        populator   = new SojSignaturePopulator();
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldPopulate335() {

        runTest( "335", "K. L. Anderson" );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldPopulate317() {

        runTest( "317", "E. Hutchinson" );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldPopulateTitle() {

        runTitleTest( "317", "Veterans Service Center Manager" );
    }


    //      This test goes to the ws for each RO, so takes too long.
    //      And besides, it fails because the test data isn't good.
    @Ignore
    @Test
    public void shouldHaveSignatureForAllRos() throws Throwable {

        List<String>            stations            = new FindRegionalOfficesWSHandlerTest().getPdfList();
        SojSignatureWSHandler   wsHandler           = (SojSignatureWSHandler) getBean( "sojSignatureWSHandler" );
        RbpsRepository          repo                = (RbpsRepository) getBean( "repository" );
        LoggingInterceptor      loggingInterceptor  = (LoggingInterceptor) getBean( "loggingInterceptor" );
        List<String>            failedStations      = new ArrayList<String>();

        wsHandler.setLogit( false );
        populator.setLogit( false );
        loggingInterceptor.setLogit( false );

        for ( String stationId : stations ) {

            try {
                FindSignaturesByStationNumberResponse response = wsHandler.getSignatures( repo, stationId );
                populator.assignVeteranServiceManagerSignature( repo, response );

                if ( StringUtils.isBlank( repo.getClaimStationAddress().getServiceManagerSignature() ) ) {

                    failedStations.add( stationId );
                }
            }
            catch ( Throwable ex ) {

                failedStations.add( stationId );
            }
        }

        if ( ! failedStations.isEmpty() ) {
            System.out.println( "failed stations: " + failedStations );
        }
        assertThat( "the list of failed stations should be empty", failedStations.size(), is(equalTo( 0 )) );
    }





    private void runTest( final String          sojId,
                          final String          signature ) {

        RbpsRepository repo = populateSignature(sojId);

        assertThat( "signature",
                    repo.getClaimStationAddress().getServiceManagerSignature(),
                    is(equalTo( signature ) ) );
    }

    private void runTitleTest( final String          sojId,
                               final String          title ) {

        RbpsRepository repo = populateSignature(sojId);

        assertThat( repo.getClaimStationAddress().getServiceManagerTitle(),
                    is(equalTo( title ) ) );
    }

    private RbpsRepository populateSignature(final String sojId) {

        String                                      xmlPath     = getXmlPath( sojId );
        FindSignaturesByStationNumberResponse       response    = testUtils.getSojSignatureResponseFromXml( xmlPath );
        RbpsRepository                              repo        =  new RbpsRepository();

        populator.assignVeteranServiceManagerSignature( repo, response );
        return repo;
    }



    public String getXmlPath( final String    label ) {

        return String.format( "gov/va/vba/rbps/services/populators/sojSignature/%s.sojSignature.response.xml", label );
    }
}
