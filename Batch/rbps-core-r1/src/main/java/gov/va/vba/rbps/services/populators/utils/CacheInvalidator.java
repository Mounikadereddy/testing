/*
 * CacheInvalidator
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators.utils;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 *      Invalidates (clears) a cache after a certain number of
 *      claims/requests.
 */
public class CacheInvalidator {

    private static Logger logger = Logger.getLogger(CacheInvalidator.class);


    private LogUtils                logUtils        = new LogUtils( logger, true );

    private List<Collection>        collections     = new ArrayList<Collection>();
    private List<Map>               maps            = new ArrayList<Map>();
    private int                     cachePeriod     = 100;
    private long                    count           = 0;



    public void registerCollection( final Collection    collection ) {

        collections.add( collection );
    }


    public void registerMap( final Map map ) {

        maps.add( map );
    }


    public void increment() {

        count++;

        if ( count % cachePeriod == 0 ) {

//            logUtils.log( String.format( "Clearing collections after >%d< cache period and >%d< total claims",
//                                         cachePeriod,
//                                         count ) );
            clearCollections();
            clearMaps();
        }
    }


    private void clearCollections() {

        for ( Collection collection     : collections ) {

            collection.clear();
        }
    }


    private void clearMaps() {

        for ( Map map     : maps ) {

            map.clear();
        }
    }









    public void setCachePeriod( final int cachePeriod ) {

        this.cachePeriod = cachePeriod;
    }
}
