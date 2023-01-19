/*
 * FindMailingAddressWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.handler.share;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindMailingAddressResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Test case for FindMailingAddressWSHandler class
 *
 * @author vafscchowdk
 *
 */
public class FindMailingAddressWSHandlerTest extends RbpsAbstractTest {

    private FindMailingAddressWSHandler findMailingAddressWSHandler;
    private RbpsRepository repository;
    @Override
    @Before
    public void setup() {

        super.setup();
        findMailingAddressWSHandler = (FindMailingAddressWSHandler) getBean( "findMailingAddressWSHandler" );

        repository = (RbpsRepository) getBean( "repository" );
        repository.setVeteran( CommonFactory.georgeVeteran() );
        repository.getVeteran().setFileNumber( "263819301" );
        repository.getVeteran().setFileNumber( "196101066" );
    }


    /**
     * Test case for findMailingAddress method
     */
    @Test
    @Ignore
    public void testfindMailingAddress() {

        FindMailingAddressResponse response = findMailingAddressWSHandler.findMailingAddress( 196101066L, repository );
        System.out.println( ToStringBuilder.reflectionToString( response.getReturn() ) );

//      assertEquals("GUIE50004", response.getReturn().get );
//      assertEquals("Benefit Claim not found on Corporate Database.", response.getReturn().getReturnMessage());
    }
}
