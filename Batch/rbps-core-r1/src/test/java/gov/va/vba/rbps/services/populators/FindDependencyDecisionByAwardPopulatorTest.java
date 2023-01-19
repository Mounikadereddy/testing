/*
 * CorporateDependentsPopulatorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 *      Test case for the <code>CorporateDependentsPopulator</code> class
 */
public class FindDependencyDecisionByAwardPopulatorTest extends RbpsAbstractTest {

    private static Logger logger = Logger.getLogger(FindDependencyDecisionByAwardPopulatorTest.class);


    private boolean         logit       = false;
    private LogUtils        logUtils    = new LogUtils( logger, logit );
    private TestUtils       testUtils   = new TestUtils();
    private RbpsRepository  repository;



    @Override
    @Before
    public void setup() {

        super.setup();
        LogUtils.setGlobalLogit( logit );
        repository      = ( RbpsRepository ) getBean( "repository" );

    }

//  @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldAddFromXmlResponseFOrDeniedFalse() {

        boolean         deniedAward     = false;
        TestUtils       testUtils       = new TestUtils();
        String          xmlPath         = "gov/va/vba/rbps/services/populators/999807588/dependencyDecision.response.xml";

        testUtils.populateViaClaimResponses( getContext(), repository, "999807588" );

        FindDependencyDecisionByAwardResponse       response    = testUtils.getDependencyDecisionResponseFromXml( xmlPath );
        FindDependencyDecisionByAwardPopulator      populator   = new FindDependencyDecisionByAwardPopulator();

        List <Dependent>    xomDependents       =  buildDependentList( repository );

        xomDependents.get(0).setOnCurrentAward( true );
        xomDependents.get(3).setOnCurrentAward( true );
        xomDependents.get(0).setCorpParticipantId( 9100778099L );
        xomDependents.get(1).setCorpParticipantId( 9100780393L );
        xomDependents.get(2).setCorpParticipantId( 9100780395L );
        xomDependents.get(3).setCorpParticipantId( 9100778098L );

        for ( Dependent dep : xomDependents ) {

            populator.populateDependencyDecisionForDependent( response, dep, repository );
            if ( dep.isDeniedAward() ) {

                deniedAward = true;
            }
        }

        assertThat( deniedAward, is(equalTo( false ) ) );

    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldAddFromXmlResponseDeniedTrue() {

        String          xmlPath         = "gov/va/vba/rbps/services/populators/522312312.melissa.findDependencyDecision.notInFuture.response.xml";
        String          userInfoPath    = "gov/va/vba/rbps/services/populators/522312312.userInfo.response.xml";

        FindDependencyDecisionByAwardResponse       response    = testUtils.getDependencyDecisionResponseFromXml( xmlPath );
        FindDependencyDecisionByAwardPopulator      populator   = new FindDependencyDecisionByAwardPopulator();
        RbpsRepository                              repository  = new RbpsRepository();

        //testUtils.populateUserInfo( repository, userInfoPath );

        repository.getVeteran().getChildren().get( 0 ).setCorpParticipantId( 13048173L );

        for ( Dependent child : repository.getVeteran().getChildren() ) {

//            logUtils.log( "Populating child to see if it's denied: " + new IndentedXomToString().toString( child, 0 ) );
            populator.populateDependencyDecisionForDependent( response, child, repository );
            assertThat( child.isDeniedAward(), is(equalTo( true ) ) );
        }
    }



    @Test
    public void shouldDetectDepRemovalInFuture() {

        String          xmlPath         = "gov/va/vba/rbps/services/populators/522312312.melissa.findDependencyDecision.response.xml";
        String          userInfoPath    = "gov/va/vba/rbps/services/populators/522312312.userInfo.response.xml";

        FindDependencyDecisionByAwardResponse       response    = testUtils.getDependencyDecisionResponseFromXml( xmlPath );
        FindDependencyDecisionByAwardPopulator      populator   = new FindDependencyDecisionByAwardPopulator();
        RbpsRepository                              repository  = new RbpsRepository();

        //testUtils.populateUserInfo( repository, userInfoPath );
//
        repository.getVeteran().getChildren().get( 0 ).setCorpParticipantId( 13048173L );

        for ( Dependent child : repository.getVeteran().getChildren() ) {

//            logUtils.log( "Populating child to see if it's denied: " + new IndentedXomToString().toString( child, 0 ) );
            populator.populateDependencyDecisionForDependent( response, child, repository );
            assertThat( child.isDeniedAward(), is(equalTo( false ) ) );
        }
    }


    private List <Dependent> buildDependentList( final RbpsRepository  repository ) {

        List <Dependent>    xomDependents       = new ArrayList<Dependent>();
        NullSafeGetter      grabber             = new NullSafeGetter();
        Spouse              spouse              = (Spouse) grabber.getAttribute( repository, "veteran.currentMarriage.marriedTo" );

        xomDependents.addAll( repository.getVeteran().getChildren() );

        if ( spouse != null ) {

            xomDependents.add( spouse );
        }

        return xomDependents;
    }
}
