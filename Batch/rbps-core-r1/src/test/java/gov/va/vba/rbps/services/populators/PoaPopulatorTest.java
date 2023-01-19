/*
 * PoaPopulatorTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.fixtures.CoreFactory;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbers;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbersResponse;
import gov.va.vba.rbps.services.ws.client.mapping.org.PoaSearchResult;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindPOAResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.ShrinqfPersonOrg;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class PoaPopulatorTest {

    private static Logger logger = Logger.getLogger(PoaPopulatorTest.class);

    private boolean                 logit           = false;
    private LogUtils                logUtils        = new LogUtils( logger, logit );
    private TestUtils               testUtils       = new TestUtils();
    RbpsRepository      			repo;


    @Before
    public void setup() {

        LogUtils.setGlobalLogit( logit );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldSetPoaOrganizationName() {

        String              xmlPath         =   "gov/va/vba/rbps/services/populators/findPOAsByFileNumbersResponse.xml";
        FindPOAsByFileNumbersResponse response        =   testUtils.getPoaResponseFromXml( xmlPath );
        RbpsRepository      repo            =   new RbpsRepository();
        PoaPopulator        poaPopulator    =   new PoaPopulator();

        repo.setVeteran( CoreFactory.adamsVeteran() );
        repo.getVeteran().setHasPOA( false );


        //      When we populate the poa.
        poaPopulator.populatePoa( repo, response );

//        logUtils.log( "PoaOrganizationName " + repo.getPoaOrganizationName() );
//        logUtils.log( "PoaOrganizationCode " + response.getReturn().getPersonOrgName() );

        //      Then - the poa org name in the repo is the same one that we set up in the poa response.
        assertThat( "the poa org name in the repo is the same one that we set up in the poa response",
                    repo.getPoaOrganizationName(),
                    is(equalTo( response.getReturn().get(0).getPowerOfAttorney().getNm() ) ) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Ignore
    @Test
    public void shouldNotBeVeteranHasPoa() {

        FindPOAsByFileNumbersResponse  response  =   CoreFactory.createPoaResponseWithoutPoa();
        RbpsRepository      repo            =   new RbpsRepository();
        PoaPopulator        poaPopulator    =   new PoaPopulator();

        repo.setVeteran( CoreFactory.adamsVeteran() );
        repo.getVeteran().setHasPOA( false );


        //      When we populate the poa.
        poaPopulator.populatePoa( repo, response );

        //      Then the veteran doesn't have a poa
        assertThat( "the veteran does not have a poa",
                    repo.getVeteran().hasPOA(),
                    is(equalTo( false )) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldBeVeteranHasPoa() {

        FindPOAsByFileNumbersResponse     response        =   CoreFactory.createPoaResponseWithPoa();
        RbpsRepository      repo            =   new RbpsRepository();
        PoaPopulator        poaPopulator    =   new PoaPopulator();

        repo.setVeteran( CoreFactory.adamsVeteran() );
        repo.getVeteran().setHasPOA( false );

        poaPopulator.populatePoa( repo, response );

        assertThat( "the veteran has a poa",
                    repo.getVeteran().hasPOA(),
                    is(equalTo( true )) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldNotBePoaOrganization() {

        PoaPopulator        poaPopulator    =   new PoaPopulator();
        PoaSearchResult poaInfo         =   CoreFactory.createPoaResponseWithoutPoa().getReturn().get(0);
        boolean             result          =   poaPopulator.isPoaOrganization( poaInfo, repo );

        assertThat( "it is not a poa organization",
                    result,
                    is(equalTo( false )) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldBePoaOrganization() {

        PoaPopulator        poaPopulator    =   new PoaPopulator();
        PoaSearchResult poaInfo         =   CoreFactory.createPoaResponseWithPoa().getReturn().get(0);
        boolean             result          =   poaPopulator.isPoaOrganization( poaInfo, repo );

        assertThat( "it is a poa organization",
                    result,
                    is(equalTo( true )) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldNotPersonOrgNameContainsHyphen() {

        PoaPopulator poaPopulator   =   new PoaPopulator();

        String nameToFix    =   "abc - bcd";
        String result       =   poaPopulator.fixPersonOrgName( nameToFix );

        assertThat( "the poa organization name should not contain a hyphen '-'",
                    result,
                    not(containsString( "-" )) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldNotPersonOrgNameContainsNumber() {

        PoaPopulator poaPopulator   =   new PoaPopulator();

        String nameToFix    =   "087 - bcd";
        String result       =   poaPopulator.fixPersonOrgName( nameToFix );

        assertThat( "the poa name should not contain the poa id",
                    result,
                    not(containsString( "087" )) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldNotPersonOrgNameStartWithSpaces() {

        PoaPopulator poaPopulator   =   new PoaPopulator();

        String nameToFix    =   "087 - bcd";
        String result       =   poaPopulator.fixPersonOrgName( nameToFix );

        assertThat( "the poa name should not start with spaces",
                    result,
                    not(containsString( " bcd" )) );
    }
}
