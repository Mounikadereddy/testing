package gov.va.vba.rbps.rulesengine.veteran;

import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.VeteranObjectBuilder;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

public class VeteranRuleRunnerTest {

    private VeteranRuleRunner ruleRunner;
    private Veteran veteran;

    @Before
    public void setUp() {
        System.out.println("\n\nSetting Mock Veteran Object to default values");

        veteran = new Veteran();
        veteran = VeteranObjectBuilder.createVeteran();
        veteran.setLatestPreviousMarriage(VeteranObjectBuilder.createPreviousSpouse());
        veteran.setPreviousMarriages(new ArrayList<Marriage>() {{ add(veteran.getLatestPreviousMarriage()); }});
        veteran.setCurrentMarriage(VeteranObjectBuilder.createCurrentSpouse());
        veteran.setChildren(VeteranObjectBuilder.createChildren(veteran));

        ruleRunner = new VeteranRuleRunner(veteran);
    }

    @Test
    public void executeRules() throws RuleEngineException {
        Response response = ruleRunner.executeRules();

        RuleExceptionMessages exceptions = (RuleExceptionMessages) response.getOutputParameters().get("Exceptions");
        System.out.println(exceptions.getMessages().toString());

        Assert.assertTrue(exceptions.getMessages().isEmpty());
    }

    @Test
    public void executeRules_WithExceptions() throws  RuleEngineException {
        veteran.getClaim().setHasAttachments(true);

        Response response = ruleRunner.executeRules();

        RuleExceptionMessages exceptions = (RuleExceptionMessages) response.getOutputParameters().get("Exceptions");
        System.out.println(exceptions.getMessages().toString());

        Assert.assertFalse(exceptions.getMessages().isEmpty());
    }
}