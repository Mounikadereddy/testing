/*
 * ProcessAwardDependentFacade
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendDependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependent;

import java.util.List;



/**
 *      Just a front for the ProcessAwardDependent request object so we can add utility methods.
 *      Because the request object is generated, we can't add code to it.
 */
public class ProcessAwardDependentFacade {


    final ProcessAwardDependent request;



    public ProcessAwardDependentFacade( final ProcessAwardDependent awardDependentRequest ) {

        this.request = awardDependentRequest;
    }


    public void setNewAwardsDependentRequests( final List<AmendDependencyDecisionVO> newDependencyList ) {

        getCurrentAwardsDependentRequests().clear();
        getCurrentAwardsDependentRequests().addAll( newDependencyList );
    }


    public List<AmendDependencyDecisionVO> getCurrentAwardsDependentRequests() {

        return request.getAmendAwardDependencyInput().getDependencyList();
    }


    public boolean noAwardsRequestProvided() {

        return request == null;
    }
}
