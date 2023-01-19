package gov.va.vba.rbps.rulesengine.spouse.rules;

import gov.va.vba.rbps.rulesengine.spouse.SpouseDecisionVariables;
import gov.va.vba.rbps.rulesengine.spouse.SpouseMessages;
import gov.va.vba.rbps.rulesengine.spouse.SpouseResponse;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import junit.framework.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.*;

public class SpouseAwardRuleSetTest  {

    Spouse spouse;
    SpouseAwardRuleSet ruleSet;
    protected SpouseResponse response = new SpouseResponse();
    protected SpouseDecisionVariables variables = new SpouseDecisionVariables();
    protected VeteranCommonDates commonDates = new VeteranCommonDates();

    public SpouseAwardRuleSetTest() {
        spouse = new Spouse();
        ruleSet = new SpouseAwardRuleSet(spouse, response, variables, commonDates);
    }


    @Test
    public void isSpousePresent() {
        ruleSet.isSpousePresent();
        Assert.assertEquals(response.getAward().getDependencyDecisionType(), DependentDecisionType.DEPENDENCY_ESTABLISHED);
        Assert.assertEquals(response.getAward().getDependencyStatusType(), DependentStatusType.SPOUSE);
    }

    @Test
    public void eventDateDetermination_CP0136_01() throws ParseException {
        Date marriageDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2015");
        Date marriageDatePlus1Year = RbpsXomUtil.addYearsToDate(1, marriageDate);

        variables.setMarragePlus1year(marriageDatePlus1Year);
        commonDates.setMarriageDate(marriageDate);
        commonDates.setClaimDate(new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2014"));

        ruleSet.eventDateDetermination_CP0136_01();

        Assert.assertEquals(response.getAward().getEventDate(), marriageDate);
    }

    @Test
    public void eventDateDetermination_CP0136_02() throws ParseException {
        Date marriageDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2015");
        Date marriageDatePlus1Year = RbpsXomUtil.addYearsToDate(1, marriageDate);
        Date claimDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2017");

        variables.setMarragePlus1year(marriageDatePlus1Year);
        commonDates.setMarriageDate(marriageDate);
        commonDates.setClaimDate(claimDate);

        ruleSet.eventDateDetermination_CP0136_02();

        Assert.assertEquals(response.getAward().getEventDate(), claimDate);
    }

    @Test
    public void eventDateDetermination_CP0136_03() throws ParseException {
        Date marriageDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2018");
        commonDates.setMarriageDate(marriageDate);
        commonDates.setFirstChangedDateofRating(new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2017"));

        ruleSet.eventDateDetermination_CP0136_03();

        Assert.assertEquals(response.getAward().getEventDate(), marriageDate);
    }

    @Test
    public void eventDateDetermination_CP0136_04() throws ParseException {
        Date fcdr = new SimpleDateFormat("MM/dd/yyyy").parse("06/01/2017");
        Date marriageDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2017");
        commonDates.setMarriageDate(marriageDate);
        commonDates.setFirstChangedDateofRating(fcdr);
        commonDates.setRatingEffectiveDate(fcdr);

        ruleSet.eventDateDetermination_CP0136_04();

        Assert.assertEquals(response.getAward().getEventDate(), fcdr);
    }

    @Test
    public void eventDateDetermination_CP0136_05() throws ParseException {
        Date fcdr = new SimpleDateFormat("MM/dd/yyyy").parse("03/1/2018");
        Date marriageDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016");
        Date ratingEffectiveDate =  new SimpleDateFormat("MM/dd/yyyy").parse("02/01/2013");
        Date claimDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2016");

        System.out.println(RbpsXomUtil.addDaysToDate(365, marriageDate));
        commonDates.setMarriageDate(marriageDate);
        commonDates.setFirstChangedDateofRating(fcdr);
        commonDates.setRatingEffectiveDate(ratingEffectiveDate);
        commonDates.setClaimDate(claimDate);

        ruleSet.eventDateDetermination_CP0136_05();

        Assert.assertEquals(response.getAward().getEventDate(), marriageDate);
    }

    @Test
    public void eventDateDetermination_CP0136_06() throws ParseException {
        Date fcdr = new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2019");
        Date marriageDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2015");
        Date ratingEffectiveDate =  new SimpleDateFormat("MM/dd/yyyy").parse("02/01/2010");
        Date claimDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2017");
        commonDates.setMarriageDate(marriageDate);
        commonDates.setFirstChangedDateofRating(fcdr);
        commonDates.setRatingEffectiveDate(ratingEffectiveDate);
        commonDates.setClaimDate(claimDate);

        ruleSet.eventDateDetermination_CP0136_06();

        Assert.assertEquals(response.getAward().getEventDate(), fcdr);
    }

    @Test
    public void eventDateDetermination_CP0136_07() throws ParseException {

        Date fcdr = new SimpleDateFormat("MM/dd/yyyy").parse("03/01/2018");
        Date ratingEffectiveDate =  new SimpleDateFormat("MM/dd/yyyy").parse("02/01/2010");
        Date marriageDate = new SimpleDateFormat("MM/dd/yyyy").parse("01/01/2010");
        Date claimDate = new SimpleDateFormat("MM/dd/yyyy").parse("06/10/2019");
        commonDates.setMarriageDate(marriageDate);
        commonDates.setFirstChangedDateofRating(fcdr);
        commonDates.setRatingEffectiveDate(ratingEffectiveDate);
        commonDates.setClaimDate(claimDate);

        ruleSet.eventDateDetermination_CP0136_07();

        Assert.assertEquals(response.getAward().getEventDate(), fcdr);
    }

}