package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.claimprocessor.impl.EP130ClaimPreProcessor;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.AwardStatus;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

public class CountNumberOfDependentsOnAwardTest  extends RbpsAbstractTest {

    private CountNumberOfDependentsOnAward           	countNumberOfDependentsOnAward;
    private FindDependencyDecisionByAwardResponse       response;
    private TestUtils       							testUtils   = new TestUtils();
    private RbpsRepository                              repository;
    private EP130ClaimPreProcessor						preProcessor;
    private DependencyDecisionByAwardProducer			producer;
    private DecisionDetailsProcessor					detailsProcessor;
    private List<DependencyDecisionVO> 					dependencyDecisionList;


    @Override
    @Before
    public void setup() {

        super.setup();

        repository      					= (RbpsRepository) getBean( "repository" );
        preProcessor    					= (EP130ClaimPreProcessor) getBean( "ep130ClaimPreProcessor" );
        producer        					= (DependencyDecisionByAwardProducer) getBean( "dependencyDecisionByAwardProducer" );
        countNumberOfDependentsOnAward      = new CountNumberOfDependentsOnAward();
        detailsProcessor 	= new DecisionDetailsProcessor();
//        testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/numberOfDependents.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/numberOfDependents.dependencyDecisionByAward.response.xml" );
        repository.getVeteran().setAwardStatus( new AwardStatus() );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        
        dependencyDecisionList = response.getReturn().getDependencyDecision().getDependencyDecision();
    }
    
 

    @Test
    public void shouldGetTotalDependentCountEP130() {
    	
    	try{ 
    		preProcessor.countNumberOfDependentsOnAward( repository );
    	} catch ( Exception ex ) {
    		
    		
    	}
    }

    
    @Ignore
    @Test
    public void shouldGetTotalDependentCount() {
    	
    	int count = countNumberOfDependentsOnAward.getTotalDependentCount( repository, response );
    	assertThat( count , is(equalTo( 21 ) ) );
    }
    
    
    @Ignore
    @Test
    public void shouldGetCountOfXomDependentsNotOnAward() {
    	
    	int count = countNumberOfDependentsOnAward.getCountOfXomDependentsNotOnAward( repository, response );
    	assertThat( count , is(equalTo( 3 ) ) );
    }
    
    
    @Ignore
    @Test
    public void shouldGetDependencyDecisionResponseSorted() {
    	
    	detailsProcessor.sortDependencyDecisionList( dependencyDecisionList );
    	assertThat( dependencyDecisionList.get(0).getPersonID(), is(equalTo( new Long( 600052528 ) ) ) );
    }
    
    
    @Ignore
    @Test
    public void shouldGetDependencyDecisionResponseSortedForDependent() {
    	
    	List<DependencyDecisionVO> 	dependentList = new ArrayList<DependencyDecisionVO>();
    	
    	for ( DependencyDecisionVO dependencyDecisionVO	:	dependencyDecisionList ) {
    		
    		if ( dependencyDecisionVO.getPersonID().equals(new Long( 600052528 ) ) ) {
    			dependentList.add( dependencyDecisionVO );
    		}
    	}
    	detailsProcessor.sortDependencyDecisionList( dependentList );
    	assertThat( dependentList.get(0).getPersonID(), is(equalTo( new Long( 600052528 ) ) ) );
    }

    
    
    
    
}
