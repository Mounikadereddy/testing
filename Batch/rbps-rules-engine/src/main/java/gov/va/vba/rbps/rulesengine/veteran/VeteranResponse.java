package gov.va.vba.rbps.rulesengine.veteran;

import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;

import java.util.HashMap;
import java.util.Map;

public class VeteranResponse implements Response {

    private RuleExceptionMessages exceptionMessages;

    @Override
    public Map<String, Object> getOutputParameters() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("Exceptions", exceptionMessages);
        return response;
    }

    public RuleExceptionMessages getExceptionMessages() {
        return exceptionMessages;
    }

    public void setExceptionMessages(final RuleExceptionMessages exceptionMessages) {
        this.exceptionMessages = exceptionMessages;
    }
}
