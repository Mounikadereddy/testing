package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.AwardStatus;

public class AwardStatusObjectBuilder {
    public static AwardStatus createAwardStatus() {
        AwardStatus awardStatus = new AwardStatus();

        awardStatus.setChangesMade(false);
        awardStatus.setIsSuspended(false);
        awardStatus.setGAOUsed(false);
        awardStatus.setHasAttorneyFeeAgreement(false);
        awardStatus.setRunningAward(true);
        awardStatus.setProposed(false);

        return awardStatus;
    }
}
