package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class ChildValidationRuleSetTest {

    ChildValidationRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates commonDates;


    public ChildValidationRuleSetTest() throws ParseException {
        response = new ChildResponse();
        commonDates = new VeteranCommonDates();
        child = new Child();

        commonDates.setRatingDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016"));
        commonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2017"));
        commonDates.setRatingEffectiveDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2019"));
        commonDates.setAllowableDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2019"));
        commonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2019"));

        child.setFirstName("John");
        child.setLastName("Doe");
        child.setBirthDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/31/1993"));

        decisionVariables = RulesEngineUtil.getChildDecisionVariables(child, commonDates);

        ruleSet = new ChildValidationRuleSet(child, response, decisionVariables, commonDates);
    }

    @Before
    public void setUp() {
        decisionVariables.setCompleted(false);
    }

    @Test
    public void childDobVerification() {
        child.setBirthDate(null);
        ruleSet.childDobVerification();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.MISSING_DOB, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void childFirstNameVerification() {
        child.setFirstName(null);
        ruleSet.childFirstNameVerification();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(ChildMessages.MISSING_FIRST_NAME));
        Assert.assertEquals("First Name Not Provided", child.getFirstName());
    }

    @Test
    public void childLastNameVerification() {
        child.setLastName(null);
        ruleSet.childLastNameVerification();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(ChildMessages.MISSING_LAST_NAME));
        Assert.assertEquals("Last Name Not Provided", child.getLastName());
    }

    @Test
    public void evaluateAstepChild() {
        child.setChildType(ChildType.STEPCHILD);
        commonDates.setMarriageDate(null);
        ruleSet.evaluateAstepChild();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(ChildMessages.MISSING_VETERAN_MARRIAGE_DATE_FOR_STEP_CHILD));
    }

    @Test
    public void isChildPreviouslyMarried() {
        child.setPreviouslyMarried(true);
        ruleSet.isChildPreviouslyMarried();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.CHILD_PREVIOUSLY_MARRIED, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void biologicalChildAddressVerification() {
        child.setChildType(ChildType.BIOLOGICAL_CHILD);
        child.setLivingWithVeteran(false);
        child.setMailingAddress(null);

        ruleSet.biologicalChildAddressVerification();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.BIOLOGICAL_CHILD_NO_MAILING_ADDRESS, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void adoptedChildCheck() {
        child.setChildType(ChildType.ADOPTED_CHILD);
        ruleSet.adoptedChildCheck();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.ADOPTED_CHILD, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void childAgeAndSdCheck_A() throws ParseException {
        child.setSeriouslyDisabled(true);
        commonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2017"));
        ruleSet.childAgeAndSdCheck_A();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.CHILD_SERIOUSLY_DISABLED, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void childAgeAndSdCheck_B() throws ParseException {
        child.setSeriouslyDisabled(true);
        commonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/31/2017"));
        ruleSet.childAgeAndSdCheck_B();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.CHILD_SERIOUSLY_DISABLED, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void denyMarriedChild() {
        child.setCurrentMarriage(new Marriage());
        ruleSet.denyMarriedChild();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.MARRIED_CHILD, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void childSsnVerification() {
        child.setSsn(null);
        ruleSet.childSsnVerification();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.SSN_NOT_PROVIDED, child.getLastName(), child.getFirstName())));
    }
}