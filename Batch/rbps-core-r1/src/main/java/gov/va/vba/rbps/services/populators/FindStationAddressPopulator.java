/*
 * FindStationAddressPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.ClaimStationAddress;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.handler.share.FindStationAddressWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.share.findstations.FindStationAddressResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.findstations.Station;


/**
 *      This class is used to populate values into ClaimStationAddress based on station id
 */
public class FindStationAddressPopulator {

    private static Logger logger = Logger.getLogger(FindStationAddressPopulator.class);

    private LogUtils                            logUtils        = new LogUtils( logger, true );
    private FindStationAddressWSHandler         findStationAddressWSHandler;


    public ClaimStationAddress getStationAddress( final Long        	locationId,
                                                  final String      	stationId,
                                                  final RbpsRepository 	repository) {

        ClaimStationAddress claimStationAddress = populateStationAddress( locationId, stationId, repository );

        return claimStationAddress;
    }


    private ClaimStationAddress populateStationAddress( final Long      		locationId,
                                                        final String    		stationId,
                                                        final RbpsRepository 	repository) {

        ClaimStationAddress             claimStationAddress = new ClaimStationAddress();
        
        FindStationAddressResponse      response            = findStationAddressWSHandler.findStationAddress( stationId, repository );

        if ( response == null || response.getReturn() ==  null) {

            logUtils.log( "FindStationAddressResponse is null.", repository );

            throw new RbpsRuntimeException( String.format("Call to FindStationAddress Web Service returned empty for station id %d", stationId ) );
        }

        Station station = response.getReturn();

        claimStationAddress.setClaimLocationId( locationId );
        claimStationAddress.setStationId( stationId );
        claimStationAddress.setAddressLine1( station.getAddressLine1() );
        claimStationAddress.setAddressLine2( station.getAddressLine2() );
        claimStationAddress.setAddressLine3( station.getAddressLine3() );
        claimStationAddress.setCity( station.getCity() );
        claimStationAddress.setState( station.getState() );
        claimStationAddress.setZipCode( station.getZipCode() );
        claimStationAddress.setZipPlus4( station.getZipPlus4() );
        claimStationAddress.setZipSecondSuffix( station.getZipSecondSuffix() );

        return claimStationAddress;
    }



    public void setFindStationAddressWSHandler( final FindStationAddressWSHandler findStationAddressWSHandler) {

        this.findStationAddressWSHandler = findStationAddressWSHandler;
    }
}
