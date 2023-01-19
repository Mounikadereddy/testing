package gov.va.vba.rbps.rulesengine.child;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

abstract public class SchoolChildBaseRuleSet extends ChildBaseRuleSet {

    public SchoolChildBaseRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    protected boolean fcdrOutside365Days() {
        return
            RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
            !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate()) &&
            veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate());
    }

    protected boolean fcdrWithin365Days() {
        return
            RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
            !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate()) &&
            veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) <= 0 &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate());
    }

    protected boolean noFcdr() {
        return
            RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating());
    }

    protected boolean singleRating() {
        return
            RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
            veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate());
    }
}
