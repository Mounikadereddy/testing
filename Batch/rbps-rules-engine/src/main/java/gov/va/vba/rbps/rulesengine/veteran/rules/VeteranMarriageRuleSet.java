package gov.va.vba.rbps.rulesengine.veteran.rules;

import gov.va.vba.rbps.rulesengine.engine.BaseRuleSet;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.coreframework.xom.*;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;

public class VeteranMarriageRuleSet extends BaseRuleSet {

	protected final RuleExceptionMessages exceptionMessages;
	protected final Veteran veteran;

	public VeteranMarriageRuleSet(Veteran veteran, RuleExceptionMessages exceptionMessages) {
		this.veteran = veteran;
		this.exceptionMessages = exceptionMessages;
	}


	/*************************************************************
	 * 
	 *   RuleSet: CP0105 - Veteran Marriage Validation
	 *   
	 *   	the current marriage of 'the Veteran' is present and
     *		the latest previous marriage of 'the Veteran' is present and
	 *		the start date of the current marriage of 'the Veteran' is before the end date of the latest previous marriage of 'the Veteran'
	 *   
	 *************************************************************/
	@Rule
	public void veteranMarriageValidation() {
		if(RbpsXomUtil.isPresent(veteran.getCurrentMarriage()) && RbpsXomUtil.isPresent(veteran.getLatestPreviousMarriage())) {

			Marriage currentMarriage = veteran.getCurrentMarriage();
			Marriage latestPreviousMarriage = veteran.getLatestPreviousMarriage();

			if(currentMarriage.getStartDate().before(latestPreviousMarriage.getEndDate())){
				this.exceptionMessages.addException("Auto Dependency Processing Reject Reason - Submitted Veteran's prior marriage date(s) do not terminate before date of current marriage. Please review.");
			}
        }
    }
	
}
