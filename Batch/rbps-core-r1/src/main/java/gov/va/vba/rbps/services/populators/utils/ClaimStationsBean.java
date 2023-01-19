/*
 * ClaimStationsBean.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators.utils;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.ClaimStationAddress;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.populators.FindRegionalOfficesPopulator;
import gov.va.vba.rbps.services.populators.FindStationAddressPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.share.sojSignature.StnDTO;

import java.util.HashMap;

import org.springframework.util.CollectionUtils;


/*
 *      This class calls populators to populate values into ClaimStationAddress
 *      to be used for SojAddress in LetterFields
 */
public class ClaimStationsBean {

    private static Logger logger = Logger.getLogger(ClaimStationsBean.class);

    private LogUtils                                    logUtils        = new LogUtils( logger, true );

    private FindStationAddressPopulator                 findStationAddressPopulator;
    private FindRegionalOfficesPopulator                findRegionalOfficesPopulator;
    private HashMap<Long, ClaimStationAddress>          claimStationAddressMap;



    public void populateClaimStationData( final RbpsRepository repository ) {

    	repository.setClaimStationAddress( getStationAddress( repository.getClaimStationLocationId(), repository ) );
    }


    public Long getLocationId( final Long putativeLocationId, final RbpsRepository repository ) {

        return findRegionalOfficesPopulator.getLocationId( putativeLocationId, repository );
    }


    private ClaimStationAddress getStationAddress( final Long claimLocationId, final RbpsRepository repository ) {

        ClaimStationAddress     claimStationAddress = getClaimStationAddress( claimLocationId, repository );

        logUtils.log( String.format( "claimStationAddress (without sig info) for location id >%d< is: %s\n",
                                     claimLocationId,
                                     new IndentedToStringUtils().toString( claimStationAddress, 1 ) ), repository );
        return claimStationAddress;
    }


    private ClaimStationAddress getClaimStationAddress( final Long claimLocationId, final RbpsRepository repository ) {

        ClaimStationAddress claimStationAddress   = getStationMap().get( claimLocationId );

        if ( claimStationAddress != null ) {

            return claimStationAddress;
        }

        return queryAndAddClaimStationAddressToMap( claimLocationId, repository );
    }


    private ClaimStationAddress queryAndAddClaimStationAddressToMap( final Long claimLocationId, final RbpsRepository repository ) {

        StnDTO regionalInfo   = getRegionalOfficeInfo( claimLocationId, repository );

        logUtils.log( String.format( "claimStationAddress >%d< not in  claimStationAddressMap, calling webService to get station data.",
                                     claimLocationId ), repository );

        ClaimStationAddress     claimStationAddress = findStationAddressPopulator.getStationAddress( claimLocationId,
                                                                                                     regionalInfo.getStationNumber(),
                                                                                                     repository );

        setRegionalOfficeInfo( claimStationAddress, regionalInfo, repository );
        addClaimStationAddressToMap( claimStationAddress );

        return claimStationAddress;
    }


    //      Public for testing.
    public void addClaimStationAddressToMap( final ClaimStationAddress claimStationAddress ) {

        getStationMap().put( claimStationAddress.getClaimLocationId(), claimStationAddress );
    }


    private void setRegionalOfficeInfo( final ClaimStationAddress     	claimStationAddress,
                                        final StnDTO                  	regionalInfo,
                                        final RbpsRepository 			repository) {

        if ( null == regionalInfo ) {

            return;
        }

        setRegionalInfo( claimStationAddress, regionalInfo );
        claimStationAddress.calculateAddressLine();

        logUtils.log( ">" + claimStationAddress.getClaimLocationId() + "< claim location has Station Address, new Address Line1 >" +  claimStationAddress.getAddressLine1() + "<", repository );
    }


    private StnDTO getRegionalOfficeInfo( final Long claimLocationId, final RbpsRepository repository ) {

        return findRegionalOfficesPopulator.getRegionalOfficeName( claimLocationId, repository );
    }


    private void setRegionalInfo( final ClaimStationAddress      claimStationAddress,
                                  final StnDTO                   regionalInfo ) {

        claimStationAddress.setName( regionalInfo.getNm() );
        claimStationAddress.setStationId( regionalInfo.getStationNumber() );
    }

//
//    private Long getClaimStationId( final ClaimStationAddress stationAddress ) {
//
//        return stationAddress.getClaimLocationId();
//    }


    private HashMap<Long, ClaimStationAddress> getStationMap() {

        if ( CollectionUtils.isEmpty( claimStationAddressMap ) ) {

            claimStationAddressMap = new HashMap<Long, ClaimStationAddress>();
        }

        return claimStationAddressMap;
    }



    public void setFindStationAddressPopulator( final FindStationAddressPopulator findStationAddressPopulator ) {

        this.findStationAddressPopulator = findStationAddressPopulator;
    }

    public void setFindRegionalOfficesPopulator( final FindRegionalOfficesPopulator findRegionalOfficesPopulator ) {

        this.findRegionalOfficesPopulator = findRegionalOfficesPopulator;
    }

    public void setCacheInvalidator( final CacheInvalidator     invalidator ) {

        invalidator.registerMap( getStationMap() );
    }
}
