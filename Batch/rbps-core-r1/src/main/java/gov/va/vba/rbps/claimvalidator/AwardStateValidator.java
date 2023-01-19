package gov.va.vba.rbps.claimvalidator;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.AwardStatus;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.rulesengine.veteran.VeteranMessages;

public class AwardStateValidator {

    protected final RuleExceptionMessages exceptionMessages;
    protected final Veteran veteran;

    public AwardStateValidator(RbpsRepository repository) {
        this.veteran = repository.getVeteran();
        this.exceptionMessages = repository.getRuleExceptionMessages();
    }

    public void validate() {

        /*************************************************************
         *
         *   RuleSet: CP0008 - Is Veteran Alive
         *
         *   	it is not true that 'the Veteran' is living
         *
         *************************************************************/
        if(!veteran.isAlive()) {
            this.exceptionMessages.addException(VeteranMessages.VETERAN_NOT_LIVING);
        }

        /*************************************************************
         *
         *   RuleSet: CP0012 - Award Changes Verification
         *
         *   	the existing award of 'the Veteran' is present and
         *		rating changes have been made to the existing award of 'the Veteran'
         *
         *************************************************************/
        if(RbpsXomUtil.isPresent(veteran.getAwardStatus()) && veteran.getAwardStatus().isProposed()) {
            this.exceptionMessages.addException(VeteranMessages.RATING_CHANGES);
        }

        /*************************************************************
         *
         *   RuleSet: CP0012 - Award Current Status Verification
         *
         *   	the existing award of 'the Veteran' is present and
         *		it is not true that the existing award of 'the Veteran' is current or
         *	 	the existing award of 'the Veteran' is suspended or terminated
         *
         *************************************************************/
        if(RbpsXomUtil.isPresent(veteran.getAwardStatus())) {

            AwardStatus awardStatus = veteran.getAwardStatus();
            if(!awardStatus.isRunningAward() || awardStatus.isSuspended()) {
                this.exceptionMessages.addException(VeteranMessages.AWARD_NOT_CURRENT_OR_SUSPENDED);
            }
        }

        /*************************************************************
         *
         *   RuleSet: CP0012 - Award GAO Usage Verification
         *
         *   	the existing award of 'the Veteran' is present and
         *   	the existing award of 'the Veteran' has used the GAO
         *
         *************************************************************/
        if(RbpsXomUtil.isPresent(veteran.getAwardStatus()) && veteran.getAwardStatus().isGAOUsed()) {
            this.exceptionMessages.addException(VeteranMessages.AWARD_USED_GAO);
        }

    }

}
