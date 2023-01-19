package gov.va.vba.rbps.services.ws.client.handler.awards;

/*
 * UpdateBnftClaimLocationToPMCWSHandler
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */



import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.UpdateBnftClaimLocationToPMC;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.UpdateBnftClaimLocationToPMCResponse;

public class UpdateBnftClaimLocationToPMCWSHandler extends BaseWSHandler<UpdateBnftClaimLocationToPMC,UpdateBnftClaimLocationToPMCResponse> {
	 private Logger logger = Logger.getLogger(this.getClass());


    @Override
    protected UpdateBnftClaimLocationToPMC buildRequest( final RbpsRepository        repo ) {

    	logger.debug("UpdateBnftClaimLocationToPMCWSHandler.buildRequest start");
        UpdateBnftClaimLocationToPMC   updateBnftClaimLocationToPMC   = new UpdateBnftClaimLocationToPMC();
        long                           claimId                        = repo.getVeteran().getClaim().getClaimId();
        boolean                        processingPensions             = repo.isProcessPensions();

        updateBnftClaimLocationToPMC.setClaimID( claimId );
        updateBnftClaimLocationToPMC.setSsn(repo.getVeteran().getSsn());
        updateBnftClaimLocationToPMC.setProcessingPensions(processingPensions);
        
        logger.debug("UpdateBnftClaimLocationToPMCWSHandler.buildRequest start");
        return updateBnftClaimLocationToPMC;
    }
}
