package gov.va.vba.rbps.rulesengine.engine;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

abstract public class BaseRuleSet implements RuleSet {

    public void run () throws RuleEngineException {
        Method[] methods = this.getClass().getMethods();
        for (Method m : methods){
            if (m.isAnnotationPresent(Rule.class)){
                try {
                    m.invoke(this);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuleEngineException("Unable to invoke annotated rule method: " + m.getName() + ", " + e.getCause());
                }
            }
        }
    }
}
