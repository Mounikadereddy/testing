/*
 * EP130ClaimPreProcessorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimprocessor.impl;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Claim;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.rule.service.RBPSRuleService;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;



//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/applicationxml", "classpath:/applicationContext-test.xml"})
public class EP130ClaimPreProcessorTest extends RbpsAbstractTest {

    private static Logger logger = Logger.getLogger(EP130ClaimPreProcessorTest.class);

    private CommonUtils                 utils;
    private LogUtils                    logUtils    = new LogUtils( logger, true );
    private TestUtils                   testUtils   = new TestUtils();
    private EP130ClaimPreProcessor      preProcessor;
    private EP130ClaimProcessorImpl		ep130ClaimProcessorImpl;
    private RbpsRepository              repository	= new RbpsRepository();


    @Override
    @Before
    public void setup() {

        super.setup();

        LogUtils.setGlobalLogit( true );
        preProcessor    = (EP130ClaimPreProcessor) getBean( "ep130ClaimPreProcessor" );
        ep130ClaimProcessorImpl = (EP130ClaimProcessorImpl) getBean( "ep130ClaimProcessorImpl" );
//        repository      = (RbpsRepository) getBean( "repository" );
    }

    
    
    
    
    
    
   @Test
    public void shouldRunAllPopulators() throws Throwable {

        try {
        	testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/fiduciary.userInfo.response.xml" );
        	repository.setClaimStationLocationId( 335L );
        	repository.getVeteran().setServiceConnectedDisabilityRating( 40.0 );
        	repository.getVeteran().setRatingDate( new Date() );
        	repository.getVeteran().setRatingEffectiveDate( new Date() );
        }
        catch ( final Throwable ex ) {

//            logUtils.log( new SoapFaultPrinter().printSoapFaultInfo( ex ) );

            ex.printStackTrace();
            throw ex;
        }
    }

   @Ignore 
    @Test
    public void shouldOnCurrentAwardSetForSameSpouse() {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        final Spouse        spouse                      =   CommonFactory.getRandomSpouse( repository.getVeteran() );
        repository.getVeteran().getCurrentMarriage().setMarriedTo( spouse );

        final CorporateDependent    corporateSpouse     =   CommonFactory.getCorporateSpouse(repository, spouse);
        corporateSpouse.setOnAward( true );
        repository.setSpouse( corporateSpouse );

        preProcessor.setOnCurrentAwardForXomDependents( repository );

        assertThat( spouse.isOnCurrentAward(), is(equalTo( true ) ) );
    }

   @Ignore 
    @Test
    public void shouldOnCurrentAwardSetForDifferentSpouse() {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        final Spouse        spouse                      =   CommonFactory.getRandomSpouse( repository.getVeteran() );
        repository.getVeteran().getCurrentMarriage().setMarriedTo( spouse );

        final Spouse                anotherSpouse       =   CommonFactory.getRandomSpouse( repository.getVeteran() );
        final CorporateDependent    corporateSpouse     =   CommonFactory.getCorporateSpouse(repository, anotherSpouse);
        corporateSpouse.setOnAward( true );
        repository.setSpouse( corporateSpouse );

        preProcessor.setOnCurrentAwardForXomDependents( repository );

        assertThat( spouse.isOnCurrentAward(), is(equalTo( false ) ) );
    }

   @Ignore 
    @Test
    public void shouldsetOnCurrentAwardForXomChildren() {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        final List <Child>  children    =   new ArrayList<Child>();
        Child       child   =   CommonFactory.getRandomChild( repository.getVeteran() );
        children.add( child );
        child   =   CommonFactory.getRandomChild( repository.getVeteran() );
        children.add( child );
        child   =   CommonFactory.getRandomChild( repository.getVeteran() );
        children.add( child );

        repository.getVeteran().setChildren(children);

        final List <CorporateDependent> corporateChildren   =   CommonFactory.getCorporateChildren( repository, children );


        for ( final CorporateDependent corpChild   :   corporateChildren ) {

            corpChild.setOnAward( true );
        }

        repository.setChildren( corporateChildren );

        preProcessor.setOnCurrentAwardForXomDependents( repository );

        int counter =   0;

        for ( final Child xomChild   :   repository.getVeteran().getChildren() ) {

            if ( xomChild.isOnCurrentAward() ) {
                counter++;
            }
        }

        assertThat( repository.getChildren().size(), is(equalTo( counter ) ) );
    }

   @Ignore 
    @Test
    public void shoudHandleCorporateSpouseWithNoXomSpouse() {

        String                      userInfoPath    = "gov/va/vba/rbps/services/populators/112121125.userinfo.response.xml";
        String                      dependentsPath  = "gov/va/vba/rbps/services/populators/112121125.findDep.response.xml";
        RbpsRepository              repo            = new RbpsRepository();
        TestUtils                   testUtils       = new TestUtils();
        EP130ClaimPreProcessor      preProcessor    = new EP130ClaimPreProcessor();

//        testUtils.populateUserInfo( repo, userInfoPath );
        testUtils.populateCorporateDependents( repo, dependentsPath );

//        preProcessor.setRepository( repo );
        preProcessor.setOnCurrentAwardForXomDependents( repository );
    }

   @Ignore 
    @Test
    public void shouldEvaluateDependentAwardStatusOne() {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        final List <Child>  children    =   new ArrayList<Child>();

        children.add( addChild( 0, true, true, true) );
        children.add( addChild( 0, true, false, true) );
        children.add( addChild( 0, false, false, false) );

//        logUtils.log("\n");
        repository.getVeteran().setChildren(children);
//        preProcessor.setRepository( repository );
        preProcessor.evaluateDependentOnAwardStatus( repository );
//        logUtils.log("************\n");
        assertThat( repository.getVeteran().getChildren().size(), is(equalTo( 2 ) ) );
    }

   @Ignore 
    @Test
    public void shouldEvaluateDependentAwardStatusTwo() {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        final List <Child>  children    =   new ArrayList<Child>();

        children.add( addChild( 0, true, true, true) );
        children.add( addChild( 0, false, true, false) );
        children.add( addChild( 0, false, false, false) );

        repository.getVeteran().setChildren(children);
//        preProcessor.setRepository( repository );
        preProcessor.evaluateDependentOnAwardStatus( repository );
//        logUtils.log("************\n");
        assertThat( repository.getVeteran().getChildren().size(), is(equalTo( 3 ) ) );
    }
   @Ignore 
    @Test
    public void shouldEvaluateDependentAwardStatusThree() {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        final List <Child>  children    =   new ArrayList<Child>();
        Child       child   =   CommonFactory.getRandomChild( repository.getVeteran() );

        children.add( addChild( 0, true, true, false) );
        children.add( addChild( 0, false, true, true) );
        children.add( addChild( 0, false, false, false) );

        repository.getVeteran().setChildren(children);
//        preProcessor.setRepository( repository );
        preProcessor.evaluateDependentOnAwardStatus( repository );
//        logUtils.log("************\n");
        assertThat( repository.getVeteran().getChildren().size(), is(equalTo( 3 ) ) );
    }

   @Ignore 
    @Test
    public void shouldEvaluateDependentAwardStatusNoChildren() {

        repository.setVeteran( CommonFactory.adamsVeteran() );

//        preProcessor.setRepository( repository );
        preProcessor.evaluateDependentOnAwardStatus( repository );
//        logUtils.log( String.format("No xom children on award" ));
//        logUtils.log("************\n");
        assertThat( repository.getVeteran().getChildren().size(), is(equalTo( 0 ) ) );
    }

   @Ignore 
    @Test
    public void shouldDecideDependencyByAward() {

        repository.setVeteran( CommonFactory.adamsVeteran() );


        final List <Child>  children    =   new ArrayList<Child>();

        children.add( addChild( 32218613, true, true, false) );
        children.add( addChild( 32218764, false, true, true) );
        children.add( addChild( 32220660, false, false, false) );

        repository.getVeteran().setChildren(children);
        repository.getVeteran().setCorpParticipantId(32218612);
//        preProcessor.setRepository( repository );
        preProcessor.decideDependencyByAward( repository );
//        logUtils.log("************\n");
    }

    @Ignore
    @Test
    public void shouldGatherRootCauseInException() {

        try {
            repository.setVeteran( CommonFactory.adamsVeteran() );
//            preProcessor.setRepository( repository );
            preProcessor.preProcess( repository );
        }
        catch ( Throwable ex ) {

//            logUtils.log( "rbps validation messages:\n" + utils.stringBuilder( repository.getValidationMessages() ) );
//            ex.printStackTrace();

            throw (RuntimeException) ex;
        }
    }

   @Ignore 
    @Test
    public void shouldAssignDateCorrectly_CQ191() {

        TestUtils           testUtils   = new TestUtils();
        RbpsRepository      repo        = new RbpsRepository();
        RBPSRuleService     rules       = (RBPSRuleService) getBean( "rbpsRuleService" );

        testUtils.populateViaClaimResponses( getContext(), repo, "470141119" );
//        rules.executeEP130( repo.getVeteran() );

//        logUtils.log( logger, "POST rules\n" + new IndentedXomToString().toString( repo.getVeteran(), 0 ) );
    }


    private Child addChild(final long participantID, final boolean add674, final boolean add686C, final boolean onCurrentAward) {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        Child       child   =   CommonFactory.getRandomChild( repository.getVeteran() );
         child.setCorpParticipantId( participantID );

         if ( add674 ) {

             child.getForms().add(FormType.FORM_21_674);
         }

         if ( add686C ) {

             child.getForms().add(FormType.FORM_21_686C);
         }

         if ( onCurrentAward ) {

             child.setOnCurrentAward( onCurrentAward );
         }

         child.setNewSchoolChild( true );

//         logUtils.log( String.format("xom child %s %s on award>> %s",child.getFirstName(), child.getFirstName(), child.isOnCurrentAward() ));

         return child;
    }
}
