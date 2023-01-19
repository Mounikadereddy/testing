/*
 * FindDependentOnAwardWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAward;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 *      This class is a data access class.
 *      It is a web service client class to the services
 *      provided by AWARD team.
 */
public class FindDependentOnAwardWSHandler {


    private static Logger logger = Logger.getLogger(FindDependentOnAwardWSHandler.class);

//    private final CommonUtils          utils = new CommonUtils();

    //     Spring beans references/injection
    //private RbpsRepository              repository;
    private WebServiceTemplate          webServiceTemplate;
    private String                      findDependentOnAwardUri;





    /**
     * This method calls the AWARDS service
     * DependentOnAward.findDependentOnAward
     * @return FindDependentOnAwardResponse
     * @throws RbpsWebServiceClientException
     */
    public FindDependentOnAwardResponse findDependentOnAward( final long dependentParticipantId, RbpsRepository repo )
            throws RbpsWebServiceClientException {

//        utils.log( logger, "----> Entering [ "+ this.getClass() + ".findDependentOnAward()]...");

        FindDependentOnAward request = buildFindDependentOnAwardRequest(dependentParticipantId, repo);

        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive( findDependentOnAwardUri,
                                                                 request,
                                                                 new HeaderSetter( findDependentOnAwardUri,
                                                                                   repo.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = "Call to WS AmendAwardsDependencyEJBService.findDependentOnAward failed";

            repo.addValidationMessage( CommonUtils.getValidationMessage(findDependentOnAwardUri, request.getClass().getSimpleName(), rootCause.getMessage()) );
            logger.error(" ***RBPS: [" + detailMessage + "]");

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }

//        utils.log( logger, "<---- Exiting [ "+ this.getClass() + ".findDependentOnAward()]..." );

        return (FindDependentOnAwardResponse) response;
    }


    /**
     * Helper method,
     * prepare a FindDependentOnAward request object
     * @return FindDependentOnAward
     */
    private static final FindDependentOnAward buildFindDependentOnAwardRequest(final long dependentParticipantId, RbpsRepository repo) {

        FindDependentOnAward findDependentOnAward = new FindDependentOnAward();
        

        findDependentOnAward.setAwardType(RbpsConstants.AWARD_TYPE);
        findDependentOnAward.setBeneficiaryId(repo.getVeteran().getCorpParticipantId());
        findDependentOnAward.setDependentId(dependentParticipantId);
        findDependentOnAward.setVeteranId(repo.getVeteran().getCorpParticipantId());

        return findDependentOnAward;
    }


    /**
     * Spring setter injection
     * @param webServiceTemplate
     */
    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }


    /**
     * Spring setter injection
     * @parm String findDependentOnAwardUri
     */
    public void setFindDependentOnAwardUri(final String findDependentOnAwardUri) {
        //this.findDependentOnAwardUri = findDependentOnAwardUri;
    	String clusterAddr = CommonUtils.getClusterAddress();
        this.findDependentOnAwardUri = clusterAddr + "/" + findDependentOnAwardUri;
    }

    /**
     * Spring setter injection
     * @parm repository
     
    public void setRepository( final RbpsRepository repository ) {

        this.repository = repository;
    }
    */
}
