package gov.va.vba.rbps.rulesengine.engine;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public interface RuleRunner {
    Response executeRules() throws InvocationTargetException, IllegalAccessException, RuleEngineException;
}
