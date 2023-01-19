package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.DateUtils;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.VeteranCommonDatesObjectBuilder;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChildDetermineAwardStatusRuleSetTest {

    ChildDetermineAwardStatusRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates commonDates;

    public ChildDetermineAwardStatusRuleSetTest() {

    }

    @Before
    public void setUp() throws ParseException {
        child = new Child();
        response = new ChildResponse();
        commonDates = VeteranCommonDatesObjectBuilder.createCommonDates();

        child.setBirthDate(DateUtils.convertDate("01/01/1990"));

        decisionVariables = RulesEngineUtil.getChildDecisionVariables(child, commonDates);

        ruleSet = new ChildDetermineAwardStatusRuleSet(child, response, decisionVariables, commonDates);

    }

    @Test
    public void determineChildAwardStatus_CP0134_01() {
        child.setBirthDate(DateUtils.convertDate("01/01/2012"));

        ruleSet.determineChildAwardStatus_CP0134_01();

        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.MINOR_CHILD);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.ELIGIBLE_MINOR_CHILD);

    }

    @Test
    public void determineChildAwardStatus_CP0134_02_A() {
        Date lastTermEndDate = DateUtils.convertDate("12/15/2018");

        child.setBirthDate(DateUtils.convertDate("01/01/1999"));
        child.setLastTerm(new Education());
        child.getLastTerm().setCourseEndDate(lastTermEndDate);

        ruleSet.determineChildAwardStatus_CP0134_02_A();

        Assert.assertTrue(response.getPriorSchoolChild().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getPriorSchoolChild().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getPriorSchoolChild().getEndDate().compareTo(lastTermEndDate) == 0);
    }

    @Test
    public void determineChildAwardStatus_CP0134_02() {
        Date expectedGraduationDate = DateUtils.convertDate("12/15/2019");

        child.setBirthDate(DateUtils.convertDate("01/01/1999"));
        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setExpectedGraduationDate(expectedGraduationDate);

        ruleSet.determineChildAwardStatus_CP0134_02();

        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getAward().getEndDate().compareTo(expectedGraduationDate) == 0);
    }

    @Test
    public void determineChildAwardStatus_CP0134_03() {
        child.setBirthDate(DateUtils.convertDate("01/01/1999"));

        ruleSet.determineChildAwardStatus_CP0134_03();

        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.NOT_AN_AWARD_DEPENDENT);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.OVER_18_NOT_IN_SCHOOL_OR_HELPLESS);
        Assert.assertTrue(decisionVariables.isCompleted());
    }

    @Test
    public void determineChildAwardStatus_CP0134_04() {
        Date expectedGraduationDate = DateUtils.convertDate("12/15/2019");
        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setExpectedGraduationDate(expectedGraduationDate);
        child.setSchoolChild(true);
        child.setBirthDate(DateUtils.convertDate("01/01/2012"));

        ruleSet.determineChildAwardStatus_CP0134_04();

        Assert.assertTrue(response.getMinorSchoolChildAward().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getMinorSchoolChildAward().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getMinorSchoolChildAward().getEndDate().compareTo(expectedGraduationDate) == 0);
    }

    @Test
    public void determineChildAwardStatus_CP0134_04A() {
        Date expectedGraduationDate = DateUtils.convertDate("12/15/2019");

        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setExpectedGraduationDate(expectedGraduationDate);
        child.setSchoolChild(true);
        child.setBirthDate(DateUtils.convertDate("01/01/2012"));
        child.setPreviousTerms(new ArrayList<Education>()  {{ add(new Education()); }} );

        ruleSet.determineChildAwardStatus_CP0134_04A();

        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getAward().getEndDate().compareTo(expectedGraduationDate) == 0);
    }

    @Test
    public void determineChildAwardStatus_CP0134_04B() {
        Date lastTermEndDate = DateUtils.convertDate("12/15/2018");

        child.setLastTerm(new Education());
        child.getLastTerm().setCourseEndDate(lastTermEndDate);
        child.setSchoolChild(true);
        child.setBirthDate(DateUtils.convertDate("01/01/2012"));

        ruleSet.determineChildAwardStatus_CP0134_04B();

        Assert.assertTrue(response.getPriorSchoolChild().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getPriorSchoolChild().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getPriorSchoolChild().getEndDate().compareTo(lastTermEndDate) == 0);
    }

    @Test
    public void determineChildAwardStatus_CP0134_5() {
        child.setSeriouslyDisabled(false);

        ruleSet.determineChildAwardStatus_CP0134_05();

        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.NOT_AN_AWARD_DEPENDENT);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.RATED_NOT_HELPLESS);
        Assert.assertTrue(decisionVariables.isCompleted());
    }

    @Test
    public void determineChildAwardStatus_CP0134_06() {
        decisionVariables.setChildAgeOnDateOfClaim(16);
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineChildAwardStatus_CP0134_06();

        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.MINOR_CHILD);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.ELIGIBLE_MINOR_CHILD);
    }

    @Test
    public void determineChildAwardStatus_CP0134_07() {
        Date expectedGraduationDate = DateUtils.convertDate("12/15/2019");

        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setExpectedGraduationDate(expectedGraduationDate);

        decisionVariables.setChildAgeOnDateOfClaim(21);
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineChildAwardStatus_CP0134_07();

        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getAward().getEndDate().compareTo(expectedGraduationDate) == 0);
    }

    @Test
    public void determineChildAwardStatus_CP0134_07A() {
        Date lastTermEndDate = DateUtils.convertDate("12/15/2018");

        child.setLastTerm(new Education());
        child.getLastTerm().setCourseEndDate(lastTermEndDate);

        decisionVariables.setChildAgeOnDateOfClaim(21);
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineChildAwardStatus_CP0134_07A();

        Assert.assertTrue(response.getPriorSchoolChild().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getPriorSchoolChild().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getPriorSchoolChild().getEndDate().compareTo(lastTermEndDate) == 0);
    }

    @Test
    public void determineChildAwardStatus_CP0134_08() {
        decisionVariables.setChildAgeOnDateOfClaim(21);
        commonDates.setFirstChangedDateofRating(null);
        child.setSchoolChild(false);
        child.setSeriouslyDisabled(false);

        ruleSet.determineChildAwardStatus_CP0134_08();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.NOT_AN_AWARD_DEPENDENT);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.OVER_18_NOT_IN_SCHOOL_OR_HELPLESS);
    }

    @Test
    public void determineChildAwardStatus_CP0134_09() {
        Date expectedGraduationDate = DateUtils.convertDate("12/15/2019");

        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setExpectedGraduationDate(expectedGraduationDate);

        decisionVariables.setChildAgeOnDateOfClaim(16);
        commonDates.setFirstChangedDateofRating(null);
        child.setSchoolChild(true);

        ruleSet.determineChildAwardStatus_CP0134_09();

        Assert.assertTrue(response.getMinorSchoolChildAward().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getMinorSchoolChildAward().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getMinorSchoolChildAward().getEndDate().compareTo(expectedGraduationDate) == 0);
    }

    @Test
    public void determineChildAwardStatus_CP0134_10() {
        decisionVariables.setChildAgeOnDateOfClaim(24);
        commonDates.setFirstChangedDateofRating(null);
        child.setSeriouslyDisabled(false);

        ruleSet.determineChildAwardStatus_CP0134_10();

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getAward().getDependencyStatusType() == DependentStatusType.NOT_AN_AWARD_DEPENDENT);
        Assert.assertTrue(response.getAward().getDependencyDecisionType() == DependentDecisionType.RATED_NOT_HELPLESS);
    }

    @Test
    public void determineChildAwardStatus_CP0134_09A() {
        Date lastTermEndDate = DateUtils.convertDate("12/15/2018");

        child.setLastTerm(new Education());
        child.getLastTerm().setCourseEndDate(lastTermEndDate);

        decisionVariables.setChildAgeOnDateOfClaim(16);
        commonDates.setFirstChangedDateofRating(null);
        child.setSchoolChild(true);

        ruleSet.determineChildAwardStatus_CP0134_09A();

        Assert.assertTrue(response.getPriorSchoolChild().getDependencyStatusType() == DependentStatusType.SCHOOL_CHILD);
        Assert.assertTrue(response.getPriorSchoolChild().getDependencyDecisionType() == DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
        Assert.assertTrue(response.getPriorSchoolChild().getEndDate().compareTo(lastTermEndDate) == 0);
    }
}