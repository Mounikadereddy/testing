/*
 * VdcClaimFilterTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Test;



public class VdcClaimFilterTest extends RbpsAbstractTest {


    private TestUtils       testUtils = new TestUtils();



    @Override
    @Before
    public void setup() {

        super.setup();

        LogUtils.setGlobalLogit( false );
    }


    @Test
    public void shouldFilterVdcClaim() {

        RbpsRepository              repo        = new RbpsRepository();
        String                      xmlPath     = "gov/va/vba/rbps/services/populators/796083300.userInfo.response.xml";
//        VdcClaimFilter              filter      = new VdcClaimFilter( new CommonUtils(repo) );
//        filter.setShouldPerformFiltering( true );

        //testUtils.populateUserInfo( repo, xmlPath );

//        assertThat( filter.filter( repo ), is(equalTo( true )));
    }


    @Test
    public void shouldNotFilterShareClaim() {

        RbpsRepository              repo        = new RbpsRepository();
        String                      xmlPath     = "gov/va/vba/rbps/services/populators/112121125.userinfo.response.xml";
//        VdcClaimFilter              filter      = new VdcClaimFilter( new CommonUtils(repo));
//        filter.setShouldPerformFiltering( true );

        //testUtils.populateUserInfo( repo, xmlPath );

//        assertThat( filter.filter( repo ), is(equalTo( false )));
    }


    @Test
    public void shouldNotFilterVdcClaimIfPropertyFalse() {

        RbpsRepository              repo        = new RbpsRepository();
        String                      xmlPath     = "gov/va/vba/rbps/services/populators/796083300.userInfo.response.xml";
//        VdcClaimFilter              filter      = new VdcClaimFilter( new CommonUtils(repo) );
//        filter.setShouldPerformFiltering( false );

        //testUtils.populateUserInfo( repo, xmlPath );

//        assertThat( filter.filter( repo ), is(equalTo( false )));
    }


    @Test
    public void shouldFilterWhenFromSpring() {


        RbpsRepository              repo        = new RbpsRepository();
        String                      xmlPath     = "gov/va/vba/rbps/services/populators/796083300.userInfo.response.xml";
//        VdcClaimFilter              filter      = (VdcClaimFilter) getBean( "vdcClaimFilter" );
//
//        //      Want this to be true, even if the properties set it to false.
//        filter.setShouldPerformFiltering( true );
//        //testUtils.populateUserInfo( repo, xmlPath );
//
//        assertThat( filter.filter( repo ), is(equalTo( true )));
    }
}
