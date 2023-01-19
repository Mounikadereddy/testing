package gov.va.vba.rbps.rulesengine.spouse.rules;

import gov.va.vba.rbps.rulesengine.spouse.SpouseDecisionVariables;
import gov.va.vba.rbps.rulesengine.spouse.SpouseMessages;
import gov.va.vba.rbps.rulesengine.spouse.SpouseResponse;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import org.junit.Test;
import junit.framework.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SpouseValidationRuleSetTest {

    Spouse spouse;
    SpouseValidationRuleSet ruleSet;
    protected SpouseResponse response = new SpouseResponse();
    protected SpouseDecisionVariables variables = new SpouseDecisionVariables();
    protected VeteranCommonDates commonDates = new VeteranCommonDates();

    public SpouseValidationRuleSetTest() {
        spouse = new Spouse();
        ruleSet = new SpouseValidationRuleSet(spouse, response, variables);
    }


    @Test
    public void testSpouseDobVerification() {
        Marriage marriage = new Marriage();
        marriage.setEndDate(new Date());

        spouse.setCurrentMarriage(marriage);
        spouse.setBirthDate(new Date());

        ruleSet.spouseDobVerification();

        Assert.assertEquals(false, variables.isExceptionGenerated());
        Assert.assertEquals(false, response.getExceptionMessages().getMessages().contains(SpouseMessages.DOB_NOT_PROVIDED));
    }

    @Test
    public void testSpouseDobVerification_WithException() {
        spouse.setCurrentMarriage(new Marriage());
        ruleSet.spouseDobVerification();
        Assert.assertEquals(true, variables.isExceptionGenerated());
        Assert.assertEquals(true, response.getExceptionMessages().getMessages().contains(SpouseMessages.DOB_NOT_PROVIDED));
    }


    @Test
    public void testSpouseFirstNameVerification() {
        spouse.setFirstName("Jane");

        ruleSet.spouseFirstNameVerification();

        Assert.assertEquals(false, variables.isExceptionGenerated());
        Assert.assertEquals(false, response.getExceptionMessages().getMessages().contains(SpouseMessages.FIRST_NAME_NOT_PROVIDED));
    }


    @Test
    public void testSpouseFirstNameVerification_WithException() {
        ruleSet.spouseFirstNameVerification();
        Assert.assertEquals(true, variables.isExceptionGenerated());
        Assert.assertEquals(true, response.getExceptionMessages().getMessages().contains(SpouseMessages.FIRST_NAME_NOT_PROVIDED));
    }


    @Test
    public void testSpouseLastNameVerification() {
        spouse.setLastName("Doe");

        ruleSet.spouseLastNameVerification();

        Assert.assertEquals(false, variables.isExceptionGenerated());
        Assert.assertEquals(false, response.getExceptionMessages().getMessages().contains(SpouseMessages.LAST_NAME_NOT_PROVIDED));
    }


    @Test
    public void testSpouseLastNameVerification_WithException() {
        ruleSet.spouseLastNameVerification();
        Assert.assertEquals(true, variables.isExceptionGenerated());
        Assert.assertEquals(true, response.getExceptionMessages().getMessages().contains(SpouseMessages.LAST_NAME_NOT_PROVIDED));
    }


    @Test
    public void testIsSpouseExistingOnAward() {
        spouse.setCurrentMarriage(new Marriage());
        spouse.setOnCurrentAward(false);

        ruleSet.isSpouseExistingOnAward();

        Assert.assertEquals(false, variables.isExceptionGenerated());
        Assert.assertEquals(false, response.getExceptionMessages().getMessages().contains(SpouseMessages.ALREADY_ON_EXISTING_AWARD));
    }



    @Test
    public void testIsSpouseExistingOnAward_WithException() {

        spouse.setCurrentMarriage(new Marriage());
        spouse.setOnCurrentAward(true);

        ruleSet.isSpouseExistingOnAward();

        Assert.assertEquals(true, variables.isExceptionGenerated());
        Assert.assertEquals(true, response.getExceptionMessages().getMessages().contains(SpouseMessages.ALREADY_ON_EXISTING_AWARD));
    }



    @Test
    public void testSpouseMarriageVerification() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Marriage currentMarriage = new Marriage();
        currentMarriage.setStartDate(format.parse("2018-01-01"));

        Marriage latestPreviousMarriage = new Marriage ();
        latestPreviousMarriage.setEndDate(format.parse("2017-01-01"));

        spouse.setCurrentMarriage(currentMarriage);
        spouse.setLatestPreviousMarriage(latestPreviousMarriage);

        ruleSet.spouseMarriageVerification();

        Assert.assertEquals(false, variables.isExceptionGenerated());
        Assert.assertEquals(false, response.getExceptionMessages().getMessages().contains(SpouseMessages.MARRIAGE_VERIFICATION_FAIL));
    }


    @Test
    public void testSpouseMarriageVerification_WithException() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Marriage currentMarriage = new Marriage();
        currentMarriage.setStartDate(format.parse("2018-01-01"));

        Marriage latestPreviousMarriage = new Marriage ();
        latestPreviousMarriage.setEndDate(new Date());

        spouse.setCurrentMarriage(currentMarriage);
        spouse.setLatestPreviousMarriage(latestPreviousMarriage);

        ruleSet.spouseMarriageVerification();

        Assert.assertEquals(true, variables.isExceptionGenerated());
        Assert.assertEquals(true, response.getExceptionMessages().getMessages().contains(SpouseMessages.MARRIAGE_VERIFICATION_FAIL));

    }


    @Test
    public void testSpouseSsnVerification() {
        spouse.setCurrentMarriage(new Marriage());
        spouse.setSsn("123456789");

        ruleSet.spouseSsnVerification();

        Assert.assertEquals(false, variables.isExceptionGenerated());
        Assert.assertEquals(false, response.getExceptionMessages().getMessages().contains(SpouseMessages.SSN_VERIFICATION_FAIL));
    }


    @Test
    public void testSpouseSsnVerification_WithException() {
        spouse.setCurrentMarriage(new Marriage());
        ruleSet.spouseSsnVerification();

        Assert.assertEquals(true, variables.isExceptionGenerated());
        Assert.assertEquals(true, response.getExceptionMessages().getMessages().contains(SpouseMessages.SSN_VERIFICATION_FAIL));

    }

}