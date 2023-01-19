package gov.va.vba.rbps.services.ws.client.handler.rbps;


import gov.va.vba.rbps.services.ws.client.mapping.rbps.ObjectFactory;
import gov.va.vba.rbps.services.ws.client.mapping.rbps.ProcessRbpsAmendDependency;
import gov.va.vba.rbps.services.ws.client.mapping.rbps.ProcessRbpsAmendDependencyResponse;
import gov.va.vba.rbps.services.ws.client.util.RbpsWebServiceClientUtil;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageCallback;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.SoapMessage;


/**
 *      A basic Client to call the Rbps WS
 *      This will allow the test harness JSP to display
 *      result from RBPS execute
 *
 *      As far as I can tell, this isn't used.  The one in the
 *      rbps-client project is used.
 */
public class RbpsWsClient extends WebServiceGatewaySupport {

    public ProcessRbpsAmendDependencyResponse runRbps() {

        ObjectFactory                         objFactory  = new ObjectFactory();
        ProcessRbpsAmendDependency            request     = objFactory.createProcessRbpsAmendDependency();
        ProcessRbpsAmendDependencyResponse    response    = null;

        //response = (ProcessRbpsAmendDependencyResponse)getWebServiceTemplate().marshalSendAndReceive(request);

        response = (ProcessRbpsAmendDependencyResponse) getWebServiceTemplate().marshalSendAndReceive( request,
                new WebServiceMessageCallback() {
                    @Override
                    public void doWithMessage(final WebServiceMessage message) {
                        SoapMessage soapMessage = (SoapMessage) message;
                        /* Set the BEP CSS security header before sending
                           the SOAP message */
                        RbpsWebServiceClientUtil.setHeader(soapMessage, null );
                    }
                });

        return response;
    }
}
