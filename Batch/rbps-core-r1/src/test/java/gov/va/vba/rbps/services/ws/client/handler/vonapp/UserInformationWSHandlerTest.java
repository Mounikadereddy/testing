/*
 * UserInformationWSHandlerTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSuppliedResponse;

import org.junit.Before;
import org.junit.Test;


/**
 *      Test case of UserInformationWSHandler
 */
public class UserInformationWSHandlerTest extends RbpsAbstractTest {


    private UserInformationWSHandler userInformationWSHandler;


    @Override
    @Before
    public void setup() {

        super.setup();
        userInformationWSHandler = (UserInformationWSHandler) getBean( "userInformationWSHandler" );
    }


    @Test
    public void testGetFindByDataSuppliedResponse() {

        RbpsRepository              repo        = new RbpsRepository();
//        utils.setCurrentProcess("1");
        
        FindByDataSuppliedResponse  response    = userInformationWSHandler.getFindByDataSuppliedResponse( repo, "1", "5" );

        assertThat( response.getReturn().getUserInformation().get(0).getProcessData().getVnpProcId(),
                    is(equalTo(36731L) ) );
    }
}
