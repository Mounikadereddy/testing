/*
 * VdcClaimFilter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.xom.ClaimLabelType;



/**
 *      Filters VDC claims if a configuration property says to.
 */
public class VdcClaimFilter {

    private static Logger logger = Logger.getLogger(VdcClaimFilter.class);


    private LogUtils        logUtils                    = new LogUtils( logger, true );
    private boolean         shouldPerformFiltering      = false;

    public VdcClaimFilter(){}
    
    
    public boolean filter( final RbpsRepository       repo ) {


        if ( ! shouldPerformFiltering ) {

            logUtils.log( "Filtering turned off.", repo );
            return false;
        }
        logUtils.log( "Filtering turned on.", repo );

        if ( ! isVdcClaim( repo ) ) {

            logUtils.log( "Not a vdc claim, not filtering.", repo );
            return false;
        }

        logUtils.log( "This is a VDC claim, filtering.", repo );
        return true;
    }


    public boolean isVdcClaim( final RbpsRepository repo ) {

        //NullSafeGetter  grabber     = new NullSafeGetter();
        ClaimLabelType  claimLabel  = (ClaimLabelType) NullSafeGetter.getAttribute( repo, "veteran.claim.claimLabel" );

        if ( claimLabel == null ) {

            logUtils.log( "No claim label provided.", repo );
        }

        if ( claimLabel == ClaimLabelType.NEW_EBENEFITS_686C
                || claimLabel == ClaimLabelType.NEW_EBENEFITS_674 ) {

            return true;
        }

        return false;
    }


    public void setShouldPerformFiltering( final boolean shouldPerformFiltering ) {

        this.shouldPerformFiltering = shouldPerformFiltering;
    }
}
