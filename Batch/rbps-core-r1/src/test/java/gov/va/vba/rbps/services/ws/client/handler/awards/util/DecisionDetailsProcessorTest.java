/*
 * DecisionDetailsProcessorTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.List;

import org.junit.Before;
import org.junit.Test;




public class DecisionDetailsProcessorTest {

    private static Logger   logger      = Logger.getLogger(DecisionDetailsProcessorTest.class);

    private boolean                 logit           = true;
    private CommonUtils             utils;
    private LogUtils                logUtils        = new LogUtils( logger, logit );

    @Before
    public void setup() {

        LogUtils.setGlobalLogit( logit );
    }


    @Test
    public void shouldSortByEffectiveDate() {

        DecisionDetailsProcessor        processor   = new DecisionDetailsProcessor();
        TestUtils                       testUtils   = new TestUtils();
        String                          xmlPath     = "gov/va/vba/rbps/services/ws/client/handler/awards/util/dependencyDetails.sort.response.xml";

        FindDependencyDecisionByAwardResponse   response = testUtils.getDependencyDecisionResponseFromXml( xmlPath );

        processor.sortDependencyDecisionList( response.getReturn().getDependencyDecision().getDependencyDecision() );


    }


    @Test
    public void shouldProduceOneDetails() {

        String                      xmlPath     = "gov/va/vba/rbps/services/ws/client/handler/awards/util/dependencyDecision.depest.response.xml";
        List<DecisionDetails>       details     = loadDetails( xmlPath );

        assertThat( "one decision details is produced",     details.size(),                         is(equalTo( 1 )));
        assertThat( "the end decision type is null",    details.get( 0 ).getEndDecisionType(),  is(nullValue()));
        assertThat( "the end status type is null",      details.get( 0 ).getEndStatusType(),    is(nullValue()));
    }


    @Test
    public void shouldProduceOneDetailsWithGrantAndDenial() {

        String                      xmlPath     = "gov/va/vba/rbps/services/ws/client/handler/awards/util/dependencyDecision.twoRecords.response.xml";
        List<DecisionDetails>       details     = loadDetails( xmlPath );

        assertThat( "one decision details is produced",     details.size(),                         is(equalTo( 1 )));
        assertThat( "the end decision type is not null",    details.get( 0 ).getEndDecisionType(),  is(notNullValue()));
        assertThat( "the end status type is not null",      details.get( 0 ).getEndStatusType(),    is(notNullValue()));
    }


    @Test
    public void shouldProduceTwoDetailsFromThreeDecisions() {

        String                      xmlPath     = "gov/va/vba/rbps/services/ws/client/handler/awards/util/dependencyDecision.threeRecords.response.xml";
        List<DecisionDetails>       details     = loadDetails( xmlPath );

        assertThat( "one decision details is produced",     details.size(),                         is(equalTo( 2 )));
        assertThat( "the first end decision type is not null",    details.get( 0 ).getEndDecisionType(),  is(notNullValue()));
        assertThat( "the first end status type is not null",      details.get( 0 ).getEndStatusType(),    is(notNullValue()));

        assertThat( "the second end decision type is null",    details.get( 1 ).getEndDecisionType(),  is(nullValue()));
        assertThat( "the second end status type is null",      details.get( 1 ).getEndStatusType(),    is(nullValue()));
    }


    public List<DecisionDetails> loadDetails( final String xmlPath ) {

        DecisionDetailsProcessor                processor   = new DecisionDetailsProcessor();
        TestUtils                               testUtils   = new TestUtils();
        FindDependencyDecisionByAwardResponse   response    = testUtils.getDependencyDecisionResponseFromXml( xmlPath );
        List<DecisionDetails>                   details     = processor.createDecisionDetailsListForDependent( response.getReturn().getDependencyDecision().getDependencyDecision(), new RbpsRepository() );

//        logUtils.log( "Decision details: " + utils.stringBuilder( details ) );

        return details;
    }
}
