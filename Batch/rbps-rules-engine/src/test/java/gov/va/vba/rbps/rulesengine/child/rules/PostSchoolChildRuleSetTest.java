package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.DateUtils;
import org.apache.commons.httpclient.util.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class PostSchoolChildRuleSetTest {

    PostSchoolChildRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates commonDates;
    Education currentTerm;
    Education lastTerm;

    public PostSchoolChildRuleSetTest () throws ParseException {
        child = new Child();
        currentTerm = new Education();
        lastTerm = new Education();
        commonDates = new VeteranCommonDates();

        child.setLastTerm(lastTerm);
        child.setCurrentTerm(currentTerm);
        child.setChildType(ChildType.STEPCHILD);
        child.setBirthDate(DateUtils.convertDate("01/01/1996"));

        commonDates.setRatingDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016"));
        commonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2017"));
        commonDates.setRatingEffectiveDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2019"));
        commonDates.setAllowableDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2019"));
        commonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2019"));

        decisionVariables = RulesEngineUtil.getChildDecisionVariables(child, commonDates);

        response = new ChildResponse();

        ruleSet = new PostSchoolChildRuleSet(child, response, decisionVariables);
    }

    @Test
    public void priorTermIncludedButNotContinuousWithCurrent() {
        decisionVariables.setPriorSchoolTermValid(true);
        child.setLastTermInCorpEndDate(DateUtils.convertDate("05/01/2018"));
        child.setLastTermInCorpEndEffectiveDate((DateUtils.convertDate("05/01/2018")));

        response.getPriorSchoolChild().setEndDate(DateUtils.convertDate("01/01/2017"));
        response.getAward().setEventDate(DateUtils.convertDate("12/01/2018"));

        ruleSet.priorTermIncludedButNotContinuousWithCurrent();

        assertTrue(decisionVariables.isConditionSatisfied());
        assertTrue(decisionVariables.isCompleted());
        assertTrue(response.getExceptionMessages().getMessages().contains(ChildMessages.INVALID_PRIOR_SCHOOL_TERM));
    }

    @Test
    public void currentAndPriorContinuousTerm() {
        decisionVariables.setPriorSchoolTermValid(true);

        response.getPriorSchoolChild().setEndDate(DateUtils.convertDate("01/01/2017"));
        response.getAward().setEventDate(DateUtils.convertDate("05/01/2017"));

        ruleSet.currentAndPriorContinuousTerm();

        assertTrue(decisionVariables.isConditionSatisfied());
        assertEquals(child.getLastTerm().getCourseEndDate(), response.getAward().getEventDate());
    }

    @Test
    public void lastTermInCorpAndCurrentContinuous() {
        decisionVariables.setPriorSchoolTermValid(true);
        child.setLastTermInCorpEndDate(DateUtils.convertDate("01/01/2018"));
        child.setLastTermInCorpBeginDate(DateUtils.convertDate("05/01/2018"));
        child.setLastTermInCorpBeginEffectiveDate((DateUtils.convertDate("03/01/2017")));
        child.setLastTermInCorpEndEffectiveDate((DateUtils.convertDate("05/01/2017")));

        response.getAward().setEventDate(DateUtils.convertDate("03/01/2017"));

        ruleSet.lastTermInCorpAndCurrentContinuous();

        assertTrue(decisionVariables.isConditionSatisfied());
        assertEquals(child.getLastTermInCorpEndDate(), response.getAward().getEventDate());
    }
}