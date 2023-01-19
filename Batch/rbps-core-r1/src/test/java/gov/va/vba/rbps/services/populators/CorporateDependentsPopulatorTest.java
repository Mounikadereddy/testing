/*
 * CorporateDependentsPopulatorTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.fixtures.CoreFactory;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import org.junit.Before;
import org.junit.Test;


/**
 *      Test case for the <code>CorporateDependentsPopulator</code> class
 */
public class CorporateDependentsPopulatorTest {


    private TestUtils    testUtils       = new TestUtils();


    @Before
    public void setup() {

        LogUtils.setGlobalLogit( false );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldAddFromXmlResponse() {

        RbpsRepository                  repo            = new RbpsRepository();
        repo.setVeteran( CoreFactory.georgeVeteran() );

        String                          xmlPath     = "gov/va/vba/rbps/services/populators/999507853.findDep.response.xml";
        testUtils.populateCorporateDependents( repo, xmlPath );

        assertThat( repo.getChildren().size(), is(equalTo( 3 ) ) );
        assertThat( repo.getSpouse(), is(notNullValue()) );
    }


    /**
     *      Make sure that a spouse is produced
     *      for a given input.
     */
//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldBuildSpouse() {

        RbpsRepository                  repo            = new RbpsRepository();
        repo.setVeteran( CoreFactory.adamsVeteran() );

        String                          dependentsPath  = "gov/va/vba/rbps/services/populators/findDependents.withSpouse.response.xml";
        testUtils.populateCorporateDependents( repo, dependentsPath );

        assertThat( repo.getSpouse(), is(notNullValue()) );
    }


    /**
     *      Make sure that the award indicator is generated correctly.
     */
//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldConvertAwardIndicator() {

        CorporateDependentsPopulator    populator   = new CorporateDependentsPopulator();
        boolean                         result      = populator.convertAwardIndicator( "y" );

        assertThat( result, is( equalTo( true ) ) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldConvertLongerAwardIndicator() {

        CorporateDependentsPopulator    populator   = new CorporateDependentsPopulator();
        boolean                         result      = populator.convertAwardIndicator( "YES" );

        assertThat( result, is( equalTo( true ) ) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldConvertNegativeAwardIndicator() {

        CorporateDependentsPopulator    populator   = new CorporateDependentsPopulator();
        boolean                         result      = populator.convertAwardIndicator( "N" );

        assertThat( result, is( equalTo( false ) ) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldConvertNullAwardIndicator() {

        CorporateDependentsPopulator    populator   = new CorporateDependentsPopulator();
        boolean                         result      = populator.convertAwardIndicator( null );

        assertThat( result, is( equalTo( false ) ) );
    }


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldConvertBlankAwardIndicator() {

        CorporateDependentsPopulator    populator   = new CorporateDependentsPopulator();
        boolean                         result      = populator.convertAwardIndicator( "" );

        assertThat( result, is( equalTo( false ) ) );
    }


//    private static class TestRecord extends Shrinq3Record {
//
//        public void setPersons( final List<Shrinq3Person>     persons ) {
//
//            this.persons = persons;
//        }
//    }
}
