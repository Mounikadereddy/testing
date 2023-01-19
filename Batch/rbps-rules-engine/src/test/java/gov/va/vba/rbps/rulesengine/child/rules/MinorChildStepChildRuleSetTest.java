package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class MinorChildStepChildRuleSetTest {

    MinorChildStepChildRuleSet ruleSet;
    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;

    VeteranCommonDates veteranCommonDates;




    public MinorChildStepChildRuleSetTest() throws ParseException {
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

        ruleSet = new MinorChildStepChildRuleSet(child, response, decisionVariables, veteranCommonDates);
    }

    @Before
    public void setUp() {
    }

    @Test
    public void awardEventDateDetermination_08() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(null);
        veteranCommonDates.setMarriageDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2015"));

        ruleSet.awardEventDateDetermination_08();

        assertEquals(veteranCommonDates.getClaimDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_09() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(null);
        veteranCommonDates.setMarriageDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2018"));

        ruleSet.awardEventDateDetermination_09();

        assertEquals(veteranCommonDates.getMarriageDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_10() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(null);
        veteranCommonDates.setMarriageDate(new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2018"));
        veteranCommonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/1991"));

        ruleSet.awardEventDateDetermination_10();

        assertEquals(child.getBirthDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_11() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setMarriageDate(RbpsXomUtil.addDaysToDate(30, veteranCommonDates.getFirstChangedDateofRating()));

        ruleSet.awardEventDateDetermination_11();

        assertEquals(veteranCommonDates.getMarriageDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_12() {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setMarriageDate(RbpsXomUtil.addDaysToDate(15, veteranCommonDates.getFirstChangedDateofRating()));
        child.setBirthDate(RbpsXomUtil.addDaysToDate(30, veteranCommonDates.getFirstChangedDateofRating()));
        ruleSet.awardEventDateDetermination_12();

        assertEquals(child.getBirthDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_13() {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(veteranCommonDates.getRatingEffectiveDate());
        veteranCommonDates.setMarriageDate(veteranCommonDates.getFirstChangedDateofRating());

        ruleSet.awardEventDateDetermination_13();

        assertEquals(veteranCommonDates.getFirstChangedDateofRating(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_14() {
        child.setOnAwardAsMinorChild(false);
        child.setBirthDate(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getClaimDate()));

        veteranCommonDates.setMarriageDate(child.getBirthDate());

        ruleSet.awardEventDateDetermination_14();

        assertEquals(child.getBirthDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_15() throws ParseException {
        child.setOnAwardAsMinorChild(false);

        veteranCommonDates.setMarriageDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2015"));

        ruleSet.awardEventDateDetermination_15();

        assertEquals(veteranCommonDates.getFirstChangedDateofRating(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_16() throws ParseException {
        child.setOnAwardAsMinorChild(false);

        veteranCommonDates.setMarriageDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2015"));

        ruleSet.awardEventDateDetermination_16();

        assertEquals(veteranCommonDates.getFirstChangedDateofRating(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_17() {
        child.setOnAwardAsMinorChild(false);

        child.setBirthDate(RbpsXomUtil.addDaysToDate(30, veteranCommonDates.getClaimDate()));

        veteranCommonDates.setMarriageDate(RbpsXomUtil.addDaysToDate(30, child.getBirthDate()));

        ruleSet.awardEventDateDetermination_17();

        assertEquals(veteranCommonDates.getMarriageDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_18() throws ParseException {
        child.setOnAwardAsMinorChild(false);

        veteranCommonDates.setMarriageDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/03/2018"));

        ruleSet.awardEventDateDetermination_18();

        assertEquals(veteranCommonDates.getMarriageDate(), response.getAward().getEventDate());
    }
}