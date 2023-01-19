/*
 * AwardsWebServicesHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.ProcessAwardDependentRequestFilter;
import gov.va.vba.rbps.services.ws.client.interfaces.ProcessAwardDependentWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependent;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.CommonsHttpMessageSender;


/**
 *      This class is a data access class.
 *      It is a web service client class to the services
 *      provided by AWARD team.
 */
public class ProcessAwardDependentWSHandler implements ProcessAwardDependentWSHandlerInterface {

    private static Logger logger = Logger.getLogger(ProcessAwardDependentWSHandler.class);

    //private CommonUtils                         utils;
    private LogUtils                            logUtils            = new LogUtils( logger, true );



    // Spring beans references/injection
    private WebServiceTemplate                  webServiceTemplate;
    private String                              processAwardDependentUri;
    private String                              processAwardDependentSoTimeout;
    private ProcessAwardDependentRequestFilter  processAwardDependentRequestFilter;


    /**
     *      This method calls the AWARDS service
     *      AmendAwardsDependencyEJBService.processAwardDependent
     *
     *      @return ProcessAwardDependentResponse
     *      @throws RbpsWebServiceClientException
     */
    @Override
    public ProcessAwardDependentResponse processAwardDependents( final RbpsRepository repository)
            throws RbpsWebServiceClientException {

        logUtils.debugEnter( repository );

        //processAwardDependentRequestFilter.setCommonUtils(utils);
        ProcessAwardDependent   request = processAwardDependentRequestFilter.getFilteredProcessAwardDependentRequest( repository );

        Object                  response;

        try {
            // set read timeout using value from properties file
        	logger.debug("###########processAwardDependents ");
           	logger.debug("###########processAwardDependentUri "+processAwardDependentUri);
           	logger.debug("########### repository.getClaimStationAddress() "+       repository.getClaimStationAddress());
            setReadTimeoutFromProperties();

            response = webServiceTemplate.marshalSendAndReceive( processAwardDependentUri,
                                                                 request,
                                                                 new HeaderSetter( processAwardDependentUri,
                                                                                   repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = "Call to WS AmendAwardsDependencyEJBService.processAwardDependents failed";

            logger.debug("getCause: " + rootCause.getCause());
            logger.debug("getLocalizedMessage: " + rootCause.getLocalizedMessage());
            logger.debug("getMessage: " + rootCause.getMessage());
            logger.debug("getStackTrace: " + rootCause.getStackTrace());
            logger.debug("getSuppressed: " + rootCause.getSuppressed());
            repository.addValidationMessage( CommonUtils.getValidationMessage(processAwardDependentUri, request.getClass().getSimpleName(), rootCause.getMessage()) );
            logger.error(" *** RBPS: [" + detailMessage + "]");

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

//            RbpsWebServiceClientUtil.stationid = oldStation;
            logUtils.debugExit( repository );
        }

        return (ProcessAwardDependentResponse) response;
    }


    private void setReadTimeoutFromProperties() {

        int value = getTimeoutConfigValue();

        for (WebServiceMessageSender sender : webServiceTemplate.getMessageSenders()) {

            if (sender instanceof CommonsHttpMessageSender) {
                CommonUtils.log(logger, "ProcessAwardDependent timeout value before: "
                        + getTimeout( sender ));

                ((CommonsHttpMessageSender) sender).setReadTimeout(value);

                CommonUtils.log(logger, "ProcessAwardDependent timeout value after: "
                        + getTimeout( sender ));
            }
        }
    }


    private int getTimeoutConfigValue() {

        try {
            int value = Integer.parseInt(processAwardDependentSoTimeout);
            CommonUtils.log(logger, "ProcessAwardDependent timeout property from rbps.properties: " + value);

            return value;
        }
        catch ( NumberFormatException ex ) {

            // The timeout value will default to 60 seconds
            CommonUtils.log(logger, "Error setting read timeout from property:\n" + ex.getMessage() + ex.getCause() );

            return (int) (5 * DateUtils.MILLIS_PER_MINUTE);
        }
    }


    private int getTimeout( final WebServiceMessageSender sender ) {

        return ((CommonsHttpMessageSender) sender).getHttpClient()
            .getHttpConnectionManager().getParams().getSoTimeout();
    }


    /*
	public void setCommonUtils(CommonUtils utils) {
		
		this.utils = utils;
	}
	*/



    public void setProcessAwardDependentRequestFilter( final ProcessAwardDependentRequestFilter processAwardDependentRequestFilter) {
        this.processAwardDependentRequestFilter = processAwardDependentRequestFilter;
    }

    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public void setProcessAwardDependentUri( final String processAwardDependentUri) {
	String clusterAddr = CommonUtils.getClusterAddress();
        this.processAwardDependentUri = clusterAddr + "/" + processAwardDependentUri;
    }

    public void setProcessAwardDependentSoTimeout( final String processAwardDependentSoTimeout) {
        this.processAwardDependentSoTimeout = processAwardDependentSoTimeout;
    }
}
