package gov.va.vba.rbps.rulesengine.spouse.rules;

import gov.va.vba.rbps.rulesengine.spouse.SpouseDecisionVariables;
import gov.va.vba.rbps.rulesengine.spouse.SpouseMessages;
import gov.va.vba.rbps.rulesengine.spouse.SpouseResponse;
import gov.va.vba.rbps.coreframework.xom.*;
import junit.framework.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SpouseRemovalRuleSetTest {

    Spouse spouse;
    SpouseRemovalRuleSet ruleSet;
    protected SpouseResponse response = new SpouseResponse();
    protected SpouseDecisionVariables variables = new SpouseDecisionVariables();

    public SpouseRemovalRuleSetTest() {
        spouse = new Spouse();
        ruleSet = new SpouseRemovalRuleSet(spouse, response, variables);
    }

    @Test
    public void testAwardDecisionDueToDeath() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = format.parse("2019-01-01");

        Marriage marriage = new Marriage();
        marriage.setTerminationType(MarriageTerminationType.DEATH);
        marriage.setEndDate(endDate);

        spouse.setCurrentMarriage(marriage);

        ruleSet.awardDecisionDueToDeath();

        Assert.assertEquals(endDate, response.getAward().getEventDate());
        Assert.assertEquals(DependentDecisionType.DEATH, response.getAward().getDependencyDecisionType());
        Assert.assertEquals(DependentStatusType.NOT_AN_AWARD_DEPENDENT, response.getAward().getDependencyStatusType());
    }



    @Test
    public void testAwardDecisionDueToDivorce() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = format.parse("2019-01-01");

        Marriage marriage = new Marriage();
        marriage.setTerminationType(MarriageTerminationType.DIVORCE);
        marriage.setEndDate(endDate);

        spouse.setCurrentMarriage(marriage);

        ruleSet.awardDecisionDueToDivorce();

        Assert.assertEquals(endDate, response.getAward().getEventDate());
        Assert.assertEquals(DependentDecisionType.MARRIAGE_TERMINATED, response.getAward().getDependencyDecisionType());
        Assert.assertEquals(DependentStatusType.NOT_AN_AWARD_DEPENDENT, response.getAward().getDependencyStatusType());
    }

    @Test
    public void testBeforeAwardEffectiveDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = format.parse("2019-07-01");
        Date awardEffectiveDate = format.parse("2019-06-01");

        Marriage marriage = new Marriage();
        marriage.setEndDate(endDate);

        spouse.setAwardEffectiveDate(awardEffectiveDate);
        spouse.setCurrentMarriage(marriage);

        ruleSet.beforeAwardEffectiveDate();

        Assert.assertEquals(false, variables.isExceptionGenerated());
        Assert.assertEquals(false, response.getExceptionMessages().getMessages().contains(SpouseMessages.BEFORE_AWARD_EFFECTIVE_DATE));
    }


    @Test
    public void testBeforeAwardEffectiveDate_WithException () throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = format.parse("2019-01-01");
        Date awardEffectiveDate = format.parse("2019-06-01");

        Marriage marriage = new Marriage();
        marriage.setEndDate(endDate);

        spouse.setAwardEffectiveDate(awardEffectiveDate);
        spouse.setCurrentMarriage(marriage);

        ruleSet.beforeAwardEffectiveDate();

        Assert.assertEquals(true, variables.isExceptionGenerated());
        Assert.assertEquals(true, response.getExceptionMessages().getMessages().contains(SpouseMessages.BEFORE_AWARD_EFFECTIVE_DATE));
    }


}