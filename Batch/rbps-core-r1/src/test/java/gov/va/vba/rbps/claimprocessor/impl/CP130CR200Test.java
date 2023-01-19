/*
 * CP130CR200Test
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimprocessor.impl;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


public class CP130CR200Test extends RbpsAbstractTest {

    private static Logger               logger              = Logger.getLogger(CP130CR200Test.class);


    private LogUtils                    logUtils            = new LogUtils( logger, false );
    private EP130ClaimPreProcessor      preProcessor;
    private RbpsRepository              repository;


    @Override
    @Before
    public void setup() {

        super.setup();

        LogUtils.setGlobalLogit( false );

        preProcessor    = (EP130ClaimPreProcessor) getBean( "ep130ClaimPreProcessor" );
        repository      = (RbpsRepository) getBean( "repository" );
    }


    @Test
    public void shouldEvaluate7960666341Dependents() {

        TestUtils       testUtils       = new TestUtils();
        String          message          = null;

        testUtils.populateViaClaimResponses( getContext(), repository, "7960666341" );
        repository.getVeteran().getCurrentMarriage().getMarriedTo().setOnCurrentAward( false );
//        preProcessor.setRepository( repository );
        try {
            preProcessor.evaluateDependentOnAwardStatus( repository );
        }
        catch ( Throwable ex ) {
            message = ex.getMessage();
        }

//        logUtils.log( message );
        assertThat( message , is( not ( equalTo( null ) ) ) );
    }


    @Test
    public void shouldEvaluateDependentNotOnAwardHasCurrentMarriageDate() {

        String          userInfoPath    = "gov/va/vba/rbps/services/populators/796088541.userinfo.response.xml";
        TestUtils       testUtils       = new TestUtils();
        String          message          = null;

//        testUtils.populateUserInfo( repository, userInfoPath );
        repository.getVeteran().getCurrentMarriage().getMarriedTo().setOnCurrentAward( false );
//        preProcessor.setRepository( repository );
        try {
            preProcessor.evaluateDependentOnAwardStatus( repository );
        }
        catch ( Throwable ex ) {
            message = ex.getMessage();
        }

//        logUtils.log( message );
        assertThat( message , is( not ( equalTo( null ) ) ) );
    }


    @Test
    public void shouldEvaluateDependentHasCurrentMarriageDate() {

        String          userInfoPath    = "gov/va/vba/rbps/services/populators/7960666342.userInfo.response.xml";
        TestUtils       testUtils       = new TestUtils();
        String          message          = null;

//        testUtils.populateUserInfo( repository, userInfoPath );
        repository.getVeteran().getCurrentMarriage().getMarriedTo().setIsDeniedAward( true );
//        preProcessor.setRepository( repository );
        try {
            preProcessor.evaluateDependentOnAwardStatus( repository );
        }
        catch ( Throwable ex ) {
            message = ex.getMessage();
        }

//        logUtils.log( message );
        assertThat( message , is( not ( equalTo( null ) ) ) );
    }


    @Test
    public void shouldEvaluateDependentAwardStatusOne() {

        repository.setVeteran( CommonFactory.adamsVeteran() );
        repository.getVeteran().getCurrentMarriage().getMarriedTo().setOnCurrentAward( true );

        final List <Child>  children    =   new ArrayList<Child>();
        Child       child   =   CommonFactory.getRandomChild( repository.getVeteran() );
        child.setOnCurrentAward( true );
        child.setFirstName( "Tom" );
        child.getForms().add( FormType.FORM_21_674 );
        child.getForms().add( FormType.FORM_21_686C );
//        logUtils.log( String.format("xom child %s %s on award>> %s",child.getFirstName(), child.getFirstName(), child.isOnCurrentAward() ));
        children.add( child );

        child   =   CommonFactory.getRandomChild( repository.getVeteran() );
        child.setOnCurrentAward(true);
        child.setFirstName( "Ram" );
        child.getForms().add( FormType.FORM_21_686C );
//        logUtils.log( String.format("xom child %s %s on award>> %s",child.getFirstName(), child.getFirstName(), child.isOnCurrentAward() ));
        children.add( child );

        child   =   CommonFactory.getRandomChild( repository.getVeteran() );
        child.setFirstName( "Todd" );
        child.setOnCurrentAward(false);
//        logUtils.log( String.format("xom child %s %s on award>> %s",child.getFirstName(), child.getFirstName(), child.isOnCurrentAward() ));

        child   =   CommonFactory.getRandomChild( repository.getVeteran() );
        child.setIsDeniedAward(true);
        child.setFirstName( "Suma" );
        child.setChildType( ChildType.BIOLOGICAL_CHILD );
        child.getForms().add( FormType.FORM_21_674 );
//        logUtils.log( String.format("xom child %s %s on award>> %s",child.getFirstName(), child.getFirstName(), child.isOnCurrentAward() ));
        children.add( child );

        child   =   CommonFactory.getRandomChild( repository.getVeteran() );
        child.setIsDeniedAward(false);
        child.setFirstName( "Luma" );
        child.getForms().add( FormType.FORM_21_686C );
//        logUtils.log( String.format("xom child %s %s on award>> %s",child.getFirstName(), child.getFirstName(), child.isOnCurrentAward() ));
        children.add( child );

        child.setFirstName( "Rema" );
        child.setChildType( ChildType.STEPCHILD );
        GregorianCalendar newGregCal = new GregorianCalendar( 2011, 12, 01);
        child.setDeniedDate( newGregCal.getTime() );
        child.getForms().add( FormType.FORM_21_686C );
//        logUtils.log( String.format("xom child %s %s on award>> %s",child.getFirstName(), child.getFirstName(), child.isOnCurrentAward() ));

        children.add( child );
//        logUtils.log("\n");
        repository.getVeteran().setChildren(children);

        preProcessor.evaluateDependentOnAwardStatus( repository );

        assertThat( repository.getVeteran().getChildren().size(), is(equalTo( 4 ) ) );
    }


    @Test
    public void shouldEvaluateDependentAwardStatusSpouse() {

        boolean thrown = false;

        repository.setVeteran( CommonFactory.adamsVeteran() );

        repository.getVeteran().getCurrentMarriage().getMarriedTo().setFirstName( "Roopa" );
        GregorianCalendar  newGregCal = new GregorianCalendar( 2011, 12, 01);
        repository.getVeteran().getCurrentMarriage().getMarriedTo().setDeniedDate( newGregCal.getTime() );
        newGregCal = new GregorianCalendar( 2011, 10, 01);
        repository.getVeteran().getCurrentMarriage().setStartDate( newGregCal.getTime() );
        repository.getVeteran().getCurrentMarriage().getMarriedTo().setIsDeniedAward( true );

        try {
            preProcessor.evaluateDependentOnAwardStatus( repository );
        }
        catch ( Throwable ex ) {
//            logUtils.log( "----------------------------------------------------------------------------");
            thrown = true;
        }

        assertTrue( thrown );
    }
}
