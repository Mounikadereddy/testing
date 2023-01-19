/*
 * DependentOnAwardPopulatorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.fixtures.CoreFactory;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAwardResponse;

import org.junit.Test;


/**
 * Test case for the <code>DependentOnAwardPopulatorTest</code> class
 */
public class DependentOnAwardPopulatorTest {


    /**
     * Make sure that the right number of corporate kids are produced
     * for a given input.
     */

//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldSetOncurrentAward() {

        Boolean result = runPopulatorForChild( true );
        assertThat( result, is(equalTo(true)) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldSetNotOncurrentAward() {

        Boolean result = runPopulatorForChild( false );
        assertThat( result, is(equalTo(false)) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldSetNullOncurrentAward() {

        Boolean result = runPopulatorForChild( null );
        assertThat( result, is(equalTo(false)) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldSetSpouseNotOncurrentAward() {

        Boolean result = runPopulatorForSpouse( false );
        assertThat( result, is(equalTo(false)) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldSetSpouseNullOncurrentAward() {

        Boolean result = runPopulatorForSpouse( null );
        assertThat( result, is(equalTo(false)) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldSetSpouseOncurrentAward() {

        Boolean result = runPopulatorForSpouse( true );
        assertThat( result, is(equalTo(true)) );
    }


    private Boolean runPopulatorForSpouse( final Boolean  dependentOnAward ) {

        RbpsRepository                  repository          = new RbpsRepository();
        Veteran                         veteran             = CommonFactory.adamsVeteran();
        Dependent                       spouse              = veteran.getCurrentMarriage().getMarriedTo();

        return runPopulatorForDependent( dependentOnAward,
                                         repository,
                                         veteran,
                                         spouse );
    }


    private Boolean runPopulatorForChild( final Boolean  dependentOnAward ) {

        RbpsRepository                  repository          = new RbpsRepository();
        Veteran                         veteran             = CommonFactory.georgeVeteran();
        Dependent                       child               = veteran.getChildren().get(0);

        return runPopulatorForDependent( dependentOnAward,
                                         repository,
                                         veteran,
                                         child );
    }


    private Boolean runPopulatorForDependent( final Boolean     dependentOnAward,
                                              final RbpsRepository    repository,
                                              final Veteran           veteran,
                                              final Dependent         dependent ) {

        repository.setVeteran( veteran );

        DependentOnAwardPopulator       populator           = new DependentOnAwardPopulator();
        FindDependentOnAwardResponse    response            = CoreFactory.createFindDependentOnAwardResponse( veteran,
                                                                                                          dependent,
                                                                                                          dependentOnAward );

        CorporateDependent  newDependent = new CorporateDependent();
        newDependent.setParticipantId( dependent.getCorpParticipantId() );
        newDependent.setFirstName( dependent.getFirstName() );
        newDependent.setBirthDate( dependent.getBirthDate() );
        newDependent.setLastName( dependent.getLastName() );
        newDependent.setSocialSecurityNumber( dependent.getSsn() );

        repository.addChild( newDependent );

//        System.out.println( "Dependent to find on award: " + new CommonUtils().stringBuilder( newDependent ) );

//        populator.setRepository( repository );
        populator.populateFromDependentOnAward( repository, response );

//        System.out.println( "in response: " + new CommonUtils().convertBoolean( response.getReturn().getIsDependentOnAward() ) );
//        System.out.println( "Dependent to find on award: " + new CommonUtils().stringBuilder( newDependent ) );

        return newDependent.isOnAward();
    }
}
