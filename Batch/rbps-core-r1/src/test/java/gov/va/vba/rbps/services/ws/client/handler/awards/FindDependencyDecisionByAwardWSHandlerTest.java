/*
 * FindDependcyDecisionByAwardWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;

import org.junit.Before;
import org.junit.Test;


/**
 *      Test case of FindDependentOnAwardWSHandler
 */
public class FindDependencyDecisionByAwardWSHandlerTest extends RbpsAbstractTest  {

//    private CommonUtils                             utils       = new CommonUtils();
    private FindDependencyDecisionByAwardWSHandler  wsHandler;


    @Override
    @Before
    public void setup() {

        super.setup();
        wsHandler =  (FindDependencyDecisionByAwardWSHandler) getBean("findDependencyDecisionByAwardWSHandler");
    }


    @Test
//    @RunTags(tags={"Spring", "Populator"} )
    public void testFindDependencyDecisionByAward() {

        RbpsRepository repository = (RbpsRepository) getBean( "repository" );
        repository.setVeteran( CommonFactory.georgeVeteran() );
        repository.getVeteran().setCorpParticipantId( 32221287 );

        FindDependencyDecisionByAwardResponse response = wsHandler.call( repository );

        System.out.println( "decisions: " + CommonUtils.stringBuilder( response.getReturn().getDependencyDecision().getDependencyDecision() ) );
    }
}
