package gov.va.vba.rbps.rulesengine.veteran.rules;

import gov.va.vba.rbps.rulesengine.engine.BaseRuleSet;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.veteran.VeteranMessages;
import gov.va.vba.rbps.coreframework.xom.*;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;

public class VeteranRuleSet extends BaseRuleSet {


	protected final RuleExceptionMessages exceptionMessages;
	protected final Veteran veteran;

	public VeteranRuleSet(Veteran veteran, RuleExceptionMessages exceptionMessages) {
		this.veteran = veteran;
		this.exceptionMessages = exceptionMessages;
	}

	/*************************************************************
	 * 
	 *   RuleSet: CP0003 - Attachment Check
	 *   
	 *   	the claim of 'the Veteran' is present and
     *      the claim of 'the Veteran' has attachments
	 *   
	 *************************************************************/
	@Rule
	public void attachmentCheck() {
        if(RbpsXomUtil.isPresent(veteran.getClaim()) && veteran.getClaim().hasAttachments()) {
			this.exceptionMessages.addException(VeteranMessages.CONTAIN_ATTACHMENTS);
        }
    }

    /*************************************************************
	 * 
	 *   RuleSet: CP0007 - US Residence Verification
	 *   
	 *   	the mailing address of 'the Veteran' is not present
	 *   
	 *************************************************************/
	@Rule
	public void USResidenceVerification() {
    	if(RbpsXomUtil.isNotPresent(veteran.getMailingAddress())) {
    		this.exceptionMessages.addException(VeteranMessages.ADDRESS_NOT_PROVIDED);
    	}
    }

	/*************************************************************
	 *
	 *   RuleSet: CP0016 - Attorney Fee Agreement Check
	 *
	 *   	the existing award of 'the Veteran' is present and
	 *		the existing award of 'the Veteran' has an attorney fee agreement
	 *
	 *************************************************************/
	@Rule
	public void attorneyFeeAgreementCheck() {
		if(RbpsXomUtil.isPresent(veteran.getAwardStatus()) && veteran.getAwardStatus().hasAttorneyFeeAgreement()) {
			this.exceptionMessages.addException(VeteranMessages.ATTORNEY_FEE_CASE);
		}
	}

	/*************************************************************
	 *
	 *   RuleSet: CP0130 - Number of Dependents Check
	 *
	 *   	the existing award of 'the Veteran' is present and
	 *		the total number of dependents of 'the Veteran' is more than 20
	 *
	 *************************************************************/
	@Rule
	public void numberOfDependentsCheck() {
		if(RbpsXomUtil.isPresent(veteran.getAwardStatus()) && veteran.getTotalNumberOfDependents() > 20) {
			this.exceptionMessages.addException(VeteranMessages.DEPENDENT_LIMIT_EXCEEDED);
		}
	}
}
