package gov.va.vba.rbps.services.populators;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.GetDecisionsAtIssueResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Test;

public class DecisionsAtIssuePopulatorTest extends RbpsAbstractTest {

    private static Logger logger = Logger.getLogger( DecisionsAtIssuePopulatorTest.class );

    private DecisionsAtIssuePopulator	populator;
    private TestUtils                   testUtils   = new TestUtils();
    private RbpsRepository              repository;

    @Override
    @Before
    public void setup() {

        super.setup();
        repository = ( RbpsRepository ) getBean( "repository" );
        populator   = new DecisionsAtIssuePopulator();
    }
    
    @Test
    public void shouldDecisionsAtIssuePopulate() {
    	

//		testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/fiduciary.userInfo.response.xml" );
		repository.getVeteran().setCorpParticipantId( 365811 );
		GetDecisionsAtIssueResponse      response    = 
			 			testUtils.getDecisionsAtIssueResponseFromXml( "gov/va/vba/rbps/services/populators/getDecisionsAtIssue.response.xml" );
		 
//	     populator.determineFCDR( repository, response );
    }
}
