/*
 * UpdateBenefitClaimDependentsPopulatorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.CorporateDependentId;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimDependents;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;




public class UpdateBenefitClaimDependentsPopulatorTest {

    private static Logger logger = Logger.getLogger(UpdateBenefitClaimDependentsPopulatorTest.class);

    private boolean                                     logit               = false;
    private LogUtils                                    logUtils            = new LogUtils( logger, logit );
    private TestUtils       							testUtils   = new TestUtils();
    private UpdateBenefitClaimDependentsPopulator       hydrator            = new UpdateBenefitClaimDependentsPopulator();
    private RbpsRepository                              repository          = new RbpsRepository();
    private Veteran                                     veteran;
    private UpdateBenefitClaimDependents                request;
    private int                                         numKidsToCreate     = 6;
    private int                                         numKidsToSend       = 3;


    @Before
    public void setup() {

        LogUtils.setGlobalLogit( logit );
        ToStringBuilder.setDefaultStyle( RbpsConstants.RBPS_TO_STRING_STYLE );

        hydrator    = new UpdateBenefitClaimDependentsPopulator();
        veteran     = CommonFactory.adamsVeteran();

        CommonFactory.addChildren( veteran, numKidsToCreate );
        repository.setVeteran( veteran );
        repository.setChildren( CommonFactory.getCorporateChildren( repository,
                                                              veteran.getChildren().subList( 0, numKidsToSend ) ) );

//        CommonFactory.logKids( repository );
    }


    
    @Test
    public void shouldSpouseNullPointer() throws Throwable {

    	RbpsRepository	repo	= new RbpsRepository();
    	testUtils.populateUserInfo( repo, "gov/va/vba/rbps/lettergeneration/spouseNull.userInfo.response.xml" );
        repo.setClaimStationLocationId( 1943L );
        repo.getClaimStationAddress().setStationId("335");
        testUtils.populateSojAddress( repo, 1943L );
        
        request = hydrator.buildAddDependentRequest( repository);
    }
    
    
    
    
    
    @Ignore
    @Test
    public void shouldSetNumDependentsToMatchListSize() throws Throwable {

        hydrate( hydrator, null );
        logUtils.logCorporateChildren( repository );

        assertThat( "" + request.getDependentUpdateInput().getDependents().size(),
                    is(equalTo( request.getDependentUpdateInput().getNumberOfDependents() ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldCreateListOfKidsToAddOfRightSize() throws Throwable {

//        hydrate( hydrator, null );
        logUtils.logCorporateChildren( repository );

        for ( Child child : repository.getVeteran().getChildren() ) {

            child.setAward( null );
            child.setMinorSchoolChildAward( null );
        }

        List<Child> newChildren = hydrator.getListOfChildrenToAdd( repository );

//        Map<Long,CorporateDependent> childMap = hydrator.createCorporateDependentMap( repository );
//        logUtils.log( "child map: " + childMap.keySet() );
//        logUtils.log( "child map size: "      + childMap.size() );
//        logUtils.log( "new children size: "   + newChildren.size() );
//        logUtils.log( "xom children size: "   + repository.getVeteran().getChildren().size() );
//        logUtils.log( "corp children size: "  + repository.getChildren().size() );
//        logUtils.log( "test child participant id: "  + repository.getVeteran().getChildren().get(0).getCorpParticipantId() );
//        logUtils.log( "in child map: "        + hydrator.childNotAlreadyInCorporateList( childMap,
//                                                                                        repository.getVeteran().getChildren().get(0) ) );

        assertThat( newChildren.size(), is(equalTo( repository.getVeteran().getChildren().size()
                                                    - repository.getChildren().size() ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldChildrenShouldBeAdded() throws Throwable {

        logUtils.logCorporateChildren( repository );

        for ( Child child : repository.getVeteran().getChildren() ) {

            child.setAward( null );
            child.setMinorSchoolChildAward( null );
        }

        List<CorporateDependentId> childList = hydrator.createCorporateDependentList( repository );
        List<Child> children = repository.getVeteran().getChildren();

        assertThat( hydrator.childShouldBeAdded( childList,
                                                 children.get( children.size() - 1), repository ),
                    is(equalTo(true)));
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowChildAlreadyInCorporateList() throws Throwable {

        hydrate( hydrator, null );
        logUtils.logCorporateChildren( repository );

        for ( Child child : repository.getVeteran().getChildren() ) {

            Award   award = CommonFactory.generateRandomAward( child );

            award.setEventDate( new Date() );
            child.setAward( award );
        }

//        Map<Long,CorporateDependent> childMap = hydrator.createCorporateDependentMap( repository );
        List<CorporateDependentId> childList = new ArrayList<CorporateDependentId>();
        assertThat( hydrator.childAlreadyInCorporateList( childList,
                                                          repository.getVeteran().getChildren().get(0) ),
                    is(equalTo(false)));
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldSetNumDependentsToMatchListSizeIncludingSpouse() throws Throwable {

        hydrate( hydrator, CommonFactory.getRandomSpouse( veteran ) );
//        logUtils.logCorporateChildren( repository );

        assertThat( "" + request.getDependentUpdateInput().getDependents().size(),
                    is(equalTo( request.getDependentUpdateInput().getNumberOfDependents() ) ) );
    }


    @Test
    @Ignore
    public void shouldSetNumDependentsToKidsAndSpouse() throws Throwable {

        int totalNumDependents = hydrate( hydrator, CommonFactory.getRandomSpouse( veteran ) );
//        logUtils.logCorporateChildren( repository );

//        logUtils.log( "dependents on request: " + request.getDependentUpdateInput().getDependents() );
        assertThat( request.getDependentUpdateInput().getDependents().size(),
                    is(equalTo( totalNumDependents ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldSetNumDependentsToNumKidsToSend() throws Throwable {

        hydrate( hydrator, null );
//        logUtils.logCorporateChildren( repository );

        assertThat( request.getDependentUpdateInput().getDependents().size(),
                    is(equalTo( numKidsToSend ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldOnlyAddChildrenNotAlreadyKnown() throws Throwable {

        hydrate( hydrator, null );
        logUtils.logCorporateChildren( repository );

        assertThat( request.getDependentUpdateInput().getDependents().size(),
                    is(equalTo( numKidsToSend ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldSetFileNumber() throws Throwable {

        hydrate( hydrator, null );
        logUtils.logCorporateChildren( repository );

        assertThat( request.getDependentUpdateInput().getFileNumber(),
                    is(equalTo( veteran.getFileNumber() ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldSetParticipantId() throws Throwable {

        hydrate( hydrator, null );
        logUtils.logCorporateChildren( repository );

        assertThat( request.getDependentUpdateInput().getPtcpntIdVet(),
                    is(equalTo( "" + veteran.getCorpParticipantId() ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldSetPayeeCode() throws Throwable {

        hydrate( hydrator, null );
        logUtils.logCorporateChildren( repository );

        assertThat( request.getDependentUpdateInput().getPayeeCode(),
                    is(equalTo( RbpsConstants.PAYEE_CODE ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldSetPayeeParticipantId() throws Throwable {

        hydrate( hydrator, null );
        logUtils.logCorporateChildren( repository );

        assertThat( request.getDependentUpdateInput().getPtcpntIdPayee(),
                    is(equalTo( "" + veteran.getCorpParticipantId() ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowSpouseNotInRepository() throws Throwable {

        Spouse  spouse   =   CommonFactory.getRandomSpouse( repository.getVeteran() );

        repository.setSpouse( null );
//        hydrator.setRepository( repository );

        assertThat( hydrator.spouseInRepository(spouse, repository),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowSpouseWithDifferentIdsNotInRepository() throws Throwable {

        Spouse              spouse      =   CommonFactory.getRandomSpouse( repository.getVeteran() );
        CorporateDependent  corpSpouse  = new CorporateDependent();

        corpSpouse.setParticipantId( spouse.getCorpParticipantId() + 1 );
        repository.setSpouse( corpSpouse );
//        hydrator.setRepository( repository );

        assertThat( hydrator.spouseInRepository(spouse, repository),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowSpouseWithSameIdsNotInRepository() throws Throwable {

        Spouse              spouse      =   CommonFactory.getRandomSpouse( repository.getVeteran() );
        CorporateDependent  corpSpouse  = new CorporateDependent();

        corpSpouse.setParticipantId( spouse.getCorpParticipantId() );
        corpSpouse.setFirstName( spouse.getFirstName() );
        corpSpouse.setBirthDate( spouse.getBirthDate() );
        corpSpouse.setLastName( spouse.getLastName() );
        corpSpouse.setSocialSecurityNumber( spouse.getSsn() );

        repository.setSpouse( corpSpouse );
//        hydrator.setRepository( repository );

        assertThat( hydrator.spouseInRepository(spouse, repository),
                    is(equalTo( true ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowSpouseNotOnAward() throws Throwable {

        Spouse  spouse   =   CommonFactory.getRandomSpouse( repository.getVeteran() );

        spouse.setAward( null );
        repository.setSpouse( null );
//        hydrator.setRepository( repository );

        assertThat( hydrator.spouseOnAward(spouse, repository),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowSpouseOnAward() throws Throwable {

        Spouse  spouse   = CommonFactory.getRandomSpouse( repository.getVeteran() );
        Award   award    = CommonFactory.generateRandomAward( spouse );

        award.setEventDate( new Date() );
        spouse.setAward( award );
//        hydrator.setRepository( repository );

        assertThat( hydrator.spouseOnAward(spouse, repository),
                    is(equalTo( true ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowSpouseNotOnAwardIfNoEventDate() throws Throwable {

        Spouse  spouse   = CommonFactory.getRandomSpouse( repository.getVeteran() );
        Award   award    = CommonFactory.generateRandomAward( spouse );

        award.setEventDate( null );
        spouse.setAward( award );
//        hydrator.setRepository( repository );

        assertThat( hydrator.spouseOnAward(spouse, repository),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowChildNotOnAwardIfNoEventDate() throws Throwable {

        CommonFactory.addChildren( repository.getVeteran() );

        Child   child = repository.getVeteran().getChildren().get(0);
        Award   award   = CommonFactory.generateRandomAward( child );

        award.setEventDate( null );
        child.setAward( award );
        child.setMinorSchoolChildAward( null );
//        hydrator.setRepository( repository );

        assertThat( hydrator.childOnAward( child ),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowChildNotOnAwardIfNoMinorSchoolChildEventDate() throws Throwable {

        CommonFactory.addChildren( repository.getVeteran() );

        Child   child = repository.getVeteran().getChildren().get(0);
        Award   award   = CommonFactory.generateRandomAward( child );

        award.setEventDate( null );
        child.setAward( null );
        child.setMinorSchoolChildAward( award );
//        hydrator.setRepository( repository );

//        logUtils.log( "child award: " + ToStringBuilder.reflectionToString( child.getAward() ) );
//        logUtils.log( "child minor school award: " + ToStringBuilder.reflectionToString( child.getMinorSchoolChildAward() ) );

        assertThat( hydrator.childOnAward( child ),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowAwardNotPresentIfNoMinorSchoolChildEventDate() throws Throwable {

        CommonFactory.addChildren( repository.getVeteran() );

        Child   child = repository.getVeteran().getChildren().get(0);
        Award   award   = CommonFactory.generateRandomAward( child );

        award.setEventDate( null );
        child.setMinorSchoolChildAward( award );
//        hydrator.setRepository( repository );

//        logUtils.log( "child minor school award: " + ToStringBuilder.reflectionToString( child.getMinorSchoolChildAward() ) );

        assertThat( hydrator.awardPresent( child.getMinorSchoolChildAward() ),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowAwardNotPresentIfNoEventDate() throws Throwable {

        CommonFactory.addChildren( repository.getVeteran() );

        Child   child = repository.getVeteran().getChildren().get(0);
        Award   award   = CommonFactory.generateRandomAward( child );

        award.setEventDate( null );
        child.setAward( award );
//        hydrator.setRepository( repository );

//        logUtils.log( "child minor school award: " + ToStringBuilder.reflectionToString( child.getMinorSchoolChildAward() ) );

        assertThat( hydrator.awardPresent( child.getAward() ),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowChildNotOnAwardIfBothAwardsHaveNoEventDate() throws Throwable {

        CommonFactory.addChildren( repository.getVeteran() );

        Child   child = repository.getVeteran().getChildren().get(0);
        Award   award   = CommonFactory.generateRandomAward( child );

        award.setEventDate( null );
        child.setAward( award );
        child.setMinorSchoolChildAward( award );
//        hydrator.setRepository( repository );

        assertThat( hydrator.childOnAward( child ),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowChildNotOnAwardIfNoAwardAtAll() throws Throwable {

        CommonFactory.addChildren( repository.getVeteran() );

        Child   child = repository.getVeteran().getChildren().get(0);

        child.setAward( null );
        child.setMinorSchoolChildAward( null );
//        hydrator.setRepository( repository );

        assertThat( hydrator.childOnAward( child ),
                    is(equalTo( false ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowChildOnAwardIfHasAward() throws Throwable {

        CommonFactory.addChildren( repository.getVeteran() );
        Child   child = repository.getVeteran().getChildren().get(0);
        Award   award   = CommonFactory.generateRandomAward( child );

        award.setEventDate( new Date() );
        child.setAward( award );
//        hydrator.setRepository( repository );

        assertThat( hydrator.childOnAward( child ),
                    is(equalTo( true ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldShowChildOnAwardIfHasMinorSchoolChildAward() throws Throwable {

        CommonFactory.addChildren( repository.getVeteran() );
        Child   child = repository.getVeteran().getChildren().get(0);
        Award   award   = CommonFactory.generateRandomAward( child );

        award.setEventDate( new Date() );
        child.setMinorSchoolChildAward( award );
//        hydrator.setRepository( repository );

        assertThat( hydrator.childOnAward( child ),
                    is(equalTo( true ) ) );
    }


//    @RunTags(tags={ "Populator", "Release"} )
    @Test
    @Ignore
    public void shouldAddSallyRiley() {

        String              userInfoPath    = "gov/va/vba/rbps/services/populators/796027583.userInfo.response.xml";
        String              dependentsPath  = "gov/va/vba/rbps/services/populators/796027583.findDependents.response.xml";

        TestUtils       testUtils   = new TestUtils();
        RbpsRepository  repo        = new RbpsRepository();

//        testUtils.populateUserInfo( repo, userInfoPath );
        testUtils.populateCorporateDependents( repo, dependentsPath );

        Award   award = new Award();
        repo.getVeteran().getChildren().get( 0 ).setAward( award );
        award.setEventDate( new Date() );

//        CommonUtils utils = new CommonUtils();
//        logUtils.log( "AWARD: " + utils.stringBuilder( repo.getVeteran().getChildren().get(0).getAward() ) );

        request = hydrator.buildAddDependentRequest( repo );
    }





    public int hydrate( final UpdateBenefitClaimDependentsPopulator hydrator,
                         final Spouse                               spouse ) {

        for ( Child child : repository.getVeteran().getChildren() ) {

            child.setAward( null );
            child.setMinorSchoolChildAward( null );
        }

        if ( spouse != null ) {

            spouse.setAward( null );
            repository.setSpouse( null );
        }

        request = hydrator.buildAddDependentRequest( repository,
                                                     veteran.getChildren().subList( 0, numKidsToSend ), spouse );

        int numDependents = numKidsToCreate - numKidsToSend;
        if ( spouse != null ) {

            numDependents++;
        }

        return numDependents;
    }
}
