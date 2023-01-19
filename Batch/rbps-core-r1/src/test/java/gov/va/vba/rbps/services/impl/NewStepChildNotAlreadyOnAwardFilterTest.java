/*
 * NewStepChildNotAlreadyOnAwardFilterTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.QuietTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.fixtures.CommonFactory;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;


public class NewStepChildNotAlreadyOnAwardFilterTest {

    private static Logger       logger      =   Logger.getLogger(NewStepChildNotAlreadyOnAwardFilterTest.class);

    private boolean                                 logit       =       true;
    private LogUtils                                logUtils    =       new LogUtils( logger, logit );
    private NewStepChildNotAlreadyOnAwardFilter     filter      =       new NewStepChildNotAlreadyOnAwardFilter();
    private RbpsRepository                          repo        =       new RbpsRepository();


    @Before
    public void setup() {

        new QuietTest().setup( logit );

        repo.setVeteran( CommonFactory.adamsVeteran() );
    }


    @Test
    public void shouldNotFilterBiologicals() {

        Child  child    =   new Child();
        child.setFirstName( "john" );
        child.setLastName( "baptist" );
        child.setChildType( ChildType.BIOLOGICAL_CHILD );

        repo.getVeteran().getChildren().clear();
        repo.getVeteran().getChildren().add( child );

        filter.filter( repo,  child );
    }


    @Test
    public void shouldNotFilterOnAward() {

        Child  child    =   new Child();
        child.setFirstName( "john" );
        child.setLastName( "baptist" );
        child.setChildType( ChildType.STEPCHILD );
        child.setOnCurrentAward( true );

        repo.getVeteran().getChildren().clear();
        repo.getVeteran().getChildren().add( child );

        filter.filter( repo,  child );
    }


    @Test
    public void shouldNotFilterYoungKids() {

        Child  child    =   new Child();
        child.setFirstName( "john" );
        child.setLastName( "baptist" );
        child.setChildType( ChildType.STEPCHILD );
        child.setOnCurrentAward( false );
        child.setBirthDate( SimpleDateUtils.addYearsToDate( -1, new Date() ) );

        repo.getVeteran().getClaim().setReceivedDate( new Date() );
        repo.getVeteran().getChildren().clear();
        repo.getVeteran().getChildren().add( child );

        filter.filter( repo,  child );
    }


    @Test
    public void shouldNotFilterOlderKids() {

        Child  child    =   new Child();
        child.setFirstName( "john" );
        child.setLastName( "baptist" );
        child.setChildType( ChildType.STEPCHILD );
        child.setOnCurrentAward( false );
        child.setBirthDate( SimpleDateUtils.addYearsToDate( -20, new Date() ) );

        repo.getVeteran().getClaim().setReceivedDate( new Date() );
        repo.getVeteran().getCurrentMarriage().setStartDate( SimpleDateUtils.addYearsToDate( -2, new Date() ) );
        repo.getVeteran().getChildren().clear();
        repo.getVeteran().getChildren().add( child );

        filter.filter( repo,  child );
    }


    @Test
    public void shouldNotBeBotheredByMissingMarriage() {

        Child  child    =   new Child();
        child.setFirstName( "john" );
        child.setLastName( "baptist" );
        child.setChildType( ChildType.STEPCHILD );
        child.setOnCurrentAward( false );
        child.setBirthDate( SimpleDateUtils.addYearsToDate( -20, new Date() ) );

        repo.getVeteran().getClaim().setReceivedDate( new Date() );
        repo.getVeteran().setCurrentMarriage( null );
        repo.getVeteran().getChildren().clear();
        repo.getVeteran().getChildren().add( child );

        filter.filter( repo,  child );
    }


    @Test( expected = RbpsRuntimeException.class )
    public void shouldFilterStepKidsNotAlreadyOnAwardMinorAtMarriage18AtClaim() {

        Child  child    =   new Child();
        child.setFirstName( "john" );
        child.setLastName( "baptist" );
        child.setChildType( ChildType.STEPCHILD );
        child.setOnCurrentAward( false );
        child.setBirthDate( SimpleDateUtils.addYearsToDate( -19, new Date() ) );

        repo.getVeteran().getClaim().setReceivedDate( new Date() );
        repo.getVeteran().getCurrentMarriage().setStartDate( SimpleDateUtils.addYearsToDate( -2, new Date() ) );

        repo.getVeteran().getChildren().clear();
        repo.getVeteran().getChildren().add( child );

//        logUtils.log( new IndentedXomToString().toString( repo.getVeteran(), 0 ) );

        filter.filter( repo,  child );
    }






    private Child addChild( final boolean add674, final boolean isSchoolChild ) {

        Child       child   =   CommonFactory.getRandomChild( repo.getVeteran() );

        if ( add674 ) {

             child.getForms().add(FormType.FORM_21_674);
        }

        if ( isSchoolChild ) {

            child.setSchoolChild( isSchoolChild );
        }

         return child;
    }
}
