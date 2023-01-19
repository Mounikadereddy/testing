package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.MarriageTerminationType;
import gov.va.vba.rbps.coreframework.xom.Spouse;

import java.util.Date;

public class MarriageObjectBuilder {

    public static Marriage createMarriage(Date startDate, MarriageTerminationType termType, Spouse spouse) {
        Marriage marriage = new Marriage();

        marriage.setStartDate(startDate);
        marriage.setTerminationType(termType);
        marriage.setMarriedTo(spouse);

        return marriage;
    }

}
