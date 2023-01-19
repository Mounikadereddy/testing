/*
 * DecisionDetailsTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;



import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.QuietTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsUtil;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendDependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.List;

import org.junit.Before;
import org.junit.Test;




public class DecisionDetailsTest {

    private static Logger   logger      = Logger.getLogger(DecisionDetailsTest.class);

    private boolean                 logit           = true;
    private CommonUtils             utils;
    private LogUtils                logUtils        = new LogUtils( logger, logit );
    private SimpleDateUtils         dateUtils       = new SimpleDateUtils();
    private CommonFactory           commonFactory   = new CommonFactory();

    @Before
    public void setup() {

        new QuietTest().setup( logit );

        LogUtils.setGlobalLogit( logit );
    }


    @Test
    public void shouldDetectSameTypeAndInDateRange() {

        DecisionDetailsProcessor                processor   = new DecisionDetailsProcessor();
        String                                  xmlPath     = "gov/va/vba/rbps/services/ws/client/handler/awards/util/dependencyDecision.depest.response.xml";
        AmendDependencyDecisionVO               prospect    = createDepestProspect();
        TestUtils                               testUtils   = new TestUtils();
        FindDependencyDecisionByAwardResponse   response    = testUtils.getDependencyDecisionResponseFromXml( xmlPath );
        List<DecisionDetails>                   details     = processor.createDecisionDetailsListForDependent( response.getReturn().getDependencyDecision().getDependencyDecision(), new RbpsRepository() );

//        logUtils.log( "Decision details: " + utils.stringBuilder( details ) );

        for ( DecisionDetails detail : details ) {

            assertThat( detail.isSameTypeAndWithinDateRange( prospect ), is(equalTo(true)));
        }
    }


    public AmendDependencyDecisionVO createDepestProspect() {

        AmendDependencyDecisionVO       prospect    = new AmendDependencyDecisionVO();

        prospect.setBirthdayDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 1970, 05, 12 ) ) );
        prospect.setEventDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 2012, 05, 02 ) ) );
        prospect.setFirstName( "JUNE" );
        prospect.setLastName( "JONES" );
        prospect.setOmnibusFlag( "Y" );
        prospect.setPersonID( 9100779665L );
        prospect.setSocialSecurityNumber( "512323184" );
        prospect.setDependencyDecisionType( "DEPEST" );
        prospect.setDependencyStatusType( "SP" );

        return prospect;
    }


    @Test
    public void shouldDetectSchattbSameTypeAndNotInDateRange_ContinousCoverage() {

        DecisionDetailsProcessor                processor   = new DecisionDetailsProcessor();
        String                                  xmlPath     = "gov/va/vba/rbps/services/ws/client/handler/awards/util/dependencyDecision.schattb.response.xml";
        AmendDependencyDecisionVO               prospect    = createSchattbProspect();
        TestUtils                               testUtils   = new TestUtils();
        FindDependencyDecisionByAwardResponse   response    = testUtils.getDependencyDecisionResponseFromXml( xmlPath );
        List<DecisionDetails>                   details     = processor.createDecisionDetailsListForDependent( response.getReturn().getDependencyDecision().getDependencyDecision(), new RbpsRepository() );

//        logUtils.log( "Decision details: " + utils.stringBuilder( details ) );

        boolean     foundOverlap = false;
        for ( DecisionDetails detail : details ) {

            foundOverlap = foundOverlap || detail.isSameTypeAndWithinDateRange( prospect );
        }

        assertThat( foundOverlap, is(equalTo(false)));
    }


    public AmendDependencyDecisionVO createSchattbProspect() {

        AmendDependencyDecisionVO       prospect    = new AmendDependencyDecisionVO();

        prospect.setBirthdayDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 1993, 04, 15 ) ) );
        prospect.setEventDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 2011, 12, 20 ) ) );
        prospect.setDecisionEndDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 2016, 06, 02 ) ) );
        prospect.setFirstName( "SALLY" );
        prospect.setLastName( "ADAMS" );
        prospect.setOmnibusFlag( "Y" );
        prospect.setPersonID( 9100780270L );
        prospect.setSocialSecurityNumber( "999817610" );
        prospect.setDependencyDecisionType( "SCHATTB" );
        prospect.setDependencyStatusType( "SCHCHD" );

        return prospect;
    }


    @Test
    public void shouldDetectMinorToSchoolSameTypeAndNotInDateRange() {

        DecisionDetailsProcessor                processor   = new DecisionDetailsProcessor();
        String                                  xmlPath     = "gov/va/vba/rbps/services/ws/client/handler/awards/util/dependencyDecision.minorToSchool.response.xml";
        AmendDependencyDecisionVO               prospect    = createSchattbForMinorToSchoolProspect();
        TestUtils                               testUtils   = new TestUtils();
        FindDependencyDecisionByAwardResponse   response    = testUtils.getDependencyDecisionResponseFromXml( xmlPath );
        List<DecisionDetails>                   details     = processor.createDecisionDetailsListForDependent( response.getReturn().getDependencyDecision().getDependencyDecision(), new RbpsRepository() );

//        logUtils.log( "Decision details: " + utils.stringBuilder( details ) );

        boolean     foundOverlap = false;
        for ( DecisionDetails detail : details ) {

            foundOverlap = foundOverlap || detail.isSameTypeAndWithinDateRange( prospect );
        }

        //      No overlap because EMC/SCHATTB aren't the same type.
        assertThat( foundOverlap, is(equalTo(false)));
    }


    public AmendDependencyDecisionVO createSchattbForMinorToSchoolProspect() {

        AmendDependencyDecisionVO       prospect    = new AmendDependencyDecisionVO();

        prospect.setBirthdayDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 1998, 05, 15 ) ) );
        prospect.setEventDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 2016, 05, 15 ) ) );
        prospect.setDecisionEndDate( RbpsUtil.dateToXMLGregorianCalendar( commonFactory.createDay( 2018, 06, 02 ) ) );
        prospect.setFirstName( "JIMMY" );
        prospect.setLastName( "JOHN" );
        prospect.setOmnibusFlag( "Y" );
        prospect.setPersonID( 9100715199L );
        prospect.setSocialSecurityNumber( "451232111" );
        prospect.setDependencyDecisionType( "SCHATTB" );
        prospect.setDependencyStatusType( "SCHCHD" );

        return prospect;
    }
}
