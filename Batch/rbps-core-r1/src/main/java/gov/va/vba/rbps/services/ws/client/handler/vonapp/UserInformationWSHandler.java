/*
 * UserInformationWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;


import static gov.va.vba.rbps.coreframework.util.RbpsConstants.CLAIM_STATUS_READY_FOR_RBPS;
import static gov.va.vba.rbps.coreframework.util.RbpsConstants.USER_IDENTITY_TYPE;
import static gov.va.vba.rbps.coreframework.util.RbpsConstants.USER_IDENTITY_VALUE;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.ReadCurrentAndProposedAward;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.ReadCurrentAndProposedAwardResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSupplied;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSuppliedResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.GetClaimInfo;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.GetClaimInfoResponse;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.UserInformationInput;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.apache.commons.lang.StringUtils;


/**
 *      Retrieves all the claim data from UserInformation Web Service
 */
public class UserInformationWSHandler  {

	
	private static Logger logger = Logger.getLogger(UserInformationWSHandler.class);
	
 // Spring beans references/injection
    private WebServiceTemplate webServiceTemplate;
    private String webServiceUri;


    public FindByDataSuppliedResponse getFindByDataSuppliedResponse( final RbpsRepository   repo, 
    																 final String currentProcess, 
    																 final String totalProcesses ) {
        return getFindByDataSuppliedResponse( repo, null, currentProcess, totalProcesses );
    }


    public FindByDataSuppliedResponse getFindByDataSuppliedResponse( final RbpsRepository   repo,
                                                                     final String           procId,
                                                                     final String			currentProcess,
                                                                     final String			totalProcesses ) {

        FindByDataSupplied          request     = buildRequest(repo, procId, currentProcess, totalProcesses );

      return call( repo, request );
        
    }


    private FindByDataSupplied buildRequest( final RbpsRepository 	repository, 
    										 final String 			procId, 
    										 final String 			currentProcess, 
    										 final String 			totalProcesses ) {

        FindByDataSupplied      findByDataSupplied      =   new FindByDataSupplied();
        UserInformationInput    userInformationInput    =   new UserInformationInput();

        if ( StringUtils.isBlank( procId ) ) {

            userInformationInput.setVnpProcStateTypeCd(CLAIM_STATUS_READY_FOR_RBPS);
            userInformationInput.setUserIdentityType(USER_IDENTITY_TYPE);
            userInformationInput.setUserIdentityValue(USER_IDENTITY_VALUE);
            
            try {
            	
	            userInformationInput.setBatchProcessorNbr( Long.valueOf(currentProcess) );
	            userInformationInput.setBatchProcessorTotal(Long.valueOf(totalProcesses) );
	            
            } catch( Exception ex ) {
            	
            	String detailMessage = "Batch process parameters passed are not numeric";
                repository.addValidationMessage( detailMessage );
                logger.error(" ***RBPS: [" + detailMessage + "]");
                
            	throw new RbpsWebServiceClientException(detailMessage, ex);
            }
        }
        else {

            userInformationInput.setVnpProcId(procId);
        }

        findByDataSupplied.setArg0(userInformationInput);

        return findByDataSupplied;
    }

   

 
    protected FindByDataSupplied buildRequest( final RbpsRepository repo ) {

        // TODO Auto-generated method stub
        return null;
    }
    private FindByDataSuppliedResponse call(RbpsRepository repository, FindByDataSupplied request) throws RbpsWebServiceClientException {

       
    	logger.debug( repository);

       
        Object response;

        try {
            response = webServiceTemplate.marshalSendAndReceive(
                webServiceUri,
                request,
                new HeaderSetter( webServiceUri, repository.getClaimStationAddress()));

        } catch (Throwable rootCause) {

            String detailMessage = "Call to WS ReadCurrentAndProposedAward failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage( webServiceUri,
                    request.getClass().getSimpleName(),
                    rootCause.getMessage() ) );
            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        } finally {

        	logger.debug(repository );
        }

        return (FindByDataSuppliedResponse) response;
    }
    public void setWebServiceUri( final String webServiceUri ) {

        String clusterAddr = CommonUtils.getClusterAddress();
        this.webServiceUri = clusterAddr + "/" + webServiceUri;
    }

    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
