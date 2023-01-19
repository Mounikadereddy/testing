package gov.va.vba.rbps.rulesengine.veteran.rules;

import gov.va.vba.rbps.rulesengine.mockobjectbuilders.VeteranObjectBuilder;
import gov.va.vba.rbps.rulesengine.veteran.VeteranMessages;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class VeteranMarriageRuleSetTest {
    private Veteran veteran;
    private VeteranMarriageRuleSet ruleSet;
    RuleExceptionMessages exceptionMessages;

    @Before
    public void setUp() {

        exceptionMessages = new RuleExceptionMessages();


        System.out.println("\n\nSetting Mock Veteran Object to default values");

        veteran = new Veteran();
        veteran = VeteranObjectBuilder.createVeteran();
        veteran.setLatestPreviousMarriage(VeteranObjectBuilder.createPreviousSpouse());
        veteran.setPreviousMarriages(new ArrayList<Marriage>() {{ add(veteran.getLatestPreviousMarriage()); }});
        veteran.setCurrentMarriage(VeteranObjectBuilder.createCurrentSpouse());
        veteran.setChildren(VeteranObjectBuilder.createChildren(veteran));

        ruleSet = new VeteranMarriageRuleSet(veteran, exceptionMessages);
    }

    @Test
    public void testVeteranMarriageValidation() {
        System.out.println("Running Veteran Marriage Validation. No exceptions expected.");

        ruleSet.veteranMarriageValidation();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertFalse(exceptionMessages.getMessages().contains(VeteranMessages.PRIOR_MARRIAGE_END_DATE));
    }

    @Test
    public void testVeteranMarriageValidation_WithException() {
        System.out.println("Running Veteran Marriage Validation. Exception message is expected.");
        System.out.println("Setting Veteran's previous marriage end date to after the start date of current marriage.");

        Date date = veteran.getCurrentMarriage().getStartDate();
        date = RbpsXomUtil.addYearsToDate(1, date);
        veteran.getLatestPreviousMarriage().setEndDate(date);

        ruleSet.veteranMarriageValidation();

        System.out.println("\nException(s):");
        System.out.println(exceptionMessages.getMessages().toString());
        Assert.assertTrue(exceptionMessages.getMessages().contains(VeteranMessages.PRIOR_MARRIAGE_END_DATE));
    }

}