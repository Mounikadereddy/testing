/**
 * ClaimValidatorFactory.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */

package gov.va.vba.rbps.claimvalidator;


import gov.va.vba.rbps.coreframework.xom.EndProductType;

import java.util.EnumMap;
import java.util.Map;


/**
 *      The RBPS Claim Validator Factory: all validators should
 *      register themselves here.
 */
public class ClaimValidatorFactory {
    private Map<EndProductType, ClaimValidatorInterface> claimValidatorMap =
        new EnumMap<EndProductType, ClaimValidatorInterface>(EndProductType.class);

    //  Spring dependency injection autowire by name
    private ClaimValidatorInterface ep130ClaimValidatorImpl;

    private boolean isLoaded = false;

    //  load the validators
    public void load() {

    	claimValidatorMap.put(EndProductType.COMPENSATION_EP130, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP131, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP132, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP133, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP134, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP135, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP136, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP137, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP138, ep130ClaimValidatorImpl);
        claimValidatorMap.put(EndProductType.COMPENSATION_EP139, ep130ClaimValidatorImpl);
        //claimValidatorMap.put(EndProductType.COMPENSATION_EP150, ep150ClaimValidatorImpl);
        //claimValidatorMap.put(EndProductType.COMPENSATION_EP155, ep155ClaimValidatorImpl);

        isLoaded = true;
    }


    /**
     *      This is the only method needed to get the correct validator
     *      @param EndProductType
     *      @return ClaimValidatorInterface
     */
    public ClaimValidatorInterface getClaimValidator(final EndProductType endProductType) {

        if (isLoaded == false) {
            load();
        }

        return claimValidatorMap.get(endProductType);
    }


    public void setEp130ClaimValidatorImpl(final ClaimValidatorInterface ep130ClaimValidatorImpl) {
        this.ep130ClaimValidatorImpl = ep130ClaimValidatorImpl;
    }

    /*public void setEp150ClaimValidatorImpl(ClaimValidatorInterface ep150ClaimValidatorImpl){
        this.ep150ClaimValidatorImpl = ep150ClaimValidatorImpl;
    }

    public void setEp155ClaimValidatorImpl(ClaimValidatorInterface ep155ClaimValidatorImpl){
        this.ep155ClaimValidatorImpl = ep155ClaimValidatorImpl;
    }*/
}
