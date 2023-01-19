/*
 * CorporateParticipantIdPopulatorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsServiceException;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.fixtures.CoreFactory;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


/**
 * Test case for the <code>CorporateParticipantIdPopulator</code> class
 *
 * @author vafscchowdk
 *
 */
public class CorporateParticipantIdPopulatorTest extends RbpsAbstractTest {

    private CorporateParticpantIdPopulator      populator;
    private RbpsRepository                      repository;


    @Override
    @Before
    public void setup() {

        super.setup();

        populator = (CorporateParticpantIdPopulator) getBean( "corporateParticpantIdPopulator" );

        repository = (RbpsRepository) getBean( "repository" );
        repository.setVeteran( CommonFactory.adamsVeteran() );
//        repository.getVeteran().setCorpParticipantId( 809 );
//        repository.getVeteran().setFileNumber( "999111230" );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldXomKidsHaveParticipantIdPopulated() {

        repository.setVeteran( CommonFactory.georgeVeteran() );
        CommonFactory.addChildren( repository.getVeteran() );

        Veteran                     veteran     = repository.getVeteran();
        List<Child>                 kids        = veteran.getChildren();
        FindDependentsResponse      response    = CoreFactory.getFindDependentsResponseFromXomKids( kids, null );

        clearParticipantId( veteran );
//        CommonFactory.logKids( repository );

        populator.populateParticipantIdFromDependents( response, repository );

        for ( Child child   : repository.getVeteran().getChildren() ) {

            assertThat( child.getCorpParticipantId(), is( not(equalTo( 0L ) ) ) );
        }
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldXomSpouseHaveParticipantIdPopulated() {

        CommonFactory.addChildren( repository.getVeteran() );

        Veteran                     veteran     = repository.getVeteran();
        List<Child>                 kids        = veteran.getChildren();
        Spouse                      spouse      = repository.getVeteran().getCurrentMarriage().getMarriedTo();
        FindDependentsResponse      response    = CoreFactory.getFindDependentsResponseFromXomKids( kids, spouse );

        clearParticipantId( veteran );
//        CommonFactory.logKids( repository );

        populator.populateParticipantIdFromDependents( response, repository );

        assertThat( spouse.getCorpParticipantId(), is( not(equalTo( 0L ) ) ) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test( expected = RbpsServiceException.class )
    public void shouldThrowExceptionIfSomeDependenetsWithoutId() {

        CommonFactory.addChildren( repository.getVeteran() );

        Veteran                     veteran     = repository.getVeteran();
        List<Child>                 kids        = veteran.getChildren();
        Spouse                      spouse      = repository.getVeteran().getCurrentMarriage().getMarriedTo();
        FindDependentsResponse      response    = CoreFactory.getFindDependentsResponseFromXomKids( kids, spouse );

        response.getReturn().getPersons().remove( response.getReturn().getPersons().size() - 1 );

        clearParticipantId( veteran );
//        CommonFactory.logKids( repository );

        populator.populateParticipantIdFromDependents( response, repository );

        assertThat( spouse.getCorpParticipantId(), is( not(equalTo( 0L ) ) ) );
    }


    private void clearParticipantId( final Veteran     veteran ) {

        List<Child> kids        =   veteran.getChildren();

        for ( Child child : kids ) {

            child.setCorpParticipantId( 0L );
        }

        if (veteran.getCurrentMarriage() != null) {

            veteran.getCurrentMarriage().getMarriedTo().setCorpParticipantId( 0L );
        }
    }
}
