package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Spouse;

public class SpouseObjectBuilder {

    public static Spouse createSpouse(String fileNumber, boolean isOnCurrentAward, boolean isEverOnAward, Award award) {
        Spouse spouse = new Spouse();

        spouse.setFileNumber(fileNumber);
        spouse.setOnCurrentAward(isOnCurrentAward);
        spouse.setIsEverOnAward(isEverOnAward);
        spouse.setAward(award);

        return spouse;
    }

}
