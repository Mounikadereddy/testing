/*
 * BaseWSHandler
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.ClaimStationAddress;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;


/**
 *      Base class for all WSHandlers.  Shares common code to call
 *      web services, but uses the Template Method pattern to delegate the building
 *      of the request and any specialized error handling.
 */
public abstract class BaseWSHandler<X,Y> {

    private static Logger logger = Logger.getLogger(BaseWSHandler.class);

    //private CommonUtils             utils;
    private LogUtils                logUtils                = new LogUtils( logger, true );


    // Spring beans references/injection
    private String                  webServiceUri;
    private WebServiceTemplate      webServiceTemplate;



    public Y call( final RbpsRepository     repo ) {

        X       request     = buildRequest( repo );

        return call( repo, request );
    }


    @SuppressWarnings( "unchecked" )
    public Y call( final RbpsRepository     repo,
                   final X                  request ) {

        Y       response    = null;

        logUtils.debugEnter( constructStackLabel( request ), repo );

        logger.debug("Calling web service at URL: " + webServiceUri);
        logger.debug("request: " + request);

        try {
            response = (Y) webServiceTemplate.marshalSendAndReceive( webServiceUri,
                                                                     request,
                                                                     new HeaderSetter( webServiceUri,
                                                                                       getClaimStationAddress( repo ) ) );
            logger.debug("response: " + response); 
            return response;
        }
        catch ( SoapFaultClientException  fault ) {

            return handleSoapFaultException( repo, request, fault );
        }
        catch (Throwable rootCause) {

            handleException( repo, request, rootCause );

            return response;
        }
        finally {

            logUtils.debugExit( constructStackLabel( request ), repo );
        }
    }


    public String constructStackLabel( final X request ) {

        return "BaseWSHandler.call( " + CommonUtils.simplifyUri( webServiceUri ) + "." + request.getClass().getSimpleName() + "() )";
    }


    public ClaimStationAddress getClaimStationAddress( final RbpsRepository repo ) {

        return repo.getClaimStationAddress();
    }


    protected void handleException( final RbpsRepository          repo,
                                    final X                       request,
                                    final Throwable               rootCause ) {

        String detailMessage =CommonUtils.getValidationMessage( webServiceUri,
                                                           request.getClass().getSimpleName(),
                                                           rootCause.getMessage() );

        repo.addValidationMessage( detailMessage );

        logUtils.log( "[" + detailMessage + "]", repo );

        throw new RbpsWebServiceClientException( detailMessage, rootCause );
    }


    protected Y handleSoapFaultException( final RbpsRepository               repo,
                                          final X                            request,
                                          final SoapFaultClientException     fault ) {

        handleException( repo, request, fault );

        return null;
    }


    protected abstract X buildRequest( RbpsRepository       repo );


    /*
      public CommonUtils getCommonUtils() {
     

        return this.utils;
    } 

*/

    /*public void setCommonUtils( final CommonUtils utils ) {

        this.utils = utils;
    } 
*/

    public void setWebServiceUri( final String webServiceUri ) {

    	String clusterAddr = CommonUtils.getClusterAddress();
        this.webServiceUri = clusterAddr + "/" + webServiceUri;
    }
    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
