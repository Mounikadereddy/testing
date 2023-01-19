/*
 * PoaPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbersResponse;
import gov.va.vba.rbps.services.ws.client.mapping.org.PoaSearchResult;
import gov.va.vba.rbps.services.ws.client.mapping.org.PowerOfAttorneyDTO;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindPOAResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.ShrinqfPersonOrg;

import java.util.ArrayList;
import java.util.List;


/**
 *      Decide whether the veteran has a POA.
 */
public class PoaPopulator {

    private static Logger logger = Logger.getLogger(PoaPopulator.class);

    private LogUtils        logUtils    = new LogUtils( logger, true );


    public void populatePoa( final RbpsRepository       repo,
                             final FindPOAsByFileNumbersResponse   response) {

        List<PoaSearchResult> results = response.getReturn();

        if (results.isEmpty()) {
            logUtils.log( "Does NOT have a POA.", repo);
            setHasPoa( repo, false );
            return;
        }

        PoaSearchResult searchResults = response.getReturn().get(0);

        // ccr 1776  issue: POA not printed on the letter
//        if ( ! isPoaOrganization( searchResults, repo ) ) {
//
//            setHasPoa( repo, false );
//            return;
//        }

        PowerOfAttorneyDTO powerOfAttorney = searchResults.getPowerOfAttorney();

        if(powerOfAttorney ==null || powerOfAttorney.getNm() ==null){
        	logUtils.log( "Does NOT have a POA.", repo);
        	 setHasPoa( repo, false );
             return;
        }
        // end of ccr 1776 changes
        recordPoaInfo( repo, powerOfAttorney );
    }


    public void recordPoaInfo( final RbpsRepository         repo,
                               final PowerOfAttorneyDTO       poaInfo ) {

        setHasPoa( repo, true );

        String  personOrgName   =   poaInfo.getNm();
        personOrgName           = fixPersonOrgName( personOrgName );

        repo.setPoaOrganizationName( personOrgName );
    }


    public String fixPersonOrgName( String personOrgName ) {

        personOrgName = personOrgName.replaceAll( "[0-9\\-]", "" );

        return personOrgName.trim();
    }


    public boolean isPoaOrganization(PoaSearchResult poaInfo, RbpsRepository repo ) {

        if ( poaInfo == null ) {

            logUtils.log( "Does NOT have a POA.", repo);
            return false;
        }

        if ( poaInfo.getPowerOfAttorney() == null ) {

            logUtils.log( "Does NOT have a POA.", repo);
            return false;
        }

        String  organizationType =   poaInfo.getPowerOfAttorney().getOrgTypeNm();

        boolean     result = ( organizationType.equalsIgnoreCase( "POA National Organization" ) )
                                    || ( organizationType.equalsIgnoreCase( "POA State Organization" ) );

        logUtils.log( String.format( "%s %s a poa",
                                     organizationType,
                                     ( result ? "IS" : "is NOT" ) ), repo);

        return result;
    }

    
    
    public static final void setHasPoa( final RbpsRepository     repo,
                           final boolean            hasPoa ) {

        repo.getVeteran().setHasPOA( hasPoa );
    }


}
