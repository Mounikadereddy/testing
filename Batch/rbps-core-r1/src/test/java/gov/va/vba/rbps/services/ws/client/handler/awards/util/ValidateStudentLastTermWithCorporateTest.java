package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;






public class ValidateStudentLastTermWithCorporateTest   extends RbpsAbstractTest {

    private FindDependencyDecisionByAwardResponse       response;
    private TestUtils       							testUtils   = new TestUtils();
    private SimpleDateUtils								dateUtils	= new SimpleDateUtils();
    private RbpsRepository                              repository;
    private DependencyDecisionByAwardProducer			producer;


    @Override
    @Before
    public void setup() {

        super.setup();

        repository    	= (RbpsRepository) getBean( "repository" );
        producer      	= (DependencyDecisionByAwardProducer) getBean( "dependencyDecisionByAwardProducer" );
    }
 
 
    @Test
    public void shouldValidateStudentNoLastTermInCorporate() {
    	
        //testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/noLastTermChild.dependencyDecisionByAward.response.xml" );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        
        Child	child	= new Child();
        Education lastTerm		=	new Education();
        
        child.setFirstName("Gina");
        child.setLastName("Fleming");
        child.setCorpParticipantId(600053392L);
        lastTerm.setCourseEndDate( dateUtils.convertDate("", "03/27/2013" ) );
        child.setLastTerm( lastTerm );
        repository.getVeteran().getChildren().add( child );
        	
        producer.checkIfChildrenHaveLastTermInCorporate( repository );
        	
        assertThat( repository.getVeteran().getChildren().get(1).getLastTerm() , is(equalTo( null ) ) );
    } 
 
    
    @Ignore 
    @Test
    public void shouldValidateStudentNoLastTermWithCorporate() {
    	
        //testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/lastTermChild.dependencyDecisionByAward.response.xml" );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        
        Child	child	= new Child();
        
        child.setFirstName("Gina");
        child.setLastName("Fleming");
        child.setCorpParticipantId(600053392L);
        repository.getVeteran().getChildren().add( child );
        	
        producer.checkIfChildrenHaveLastTermInCorporate( repository );
        	
        assertThat( repository.getVeteran().getChildren().get(1).getLastTerm() , is(equalTo( null ) ) );
    } 
 
    
    @Ignore   
    @Test
    public void shouldValidateStudentLastTermFutureOutside30DaysWithCorporate() {
    	
        //testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/lastTermChild.dependencyDecisionByAward.response.xml" );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        
        Child	child	= new Child();
        Education lastTerm		=	new Education();
        
        child.setFirstName("Gina");
        child.setLastName("Fleming");
        child.setCorpParticipantId(600053392L);
        lastTerm.setCourseEndDate( dateUtils.convertDate("", "03/27/2013" ) );
        child.setLastTerm( lastTerm );
        repository.getVeteran().getChildren().add( child );
        try {
        	
        	producer.checkIfChildrenHaveLastTermInCorporate( repository );
        	
        } catch ( Exception ex ) {
        	
        	assertThat( ex.getMessage() , is(equalTo( "Child Gina Fleming has School termination date discrepancy . Please review." ) ) );
        }
    } 
   
    
    @Ignore
    @Test
    public void shouldValidateStudentLastTermOutside30DaysWithCorporate() {
    	
        //testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/lastTermChild.dependencyDecisionByAward.response.xml" );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        
        Child	child	= new Child();
        Education lastTerm		=	new Education();
        
        child.setFirstName("Gina");
        child.setLastName("Fleming");
        child.setCorpParticipantId(600053392L);
        lastTerm.setCourseEndDate( dateUtils.convertDate("", "09/27/2012" ) );
        child.setLastTerm( lastTerm );
        repository.getVeteran().getChildren().add( child );
        try {
        	
        	producer.checkIfChildrenHaveLastTermInCorporate( repository );
        	
        } catch ( Exception ex ) {
        	
        	assertThat( ex.getMessage() , is(equalTo( "Child Gina Fleming has School termination date discrepancy . Please review." ) ) );
        }
    } 
    
    
        
    @Ignore
    @Test
    public void shouldValidateStudentLastTermFutureWithin30DaysWithCorporate() {
    	
        //testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/lastTermChild.dependencyDecisionByAward.response.xml" );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        
        Child	child	= new Child();
        Education lastTerm		=	new Education();
        
        child.setFirstName("Gina");
        child.setLastName("Fleming");
        child.setCorpParticipantId(600053392L);
        lastTerm.setCourseEndDate( dateUtils.convertDate("", "02/27/2013" ) );
        child.setLastTerm( lastTerm );
        repository.getVeteran().getChildren().add( child );
        
        producer.checkIfChildrenHaveLastTermInCorporate( repository );
        
        assertThat( repository.getVeteran().getChildren().get(1).getLastTerm().getCourseEndDate() , is(equalTo( dateUtils.convertDate("", "02/27/2013" ) ) ) );
    } 
    
    
    @Ignore    
    @Test
    public void shouldValidateStudentLastTermWithin30DaysWithCorporate() {
    	
        //testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/lastTermChild.dependencyDecisionByAward.response.xml" );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        
        Child	child	= new Child();
        Education lastTerm		=	new Education();
        
        child.setFirstName("Gina");
        child.setLastName("Fleming");
        child.setCorpParticipantId(600053392L);
        lastTerm.setCourseEndDate( dateUtils.convertDate("", "02/10/2013" ) );
        child.setLastTerm( lastTerm );
        repository.getVeteran().getChildren().add( child );
        
        producer.checkIfChildrenHaveLastTermInCorporate( repository );
        
        assertThat( repository.getVeteran().getChildren().get(1).getLastTerm().getCourseEndDate() , is(equalTo( dateUtils.convertDate("", "02/10/2013" ) ) ) );
    } 
    
    
    @Ignore
    @Test
    public void shouldValidateStudentLastTermSameWithCorporate() {
    	
        //testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/previouslymarriedChild.userInfo.response.xml" );
        response = 
			testUtils.getDependencyDecisionResponseFromXml( "gov/va/vba/rbps/lettergeneration/lastTermChild.dependencyDecisionByAward.response.xml" );
        producer.addFakeResponse( repository.getVeteran().getClaim().getClaimId(), response );
        
        Child	child	= new Child();
        Education lastTerm		=	new Education();
        
        child.setFirstName("Gina");
        child.setLastName("Fleming");
        child.setCorpParticipantId(600053392L);
        lastTerm.setCourseEndDate( dateUtils.convertDate("", "02/20/2013" ) );
        child.setLastTerm( lastTerm );
        repository.getVeteran().getChildren().add( child );
        
        producer.checkIfChildrenHaveLastTermInCorporate( repository );
        
        assertThat( repository.getVeteran().getChildren().get(1).getLastTerm().getCourseEndDate() , is(equalTo( dateUtils.convertDate("", "02/20/2013" ) ) ) );
    }
 
    
    
}
