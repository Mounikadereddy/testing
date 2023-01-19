package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class CheckIfVeteranHasChildrenPreviouslyMarriedTest  extends RbpsAbstractTest {

    private FindDependencyDecisionByAwardResponse       response;
    private TestUtils       							testUtils   = new TestUtils();
    private RbpsRepository                              repository;
    private DependencyDecisionByAwardProducer			producer;


    @Override
    @Before
    public void setup() {

        super.setup();

        repository      								= (RbpsRepository) getBean( "repository" );
        producer        								= (DependencyDecisionByAwardProducer) getBean( "dependencyDecisionByAwardProducer" );
    }
    
    
    @Ignore
    @Test
    public void shouldCheckIfVeteranHasChildrenPreviouslyMarried() {
    	
//        testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.dependencyDecisionByAward.response.xml" );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        producer.checkIfVeteranHasChildrenPreviouslyMarried( repository );
        assertThat( repository.getVeteran().getChildren().get(0).isPreviouslyMarried() , is(equalTo( true ) ) );
    }
    
    
    @Test
    public void shouldCheckIfVeteranHasNoChildrenPreviouslyMarried() {
    	
//        testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.award.response.xml" );
        repository.getVeteran().setChildren(null);
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        producer.checkIfVeteranHasChildrenPreviouslyMarried( repository );
        assertThat( repository.getVeteran().getChildren().get(0).isPreviouslyMarried() , is(equalTo( true ) ) );
    }
    
    
}
