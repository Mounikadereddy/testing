/*
 * FindRegionalOfficesPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.populators.utils.CacheInvalidator;
import gov.va.vba.rbps.services.ws.client.handler.share.FindRegionalOfficesWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.FindRegionalOfficesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.StnDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 *      Return a regional office name given a claim location id.
 */
public class FindRegionalOfficesPopulator {

    private static Logger logger = Logger.getLogger(FindRegionalOfficesPopulator.class);

    private LogUtils                            logUtils            = new LogUtils( logger, true );

    private FindRegionalOfficesWSHandler        findRegionalOfficesWSHandler;
    private Map< Long, StnDTO>                  locationIdMap;
    private Map<String, Long>                   stationIdMap;


    public StnDTO getRegionalOfficeName( final Long claimLocationId, final RbpsRepository repository ) {

        StnDTO  stationInfo = populateRegionalOffices(repository).get( claimLocationId );

        if ( stationInfo == null ) {

            throw new RbpsRuntimeException( String.format( "Unable to find the name for the regional office with location id of >%d<",
                                                           claimLocationId ) );
        }

        logUtils.log( String.format( "The name for location id >%d< is >%s<", claimLocationId, stationInfo.getNm() ),repository );
        return stationInfo;
    }


    public Long getLocationId( final Long   putativeLocationId, final RbpsRepository repository ) {

        populateRegionalOffices( repository );

        if ( getRegionalOfficesMap().keySet().contains( putativeLocationId ) ) {

            logUtils.log( String.format( "location id >%d< is really a location id", putativeLocationId ),repository );
            return putativeLocationId;
        }

        Long    locationId = getStationIdMap().get( "" + putativeLocationId );

        if ( locationId == null ) {

            logStationIds( repository );

            throw new RbpsRuntimeException( String.format( ">%d< does not seem to be either a location id or station id.",
                                                           putativeLocationId ) );
        }

        logUtils.log( String.format( "Station id >%d< has the location id >%d<", putativeLocationId, locationId ),repository );
        return locationId;
    }


    private Map< Long, StnDTO> populateRegionalOffices( final RbpsRepository repository ) {

        if ( haveRegionalOfficesInfo() ) {

            return getRegionalOfficesMap();
        }

        FindRegionalOfficesResponse      response         = findRegionalOfficesWSHandler.findRegionalOffices( repository );

        return createRegionalOfficesMap( response, repository );
    }


    public boolean noDataInResponse( final FindRegionalOfficesResponse response ) {

        return response == null || response.getReturn() ==  null;
    }


    private boolean haveRegionalOfficesInfo() {

        return ! getRegionalOfficesMap().isEmpty();
    }


    //      Public for testing.
    public Map< Long, StnDTO> createRegionalOfficesMap( final FindRegionalOfficesResponse response, final RbpsRepository repository ) {

        if ( noDataInResponse( response ) ) {

            logUtils.log( " => FindRegionalOfficesResponse is empty.",repository );
            throw new RbpsRuntimeException( "Unable to find any information about regional offices." );
        }

        List<StnDTO> types = response.getReturn();

        for ( StnDTO type : types ) {

            getRegionalOfficesMap().put( type.getLctnId(), type );
            getStationIdMap().put(  type.getStationNumber(), type.getLctnId() );
        }

        return getRegionalOfficesMap();
    }


    private Map< Long, StnDTO> getRegionalOfficesMap() {

        if ( locationIdMap == null ) {

            locationIdMap = new HashMap< Long, StnDTO>();
        }

        return locationIdMap;
    }


    private Map<String, Long> getStationIdMap() {

        if ( stationIdMap == null ) {

            stationIdMap = new HashMap<String, Long>();
        }

        return stationIdMap;
    }


    private void logStationIds( final RbpsRepository repository) {

        String      ids = "";

        for ( String id : getStationIdMap().keySet() ) {

            ids += String.format( "        %s - %d\n", id, getStationIdMap().get( id ) );
        }

        logUtils.log( "The list of station ids is:\n" + ids, repository );
    }



    public void setFindRegionalOfficesWSHandler( final FindRegionalOfficesWSHandler findRegionalOfficesWSHandler) {

        this.findRegionalOfficesWSHandler = findRegionalOfficesWSHandler;
    }

    public void setCacheInvalidator( final CacheInvalidator     invalidator ) {

        invalidator.registerMap( getStationIdMap() );
        invalidator.registerMap( getRegionalOfficesMap() );
    }
}
