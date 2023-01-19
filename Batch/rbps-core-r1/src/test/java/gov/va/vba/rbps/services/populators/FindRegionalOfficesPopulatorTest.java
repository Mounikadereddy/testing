/*
 * FindRegionalOfficesPopulatorTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindRegionalOfficesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.StnDTO;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;

import java.util.Map;

import org.junit.Test;


public class FindRegionalOfficesPopulatorTest {


//    @RunTags(tags={"InputXml", "Populator", "Release"} )
    @Test
    public void shouldGetTheRightNumberOfOffices() {

        String                          officesPath = "gov/va/vba/rbps/services/populators/findRegionalOffices.response.xml";
        FindRegionalOfficesPopulator    populator   = new FindRegionalOfficesPopulator();
        FindRegionalOfficesResponse     response    = new TestUtils().getRegionalOfficesResponseFromXml( officesPath );
//        Map< Long, StnDTO>              map         = populator.createRegionalOfficesMap( response );

        //      Just have to match up with what's in DEVL.  No other choice, really.
        //      And since it's test data, it can change arbitrarily.
//        assertThat( map.size(), is(equalTo( 68 ) ) );
    }


    @Test( expected = RbpsRuntimeException.class )
    public void shouldHandleNullResponse() {

        FindRegionalOfficesPopulator    populator   = new FindRegionalOfficesPopulator();

//        populator.createRegionalOfficesMap( null );
    }


    @Test()
    public void shouldHandleEmptyResponse() {

        FindRegionalOfficesPopulator    populator   = new FindRegionalOfficesPopulator();

//        populator.createRegionalOfficesMap( new FindRegionalOfficesResponse() );
    }


    @Test
    public void shouldFindHonolulu() {

        String                          officesPath = "gov/va/vba/rbps/services/populators/findRegionalOffices.response.xml";
        FindRegionalOfficesPopulator    populator   = new FindRegionalOfficesPopulator();
        FindRegionalOfficesResponse     response    = new TestUtils().getRegionalOfficesResponseFromXml( officesPath );
//        Map< Long, StnDTO>              map         = populator.createRegionalOfficesMap( response );

//        for ( StnDTO s : map.values() ) {
//
//            System.out.println( s.getLctnId() + "- " + s.getNm() + " - " + s.getStationNumber() );
//        }
//        for ( Long key : map.keySet() ) {
//
//            System.out.println( key );
//        }

        //      Just have to match up with what's in DEVL.  No other choice, really.
        //      And since it's test data, it can change arbitrarily.
//        assertThat( map.get( 1857L ), is(notNullValue()) );
//        assertThat( map.get( 1857L ).getNm(), is(equalTo( "Honolulu" ) ) );
    }
}
