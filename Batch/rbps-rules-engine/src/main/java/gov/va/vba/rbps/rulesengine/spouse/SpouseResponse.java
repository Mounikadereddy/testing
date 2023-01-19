package gov.va.vba.rbps.rulesengine.spouse;

import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpouseResponse implements Response {

    private final RuleExceptionMessages exceptionMessages = new RuleExceptionMessages();
    private final Award award = new Award();

    @Override
    public Map<String, Object> getOutputParameters() {
        Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("Award", award);
        finalResponse.put("Exceptions", exceptionMessages);
        return finalResponse;
    }

    public RuleExceptionMessages getExceptionMessages() {
        return exceptionMessages;
    }

    public Award getAward() {
        return award;
    }
}
