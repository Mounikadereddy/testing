package gov.va.vba.rbps.rulesengine.mockobjectbuilders;

import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;

import java.util.Date;

public class AwardObjectBuilder {
    public static Award createAward(DependentDecisionType dependentDecisionType, DependentStatusType dependentStatusType, Date eventDate) {
        Award award = new Award();

        award.setDependencyDecisionType(dependentDecisionType);
        award.setDependencyStatusType(dependentStatusType);
        award.setEventDate(eventDate);

        return award;
    }
}
