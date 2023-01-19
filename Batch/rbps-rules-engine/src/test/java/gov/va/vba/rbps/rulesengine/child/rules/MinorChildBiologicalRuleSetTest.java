package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;


public class MinorChildBiologicalRuleSetTest {


    MinorChildBiologicalRuleSet ruleSet;

    Child child;
    ChildDecisionVariables decisionVariables;
    ChildResponse response;
    VeteranCommonDates veteranCommonDates;


    public MinorChildBiologicalRuleSetTest() throws ParseException {
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

        ruleSet = new MinorChildBiologicalRuleSet(child, response, decisionVariables, veteranCommonDates);
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void awardEventDateDetermination_01() {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(null);
        ruleSet.awardEventDateDetermination_01();

        assertEquals(veteranCommonDates.getClaimDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_02() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(null);
        veteranCommonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/31/1991"));
        ruleSet.awardEventDateDetermination_02();

        assertEquals(child.getBirthDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_03() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("01/31/1991"));
        ruleSet.awardEventDateDetermination_03();

        assertEquals(child.getBirthDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_04() {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(veteranCommonDates.getRatingEffectiveDate());
        ruleSet.awardEventDateDetermination_04();

        assertEquals(veteranCommonDates.getFirstChangedDateofRating(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_05() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/31/1991"));
        ruleSet.awardEventDateDetermination_05();

        assertEquals(child.getBirthDate(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_06() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        ruleSet.awardEventDateDetermination_06();

        assertEquals(veteranCommonDates.getFirstChangedDateofRating(), response.getAward().getEventDate());
    }

    @Test
    public void awardEventDateDetermination_07() throws ParseException {
        child.setOnAwardAsMinorChild(false);
        veteranCommonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("07/01/2018"));
        veteranCommonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("08/01/2019"));

        ruleSet.awardEventDateDetermination_07();

        assertEquals(veteranCommonDates.getFirstChangedDateofRating(), response.getAward().getEventDate());
    }
}