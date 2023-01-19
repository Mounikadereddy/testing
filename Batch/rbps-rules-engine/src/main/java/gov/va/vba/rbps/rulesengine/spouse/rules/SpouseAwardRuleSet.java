package gov.va.vba.rbps.rulesengine.spouse.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.rulesengine.engine.BaseRuleSet;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.spouse.SpouseDecisionVariables;
import gov.va.vba.rbps.rulesengine.spouse.SpouseResponse;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;


public class SpouseAwardRuleSet extends BaseRuleSet {

    protected final Spouse spouse;
    protected final SpouseResponse spouseResponse;
    protected final SpouseDecisionVariables spouseDecisionVariables;
    protected final VeteranCommonDates veteranCommonDates;
    private Logger logger = Logger.getLogger(this.getClass());

    public SpouseAwardRuleSet(Spouse spouse, SpouseResponse spouseResponse, SpouseDecisionVariables spouseDecisionVariables, VeteranCommonDates veteranCommonDates) {
        this.spouse = spouse;
        this.spouseResponse = spouseResponse;
        this.spouseDecisionVariables = spouseDecisionVariables;
        this.veteranCommonDates = veteranCommonDates;
    }


    /*************************************************************
     *
     *   Rule: CP0135 - Spouse Award Decision Determination
     *
     *   if
     * 	   'the Spouse' is present
     *  then
     * 	   set the dependency status type of 'the Award' to SPOUSE  ;
     * 	   set the dependency decision type of 'the Award' to DEPENDENCY_ESTABLISHED ;
     *
     *************************************************************/
    @Rule
    public void isSpousePresent() {
        if (RbpsXomUtil.isPresent(spouse)) {
            spouseResponse.getAward().setDependencyDecisionType(DependentDecisionType.DEPENDENCY_ESTABLISHED);
            spouseResponse.getAward().setDependencyStatusType(DependentStatusType.SPOUSE);

            logger.debug("Message: Award Dependency Decision Type set to " + DependentDecisionType.DEPENDENCY_ESTABLISHED);
            logger.debug("Message: Award Dependency Status Type set to " + DependentStatusType.SPOUSE);
        }
    }


    /*************************************************************
     *
     *   Rule: CP0136-01
     *
     *   if
     *     the FCDR of 'the Veteran' is not present
     *     and 'the Veteran' claim received date is before or the same as '1 Year of date of marriage'
     *  then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     *
     *
     *************************************************************/
    @Rule
    public void eventDateDetermination_CP0136_01() {
        if (RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating())
                && (veteranCommonDates.getClaimDate().before(spouseDecisionVariables.getMarragePlus1year()) || veteranCommonDates.getClaimDate().equals(spouseDecisionVariables.getMarragePlus1year()))){
            spouseResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("eventDateDetermination_CP0136_01: Award event date set to Veteran marriage date.");
        }
    }



    /*************************************************************
     *
     *   Rule: CP0136-02
     *
     * if
     *     the FCDR of 'the Veteran' is not present
     *     and 'the Veteran' claim received date is after '1 Year of date of marriage'
     * then
     *     set the event date of 'the Award' to 'the Veteran' claim received date ;
     *
     *
     *************************************************************/
    @Rule
    public void eventDateDetermination_CP0136_02() {
        if (RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating()) && veteranCommonDates.getClaimDate().after(spouseDecisionVariables.getMarragePlus1year())){
            spouseResponse.getAward().setEventDate(veteranCommonDates.getClaimDate());
            logger.debug("eventDateDetermination_CP0136_02: Award event date set to Veteran claim date.");
        }
    }



    /*************************************************************
     *
     *   Rule: CP0136-03
     *
     * if
     *     all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is after the FCDR of 'the Veteran' ,
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     *
     *
     *************************************************************/
    @Rule
    public void eventDateDetermination_CP0136_03() {
        if (RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) && veteranCommonDates.getMarriageDate().after(veteranCommonDates.getFirstChangedDateofRating())){
            spouseResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("eventDateDetermination_CP0136_03: Award event date set to Veteran marriage date.");
        }
    }


    /*************************************************************
     *
     *   Rule: CP0136-04
     *
     * if
     *     all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran' ,
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     *
     *
     *************************************************************/
    @Rule
    public void eventDateDetermination_CP0136_04() {
        if (
            RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
            && veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
            && (veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating())
            || veteranCommonDates.getMarriageDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
        ) {
            spouseResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("eventDateDetermination_CP0136_04: Award event date set to Veteran FCDR.");
        }

    }

    /*************************************************************
     *
     *   Rule: CP0136-05
     *
     * if
     *     all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the marriage date of 'the Veteran' plus 365 Days
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     *
     *
     *************************************************************/
    @Rule
    public void eventDateDetermination_CP0136_05() {
        if (
            RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
            && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
            && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())))
            && (veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating()) || veteranCommonDates.getMarriageDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
            && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate())))
        ) {
            spouseResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("eventDateDetermination_CP0136_05: Award event date set to Veteran marriage date.");
        }

    }


    /*************************************************************
     *
     *   Rule: CP0136-06
     *
     * if
     *     all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is after the marriage date of 'the Veteran' plus 365 Days
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     *
     *
     *************************************************************/
    @Rule
    public void eventDateDetermination_CP0136_06() {
        if (
            RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
            && !veteranCommonDates.getFirstChangedDateofRating().equals(veteranCommonDates.getRatingEffectiveDate())
            && (veteranCommonDates.getClaimDate().before(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())) || veteranCommonDates.getClaimDate().equals(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating())))
            && (veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating()) || veteranCommonDates.getMarriageDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
            && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate()))
        ) {
            spouseResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("eventDateDetermination_CP0136_06: Award event date set to Veteran FCDR.");
        }
    }


    /*************************************************************
     *
     *   Rule: CP0136-07
     *
     * if
     *     all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the rating effective date of 'the Veteran' is not the FCDR of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - 'the Veteran' claim received date is after the marriage date of 'the Veteran' plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     *
     *
     *************************************************************/
    @Rule
    public void eventDateDetermination_CP0136_07() {
        if (
            RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
            && !veteranCommonDates.getRatingEffectiveDate().equals(veteranCommonDates.getFirstChangedDateofRating())
            && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getFirstChangedDateofRating()))
            && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate()))
            && (veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating()) || veteranCommonDates.getMarriageDate().equals(veteranCommonDates.getFirstChangedDateofRating()))
        ) {
            spouseResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("eventDateDetermination_CP0136_07: Award event date set to Veteran FCDR.");
        }
    }


}
