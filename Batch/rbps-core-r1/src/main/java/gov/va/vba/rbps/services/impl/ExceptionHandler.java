/*
 * ExceptionHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import static gov.va.vba.rbps.coreframework.util.RbpsConstants.CLAIM_LABEL;
import static gov.va.vba.rbps.coreframework.util.RbpsConstants.PROC_ID;
import static gov.va.vba.rbps.coreframework.util.RbpsUtil.getExceptionSafeLongValue;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimprocessor.util.ClaimProcessorHelper;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Claim;
import gov.va.vba.rbps.coreframework.xom.ClaimLabelType;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.services.ws.client.util.SoapFaultPrinter;

import javax.xml.transform.dom.DOMSource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.soap11.Soap11Fault;
import org.w3c.dom.NodeList;


/**
 *      This class handles the SoapFaultException. In future it will handle all other kinds of exceptions as well.
 */
public class ExceptionHandler {

    private static Logger logger = Logger.getLogger(ExceptionHandler.class);


    private ClaimProcessorHelper    claimProcessorHelper;


    public void handleSoapFaultException(final SoapFaultClientException fault, final RbpsRepository repository) {

        try {
            NodeList nodeList = null;

            nodeList = ((DOMSource) ((Soap11Fault) fault.getSoapFault())
                    .getSource()).getNode().getOwnerDocument()
                    .getElementsByTagName("errorCode");

            for (int ii = 0; ii < nodeList.getLength(); ii++) {
                String nodeValue = nodeList.item(ii).getFirstChild().getNodeValue().trim();

                if (setProcId(nodeValue, repository)) {

                	repository.setValidClaim(false);
                    break;
                }
            }

            handleException(fault, repository);
        }
        catch ( Throwable ex ) {

            handleException( new RbpsException( "Unable to handle soap fault exception", ex ), repository );
        }
    }


    private boolean setProcId(final String nodeValue, final RbpsRepository repository) {

        if ( nodeValue.startsWith(PROC_ID) ) {

            String procId = nodeValue.substring(nodeValue.indexOf(PROC_ID) + PROC_ID.length()).trim();
            repository.setVnpProcId(getExceptionSafeLongValue(procId));

            return true;
        }

        return false;
    }


    /**
     * TODO: Remove this method when it will be decided that there is no need for claim label in case of SoapFaultException
     *
     * @param nodeValue
     * @return whether the claim label was found.
     */
    private boolean setClaimLabel(final String nodeValue, final RbpsRepository repository) {

        if (nodeValue.startsWith(CLAIM_LABEL)) {
            String claimLabel = nodeValue.substring(
                    nodeValue.indexOf(CLAIM_LABEL) + CLAIM_LABEL.length()).trim();

            if (repository.getVeteran() == null) {
            	repository.setVeteran(new Veteran());
            }

            if (repository.getVeteran().getClaim() == null) {
            	repository.getVeteran().setClaim(new Claim());
            }

            repository.getVeteran().getClaim().setClaimLabel(ClaimLabelType.find(claimLabel));

            return true;
        }

        return false;
    }


    public String handleException( final Throwable ex, final RbpsRepository repository ) {

        String             exceptionInfo        = "";
        String             sendToManualFailure  = "";
        StringBuilder      status               = new StringBuilder( "Runtime Exception -> Manual" );

        repository.addValidationMessages( CommonUtils.getExceptionMessages( ex ) );
        
      	sendClaimToManualProcessing( repository );
        sendToManualFailure += repository.getFormattedValidationMessages();
        
        if ( StringUtils.isEmpty( repository.getFormattedRuleExceptionMessages() ) ){
        	
        	sendToManualFailure += repository.getFormattedRuleExceptionMessages();
        }
        
        addExceptionToStatus( ex, status );

        repository.setClaimProcessingState( status.toString() );

        exceptionInfo += addExceptionInfoToString( ex );
        exceptionInfo += sendToManualFailure;

        return exceptionInfo;
    }


    private String addExceptionInfoToString( final Throwable ex ) {

        String      exceptionInfo   = "";
        SoapFaultPrinter	faultPrinter	= new SoapFaultPrinter();
        String      		faultInfo       = faultPrinter.printSoapFaultInfo( ex );

        if ( ! StringUtils.isBlank( faultInfo ) ) {

            exceptionInfo += faultInfo;
        }

        exceptionInfo += String.format( "\nHandling exception:\n%s", ExceptionUtils.getFullStackTrace( ex ) );

        return exceptionInfo;
    }


    private void addExceptionToStatus( final Throwable      ex,
                                       final StringBuilder  status ) {

        if ( ex == null ) {

            return;
        }

        status.append( ": " ).append( ex.getMessage() );
    }


    private String sendClaimToManualProcessing( final RbpsRepository repository ) {

        try {

            if ( ! repository.validProcId() ) {

                logger.error( "No valid procid, can't send claim to manual." );
                return "Unable to send claim to manual processing";
            }

            claimProcessorHelper.sendClaimForManualProcessing( repository );

            return "";
        }
        catch ( Throwable ex ) {

            return "Failed to send the claim to manual processing\n" + addExceptionInfoToString( ex );
        }
    }


    public void setClaimProcessorHelper( final ClaimProcessorHelper claimProcessorHelper) {

        this.claimProcessorHelper = claimProcessorHelper;
    }
}
