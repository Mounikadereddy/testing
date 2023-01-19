/*
 * VnpProcUpdateWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;


import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *      Test case of VnpProcUpdateWSHandler class
 */
public class VnpProcUpdateWSHandlerTest extends RbpsAbstractTest {

    private VnpProcUpdateWSHandler vnpProcUpdateWSHandler;

    @Override
    @Before
    public void setup() {

        super.setup();
        vnpProcUpdateWSHandler = (VnpProcUpdateWSHandler) getBean( "vnpProcUpdateWSHandler" );
    }


    @Override
    @After
    public void tearDown() throws Exception {

        vnpProcUpdateWSHandler = null;
        super.tearDown();
    }


    /**
     * Testcase for vnpProcUpdate method
     */
    @Test
    public void testGetUserInformation() {

            System.out.println( "procId returned from vnpProcUpdate Web Service : " + vnpProcUpdateWSHandler.vnpProcUpdate( new RbpsRepository() ).getReturn().getVnpProcId());
            System.out.println( "status returned from vnpProcUpdate Web Service : " + vnpProcUpdateWSHandler.vnpProcUpdate( new RbpsRepository() ).getReturn().getVnpProcStateTypeCd() );
            System.out.println( "last modified date returned from vnpProcUpdate Web Service : " + vnpProcUpdateWSHandler.vnpProcUpdate( new RbpsRepository() ).getReturn().getLastModifdDt() );
    }
}
