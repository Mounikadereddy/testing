/*
 * UpdateBenefitClaimWSHandlerInterface.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.interfaces;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimResponse;


/**
 *      This Interface provides methods of BenefitClaimWebService
 */
public interface UpdateBenefitClaimWSHandlerInterface {

    /**
     * This method updates the claim label
     *
     * @return
     * @throws RbpsWebServiceClientException
     */
    public UpdateBenefitClaimResponse updateBenefitClaim(RbpsRepository repository) throws RbpsWebServiceClientException;
}
