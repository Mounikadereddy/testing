/*
 * RbpsFilterTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import gov.va.vba.rbps.QuietTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class RbpsFilterTest {


    @Before
    public void setup() {

        new QuietTest().setup( true );
    }


    @Ignore
    @Test( expected = RbpsRuntimeException.class )
    public void shouldFilterChildWithBadChildType() {

        TestUtils                       testUtils   = new TestUtils();
        String                          xmlPath     = "gov/va/vba/rbps/services/impl/userInfo.adopted.response.xml";
        RbpsRepository                  repo        = new RbpsRepository();
        RbpsFilter                      filter      = new RbpsFilter();

        //testUtils.populateUserInfo( repo, xmlPath );

        makeEmAllSchoolKids( repo );

        filter.filter( repo );
    }


    @Ignore
    @Test
    public void shouldNotFilterChildWithGoodChildType() {

        TestUtils                       testUtils   = new TestUtils();
        String                          xmlPath     = "gov/va/vba/rbps/services/impl/userInfo.biological.response.xml";
        RbpsRepository                  repo        = new RbpsRepository();
        RbpsFilter                      filter      = new RbpsFilter();

        //testUtils.populateUserInfo( repo, xmlPath );

        makeEmAllSchoolKids( repo );

        filter.filter( repo );
    }


    private void makeEmAllSchoolKids( final RbpsRepository repo ) {

        for ( Child child : repo.getVeteran().getChildren() ) {

            child.setSchoolChild( true );
            child.getForms().add( FormType.FORM_21_674 );
        }
    }
}
