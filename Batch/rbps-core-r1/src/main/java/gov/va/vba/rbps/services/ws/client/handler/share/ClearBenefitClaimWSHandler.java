package gov.va.vba.rbps.services.ws.client.handler.share;



import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.ClearBenefitClaim;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.ClearBenefitClaim.ClearBenefitClaimInput;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.ClearBenefitClaimResponse;;




public class ClearBenefitClaimWSHandler extends BaseWSHandler<ClearBenefitClaim,ClearBenefitClaimResponse> {
	

    @Override
    protected ClearBenefitClaim buildRequest( final RbpsRepository        repository ) {

    	ClearBenefitClaim   	clearBenefitClaim   	= new ClearBenefitClaim();
    	ClearBenefitClaimInput	input					= new ClearBenefitClaimInput();

        input.setFileNumber( repository.getVeteran().getFileNumber() );
        input.setPayeeCode(RbpsConstants.PAYEE_CODE);
        input.setBenefitClaimType( RbpsConstants.BENEFIT_CLAIM_TYPE );
        input.setEndProductCode( repository.getVeteran().getClaim().getEndProductCode().getValue() );
        input.setIncremental( repository.getVeteran().getClaim().getEndProductCode().getValue() );

        /** 647228/298341, Completed Claims with Reject Claim Labels (RTC  298341) - ESCP 545 
         * When the letter is successfully generated, 
		 * PCLR_REASON_CODE_LTR	= "03J"
		 * The claim is Closed using the same process as PCLR.
		 * Claim less than 30% will be closed with reason code 65**/ 
      //SR# 723083
        if( (repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) || ( repository.getVeteranAbove30AndDenial() )){
        	 input.setPclrReasonCode( RbpsConstants.PCLR_REASON_CODE );
 	        input.setPclrReasonText( "Veteran less than 30% SC disabled" ); 
    	}
        else {
	        input.setPclrReasonCode( RbpsConstants.PCLR_REASON_CODE_LTR );
	        input.setPclrReasonText( " RBPS successful award and letter" ); 
        }
        clearBenefitClaim.setClearBenefitClaimInput( input );

        return clearBenefitClaim;
    }

}
