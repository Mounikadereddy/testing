/*
 * FindRegionalOfficesWSHandlerTest
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindRegionalOfficesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.StnDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;


public class FindRegionalOfficesWSHandlerTest  extends RbpsAbstractTest {


    private CommonUtils                         utils;
    private FindRegionalOfficesWSHandler        wsHandler;
    private RbpsRepository                      repo;


    @Override
    @Before
    public void setup() {

        super.setup();
        wsHandler = (FindRegionalOfficesWSHandler) getBean( "findRegionalOfficesWSHandler" );

        repo = (RbpsRepository) getBean( "repository" );
    }


    @Test
    public void shouldGetList() {

        wsHandler.findRegionalOffices( repo );
    }


    @Test
    public void shouldGetRightList() throws IOException {

        List<String>                    stationIdsFromPdf   = getPdfList();
        FindRegionalOfficesResponse     response            = wsHandler.findRegionalOffices( repo );
        List<String>                    failedStations      = new ArrayList<String>();
        List<String>                    wsStationIds        = convertToStationIds( response );
//        Map<String,StnDTO>              stationMap          = convertToMap( response );


        for ( String station : stationIdsFromPdf ) {

            if ( ! wsStationIds.contains( station ) ) {

                failedStations.add( station );
            }
        }

        if ( ! failedStations.isEmpty() ) {
            System.out.println( "failed stations: " + failedStations );
        }
        assertThat( "the list of failed stations should be empty", failedStations.size(), is(equalTo( 0 )) );
//        System.out.println( "\n\n\n358: " + utils.stringBuilder( stationMap.get( "358" )) );
    }


    public List<String> getPdfList() throws IOException {

        ClassPathResource               resource            = new ClassPathResource( "gov/va/vba/rbps/services/ws/client/handler/share/pdf_station_ids" );
        String                          pdfString           = FileUtils.readFileToString( resource.getFile() );
        List<String>                    stationIdsFromPdf   = convertToList( pdfString );

        return stationIdsFromPdf;
    }


//    private Map<String, StnDTO> convertToMap( final FindStationsResponse response ) {
//
//        Map<String,StnDTO>    stations = new HashMap<String,StnDTO>();
//
//        for ( StnDTO station : response.getReturn() ) {
//
//            stations.put( station.getStationNumber(), station );
//        }
//
////        System.out.println( "station map: " + utils.stringBuilder( stations ) );
//
//        return stations;
//    }


    private List<String> convertToStationIds( final FindRegionalOfficesResponse response ) {

        List<String>    stations = new ArrayList<String>();

        for ( StnDTO station : response.getReturn() ) {

            stations.add( station.getStationNumber() );
        }

//        System.out.println( "stations from response: " + utils.stringBuilder( stations ) );

        return stations;
    }


    private List<String> convertToList( final String fileContents ) {

        List<String>    stations = new ArrayList<String>();

        for ( String station : fileContents.split( "\n" ) ) {

            stations.add( station.trim() );
        }

//        System.out.println( "stations from pdf: " + utils.stringBuilder( stations ) );

        return stations;
    }
}
