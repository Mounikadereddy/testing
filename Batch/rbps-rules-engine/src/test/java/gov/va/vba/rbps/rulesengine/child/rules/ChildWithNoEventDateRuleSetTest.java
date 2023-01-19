package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class ChildWithNoEventDateRuleSetTest {


    ChildWithNoEventDateRuleSet ruleSet;

    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates veteranCommonDates;


    public ChildWithNoEventDateRuleSetTest() throws ParseException {
        response = new ChildResponse();
        veteranCommonDates = new VeteranCommonDates();
        child = new Child();



        veteranCommonDates.setRatingDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016"));
        veteranCommonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2017"));
        veteranCommonDates.setRatingEffectiveDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2019"));
        veteranCommonDates.setAllowableDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2019"));
        veteranCommonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2019"));

        child.setFirstName("John");
        child.setLastName("Doe");
        child.setBirthDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/31/1993"));

        decisionVariables = RulesEngineUtil.getChildDecisionVariables(child, veteranCommonDates);

        ruleSet = new ChildWithNoEventDateRuleSet(child, response, decisionVariables, veteranCommonDates);
    }

    @Test
    public void minorChildWithNoEventDate() {
        child.setOnAwardAsMinorChild(false);
        response.getAward().setDependencyStatusType(DependentStatusType.MINOR_CHILD);

        ruleSet.minorChildWithNoEventDate();

        assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.NO_EVENT_DATE_MINOR_CHILD, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void minorToSchoolChildWithNoEventDate() {
        response.getMinorSchoolChildAward().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
        response.getMinorSchoolChildAward().setEventDate(null);

        ruleSet.minorToSchoolChildWithNoEventDate();

        assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.NO_EVENT_DATE_MINOR_TO_SCHOOL_CHILD, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void priorSchoolChildWithNoEventDate() {
        response.getPriorSchoolChild().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
        response.getPriorSchoolChild().setEventDate(null);

        ruleSet.priorSchoolChildWithNoEventDate();

        assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.NO_EVENT_DATE_PRIOR_SCHOOL_CHILD, child.getLastName(), child.getFirstName())));
        assertEquals("ignore", response.getPriorSchoolTermStatus());
    }

    @Test
    public void schoolChildWithNoEventDate() {
        response.getAward().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
        response.getPriorSchoolChild().setEventDate(null);

        ruleSet.schoolChildWithNoEventDate();

        assertTrue(response.getExceptionMessages().getMessages().contains(String.format(ChildMessages.NO_EVENT_DATE_SCHOOL_CHILD, child.getLastName(), child.getFirstName())));
    }

    @Test
    public void verifyMinorSchoolChildEventDate() {
        response.getAward().setDependencyStatusType(DependentStatusType.MINOR_CHILD);
        response.getAward().setEventDate(RbpsXomUtil.addDaysToDate(30, decisionVariables.getChild18BirthDay()));

        ruleSet.verifyMinorSchoolChildEventDate();

        assertNull(response.getAward());
    }

    @Test
    public void verifyMinorStepChildSecondEventDate() throws ParseException {
        child.setChildType(ChildType.STEPCHILD);

        response.getMinorSchoolChildAward().setEventDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2014"));

        veteranCommonDates.setMarriageDate(RbpsXomUtil.addDaysToDate(30, response.getMinorSchoolChildAward().getEventDate()));

        ruleSet.verifyMinorStepChildSecondEventDate();

        assertEquals(veteranCommonDates.getMarriageDate(), response.getMinorSchoolChildAward().getEventDate());
    }
}