/*
 * NewSchoolChildFilterTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DependencyDecisionByAwardProducer;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Test;


public class NewSchoolChildFilterTest extends RbpsAbstractTest {

    DependentOnAwardFilter                          onAwardFilter;
    DependencyDecisionByAwardProducer               producer;
    FindDependencyDecisionByAwardResponse           response;
    NewSchoolChildFilter                            newSchoolChildFilter    = new NewSchoolChildFilter();
    RbpsRepository                                  repository;



    @Override
    @Before
    public void setup() {

        super.setup();
        LogUtils.setGlobalLogit( true );

        repository      = ( RbpsRepository ) getBean( "repository" );
        onAwardFilter   = ( DependentOnAwardFilter ) getBean( "dependentOnAwardFilter" );
        producer        = ( DependencyDecisionByAwardProducer ) getBean( "dependencyDecisionByAwardProducer" );
    }


    @Test( expected = RbpsRuntimeException.class)
    public void shouldThrowExceptionForNewStudentLastTermWithinOneYearOfDOC() {

        TestUtils       testUtils       = new TestUtils();

        testUtils.populateViaClaimResponses( getContext(), repository, "796088541" );
        onAwardFilter.evaluateDependentOnAward( repository );

        Child tim    =   repository.getVeteran().getChildren().get( 4 );

        //      When
        newSchoolChildFilter.filter( repository, tim );
    }

    @Test( expected = RbpsRuntimeException.class)
    public void shouldThrowExceptionForNewStudentAlreadyOnAward() {

        TestUtils       testUtils       = new TestUtils();

        testUtils.populateViaClaimResponses( getContext(), repository, "796088541" );
        onAwardFilter.evaluateDependentOnAward( repository );

        Child tim    =   repository.getVeteran().getChildren().get( 4 );

        //      When
        newSchoolChildFilter.filter( repository, tim );
    }
}
