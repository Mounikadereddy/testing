package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;

public class MinorChildStepChildRuleSet extends ChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public MinorChildStepChildRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    /*************************************************************
     *
     * CP0137-08
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is not present
     *             - the marriage date of 'the Veteran' is present
     *             - 'the Veteran' claim received date is after the marriage date of 'the Veteran' plus 365 Days
     *             - 'the Veteran' claim received date is after '1 Year of date of birth of the child'
     * then
     *     set the event date of 'the Award' to 'the Veteran' claim received date ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_08() {
        if (
            !child.isOnAwardAsMinorChild()
                    && RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating())
                    && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                    && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate()))
                    && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addYearsToDate(1, child.getBirthDate()))
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getClaimDate());
            logger.debug("awardEventDateDetermination_08: Award event date set to the claim received date of the veteran: " + veteranCommonDates.getClaimDate());
        }
    }



    /*************************************************************
     *
     * CP0137-09
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is not present
     *             - the marriage date of 'the Veteran' is present
     *             - the birth date of 'the Child' is before or the same as the marriage date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the marriage date of 'the Veteran' plus 365 Days ,
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_09() {
        if (
            !child.isOnAwardAsMinorChild()
                    && RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating())
                    && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                    && (child.getBirthDate().before(veteranCommonDates.getMarriageDate()) || child.getBirthDate().equals(veteranCommonDates.getMarriageDate()))
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate())))
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("awardEventDateDetermination_09: Award event date set to the marriage date of the veteran: " + veteranCommonDates.getMarriageDate());
        }
    }


    /*************************************************************
     *
     * CP0137-10
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is not present
     *             - the marriage date of 'the Veteran' is present
     *             - the birth date of 'the Child' is after the marriage date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as '1 Year of date of birth of the child' ,
     * then
     *     set the event date of 'the Award' to the birth date of 'the Child' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_10() {
        if (
            !child.isOnAwardAsMinorChild()
                    && RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating())
                    && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                    && child.getBirthDate().after(veteranCommonDates.getMarriageDate())
                    && (veteranCommonDates.getClaimDate().before(childDecisionVariables.getDobPlusOneYear()) || veteranCommonDates.getClaimDate().equals(childDecisionVariables.getDobPlusOneYear()))
        ) {
            childResponse.getAward().setEventDate(child.getBirthDate());
            logger.debug("awardEventDateDetermination_10: Award event date set to the birth date of the child: " + child.getBirthDate());
        }
    }


    /*************************************************************
     *
     * CP0137-11
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is after the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is after the birth date of 'the Child'
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_11() {
        if (
            !child.isOnAwardAsMinorChild()
                && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                && veteranCommonDates.getMarriageDate().after(veteranCommonDates.getFirstChangedDateofRating())
                && veteranCommonDates.getMarriageDate().after(child.getBirthDate())
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("awardEventDateDetermination_11: Award event date set to the marriage date of the veteran: " + veteranCommonDates.getMarriageDate());
        }

    }

    /*************************************************************
     *
     * CP0137-12
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the birth date of 'the Child' is after the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the birth date of 'the Child'
     * then
     *     set the event date of 'the Award' to the birth date of 'the Child' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_12() {
        if (
            !child.isOnAwardAsMinorChild()
                    && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                    && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                    && child.getBirthDate().after(veteranCommonDates.getFirstChangedDateofRating())
                    && (veteranCommonDates.getMarriageDate().before(child.getBirthDate()) || veteranCommonDates.getMarriageDate().equals(child.getBirthDate()))
        ) {
            childResponse.getAward().setEventDate(child.getBirthDate());
            logger.debug("awardEventDateDetermination_12: Award event date set to the birth date of the child: " + child.getBirthDate());
        }
    }


    /*************************************************************
     *
     * CP0137-13
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_13() {
        if (
            !child.isOnAwardAsMinorChild()
            && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
            && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
            && veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
            && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
            && (veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating()) || veteranCommonDates.getMarriageDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("awardEventDateDetermination_13: Award event date set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }


    /*************************************************************
     *
     * CP0137-14
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the birth date of 'the Child' plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the birth date of 'the Child'
     * then
     *     set the event date of 'the Award' to the birth date of 'the Child' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_14() {
        if (
            !child.isOnAwardAsMinorChild()
                    && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                    && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                    && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())))
                    && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, child.getBirthDate())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, child.getBirthDate())))
                    && (veteranCommonDates.getMarriageDate().before(child.getBirthDate()) || veteranCommonDates.getMarriageDate().equals(child.getBirthDate()))
        ) {
            childResponse.getAward().setEventDate(child.getBirthDate());
            logger.debug("awardEventDateDetermination_14: Award event date set to the birth date of the child: " + child.getBirthDate());
        }
    }



    /*************************************************************
     *
     * CP0137-15
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is after the birth date of 'the Child' plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is after the marriage date of 'the Veteran' plus 365 Days
     * then
     *      set the event date of 'the Award' to 'the Veteran' claim received date minus 365 days
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_15() {
        if (
            !child.isOnAwardAsMinorChild()
            && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
            && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
            && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
            && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())))
            && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
            && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getBirthDate()))
            && (veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating()) || veteranCommonDates.getMarriageDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
            && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate()))
        ) {
            // SR# 1184959 - 12/23/2019
            childResponse.getAward().setEventDate(RbpsXomUtil.addDaysToDate(-365, veteranCommonDates.getClaimDate()));
            logger.debug("awardEventDateDetermination_15: Award event date set to the claim date of the veteran minus 365 days: " + RbpsXomUtil.addDaysToDate(-365, veteranCommonDates.getClaimDate()));

        }
    }



    /*************************************************************
     *
     * CP0137-16
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the rating effective date of 'the Veteran' is not the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     * then
     *      set the event date of 'the Award' to the FCDR of 'the Veteran'
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_16() {
        if (
            !child.isOnAwardAsMinorChild()
            && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
            && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
            && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
            && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating()))
            && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
            && (veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating()) || veteranCommonDates.getMarriageDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
        ) {
            // SR# 1184959 - 12/23/2019
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("awardEventDateDetermination_16: Award event date set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }



    /*************************************************************
     *
     * CP0137-17
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the birth date of 'the Child' plus 365 Days
     *             - the marriage date of 'the Veteran' is after the birth date of 'the Child'
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_17() {
        if (
            !child.isOnAwardAsMinorChild()
                    && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                    && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                    && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) )
                    && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
                    && veteranCommonDates.getMarriageDate().after(child.getBirthDate())
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, child.getBirthDate())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, child.getBirthDate())) )
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("awardEventDateDetermination_17: Award event date set to the marriage date the veteran: " + veteranCommonDates.getMarriageDate());
        }
    }


    /*************************************************************
     *
     * CP0137-18
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is after the birth date of 'the Child' plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the marriage date of 'the Veteran' plus 365 Days ,
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran';
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_18() {
        if (
            !child.isOnAwardAsMinorChild()
                    && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                    && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                    && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) )
                    && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
                    && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getBirthDate()))
                    && veteranCommonDates.getMarriageDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate())) )
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("awardEventDateDetermination_18: Award event date set to the marriage date the veteran: " + veteranCommonDates.getMarriageDate());
        }
    }
}
