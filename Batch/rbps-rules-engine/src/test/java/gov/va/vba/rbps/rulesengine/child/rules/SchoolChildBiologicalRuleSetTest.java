package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.DateUtils;
import gov.va.vba.rbps.rulesengine.mockobjectbuilders.VeteranCommonDatesObjectBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

public class SchoolChildBiologicalRuleSetTest {

    SchoolChildBiologicalRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates commonDates;

    @Before
    public void setUp() throws ParseException {
        child = new Child();
        child.setChildType(ChildType.BIOLOGICAL_CHILD);
        child.setBirthDate(DateUtils.convertDate("01/01/1996"));

        response = new ChildResponse();
        commonDates = VeteranCommonDatesObjectBuilder.createCommonDates();

        decisionVariables = RulesEngineUtil.getChildDecisionVariables(child, commonDates);

        ruleSet = new SchoolChildBiologicalRuleSet(child, response, decisionVariables, commonDates);

        response.getAward().setEventDate(DateUtils.convertDate("01/01/1900"));
    }

    @Test
    public void setBioChildEventDate_CP0139_5A() {
        setupFcdrOutside365();

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("11/01/2016"));
        child.getCurrentTerm().setCourseEndDate(DateUtils.convertDate("05/05/2018"));

        System.out.println("\nRunning rule set biological child event date CP0139_5A");

        ruleSet.setBioChildEventDate_CP0139_5A();

        System.out.println("\nExpected Award Event Date: " + commonDates.getFirstChangedDateofRating());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(commonDates.getFirstChangedDateofRating()));
    }

    @Test
    public void setBioChildEventDate_CP0139_5B() {
        setupFcdrOutside365();

        decisionVariables.setPriorSchoolTermValid(false);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("05/05/2017"));

        System.out.println("\nRunning rule set biological child event date CP0139_5B");

        ruleSet.setBioChildEventDate_CP0139_5B();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_5C() {
        setupFcdrOutside365();

        decisionVariables.setPriorSchoolTermValid(true);

        child.setLastTerm(new Education());
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("05/05/2017"));
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("02/05/2017"));

        System.out.println("\nRunning rule set biological child event date CP0139_5C");

        ruleSet.setBioChildEventDate_CP0139_5C();

        System.out.println("\nExpected Award Event Date: " + child.getLastTerm().getCourseEndDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getLastTerm().getCourseEndDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_5D() {
        setupFcdrOutside365();

        decisionVariables.setPriorSchoolTermValid(true);

        child.setLastTerm(new Education());
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("02/05/2017"));
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("12/05/2017"));

        System.out.println("\nRunning rule set biological child event date CP0139_5D");

        ruleSet.setBioChildEventDate_CP0139_5D();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_5E() {
        setupFcdrOutside365();

        decisionVariables.setPriorSchoolTermValid(true);

        child.setLastTerm(new Education());
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("10/05/2016"));
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("11/05/2016"));

        System.out.println("\nRunning rule set biological child event date CP0139_5E");

        ruleSet.setBioChildEventDate_CP0139_5E();

        System.out.println("\nExpected Award Event Date: " + commonDates.getFirstChangedDateofRating());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(commonDates.getFirstChangedDateofRating()));
    }

    @Test
    public void setBioChildEventDate_CP0139_7A() {
        setupFcdrWithin365();

        decisionVariables.setPriorSchoolTermValid(false);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("11/10/2016"));

        System.out.println("\nRunning rule set biological child event date CP0139_7A");

        ruleSet.setBioChildEventDate_CP0139_7A();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_7B() {
        setupFcdrWithin365();

        decisionVariables.setPriorSchoolTermValid(false);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("11/10/2013"));
        child.getCurrentTerm().setCourseEndDate(DateUtils.convertDate("11/10/2017"));

        System.out.println("\nRunning rule set biological child event date CP0139_7B");

        ruleSet.setBioChildEventDate_CP0139_7B();

        System.out.println("\nExpected Award Event Date: " + commonDates.getFirstChangedDateofRating());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(commonDates.getFirstChangedDateofRating()));
    }

    @Test
    public void setBioChildEventDate_CP0139_7C() {
        setupFcdrWithin365();

        child.setLastTerm(new Education());

        decisionVariables.setPriorSchoolTermValid(true);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("11/10/2013"));
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("11/10/2017"));

        System.out.println("\nRunning rule set biological child event date CP0139_7C");

        ruleSet.setBioChildEventDate_CP0139_7C();

        System.out.println("\nExpected Award Event Date: " + child.getLastTerm().getCourseEndDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getLastTerm().getCourseEndDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_7D() {
        setupFcdrWithin365();

        child.setLastTerm(new Education());

        decisionVariables.setPriorSchoolTermValid(true);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("12/10/2014"));
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("11/30/2014"));

        System.out.println("\nRunning rule set biological child event date CP0139_7D");

        ruleSet.setBioChildEventDate_CP0139_7D();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_7E() {
        setupFcdrWithin365();

        child.setLastTerm(new Education());

        decisionVariables.setPriorSchoolTermValid(true);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("12/10/2014"));
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("11/30/2013"));

        System.out.println("\nRunning rule set biological child event date CP0139_7E");

        ruleSet.setBioChildEventDate_CP0139_7E();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_1A() {
        // No FCDR

        commonDates.setFirstChangedDateofRating(null);
        commonDates.setClaimDate(DateUtils.convertDate("01/01/2014"));

        decisionVariables.setPriorSchoolTermValid(false);

        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("01/01/2013"));
        child.getCurrentTerm().setCourseEndDate(DateUtils.convertDate("02/01/2016"));

        System.out.println("\nRunning rule set biological child event date CP0139_1A");

        ruleSet.setBioChildEventDate_CP0139_1A();

        System.out.println("\nExpected Award Event Date: " + decisionVariables.getChild18BirthDay());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(decisionVariables.getChild18BirthDay()));
    }

    @Test
    public void setBioChildEventDate_CP0139_1B() {
        // No FCDR

        commonDates.setFirstChangedDateofRating(null);
        commonDates.setClaimDate(DateUtils.convertDate("01/01/2014"));

        decisionVariables.setPriorSchoolTermValid(false);

        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("01/01/2017"));

        System.out.println("\nRunning rule set biological child event date CP0139_1B");

        ruleSet.setBioChildEventDate_CP0139_1B();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_1C() {
        // No FCDR

        commonDates.setFirstChangedDateofRating(null);
        commonDates.setClaimDate(DateUtils.convertDate("01/01/2014"));

        decisionVariables.setPriorSchoolTermValid(true);

        child.setCurrentTerm(new Education());
        child.setLastTerm(new Education());
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("01/01/2014"));
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("01/01/2014"));

        System.out.println("\nRunning rule set biological child event date CP0139_1C");

        ruleSet.setBioChildEventDate_CP0139_1C();

        System.out.println("\nExpected Award Event Date: " + child.getLastTerm().getCourseEndDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getLastTerm().getCourseEndDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_1D() {
        // No FCDR

        commonDates.setFirstChangedDateofRating(null);
        commonDates.setClaimDate(DateUtils.convertDate("03/01/2015"));

        decisionVariables.setPriorSchoolTermValid(true);

        child.setCurrentTerm(new Education());
        child.setLastTerm(new Education());
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("04/01/2014"));
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("01/01/2014"));

        System.out.println("\nRunning rule set biological child event date CP0139_1D");

        ruleSet.setBioChildEventDate_CP0139_1D();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_1E() {
        // No FCDR

        commonDates.setFirstChangedDateofRating(null);
        commonDates.setClaimDate(DateUtils.convertDate("03/01/2015"));

        decisionVariables.setPriorSchoolTermValid(true);

        child.setCurrentTerm(new Education());
        child.setLastTerm(new Education());
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("07/01/2014"));
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("01/01/2014"));

        System.out.println("\nRunning rule set biological child event date CP0139_1E");

        ruleSet.setBioChildEventDate_CP0139_1E();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_1F() {
        // No FCDR

        commonDates.setFirstChangedDateofRating(null);
        commonDates.setClaimDate(DateUtils.convertDate("03/01/2015"));

        decisionVariables.setPriorSchoolTermValid(false);

        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("07/01/2014"));
        child.getCurrentTerm().setCourseEndDate(DateUtils.convertDate("04/05/2015"));

        System.out.println("\nRunning rule set biological child event date CP0139_1F");

        ruleSet.setBioChildEventDate_CP0139_1F();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_1G() {
        // No FCDR

        commonDates.setFirstChangedDateofRating(null);
        commonDates.setClaimDate(DateUtils.convertDate("03/01/2015"));

        decisionVariables.setPriorSchoolTermValid(false);

        child.setCurrentTerm(new Education());
        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("01/01/2014"));
        child.getCurrentTerm().setCourseEndDate(DateUtils.convertDate("04/05/2015"));

        System.out.println("\nRunning rule set biological child event date CP0139_1G");

        ruleSet.setBioChildEventDate_CP0139_1G();

        System.out.println("\nExpected Award Event Date: " + commonDates.getClaimDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(commonDates.getClaimDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_3A() {
        setupSingleRating();

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("01/01/2019"));
        child.getCurrentTerm().setCourseEndDate(DateUtils.convertDate("09/01/2019"));

        System.out.println("\nRunning rule set biological child event date CP0139_3A");

        ruleSet.setBioChildEventDate_CP0139_3A();

        System.out.println("\nExpected Award Event Date: " + commonDates.getFirstChangedDateofRating());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(commonDates.getFirstChangedDateofRating()));
    }

    @Test
    public void setBioChildEventDate_CP0139_3B() {
        setupSingleRating();

        decisionVariables.setPriorSchoolTermValid(false);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("09/01/2019"));
        child.getCurrentTerm().setCourseEndDate(DateUtils.convertDate("09/01/2019"));

        System.out.println("\nRunning rule set biological child event date CP0139_3B");

        ruleSet.setBioChildEventDate_CP0139_3B();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_3C() {
        setupSingleRating();

        decisionVariables.setPriorSchoolTermValid(false);

        commonDates.setFirstChangedDateofRating(DateUtils.convertDate("1/01/2013"));
        commonDates.setRatingEffectiveDate(commonDates.getFirstChangedDateofRating());

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("02/01/2013"));

        System.out.println("\nRunning rule set biological child event date CP0139_3C");

        ruleSet.setBioChildEventDate_CP0139_3C();

        System.out.println("\nExpected Award Event Date: " + commonDates.getFirstChangedDateofRating());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(commonDates.getFirstChangedDateofRating()));
    }

    @Test
    public void setBioChildEventDate_CP0139_3D() {
        setupSingleRating();

        child.setLastTerm(new Education());

        decisionVariables.setPriorSchoolTermValid(true);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("02/01/2013"));
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("03/01/2014"));

        System.out.println("\nRunning rule set biological child event date CP0139_3D");

        ruleSet.setBioChildEventDate_CP0139_3D();

        System.out.println("\nExpected Award Event Date: " + child.getLastTerm().getCourseEndDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getLastTerm().getCourseEndDate()));
    }

    @Test
    public void setBioChildEventDate_CP0139_3E() {
        setupSingleRating();

        child.setLastTerm(new Education());

        decisionVariables.setPriorSchoolTermValid(true);

        child.getCurrentTerm().setCourseStudentStartDate(DateUtils.convertDate("02/01/2015"));
        child.getLastTerm().setCourseEndDate(DateUtils.convertDate("03/01/2014"));

        System.out.println("\nRunning rule set biological child event date CP0139_3E");

        ruleSet.setBioChildEventDate_CP0139_3E();

        System.out.println("\nExpected Award Event Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        System.out.println("Actual Award Event Date: " + response.getAward().getEventDate());

        Assert.assertTrue(response.getAward().getEventDate().equals(child.getCurrentTerm().getCourseStudentStartDate()));
    }

    private void setupFcdrOutside365() {
        commonDates.setFirstChangedDateofRating(DateUtils.convertDate("01/01/2017"));
        commonDates.setClaimDate(DateUtils.convertDate("02/01/2018"));
        child.setCurrentTerm(new Education());
    }

    private void setupFcdrWithin365() {
        commonDates.setFirstChangedDateofRating(DateUtils.convertDate("01/01/2017"));
        commonDates.setClaimDate(DateUtils.convertDate("12/01/2015"));
        child.setCurrentTerm(new Education());
    }

    private void setupSingleRating() {
        commonDates.setRatingEffectiveDate(commonDates.getFirstChangedDateofRating());
        child.setCurrentTerm(new Education());
    }
}