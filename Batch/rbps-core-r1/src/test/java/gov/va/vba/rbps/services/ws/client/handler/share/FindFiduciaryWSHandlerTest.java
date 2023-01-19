/*
 * FindFiduciaryWSHandlerTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.populators.FiduciaryPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFiduciaryResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Test;


public class FindFiduciaryWSHandlerTest  extends RbpsAbstractTest {

    private FindFiduciaryWSHandler          findFiduciaryWSHandler;
    private RbpsRepository                  repository;


    @Override
    @Before
    public void setup() {

        super.setup();
        findFiduciaryWSHandler = (FindFiduciaryWSHandler) getBean( "findFiduciaryWSHandler" );

        repository = (RbpsRepository) getBean( "repository" );
        repository.setVeteran( CommonFactory.georgeVeteran() );
        repository.getVeteran().setFileNumber( "888010006" );
    }


    /**
     * Test case for createNote method
     */
    @Test
    public void testcreateFiduciary() {

        String      xmlPath     = "gov/va/vba/rbps/services/populators/findFiduciary.response.xml";
        TestUtils   testUtils   = new TestUtils();

        FindFiduciaryResponse response = testUtils.getFindFiduciaryResponseFromXml( xmlPath );

        if ( response.getReturn() == null ) {

            System.out.println( "Fiduciary came back empty." );
            throw new IllegalArgumentException( "Fiduciary came back empty." );
        }

        System.out.println( "Response: Fiduciary Services = " + response.getReturn().getPersonOrganizationName());
        System.out.println( "Response: Fiduciary name = " + response.getReturn().getPersonOrgName());
        System.out.println( "Response: Fiduciary PrepositionalPhrase = " + response.getReturn().getPrepositionalPhraseName());
        System.out.println( "Response: Fiduciary AttentionText = " + response.getReturn().getPersonOrgAttentionText());
        System.out.println( "");

        FiduciaryPopulator    populator = (FiduciaryPopulator) getBean( "fiduciaryPopulator" );
        populator.populateFiduciary(response, repository);

        System.out.println( "Repository: Fiduciary name = " + repository.getFiduciaryName());
        System.out.println( "Repository: Fiduciary PrepositionalPhrase = " + repository.getFiduciaryPrepositionalPhrase());
        System.out.println( "Response: Fiduciary AttentionText = " + repository.getFiduciaryAttentionText());

    }
}
