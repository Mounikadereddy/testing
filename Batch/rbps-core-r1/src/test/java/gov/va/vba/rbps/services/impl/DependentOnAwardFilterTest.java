/*
 * DependentOnAwardFilterTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.populators.CorporateDependentsPopulator;
import gov.va.vba.rbps.services.populators.OnAwardPopulator;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DependencyDecisionByAwardProducer;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.ProcessAwardDependentRequestFilter;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class DependentOnAwardFilterTest extends RbpsAbstractTest {


    private DependentOnAwardFilter                      onAwardFilter;
    private DependencyDecisionByAwardProducer           producer;
    private RbpsRepository                              repository;
    private FindDependencyDecisionByAwardResponse       response;
    private TestUtils                   				testUtils   = new TestUtils();
    private CorporateDependentsPopulator        	    corporateDependentsPopulator;
    private FindDependentsResponse						findDependentsResponse;
    
    

    @Override
    @Before
    public void setup() {

        super.setup();
        LogUtils.setGlobalLogit( false );

        repository      = ( RbpsRepository ) getBean( "repository" );
        onAwardFilter   = ( DependentOnAwardFilter ) getBean( "dependentOnAwardFilter" );
        producer        = (DependencyDecisionByAwardProducer) getBean( "dependencyDecisionByAwardProducer" );
        corporateDependentsPopulator = ( CorporateDependentsPopulator ) getBean( "corporateDependentsPopulator" );
        
    }
    
    
    @Test
    public void shouldSpouseOnAwardTest() {
    	
//    	testUtils.populateUserInfo( repository, "gov/va/vba/rbps/services/populators/spouse.userInfo.response.xml" );
  
        FindDependentsResponse findDependentsResponse = testUtils.getFindDependentsResponseFromXml( "gov/va/vba/rbps/services/populators/spouse.findDependents.response.xml" );;
        corporateDependentsPopulator.populateFromDependents( findDependentsResponse, repository );

    	OnAwardPopulator.setOnCurrentAwardForXomDependent( repository );
        
    	String          xmlPath         = "gov/va/vba/rbps/services/populators/spouse.findDependencyDecisionByAwardResponse.xml";
        response       = testUtils.getDependencyDecisionResponseFromXml( xmlPath );
        
    	producer.addFakeResponse(repository.getVeteran().getClaim().getClaimId(), response);
    	
    	producer.populateDependencyDecisionForDependent( repository, repository.getVeteran().getCurrentMarriage().getMarriedTo() );
    	
    	onAwardFilter.evaluateDependentOnAward( repository );
    	
        System.out.println("Nothing");
    }

    
    @Ignore
    @Test
    public void shouldEvaluate999807588Dependents() {

        TestUtils       testUtils       = new TestUtils();

        testUtils.populateViaClaimResponses( getContext(), repository, "999807588" );

        List <Dependent>    xomDependents       =  buildDependentList( repository );
        List <Dependent>    throwExceptionList = new ArrayList<Dependent>();

        xomDependents.get(0).setIsDeniedAward( true );
        xomDependents.get(0).setOnCurrentAward( true );
        xomDependents.get(2).setIsDeniedAward( false );
        xomDependents.get(3).setIsDeniedAward( false );
        xomDependents.get(3).setOnCurrentAward( true );
        xomDependents.get(0).setCorpParticipantId( 9100778099L );
        xomDependents.get(1).setCorpParticipantId( 9100780393L );
        xomDependents.get(2).setCorpParticipantId( 9100780395L );
        xomDependents.get(3).setCorpParticipantId( 9100778098L );

        for ( final Dependent dep   :   xomDependents ) {

            onAwardFilter.evaluateDependentForDenied( repository, throwExceptionList, dep );
        }

        assertThat( throwExceptionList.size() , is(  equalTo( 0 ) ) );
    }


    @Ignore
    @Test
    public void shouldEvaluate999807615Dependents() {

        TestUtils       testUtils       = new TestUtils();

        testUtils.populateViaClaimResponses( getContext(), repository, "999807615" );

        List <Dependent>    xomDependents       =  buildDependentList( repository );
        List <Dependent>    throwExceptionList = new ArrayList<Dependent>();

        xomDependents.get(0).setIsDeniedAward( true );
        xomDependents.get(0).setCorpParticipantId( 9100780161L );
        xomDependents.get(1).setCorpParticipantId( 9100780214L );

        for ( final Dependent dep   :   xomDependents ) {

            onAwardFilter.evaluateDependentForDenied( repository, throwExceptionList, dep );
        }

        assertThat( throwExceptionList.size() , is(  equalTo( 0 ) ) );
    }


    @Ignore
    @Test
    public void shouldEvaluate999807617Dependents() {

        TestUtils       testUtils       = new TestUtils();

        testUtils.populateViaClaimResponses( getContext(), repository, "999807617" );

        List <Dependent>    xomDependents       =  buildDependentList( repository );
        List <Dependent>    throwExceptionList = new ArrayList<Dependent>();

        xomDependents.get(0).setIsDeniedAward( true );
        xomDependents.get(0).setCorpParticipantId( 9100780274L );
        xomDependents.get(1).setCorpParticipantId( 9100780275L );

        for ( final Dependent dep   :   xomDependents ) {

            onAwardFilter.evaluateDependentForDenied( repository, throwExceptionList, dep );
        }

        assertThat( throwExceptionList.size() , is(  equalTo( 0 ) ) );
    }


    @Ignore
    @Test( expected = RbpsRuntimeException.class )
    public void shouldGetExceptionWhenCheckingMarriedAfterDenied() {

        repository.setVeteran( CommonFactory.adamsVeteran() );
        repository.getVeteran().getCurrentMarriage().setStartDate( null );


        onAwardFilter.isMarriagedAfterDenied( repository,
                                              repository.getVeteran().getCurrentMarriage().getMarriedTo() );
    }


    @Ignore
    @Test
    public void shouldNotGetExceptionWhenCheckingMarriedAfterDenied() {

        Marriage marriage = new Marriage();
        marriage.setStartDate( new Date() );

        repository.setVeteran( CommonFactory.adamsVeteran() );

        Spouse  spouse = repository.getVeteran().getCurrentMarriage().getMarriedTo();
        repository.getVeteran().getCurrentMarriage().setStartDate( new Date() );
        spouse.setCurrentMarriage( marriage );
        spouse.setDeniedDate( new Date() );

        onAwardFilter.isMarriagedAfterDenied( repository,
                                              repository.getVeteran().getCurrentMarriage().getMarriedTo() );
    }


    private List <Dependent> buildDependentList( final RbpsRepository  repository ) {

        List <Dependent>    xomDependents       = new ArrayList<Dependent>();
        NullSafeGetter      grabber             = new NullSafeGetter();
        Spouse              spouse              = (Spouse) grabber.getAttribute( repository, "veteran.currentMarriage.marriedTo" );

        xomDependents.addAll( repository.getVeteran().getChildren() );

        if ( spouse != null ) {

            xomDependents.add( spouse );
        }

        return xomDependents;
    }
}
