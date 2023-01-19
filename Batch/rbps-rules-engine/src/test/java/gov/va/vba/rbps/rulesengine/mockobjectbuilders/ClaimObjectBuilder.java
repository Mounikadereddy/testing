package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.Claim;
import gov.va.vba.rbps.coreframework.xom.EndProductType;

public class ClaimObjectBuilder {

    public static Claim createClaim() {
        Claim claim = new Claim();

        claim.setHasAttachments(false);
        claim.setNew(false);
        claim.setClaimId(999999999);
        claim.setReceivedDate(DateUtils.convertDate("01/01/2018"));
        claim.setEndProductCode(EndProductType.COMPENSATION_EP130);

        return claim;
    }
}
