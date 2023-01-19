package gov.va.vba.rbps.services.ws.client.handler;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;
import org.springframework.ws.client.core.WebServiceTemplate;

public abstract class RbpsWSHandler {

    private static Logger logger = Logger.getLogger(RbpsWSHandler.class);

    private final LogUtils logUtils            = new LogUtils( logger, true );
    private WebServiceTemplate                  webServiceTemplate;
    private String                              webServiceUri;


    protected Object getResponse(final Object request, final RbpsRepository repo) throws RbpsWebServiceClientException {
        logUtils.debugEnter( repo );

        Object response;

        try {
            response = webServiceTemplate.marshalSendAndReceive(webServiceUri, request, new HeaderSetter( webServiceUri, repo.getClaimStationAddress() ));
        } catch (Throwable rootCause) {

            String detailMessage = "Call to WS " + request.getClass().getSimpleName() +" failed";

            repo.addValidationMessage( CommonUtils.getValidationMessage(webServiceUri, request.getClass().getSimpleName(), rootCause.getMessage()) );
            logger.error(" *** RBPS: [" + detailMessage + "]");

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        } finally {
            logUtils.debugExit( repo );
        }

        return response;
    }

    public void setWebServiceTemplate(final WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public void setWebServiceUri(final String webServiceUri) {
        String clusterAddr = CommonUtils.getClusterAddress();
        this.webServiceUri = clusterAddr + "/" + webServiceUri;
    }

}
