package gov.va.vba.rbps.rulesengine.engine;

import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.spouse.SpouseDecisionVariables;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

import java.util.Map;

public class RulesEngineUtil {

    public static ChildDecisionVariables getChildDecisionVariables(final Child child, final VeteranCommonDates commonDates) {
        ChildDecisionVariables variables = new ChildDecisionVariables();

        variables.setRatingPlusOneYear(RbpsXomUtil.addYearsToDate(1, commonDates.getRatingDate()));

        variables.setChild18BirthDay(RbpsXomUtil.addYearsToDate(18, child.getBirthDate()));

        variables.setChildAgeOnEffectiveDate(RbpsXomUtil.getAgeOn(child, commonDates.getRatingEffectiveDate()));

        variables.setRatingPlusOneYear30Days(RbpsXomUtil.addDaysToDate(30, RbpsXomUtil.addYearsToDate(1, commonDates.getRatingDate())));

        variables.setChild19BirthDay(RbpsXomUtil.addYearsToDate(19, child.getBirthDate()));

        variables.setDobPlusOneYear(RbpsXomUtil.addYearsToDate(1, child.getBirthDate()));

        variables.setChildAgeOnDateOfClaim(RbpsXomUtil.getAgeOn(child, commonDates.getClaimDate()));

        variables.setRatingPlusOneYearSevenDays(RbpsXomUtil.addDaysToDate(7, RbpsXomUtil.addYearsToDate(1, commonDates.getRatingDate())));

        return variables;
    }


    public static SpouseDecisionVariables getSpouseDecisionVariables(final VeteranCommonDates commonDates) {
        SpouseDecisionVariables variables = new SpouseDecisionVariables();
        variables.setRatingDatePlus1year(RbpsXomUtil.addYearsToDate(1, commonDates.getRatingDate()));
        variables.setRatingPlus1yearPlus300Days(RbpsXomUtil.addDaysToDate(300, RbpsXomUtil.addYearsToDate(1, commonDates.getRatingDate())));
        variables.setMarragePlus1year(RbpsXomUtil.addYearsToDate(1, commonDates.getMarriageDate()));
        variables.setRatingPlus1yearPlus7days(RbpsXomUtil.addDaysToDate(7, RbpsXomUtil.addYearsToDate(1, commonDates.getRatingDate())));
        return variables;
    }
}
