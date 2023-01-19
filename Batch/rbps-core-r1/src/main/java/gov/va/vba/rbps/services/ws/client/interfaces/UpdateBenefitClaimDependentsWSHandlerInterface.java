/*
 * UpdateBenefitClaimDependentsWSHandlerInterface.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.interfaces;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimDependentsResponse;


/**
 * This interface is a data access interface.
 * It is a web service client interface to the UpdateBenefitClaimDependents web
 * service provided by SHARE team.
 *
 * @author Tom.Corbin
 * @since 07/2011
 * @version 1.1
 *
 * Last change on 07/10/2010
 * Revision History
 * Date         Name        Description
 *  ------------------------------------------------------------
 *  07/19/2011  T.Corbin    Created
 */
public interface UpdateBenefitClaimDependentsWSHandlerInterface {

    /**
     * This method calls the SHARE service
     * UpdateBenefitClaimDependents.updateBenefitClaimDependents
     * @author vafsccorbit
     * @return FindRatingDataResponse
     * @throws RbpsWebServiceClientException
     */
    UpdateBenefitClaimDependentsResponse  updateDependents(RbpsRepository repository) throws RbpsWebServiceClientException;
}
