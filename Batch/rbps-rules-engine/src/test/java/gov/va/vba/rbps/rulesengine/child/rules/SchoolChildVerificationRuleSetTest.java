package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.*;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.DateUtils;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.VeteranCommonDatesObjectBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

public class SchoolChildVerificationRuleSetTest {

    SchoolChildVerificationRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates commonDates;

    @Before
    public void setUp() throws ParseException {
        child = new Child();
        response = new ChildResponse();
        commonDates = VeteranCommonDatesObjectBuilder.createCommonDates();

        child.setFirstName("Testy");
        child.setLastName("McTester");
        child.setBirthDate(DateUtils.convertDate("01/01/2012"));

        decisionVariables = RulesEngineUtil.getChildDecisionVariables(child, commonDates);

        ruleSet = new SchoolChildVerificationRuleSet(child, response, decisionVariables, commonDates);
    }

    @Test
    public void childCourseEducationVerification() {
        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setEducationType(EducationType.FULL_TIME);
        child.getCurrentTerm().setEducationLevelType(EducationLevelType.POST_SECONDARY);

        ruleSet.childCourseEducationVerification();

        System.out.println(response.getExceptionMessages().getMessages().toString());

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.CHILD_COURSE_EDUCATION_VERIFICATION, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void childSchoolAddressVerification() {
        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setEducationType(EducationType.FULL_TIME);
        child.getCurrentTerm().setEducationLevelType(EducationLevelType.POST_SECONDARY);
        child.getCurrentTerm().setSchool(new School());

        ruleSet.childSchoolAddressVerification();

        System.out.println(response.getExceptionMessages().getMessages().toString());

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.CHILD_SCHOOL_ADDRESS_VERIFICATION, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void childSchoolNameVerification() {
        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setEducationType(EducationType.FULL_TIME);
        child.getCurrentTerm().setEducationLevelType(EducationLevelType.POST_SECONDARY);
        child.getCurrentTerm().setSchool(new School());

        ruleSet.childSchoolNameVerification();

        System.out.println(response.getExceptionMessages().getMessages().toString());

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.CHILD_SCHOOL_NAME_VERIFICATION, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void tuitionPaidByGovt() {
        child.setTuitionPaidByGovt(true);

        ruleSet.tuitionPaidByGovt();

        System.out.println(response.getExceptionMessages().getMessages().toString());

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.TUITION_PAID_BY_GOVT, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void childHomeSchooledCheck() {
        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setEducationLevelType(EducationLevelType.HOME_SCHOOLED);

        ruleSet.childHomeSchooledCheck();

        System.out.println(response.getExceptionMessages().getMessages().toString());

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.HOME_SCHOOLED_CHECK, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void courseStartDateCheck() {
        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setEducationType(EducationType.FULL_TIME);

        ruleSet.courseStartDateCheck();

        System.out.println(response.getExceptionMessages().getMessages().toString());

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.MISSING_COURSE_START_DATE, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void partTimeValidation() {
        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setEducationType(EducationType.PART_TIME);
        child.getCurrentTerm().setSessionsPerWeek(1);
        child.getCurrentTerm().setHoursPerWeek(2);

        ruleSet.partTimeValidation();

        System.out.println(response.getExceptionMessages().getMessages().toString());

        Assert.assertTrue(decisionVariables.isCompleted());
        Assert.assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.PART_TIME, child.getLastName(), child.getFirstName())));
    }
}