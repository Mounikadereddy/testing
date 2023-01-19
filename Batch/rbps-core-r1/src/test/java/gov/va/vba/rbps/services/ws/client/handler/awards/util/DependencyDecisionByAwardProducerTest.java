/*
 * DependencyDecisionByAwardProducerTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;

import org.junit.Before;
import org.junit.Test;




public class DependencyDecisionByAwardProducerTest extends RbpsAbstractTest {

    private DependencyDecisionByAwardProducer           producer;
    private FindDependencyDecisionByAwardResponse       response;

    private RbpsRepository                              repository;



    @Override
    @Before
    public void setup() {

        super.setup();

        repository      = (RbpsRepository) getBean( "repository" );
        producer        = (DependencyDecisionByAwardProducer) getBean( "dependencyDecisionByAwardProducer" );
    }


    @Test
    public void shouldGetDependencyDecisionResponseWithSameClaimId() {

        producer.addFakeResponse(1234, null);
        response = producer.getDependencyDecisionResponse( repository, 1234 );

        assertThat( response, is(equalTo( null )));
    }


    @Test
    public void shouldGetDependencyDecisionResponseWithDiffClaimId() {

        repository.setVeteran( CommonFactory.adamsVeteran() );

        producer.addFakeResponse( 1234, null );
        response = producer.getDependencyDecisionResponse( repository, 32218636 );

        assertThat( response, is(notNullValue()) );
    }
}
