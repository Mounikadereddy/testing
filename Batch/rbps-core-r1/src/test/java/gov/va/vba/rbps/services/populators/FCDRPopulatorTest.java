package gov.va.vba.rbps.services.populators;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Test;

public class FCDRPopulatorTest extends RbpsAbstractTest {

    private static Logger logger = Logger.getLogger(FCDRPopulatorTest.class);

    private FCDRPopulator        		populator;
    private TestUtils                   testUtils   = new TestUtils();
    private RbpsRepository              repository = new RbpsRepository();

    @Override
    @Before
    public void setup() {

        super.setup();
//        repository = ( RbpsRepository ) getBean( "repository" );
        populator   = ( FCDRPopulator) getBean( "fcdrPopulator" );
    }

    @Test
    public void shouldFCDRPopulate() {
    	
    	testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/fiduciary.userInfo.response.xml" );
    	//repository.getVeteran().setCorpParticipantId( 32246689 );
    	repository.getVeteran().setCorpParticipantId( 4099609 );
    	//repository.getVeteran().setCorpParticipantId( 365811 );
    	 CompareByDateRangeResponse      response    = 
    		 			testUtils.getRatingComparisonResponseFromXml( "gov/va/vba/rbps/services/populators/fcdr.compareByDateRangeResponse.xml" );
         populator.populateRatingDates( response, repository );
    	 
    }
}
