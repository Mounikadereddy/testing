package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.DateUtils;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class SchoolChildStepChildRuleSetTest {

    SchoolChildStepChildRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates commonDates;
    Education currentTerm;
    Education lastTerm;

    public SchoolChildStepChildRuleSetTest () throws ParseException {
        child = new Child();
        currentTerm = new Education();
        lastTerm = new Education();
        commonDates = new VeteranCommonDates();

        child.setCurrentTerm(currentTerm);
        child.setLastTerm(lastTerm);
        child.setChildType(ChildType.STEPCHILD);
        child.setBirthDate(DateUtils.convertDate("01/01/1996"));

        commonDates.setRatingDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016"));
        commonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2017"));
        commonDates.setRatingEffectiveDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2019"));
        commonDates.setAllowableDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2019"));
        commonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("05/01/2019"));

        decisionVariables = RulesEngineUtil.getChildDecisionVariables(child, commonDates);

        response = new ChildResponse();

        ruleSet = new SchoolChildStepChildRuleSet(child, response, decisionVariables, commonDates);
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_6A() {

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("01/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("06/05/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("06/01/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_6A();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getFirstChangedDateofRating(), response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_6B() {

        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("06/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("06/01/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_6B();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(currentTerm.getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_6C() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("06/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("06/01/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("06/01/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_6C();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(lastTerm.getCourseEndDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_6D() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("06/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("06/01/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("06/01/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_6D();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(currentTerm.getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_6E() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("06/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("06/01/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("06/01/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_6E();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getFirstChangedDateofRating(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_8A() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("06/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("06/01/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("06/01/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_8A();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getFirstChangedDateofRating(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_8B() {

        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("03/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("04/01/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_8B();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getFirstChangedDateofRating(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_8C() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("03/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("06/01/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("01/01/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_8C();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getLastTerm().getCourseEndDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_8D() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("03/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("01/01/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("01/31/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_8D();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getCurrentTerm().getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_8E() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("07/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("01/01/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2016"));
        commonDates.setClaimDate(DateUtils.convertDate("01/31/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_8E();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getCurrentTerm().getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_8F() {
        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("03/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("01/01/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("04/01/2019"));
        commonDates.setClaimDate(DateUtils.convertDate("01/31/2019"));

        ruleSet.determineSchoolChildStepChildEventDate_139_8F();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getMarriageDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_8G() {
        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("03/16/2018"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));


        commonDates.setMarriageDate(DateUtils.convertDate("08/01/2019"));
        commonDates.setClaimDate(DateUtils.convertDate("04/25/2020"));

        ruleSet.determineSchoolChildStepChildEventDate_139_8G();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getMarriageDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_2A() {

        // 01/01/2014 18th birthday

        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("01/16/2014"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("05/05/2014"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2013"));
        commonDates.setClaimDate(DateUtils.convertDate("01/01/2014"));
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineSchoolChildStepChildEventDate_139_2A();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(decisionVariables.getChild18BirthDay(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_2B() {
        // 01/01/2014 18th birthday

        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("08/16/2014"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2013"));
        commonDates.setClaimDate(DateUtils.convertDate("01/01/2014"));
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineSchoolChildStepChildEventDate_139_2B();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getCurrentTerm().getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_2C() {
        // 01/01/2014 18th birthday

        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("08/16/2014"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2014"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("08/01/2014"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2013"));
        commonDates.setClaimDate(DateUtils.convertDate("01/01/2014"));
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineSchoolChildStepChildEventDate_139_2C();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getLastTerm().getCourseEndDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_2D() {
        // 01/01/2014 18th birthday

        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("08/16/2014"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("08/01/2014"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2013"));
        commonDates.setClaimDate(DateUtils.convertDate("08/12/2015"));
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineSchoolChildStepChildEventDate_139_2D();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getCurrentTerm().getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_2E() {
        // 01/01/2014 18th birthday

        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("08/16/2014"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("01/01/2014"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2013"));
        commonDates.setClaimDate(DateUtils.convertDate("08/12/2015"));
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineSchoolChildStepChildEventDate_139_2E();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getCurrentTerm().getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_2F() {
        // 01/01/2014 18th birthday

        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("08/16/2014"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("01/01/2014"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2013"));
        commonDates.setClaimDate(DateUtils.convertDate("08/12/2015"));
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineSchoolChildStepChildEventDate_139_2F();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getCurrentTerm().getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_2G() {
        // 01/01/2014 18th birthday

        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("08/16/2014"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("10/05/2015"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2013"));
        commonDates.setClaimDate(DateUtils.convertDate("09/12/2015"));
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineSchoolChildStepChildEventDate_139_2G();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getClaimDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_2H() {
        // 01/01/2014 18th birthday

        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("08/16/2014"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("10/05/2015"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2014"));
        commonDates.setClaimDate(DateUtils.convertDate("09/12/2015"));
        commonDates.setFirstChangedDateofRating(null);

        ruleSet.determineSchoolChildStepChildEventDate_139_2H();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getMarriageDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_4A() {
        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("01/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("06/05/2019"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2014"));
        commonDates.setRatingEffectiveDate(commonDates.getFirstChangedDateofRating());

        ruleSet.determineSchoolChildStepChildEventDate_139_4A();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getFirstChangedDateofRating(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_4B() {
        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("06/16/2019"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("08/05/2019"));

        commonDates.setMarriageDate(DateUtils.convertDate("12/01/2014"));
        commonDates.setRatingEffectiveDate(commonDates.getFirstChangedDateofRating());

        ruleSet.determineSchoolChildStepChildEventDate_139_4B();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getCurrentTerm().getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_4C() {
        decisionVariables.setPriorSchoolTermValid(false);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("01/01/2014"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("05/05/2014"));

        commonDates.setFirstChangedDateofRating(DateUtils.convertDate("12/01/2013"));
        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2013"));
        commonDates.setRatingEffectiveDate(commonDates.getFirstChangedDateofRating());

        ruleSet.determineSchoolChildStepChildEventDate_139_4C();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getFirstChangedDateofRating(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_4D() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("01/01/2014"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("05/05/2014"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("12/01/2013"));

        commonDates.setFirstChangedDateofRating(DateUtils.convertDate("12/01/2013"));
        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2013"));
        commonDates.setRatingEffectiveDate(commonDates.getFirstChangedDateofRating());

        ruleSet.determineSchoolChildStepChildEventDate_139_4D();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getLastTerm().getCourseEndDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_4E() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("07/01/2014"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("05/05/2014"));

        lastTerm.setCourseEndDate(DateUtils.convertDate("12/01/2013"));

        commonDates.setFirstChangedDateofRating(DateUtils.convertDate("12/01/2013"));
        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2013"));
        commonDates.setRatingEffectiveDate(commonDates.getFirstChangedDateofRating());

        ruleSet.determineSchoolChildStepChildEventDate_139_4E();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(child.getCurrentTerm().getCourseStudentStartDate(),  response.getAward().getEventDate());
    }

    @Test
    public void determineSchoolChildStepChildEventDate_139_09() {
        decisionVariables.setPriorSchoolTermValid(true);

        currentTerm.setCourseStudentStartDate(DateUtils.convertDate("07/01/2014"));
        currentTerm.setCourseEndDate(DateUtils.convertDate("05/05/2014"));

        commonDates.setFirstChangedDateofRating(DateUtils.convertDate("12/01/2013"));
        commonDates.setMarriageDate(DateUtils.convertDate("01/01/2015"));
        commonDates.setRatingEffectiveDate(commonDates.getFirstChangedDateofRating());

        ruleSet.determineSchoolChildStepChildEventDate_139_09();

        assertNotNull(response.getAward().getEventDate());
        assertEquals(commonDates.getMarriageDate(),  response.getAward().getEventDate());
    }
}