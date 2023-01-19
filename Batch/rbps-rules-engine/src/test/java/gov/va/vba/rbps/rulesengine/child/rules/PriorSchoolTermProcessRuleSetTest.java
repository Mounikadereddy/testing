package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.DateUtils;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.VeteranCommonDatesObjectBuilder;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class PriorSchoolTermProcessRuleSetTest {

    PriorSchoolTermProcessRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates commonDates;

    @Before
    public void setUp() throws ParseException {
        child = new Child();
        response = new ChildResponse();
        commonDates = VeteranCommonDatesObjectBuilder.createCommonDates();

        child.setBirthDate(DateUtils.convertDate("01/01/2010"));

        decisionVariables = RulesEngineUtil.getChildDecisionVariables(child, commonDates);

        ruleSet = new PriorSchoolTermProcessRuleSet(child, response, decisionVariables, commonDates);

    }

    @Test
    public void setSchoolAllowableDate_StepChild() {
        Date marriageDate = DateUtils.convertDate("04/01/2019");
        child.setChildType(ChildType.STEPCHILD);
        commonDates.setMarriageDate(marriageDate);

        ruleSet.setSchoolAllowableDate();

        Assert.assertTrue(decisionVariables.getSchoolAllowableDate().compareTo(marriageDate) == 0);
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.SET_SCHOOL_ALLOWABLE_DATE_TO_MARRIAGE));

    }

    @Test
    public void setSchoolAllowableDate() {
        child.setChildType(ChildType.BIOLOGICAL_CHILD);

        ruleSet.setSchoolAllowableDate();

        Assert.assertTrue(decisionVariables.getSchoolAllowableDate().compareTo(commonDates.getAllowableDate()) == 0);
        Assert.assertTrue(response.getLogMessage().getMessages().contains(ChildMessages.SET_SCHOOL_ALLOWABLE_DATE));

    }

    @Test
    public void checkSchoolAllowableDateTo18thBirthday() {
        decisionVariables.setSchoolAllowableDate(DateUtils.convertDate("01/01/2019"));

        ruleSet.checkSchoolAllowableDateTo18thBirthday();

        Assert.assertTrue(decisionVariables.getSchoolAllowableDate().compareTo(RbpsXomUtil.addYearsToDate(18, child.getBirthDate())) == 0);
    }

    @Test
    public void checkPriorBeginDateBeforeSchoolTerm() {
        child.setLastTerm(new Education());
        child.getLastTerm().setCourseStudentStartDate(DateUtils.convertDate("02/04/2019"));
        child.setLastTermInCorpBeginDate(DateUtils.convertDate("04/02/2019"));

        ruleSet.checkPriorBeginDateBeforeSchoolTerm();

        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(ChildMessages.PRIOR_DATE_IS_BEFORE_DATE_OF_SCHOOL_TERM));
        Assert.assertTrue(decisionVariables.isPriorConditionSatisfied());
        Assert.assertTrue(decisionVariables.isCompleted());
    }

    @Test
    public void priorSchoolTermEndDateIsBeforeAllowableDate() {
        child.setLastTerm(new Education());
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("05/05/2019"));
        decisionVariables.setSchoolAllowableDate(DateUtils.convertDate("06/06/2019"));

        ruleSet.priorSchoolTermEndDateIsBeforeAllowableDate();

        Assert.assertTrue(response.getPriorSchoolTermStatus().compareTo("ignore") == 0);
        Assert.assertFalse(decisionVariables.isPriorSchoolTermValid());
        Assert.assertTrue(decisionVariables.isPriorConditionSatisfied());
    }

    @Test
    public void setPriorSchoolBeginToAllowableDate() {
        child.setLastTerm(new Education());
        child.getLastTerm().setCourseStudentStartDate(DateUtils.convertDate("01/01/2019"));

        decisionVariables.setSchoolAllowableDate(DateUtils.convertDate("04/01/2019"));

        ruleSet.setPriorSchoolBeginToAllowableDate();

        Assert.assertTrue(child.getLastTerm().getCourseStudentStartDate().compareTo(decisionVariables.getSchoolAllowableDate()) == 0);
        Assert.assertTrue(response.getPriorSchoolChild().getEventDate().compareTo(decisionVariables.getSchoolAllowableDate()) == 0);
    }

    @Test
    public void lastTermInCorpBeginDateNotSet() {
        child.setLastTerm(new Education());
        child.getLastTerm().setCourseStudentStartDate(DateUtils.convertDate("05/05/2019"));

        ruleSet.lastTermInCorpBeginDateNotSet();

        Assert.assertTrue(response.getPriorSchoolChild().getEventDate().compareTo(child.getLastTerm().getCourseStudentStartDate()) == 0);
        Assert.assertTrue(decisionVariables.isPriorConditionSatisfied());
    }

    @Test
    public void setEventDateOfLastTermToStartDate() {
        child.setLastTermInCorpEndDate(DateUtils.convertDate("02/02/2018"));
        child.setLastTermInCorpEndEffectiveDate(DateUtils.convertDate("02/02/2018"));

        child.setLastTerm(new Education());
        Date startDate = DateUtils.convertDate("02/02/2019");
        child.getLastTerm().setCourseStudentStartDate(startDate);

        ruleSet.setEventDateOfLastTermToStartDate();

        Assert.assertTrue(response.getPriorSchoolChild().getEventDate().compareTo(startDate) == 0);
        Assert.assertTrue(decisionVariables.isPriorConditionSatisfied());
    }

    @Test
    public void setEventDateOfLastTermToEndDate() {
        child.setLastTermInCorpEndDate(DateUtils.convertDate("01/15/2019"));
        child.setLastTermInCorpEndEffectiveDate(DateUtils.convertDate("02/02/2019"));

        child.setLastTerm(new Education());
        child.getLastTerm().setCourseStudentStartDate(DateUtils.convertDate("02/02/2019"));

        ruleSet.setEventDateOfLastTermToEndDate();

        Assert.assertTrue(response.getPriorSchoolChild().getEventDate().compareTo(child.getLastTermInCorpEndDate()) == 0);
        Assert.assertTrue(decisionVariables.isPriorConditionSatisfied());
    }

    @Test
    public void priorTermContinuousWithMinorChildAward() {
        child.setOnAwardAsMinorChild(true);
        child.setTerminatedOn18thBirthday(true);

        response.getPriorSchoolChild().setEventDate(DateUtils.convertDate("01/01/2019"));

        ruleSet.priorTermContinuousWithMinorChildAward();

        Assert.assertTrue(response.getPriorSchoolChild().getEventDate().compareTo(RbpsXomUtil.addYearsToDate(18, child.getBirthDate())) == 0);
    }
}