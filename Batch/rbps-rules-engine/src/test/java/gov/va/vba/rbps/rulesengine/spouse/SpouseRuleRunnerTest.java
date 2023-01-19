package gov.va.vba.rbps.rulesengine.spouse;


import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;

import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

import junit.framework.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SpouseRuleRunnerTest {

    Spouse spouse;
    SpouseRuleRunner runner;
    VeteranCommonDates commonDates;


    public SpouseRuleRunnerTest() throws ParseException {
        Spouse spouse = new Spouse();
        Marriage spouseMarriage = new Marriage();
        VeteranCommonDates commonDates = new VeteranCommonDates();
        Date marriageDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2015");

        spouseMarriage.setEndDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2019"));
        spouseMarriage.setStartDate(marriageDate);

        spouse.setBirthDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/1988"));
        spouse.setCurrentMarriage(spouseMarriage);
        spouse.setFirstName("Jane");
        spouse.setLastName("Doe");
        spouse.setSsn("123456789");

        commonDates.setMarriageDate(marriageDate);
        commonDates.setRatingDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016"));
        commonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2017"));
        commonDates.setRatingEffectiveDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2019"));
        commonDates.setAllowableDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2019"));
        commonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2019"));

        this.commonDates = commonDates;
        this.spouse = spouse;
        this.runner = new SpouseRuleRunner(spouse, commonDates);
    }


    @Test
    public void executeRules_ValidationExceptionNotGenerated() throws RuleEngineException {
        Response response = runner.executeRules();
        RuleExceptionMessages exceptions = (RuleExceptionMessages) response.getOutputParameters().get("Exceptions");
        Assert.assertTrue(exceptions.getMessages().isEmpty());
    }


    @Test
    public void executeRules_ValidationExceptionGenerated() throws RuleEngineException {
        spouse.setFirstName(null);
        spouse.setCurrentMarriage(null);
        Response response = runner.executeRules();
        RuleExceptionMessages exceptions = (RuleExceptionMessages) response.getOutputParameters().get("Exceptions");
        Assert.assertFalse(exceptions.getMessages().isEmpty());
    }
}