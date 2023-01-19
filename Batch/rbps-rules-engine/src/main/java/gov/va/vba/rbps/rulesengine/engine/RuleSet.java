package gov.va.vba.rbps.rulesengine.engine;
import gov.va.vba.rbps.coreframework.xom.Person;

import java.lang.reflect.InvocationTargetException;

public interface RuleSet {
    void run() throws InvocationTargetException, IllegalAccessException, RuleEngineException;
}
