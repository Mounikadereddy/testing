/*
 * UpdateBenefitClaimDependentsWSHandler.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.share;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
//import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.populators.UpdateBenefitClaimDependentsPopulator;
import gov.va.vba.rbps.services.ws.client.interfaces.UpdateBenefitClaimDependentsWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimDependents;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimDependentsResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

import org.springframework.ws.client.core.WebServiceTemplate;


/**
 *      Calls the web service to add dependents to the corporate database.
 */
public class UpdateBenefitClaimDependentsWSHandler implements UpdateBenefitClaimDependentsWSHandlerInterface {

    private static Logger logger = Logger.getLogger(UpdateBenefitClaimDependentsWSHandler.class);

   //private CommonUtils           utils;
    private final LogUtils              logUtils    = new LogUtils( logger, true );

    // Spring beans references/injection
    private WebServiceTemplate      webServiceTemplate;
    private String                  updateBenefitClaimDependentsUri;
//    private RbpsRepository          repository;



    @Override
    public UpdateBenefitClaimDependentsResponse updateDependents(RbpsRepository repository) throws RbpsWebServiceClientException {

        UpdateBenefitClaimDependents request = buildRequest(repository);

//        call( request );

        logUtils.debugEnter( repository );

        UpdateBenefitClaimDependentsResponse response = null;
        try {
            response = (UpdateBenefitClaimDependentsResponse) webServiceTemplate.marshalSendAndReceive(
                    updateBenefitClaimDependentsUri,
                    request,
                    new HeaderSetter( updateBenefitClaimDependentsUri,
                    		repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = String.format( "Call to WS %s.%s failed", this.getClass().getSimpleName(), updateBenefitClaimDependentsUri );

            repository.addValidationMessage( CommonUtils.getValidationMessage(updateBenefitClaimDependentsUri, request.getClass().getSimpleName(), rootCause.getMessage()) );
            logUtils.log( " ***RBPS: [" + detailMessage + "]", repository);

            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit( repository );
        }

        return response;
    }


    protected UpdateBenefitClaimDependents buildRequest(RbpsRepository repository) {

        UpdateBenefitClaimDependentsPopulator   populator ;
        UpdateBenefitClaimDependents            data;

        populator   = new UpdateBenefitClaimDependentsPopulator();
        //populator.setCommonUtils(utils);
        data        = populator.buildAddDependentRequest( repository );

//        CommonUtils utils = new CommonUtils();

        String msg = "";

        msg += String.format( "\n\tsending file number:     %s", repository.getVeteran().getFileNumber() );
        msg += String.format( "\n\tnum dependents adding:   %s", data.getDependentUpdateInput().getNumberOfDependents() );
        msg += String.format( "\nrequest                   >\n%s\n<", CommonUtils.stringBuilder( data ) );
        msg += String.format( "\ndependent input           >\n%s\n<", CommonUtils.stringBuilder( data.getDependentUpdateInput() ) );
        msg += String.format( "\ndependents                >\n%s\n<", CommonUtils.stringBuilder( data.getDependentUpdateInput().getDependents() ) );

        logUtils.log( msg,repository);

        return data;
    }


//  catch ( SoapFaultClientException  ex ) {
//
//                //
//                //      If we have a duplicate record exception, we don't care,
//                //      in that case we know the spouse was added to corporate.
//                //
//                if ( ex.getMessage().contains( "Duplicate Veteran Records" ) ) {
//
//                    logUtils.log( "UpdateBenefitClaimDependentsWSHandler: sent request, got 'Duplicate Veteran Records' back." + ex.getMessage() );
//
//                    //
//                    //      We don't do anything with this data structure.
//                    //      The important thing is if we update something.
//                    //
//                    UpdateBenefitClaimDependentsResponse    faultResponse   = new UpdateBenefitClaimDependentsResponse();
//                    BenefitClaimRecord                      record          = new BenefitClaimRecord();
//
//                    faultResponse.setReturn( record );
//
//                    logUtils.log( "\n\t<---- >"+ this.getClass() + "." + updateBenefitClaimDependentsUri + "< (duplicate veteran records)" );
//
//                    return faultResponse;
//                }
//
//                String detailMessage = "Call to WS FamilyTree.findDependents failed";
//                logger.error(" ***RBPS: [" + detailMessage + "]");
//
//                throw new RbpsWebServiceClientException(detailMessage, ex);
//            }

    /*
	public void setCommonUtils(CommonUtils utils) {
		
		this.utils = utils;
	}
	*/

    /***
     * Setter injection
     * @param updateBenefitClaimDependentsUri
     */
    public void setUpdateBenefitClaimDependentsUri( final String udpateBenefitClaimDependentsUri ) {

    	String clusterAddr = CommonUtils.getClusterAddress();
        this.updateBenefitClaimDependentsUri = clusterAddr + "/" + udpateBenefitClaimDependentsUri;
    }

    /***
     * Setter injection
     * @param rbpsRepository
     */
//    public void setRepository( final RbpsRepository repository ) {
//
//        this.repository = repository;
//    }

    /***
     * Setter injection
     * @param webServiceTemplate
     */
    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
