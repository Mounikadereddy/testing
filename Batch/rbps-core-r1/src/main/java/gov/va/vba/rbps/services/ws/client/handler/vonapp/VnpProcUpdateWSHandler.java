/*
 * VnpProcUpdateWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.ws.client.handler.vonapp;


//import java.util.Date;

import java.util.Date;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.interfaces.VnpProcUpdateWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimstatus.VnpProcDTO;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimstatus.VnpProcUpdate;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimstatus.VnpProcUpdateResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 *      Updates the claim status.  To "Manual" for example. Or, Started, Complete...
 */
public class VnpProcUpdateWSHandler implements VnpProcUpdateWSHandlerInterface {

    private static Logger logger = Logger.getLogger(VnpProcUpdateWSHandler.class);

    //protected CommonUtils           utils;
    private LogUtils                logUtils                = new LogUtils( logger, true );

    public String                  updateVnpProcUri;
    public WebServiceTemplate      webServiceTemplate;
    //public RbpsRepository          repository;



    @Override
    public VnpProcUpdateResponse vnpProcUpdate(RbpsRepository repository)  throws RbpsWebServiceClientException {

        logUtils.debugEnter( repository );

        if ( ! repository.validProcId() ) {

            logUtils.log( "There is no proc id, so can't call "+ this.getClass().getSimpleName(), repository );

            return new VnpProcUpdateResponse();
        }

        VnpProcUpdate request = buildVnpProcUpdateRequest(repository);

        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive( updateVnpProcUri,
                                                                 request,
                                                                 new HeaderSetter( updateVnpProcUri,
                                                                                   repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = "Call to WS "+ this.getClass().getSimpleName() + ".vnpProcUpdate failed for proc id: >" + repository.getVnpProcId() + "<";

            repository.addValidationMessage( CommonUtils.getValidationMessage(updateVnpProcUri, request.getClass().getSimpleName(), rootCause.getMessage()) );
            logUtils.log( " ***RBPS: [" + detailMessage + "]", repository );

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit( repository );
        }

        return (VnpProcUpdateResponse) response;
    }


    private VnpProcUpdate buildVnpProcUpdateRequest(RbpsRepository repository) {

        VnpProcUpdate vnpProcUpdate   = new VnpProcUpdate();
        VnpProcDTO    vnpProcDTO      = new VnpProcDTO();

        vnpProcDTO.setVnpProcId(repository.getVnpProcId());
        vnpProcDTO.setVnpProcTypeCd(RbpsConstants.PROC_TYPE);
        vnpProcDTO.setVnpProcStateTypeCd(repository.getVnpProcStateType());
        vnpProcDTO.setCreatdDt(repository.getVnpCreatdDt());
        vnpProcDTO.setLastModifdDt(SimpleDateUtils.dateToXMLGregorianCalendar(new Date()));
        vnpProcDTO.setJrnDt(repository.getJrnDt());
        vnpProcDTO.setJrnLctnId(repository.getJrnLctnId());
        vnpProcDTO.setJrnObjId(repository.getJrnObjId());
        vnpProcDTO.setJrnStatusTypeCd(repository.getJrnStatusTypeCd());
        vnpProcDTO.setJrnUserId(repository.getJrnUserId());
        vnpProcUpdate.setArg0(vnpProcDTO);

        logUtils.log( "\n\tproc id          " + repository.getVnpProcId()
                    + "\n\tnew status       " + repository.getVnpProcStateType(), repository );

        return vnpProcUpdate;
    }






    public void setUpdateVnpProcUri(final String updateVnpProcUri) {
    	String clusterAddr = CommonUtils.getClusterAddress();
        this.updateVnpProcUri = clusterAddr + "/" + updateVnpProcUri;
    }
    public void setWebServiceTemplate(final WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }
    /*
    public void setRepository(final RbpsRepository repository) {
        this.repository = repository;
    }
    */
}
