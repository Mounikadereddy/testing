package gov.va.vba.rbps.rulesengine.engine;

import gov.va.vba.rbps.rulesengine.child.ChildRulesRunner;
import gov.va.vba.rbps.rulesengine.spouse.SpouseRuleRunner;
import gov.va.vba.rbps.rulesengine.veteran.VeteranRuleRunner;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

import java.util.Map;

public class RulesEngine {

    public Response execute(Veteran veteran) throws RuleEngineException {
        return new VeteranRuleRunner(veteran).executeRules();
    }

    public Response execute(Spouse spouse, VeteranCommonDates commonDates) throws RuleEngineException {
        return new SpouseRuleRunner(spouse, commonDates).executeRules();
    }

    public Response execute(Child child, VeteranCommonDates commonDates) throws RuleEngineException {
        return new ChildRulesRunner(child, commonDates).executeRules();
    }
}
