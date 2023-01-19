package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;

public class MinorChildBiologicalRuleSet extends ChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public MinorChildBiologicalRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    /*************************************************************
     *
     * CP0137-01
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is after '1 Year of date of birth of the child'
     * then
     *     set the event date of 'the Award' to 'the Veteran' claim received date ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_01() {
        if (
                !child.isOnAwardAsMinorChild()
                && RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating())
                && veteranCommonDates.getClaimDate().after(childDecisionVariables.getDobPlusOneYear())
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getClaimDate());
            logger.debug("awardEventDateDetermination_01: Award event date set to Veteran claim received date: " + veteranCommonDates.getClaimDate());
        }
    }


    /*************************************************************
     *
     * CP0137-02
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is before or the same as '1 Year of date of birth of the child' ,
     * then
     *     set the event date of 'the Award' to the birth date of 'the Child' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_02() {
        if (
                !child.isOnAwardAsMinorChild()
                && RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating())
                && veteranCommonDates.getClaimDate().before(childDecisionVariables.getDobPlusOneYear())
        ) {
            childResponse.getAward().setEventDate(child.getBirthDate());
            logger.debug("awardEventDateDetermination_02: Award event date set to child birth date: " + child.getBirthDate());
        }
    }


    /*************************************************************
     *
     * CP0137-03
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the birth date of 'the Child' is after the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the birth date of 'the Child' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_03() {
        if (
                !child.isOnAwardAsMinorChild()
                && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                && child.getBirthDate().after(veteranCommonDates.getFirstChangedDateofRating())
        ) {
            childResponse.getAward().setEventDate(child.getBirthDate());
            logger.debug("awardEventDateDetermination_03: Award event date set to child birth date: " + child.getBirthDate());
        }
    }


    /*************************************************************
     *
     * CP0137-04
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran'  is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     *
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_04() {
        if (
                !child.isOnAwardAsMinorChild()
                && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                && veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
                && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("awardEventDateDetermination_04: Award event date set to FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }


    /*************************************************************
     *
     * CP0137-05
     *
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran'  is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the birth date of 'the Child'  plus 365 Days
     * then
     *     set the event date of 'the Award' to the birth date of 'the Child' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_05() {
        if (
                !child.isOnAwardAsMinorChild()
                    && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                    && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())))
                    && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
                    && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, child.getBirthDate())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, child.getBirthDate())))
        ) {
            childResponse.getAward().setEventDate(child.getBirthDate());
            logger.debug("awardEventDateDetermination_05: Award event date set to the child's birth date: " + child.getBirthDate());
        }
    }

    /*************************************************************
     *
     * CP0137-06
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is after the birth date of 'the Child' plus 365 Days
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_06() {
        if (
                !child.isOnAwardAsMinorChild()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                        && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
                        && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())))
                        && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
                        && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getBirthDate()))
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("awardEventDateDetermination_06: Award event date set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }


    /*************************************************************
     *
     * CP0137-07
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - it is not true that 'the Child'  is on award as minor child
     *             - the FCDR of 'the Veteran' is present
     *             - the rating effective date of 'the Veteran' is not the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - 'the Veteran' claim received date is after the birth date of 'the Child' plus 365 Days
     *             - the birth date of 'the Child' is before or the same as the FCDR of 'the Veteran' ,
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     *
     *************************************************************/
    @Rule
    public void awardEventDateDetermination_07() {
        if (
                !child.isOnAwardAsMinorChild()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
                        && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
                        && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating()))
                        && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getBirthDate()))
                        && (child.getBirthDate().before(veteranCommonDates.getFirstChangedDateofRating()) || child.getBirthDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("awardEventDateDetermination_07: Award event date set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }
}

