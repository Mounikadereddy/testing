package gov.va.vba.rbps.services.ws.client.handler.share;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.interfaces.FindFiduciaryWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFiduciary;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFiduciaryResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.ShrinqfPersonOrg;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;


/**
 *      Finds out if the Veteran has a fiduciary.  That affects the letter
 *      and who it gets sent to.
 */
public class FindFiduciaryWSHandler implements FindFiduciaryWSHandlerInterface{

    private static Logger logger = Logger.getLogger(FindFiduciaryWSHandler.class);

    //private CommonUtils       utils;
    private final LogUtils          logUtils        = new LogUtils( logger, true );

    // Spring beans references/injection
    private WebServiceTemplate      webServiceTemplate;
    private String                  findFiduciaryUri;
//    private RbpsRepository          repository;


    /**
     * This method calls the SHARE service
     * FamilyTree.findFiduciary
     *
     * @return findFiduciary
     * @throws RbpsWebServiceClientException
     */
    @Override
    public FindFiduciaryResponse findFiduciary(RbpsRepository repository) throws RbpsWebServiceClientException {

        FindFiduciary request = buildFindFiduciaryRequest(repository);

        logUtils.debugEnter( repository);

        FindFiduciaryResponse  response;

        try {
            response = (FindFiduciaryResponse) webServiceTemplate.marshalSendAndReceive(
                    findFiduciaryUri,
                    request,
                    new HeaderSetter( findFiduciaryUri, repository.getClaimStationAddress() ) );
        }
        catch ( SoapFaultClientException  fault ) {

            if ( fault.getMessage().contains( "No records" ) ) {

                FindFiduciaryResponse   faultResponse   = new FindFiduciaryResponse();
                ShrinqfPersonOrg        record          = new ShrinqfPersonOrg();

                faultResponse.setReturn( record );

                return faultResponse;
            }

            String detailMessage = "Call to WS FamilyTree.findFiduciary failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage(findFiduciaryUri, request.getClass().getSimpleName(), fault.getMessage()) );

            logger.error(" ***RBPS: [" + detailMessage + "]");

            throw new RbpsWebServiceClientException(detailMessage, fault);
        }
        catch (Throwable rootCause) {
            String detailMessage = "Call to WS FamilyTree.findFiduciary failed";
            logger.error(" ***RBPS: [" + detailMessage + "]");

            repository.addValidationMessage( CommonUtils.getValidationMessage(findFiduciaryUri, request.getClass().getSimpleName(), rootCause.getMessage()) );

            System.out.println( "type of exception: " + rootCause.getClass() );

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit(repository );
        }

//        logUtils.log( "FindFiduciaryWSHandler: " );
//        for ( Shrinq3Person person : response.getReturn().getPersons() ) {
//            logUtils.log( utils.stringBuilder( person ) );
//        }

//        if ( response != null
//                && response.getReturn() != null ) {
//            logUtils.log( ToStringBuilder.reflectionToString( response.getReturn(),ToStringStyle.MULTI_LINE_STYLE ));
//        }

        return response;
    }

    private FindFiduciary buildFindFiduciaryRequest(RbpsRepository repository) {

        FindFiduciary      data = new FindFiduciary();
        data.setFileNumber( repository.getVeteran().getFileNumber() );

        return data;
    }



    /*
	public void setCommonUtils(CommonUtils utils) {
		
		this.utils = utils;
	}
	*/

    public void setFindFiduciaryUri( final String findFiduciaryUri ) {

    	String clusterAddr = CommonUtils.getClusterAddress(); 
        this.findFiduciaryUri = clusterAddr + "/" + findFiduciaryUri;
    }
//    public void setRepository( final RbpsRepository repository ) {
//
//        this.repository = repository;
//    }
    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
