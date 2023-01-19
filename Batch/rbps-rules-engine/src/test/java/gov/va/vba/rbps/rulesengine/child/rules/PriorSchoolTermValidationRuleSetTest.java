package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.School;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PriorSchoolTermValidationRuleSetTest {

    PriorSchoolTermValidationRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;

    public PriorSchoolTermValidationRuleSetTest() throws ParseException {
        response = new ChildResponse();
        VeteranCommonDates commonDates = new VeteranCommonDates();
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

        ruleSet = new PriorSchoolTermValidationRuleSet(child, response, decisionVariables);
    }

    @Before
    public void setUp() {
        decisionVariables.setCompleted(false);
        child.setLastTerm(new Education());
        child.setCurrentTerm(new Education ());
        response.getLogMessage().getMessages().clear();
        response.getExceptionMessages().getMessages().clear();
        decisionVariables.setPriorSchoolTermValid(true);
    }

    @Test
    public void priorSchoolTermPresent() {
        ruleSet.priorSchoolTermPresent();
        Assert.assertTrue(decisionVariables.isPriorSchoolTermValid());
        Assert.assertTrue(response.getLogMessage().getMessages().contains("last term present"));

        child.setLastTerm(null);
        ruleSet.priorSchoolTermPresent();
        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertTrue(response.getLogMessage().getMessages().contains("last term not present"));
    }

    @Test
    public void partTimeValidation() {
        child.setLastTerm(new Education());
        child.getLastTerm().setSessionsPerWeek(1);
        child.getLastTerm().setHoursPerWeek(2);

        ruleSet.partTimeValidation();

        System.out.println(response.getExceptionMessages().getMessages().toString());

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.PART_TIME, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void courseStartEndDateCheck() {
        ruleSet.courseStartEndDateCheck();
        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertEquals("ignore", response.getPriorSchoolTermStatus());
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.LAST_TERM_NOT_VALID));

    }

    @Test
    public void priorTermSameAsCurrentTerm() {
        Date date = new Date();
        child.getLastTerm().setCourseStudentStartDate(date);
        child.getCurrentTerm().setCourseStudentStartDate(date);
        child.getLastTerm().setCourseEndDate(date);
        child.getCurrentTerm().setCourseEndDate(date);

        ruleSet.priorTermSameAsCurrentTerm();

        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertEquals("ignore", response.getPriorSchoolTermStatus());
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.LAST_TERM_SAME_AS_CURRENT));
    }

    @Test
    public void priorEndDateBeforePriorStartDateCheck() throws ParseException {
        child.getLastTerm().setCourseStudentStartDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-02-01"));
        child.getLastTerm().setCourseEndDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-01-31"));

        ruleSet.priorEndDateBeforePriorStartDateCheck();

        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertEquals("ignore", response.getPriorSchoolTermStatus());
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.LAST_TERM_NOT_VALID));
    }

    @Test
    public void priorCourseStartFutureDateCheck() throws ParseException {
        child.getLastTerm().setCourseStudentStartDate(RbpsXomUtil.addDaysToDate(1, new Date()));
        ruleSet.priorCourseStartFutureDateCheck();

        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertEquals("ignore", response.getPriorSchoolTermStatus());
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.LAST_TERM_NOT_VALID));

    }

    @Test
    public void tuitionFeePaidByGovt() {
        child.setTuitionPaidByGovt(true);
        ruleSet.tuitionFeePaidByGovt();

        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertEquals("ignore", response.getPriorSchoolTermStatus());
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.LAST_TERM_NOT_VALID));
    }

    @Test
    public void childSchoolNameVerification() {
        child.getLastTerm().setSchool(new School());


        ruleSet.childSchoolNameVerification();

        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertEquals("Reject", response.getPriorSchoolTermStatus());
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.LAST_TERM_NOT_VALID));
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(
                String.format(ChildMessages.MISSING_SCHOOL_NAME, child.getLastName(), child.getFirstName())
        ));

    }

    @Test
    public void childSchoolAddressVerification() {
        child.getLastTerm().setSchool(new School());


        ruleSet.childSchoolAddressVerification();

        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertEquals("Reject", response.getPriorSchoolTermStatus());
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.LAST_TERM_NOT_VALID));
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(
                String.format(ChildMessages.MISSING_SCHOOL_ADDRESS, child.getLastName(), child.getFirstName())
        ));
    }

    @Test
    public void priorTermIncludedInAward() throws ParseException {
        Date lastTermInCorpBeginDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016");
        Date lastTermInCorpEndDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016");

        child.getLastTerm().setCourseStudentStartDate(RbpsXomUtil.addDaysToDate(1, lastTermInCorpBeginDate));
        child.getLastTerm().setCourseEndDate(lastTermInCorpEndDate);

        child.setLastTermInCorpEndDate(lastTermInCorpEndDate);
        child.setLastTermInCorpBeginDate(lastTermInCorpBeginDate);


        ruleSet.priorTermIncludedInAward();

        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertEquals("ignore", response.getPriorSchoolTermStatus());
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.LAST_TERM_NOT_VALID));
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(
                String.format(ChildMessages.PRIOR_TERM_INCLUDED_IN_CURRENT_AWARD, child.getLastName(), child.getFirstName())
        ));

    };
}