/*
 * RbpsServiceFacadeTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.impl;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


/**
 *      RBPS Controller test case
 */
public class RbpsServiceFacadeTest extends RbpsAbstractTest {

    private RbpsServiceFacade  rbpsService;


    @Override
    @Before
    public void setup() {
        super.setup();

        rbpsService       = (RbpsServiceFacade) getBean("rbpsServiceFacade");
    }


    @Test
    public void testServiceFacade() {

        RbpsRepository              repo        = new RbpsRepository();
//        utils.setCurrentProcess("1");
        rbpsService.executeAll("1", "5", repo);
    }
}
