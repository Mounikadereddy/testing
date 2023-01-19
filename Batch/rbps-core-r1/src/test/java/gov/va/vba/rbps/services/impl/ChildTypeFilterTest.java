/*
 * ChildTypeFilterTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


//import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.QuietTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
//import gov.va.vba.rbps.coreframework.util.CommonUtils;
//import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
//import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


public class ChildTypeFilterTest {

//    private static Logger logger = Logger.getLogger(ChildTypeFilterTest.class);

//    private CommonUtils     utils       = new CommonUtils();
//    private LogUtils        logUtils    = new LogUtils( logger, true );


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
        ChildTypeFilter                 childFilter = new ChildTypeFilter();

//        testUtils.populateUserInfo( repo, xmlPath );

        for ( Child child : repo.getVeteran().getChildren() ) {

            childFilter.filter( repo, child );
        }
    }


    @Ignore
    @Test
    public void shouldNotFilterChildWithGoodChildType() {

        TestUtils                       testUtils   = new TestUtils();
        String                          xmlPath     = "gov/va/vba/rbps/services/impl/userInfo.biological.response.xml";
        RbpsRepository                  repo        = new RbpsRepository();
        ChildTypeFilter                 childFilter = new ChildTypeFilter();

//        testUtils.populateUserInfo( repo, xmlPath );

        for ( Child child : repo.getVeteran().getChildren() ) {

            childFilter.filter( repo, child );
        }
    }

    @Ignore
    @Test
    public void shouldFilterChildWithGoodChildType() {

        TestUtils                       testUtils   = new TestUtils();
        String                          xmlPath     = "gov/va/vba/rbps/services/impl/userInfo.response.xml";
        RbpsRepository                  repo        = new RbpsRepository();
        ChildTypeFilter                 childFilter = new ChildTypeFilter();

//        testUtils.populateUserInfo( repo, xmlPath );

        for ( Child child : repo.getVeteran().getChildren() ) {

            childFilter.filter( repo, child );
        }
    }

    
    @Test
    public void shouldFilterChildWithGoodChildType1() {

        TestUtils                       testUtils   = new TestUtils();
        String                          xmlPath     = "gov/va/vba/rbps/services/impl/userInfo1.response.xml";
        RbpsRepository                  repo        = new RbpsRepository();
        ChildTypeFilter                 childFilter = new ChildTypeFilter();

//        testUtils.populateUserInfo( repo, xmlPath );

        for ( Child child : repo.getVeteran().getChildren() ) {

            childFilter.filter( repo, child );
        }
    }

    
    
    
    @Ignore
    @Test
    public void shouldNotFilterChildAlreadyOnAward() {

        TestUtils                       testUtils       = new TestUtils();
        String                          xmlPath         = "gov/va/vba/rbps/services/impl/userInfo.jeffRamos.alreadyOnAward.response.xml";
        String                          dependentsPath  = "gov/va/vba/rbps/services/impl/findDependents.jeffRamos.response.xml";
        RbpsRepository                  repo            = new RbpsRepository();
        ChildTypeFilter                 childFilter     = new ChildTypeFilter();

//        testUtils.populateUserInfo( repo, xmlPath );
        testUtils.populateCorporateDependents( repo, dependentsPath );

//        Child jeffRamos = repo.getVeteran().getChildren().get( 0 );
//        jeffRamos.setOnCurrentAward( true );

//        logUtils.log( "jeff ramos: " + new IndentedXomToString().toString( jeffRamos, 0 ) );

        for ( Child child : repo.getVeteran().getChildren() ) {

            childFilter.filter( repo, child );
        }
    }
}
