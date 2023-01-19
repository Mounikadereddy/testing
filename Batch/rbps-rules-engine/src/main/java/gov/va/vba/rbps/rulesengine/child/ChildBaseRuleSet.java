package gov.va.vba.rbps.rulesengine.child;

import gov.va.vba.rbps.rulesengine.engine.BaseRuleSet;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

abstract public class ChildBaseRuleSet extends BaseRuleSet {


    protected final Child child;
    protected final ChildResponse childResponse;
    protected final ChildDecisionVariables childDecisionVariables;
    protected final VeteranCommonDates veteranCommonDates;


    public ChildBaseRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        this.child = child;
        this.childResponse = childResponse;
        this.childDecisionVariables = childDecisionVariables;
        this.veteranCommonDates = veteranCommonDates;
    }

    public ChildBaseRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables) {
        this.child = child;
        this.childResponse = childResponse;
        this.childDecisionVariables = childDecisionVariables;
        this.veteranCommonDates = null;
    }
}
