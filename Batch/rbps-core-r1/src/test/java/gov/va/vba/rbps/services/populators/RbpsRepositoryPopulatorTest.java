/*
 * RbpsRepositoryPopulatorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.IndentedXomToString;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.WSOutputXMLReader;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSuppliedResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.UserInformation;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.CollectionUtils;


/**
 *      Test case of RbpsRepositoryPopulator
 */
public class RbpsRepositoryPopulatorTest {

    private boolean                     logit       = false;
    private RbpsRepository              repo;
    private RbpsRepositoryPopulator     rbpsRepositoryPopulator;


    @Before
    public void setup() {

        LogUtils.setGlobalLogit( logit );
        ToStringBuilder.setDefaultStyle( RbpsConstants.RBPS_TO_STRING_STYLE );

        repo                    = new RbpsRepository();
        rbpsRepositoryPopulator = new RbpsRepositoryPopulator();
//        rbpsRepositoryPopulator.setRepository( repo );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void testPopulateFromVonapp() {

        UserInformation userInformation = getUserInfo( WSOutputXMLReader.readUserInformationResponse() );
//        rbpsRepositoryPopulator.populateFromVonapp(userInformation);

        if ( ! CollectionUtils.isEmpty(userInformation.getPersonData())) {

            assertEquals(repo.getVeteran().getFileNumber(), userInformation.getPersonData().get(0).getFileNbr());
        }

        if (userInformation.getProcessData() != null) {

            assertEquals(repo.getVnpProcId(), Long.valueOf(userInformation.getProcessData().getVnpProcId()));
        }

//      if ( ! CollectionUtils.isEmpty(userInformation.getBnftClaim()) && repo.getVeteran().getClaim() != null)
//          assertEquals(repo.getVeteran().getClaim().hasAttachments(), userInformation.getBnftClaim().get(0).getAtchmsInd().equalsIgnoreCase(RbpsConstants.INDICATOR_Y));

        if ( ! CollectionUtils.isEmpty(repo.getVeteran().getChildren()) && !CollectionUtils.isEmpty(userInformation.getChildSchool())) {

            assertEquals(repo.getVeteran().getChildren().get(0).getCurrentTerm().getCourseName(), userInformation.getChildSchool().get(0).getCourseNameTxt());
            assertEquals(repo.getVeteran().getChildren().get(0).getPreviousTerms().get(0).getHoursPerWeek(), userInformation.getChildSchool().get(0).getPrevHoursPerWkNum().intValue());
        }

        assertEquals(repo.getVeteran().getFirstName(), "vlad");
        assertEquals(repo.getVeteran().getLastName(), "Muntyan");
        assertEquals(repo.getVeteran().getCorpParticipantId(), 30837971);
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void testPopulateFromVonappWith674DoesHaveCurrentTerm() {

        UserInformation userInformation = getUserInfo( WSOutputXMLReader.readUserInformationResponseWith674() );
//        rbpsRepositoryPopulator.populateFromVonapp(userInformation);

        logVet();

        assertThat( repo.getVeteran().getChildren().get(0).getCurrentTerm(), is(notNullValue()) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void testPopulateFromVonappWith674DoesHaveTuitionPaidByGovt() {

        UserInformation userInformation = getUserInfo( WSOutputXMLReader.readUserInformationResponseWith674() );
//        rbpsRepositoryPopulator.populateFromVonapp(userInformation);

        logVet();

        assertThat( repo.getVeteran().getChildren().get(0).isTuitionPaidByGovt(), is(true) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void testPopulateFromVonappWith674WhenDatesDontMatchDoesntHaveTuitionPaidByGovt() {

        UserInformation userInformation = getUserInfo( WSOutputXMLReader.readUserInformationResponseWith674DatesDontMatch() );
//        rbpsRepositoryPopulator.populateFromVonapp(userInformation);

        logVet();

        assertThat( repo.getVeteran().getChildren().get(0).isTuitionPaidByGovt(), is(false) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void testPopulateFromVonappWith674WhenDatesDontMatchDoesNotSupplyCurrentTerm() {

        UserInformation userInformation = getUserInfo( WSOutputXMLReader.readUserInformationResponseWith674DatesDontMatch() );
//        rbpsRepositoryPopulator.populateFromVonapp(userInformation);

        logVet();

        assertThat( repo.getVeteran().getChildren().get(0).getCurrentTerm(), is(nullValue()) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldProcessFirstWorkingVdcClaim() {

        String          userInfoPath    = "gov/va/vba/rbps/services/populators/796083300.userInfo.response.xml";
        TestUtils       testUtils       = new TestUtils();

        //testUtils.populateUserInfo( repo, userInfoPath );
        logVet();
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldPrintNicely() {

        String          userInfoPath    = "gov/va/vba/rbps/services/populators/112121125.userinfo.response.xml";
        TestUtils       testUtils       = new TestUtils();

        //testUtils.populateUserInfo( repo, userInfoPath );
        logVet();
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldPrintNicely2() {

        String          userInfoPath    = "gov/va/vba/rbps/services/populators/999507853.userinfo.xml";
        TestUtils       testUtils       = new TestUtils();

        //testUtils.populateUserInfo( repo, userInfoPath );
        logVet();
    }


    @Test
    public void shouldSetSchoolChildIfIndBlank() {


    }


    public void logVet() {

        if ( ! logit ) {

            return;
        }

        System.out.println( new IndentedXomToString().toString( repo.getVeteran(), 0 ) );
    }


    private UserInformation getUserInfo( final FindByDataSuppliedResponse response ) {

        return response.getReturn().getUserInformation().get(0);
    }
}
