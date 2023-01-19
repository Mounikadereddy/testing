package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.child.SchoolChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.engine.Rule;

public class SchoolChildStepChildRuleSet extends SchoolChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public SchoolChildStepChildRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }


    /**
     * Rule: CP0139-6A
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the FCDR of 'the Veteran'
     *             - the course end date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_6A() {
        if (
                fcdrOutside365Days()
                    && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                    && child.getCurrentTerm().getCourseStudentStartDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0
                    && veteranCommonDates.getMarriageDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0
                    && child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getFirstChangedDateofRating())
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("determineSchoolChildStepChildEventDate_139_6A: Event date of the award set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }

    }


    /**
     * Rule: CP0139-6B
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             -  priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' )
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_6B() {
        if (
                fcdrOutside365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && !childDecisionVariables.isPriorSchoolTermValid()
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0
                        && child.getCurrentTerm().getCourseStudentStartDate().after(veteranCommonDates.getFirstChangedDateofRating())
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_6B: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }

    }




    /**
     * Rule: CP0139-6C
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              -  priorSchoolTermValid
     *               - the last term of 'the Child'  is present
     *             - the course end date of ( the last term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the course end date of ( the last term of 'the Child' )
     * then
     *     set the event date of 'the Award' to the course end date of ( the last term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_6C() {
        if (
                fcdrOutside365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && child.getLastTerm().getCourseEndDate().after(veteranCommonDates.getFirstChangedDateofRating())
                        && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
                        && veteranCommonDates.getMarriageDate().compareTo(child.getLastTerm().getCourseEndDate()) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_6C: Event date of the award set to the course end date of the last term of the child: " + child.getLastTerm().getCourseEndDate());
        }

    }


    /**
     * Rule: CP0139-6D
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid
     *             - the last term of 'the Child'  is present
     *             - the course start date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the course start date of ( the current term of 'the Child' ) is after the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' )
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_6D() {
        if (
                fcdrOutside365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && child.getCurrentTerm().getCourseStudentStartDate().after(veteranCommonDates.getFirstChangedDateofRating())
                        && child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate()))
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0

        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_6D: Event date of the award set to the course end date of the last term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }

    }



    /**
     * Rule: CP0139-6E
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid
     *              - the last term of 'the Child'  is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - the course end date of ( the last term of 'the Child' ) is before or the same as the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_6E() {
        if (
                fcdrOutside365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
                        && child.getLastTerm().getCourseEndDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0
                        && veteranCommonDates.getMarriageDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0

        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("determineSchoolChildStepChildEventDate_139_6E: Event date of the award set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }

    }


    /**
     * Rule: CP0139-8A
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of the current term of 'the Child' ,
     * then
     * 	set the event date of 'the Award' to the course start date of the current term of 'the Child' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_8A() {
        if (
                fcdrWithin365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && !childDecisionVariables.isPriorSchoolTermValid()
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0

        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_8A: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }

    }



    /**
     * Rule: CP0139-8B
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             -  priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is after the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course end date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_8B() {
        if (
                fcdrWithin365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && !childDecisionVariables.isPriorSchoolTermValid()
                        && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate()))
                        && child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getFirstChangedDateofRating())
                        && veteranCommonDates.getMarriageDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0

        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("determineSchoolChildStepChildEventDate_139_8B: Event date of the award set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }

    }


    /**
     * Rule: CP0139-8C
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *             -  priorSchoolTermValid
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - 'the Veteran' claim received date is before or the same as the course end date of ( the last term of 'the Child' ) plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the course end date of ( the last term of 'the Child' )
     * then
     *     set the event date of 'the Award' to the course end date of ( the last term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_8C() {
        if (
                fcdrWithin365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
                        && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getLastTerm().getCourseEndDate())) <= 0
                        && veteranCommonDates.getMarriageDate().compareTo(child.getLastTerm().getCourseEndDate()) <= 0

        ) {
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_8C: Event date of the award set to end date of the last term of the child: " + child.getLastTerm().getCourseEndDate());
        }

    }




    /**
     * Rule: CP0139-8D
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - 'the Veteran' claim received date is after the course end date of ( the last term of 'the Child' ) plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' )
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_8D() {
        if (
                fcdrWithin365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
                        && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
                        && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getLastTerm().getCourseEndDate()))
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0

        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_8D: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }

    }





    /**
     * Rule: CP0139-8E
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course start date of ( the current term of 'the Child' ) is after the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' )
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_8E() {
        if (
                fcdrWithin365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
                        && child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate()))
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0

        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_8E: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }

    }


    /**
     * Rule: CP0139-8F
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the marriage date of 'the Veteran' is after the course start date of ( the current term of 'the Child' )
     *             - the marriage date of 'the Veteran' is before the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_8F() {
        if (
                fcdrWithin365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && !childDecisionVariables.isPriorSchoolTermValid()
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
                        && veteranCommonDates.getMarriageDate().after( child.getCurrentTerm().getCourseStudentStartDate())
                        && veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating())

        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_8E: Event date of the award set to the marriage date of the veteran: " + veteranCommonDates.getMarriageDate());
        }

    }


    /**
     * Rule: CP0139-8G
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the marriage date of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is after the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - 'the Veteran' claim received date is before or the same as the marriage date of 'the Veteran' plus 365 Days
     *             - the marriage date of 'the Veteran' is before the FCDR of 'the Veteran'
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_8G() {
        if (
                fcdrWithin365Days()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && !childDecisionVariables.isPriorSchoolTermValid()
                        && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate()))
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, veteranCommonDates.getMarriageDate())) <= 0
                        && veteranCommonDates.getMarriageDate().before(veteranCommonDates.getFirstChangedDateofRating())

        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_8G: Event date of the award set to the marriage date of the veteran: " + veteranCommonDates.getMarriageDate());
        }

    }


    /**
     * Rule: CP0139-2A
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is before or the same as '1 Year of childs 18th birth day'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as 'childs 18th birth day' plus 5 months
     *             - the course end date of ( the current term of 'the Child' ) is after 'childs 18th birth day'
     *             - the marriage date of 'the Veteran' is before or the same as 'childs 18th birth day'
     * then
     *     set the event date of 'the Award' to 'childs 18th birth day' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_2A() {
        if (
            noFcdr()
                    && veteranCommonDates.getClaimDate().compareTo(childDecisionVariables.getChild19BirthDay()) <= 0
                    && RbpsXomUtil.isPresent(child.getCurrentTerm())
                    && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                    && !childDecisionVariables.isPriorSchoolTermValid()
                    && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay())) <= 0
                    && child.getCurrentTerm().getCourseEndDate().after(childDecisionVariables.getChild18BirthDay())
                    && veteranCommonDates.getMarriageDate().compareTo(childDecisionVariables.getChild18BirthDay()) <= 0
        ) {
            childResponse.getAward().setEventDate(childDecisionVariables.getChild18BirthDay());
            logger.debug("determineSchoolChildStepChildEventDate_139_2A: Event date of the award set to the 18th birthday of the child: " + childDecisionVariables.getChild18BirthDay());
        }

    }



    /**
     * Rule: CP0139-2B
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is before or the same as '1 Year of childs 18th birth day'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             -  priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is after 'childs 18th birth day' plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' ) ,
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_2B() {
        if (
                noFcdr()
                        && veteranCommonDates.getClaimDate().compareTo(childDecisionVariables.getChild19BirthDay()) <= 0
                        && RbpsXomUtil.isPresent(child.getCurrentTerm())
                        && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                        && !childDecisionVariables.isPriorSchoolTermValid()
                        && child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay()))
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_2B: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }

    }


    /**
     * Rule: CP0139-2C
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             -  priorSchoolTermValid
     *             - the last term of 'the Child'  is present
     *             - 'the Veteran' claim received date is before or the same as the course end date of ( the last term of 'the Child' ) plus 365 Days
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the course end date of ( the last term of 'the Child' ) ,
     * then
     *     set the event date of 'the Award' to the course end date of ( the last term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_2C() {
        if (
                noFcdr()
                        && RbpsXomUtil.isPresent(child.getCurrentTerm())
                        && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getLastTerm().getCourseEndDate())) <= 0
                        && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
                        && veteranCommonDates.getMarriageDate().compareTo(child.getLastTerm().getCourseEndDate()) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_2C: Event date of the award set to the course end date of the last term of the child: " + child.getLastTerm().getCourseEndDate());
        }

    }

    /**
     * Rule: CP0139-2D
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid
     *             - the last term of 'the Child'  is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - 'the Veteran' claim received date is after the course end date of ( the last term of 'the Child' ) plus 365 Days
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             -  the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' ) ,
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_2D() {
        if (
                noFcdr()
                        && RbpsXomUtil.isPresent(child.getCurrentTerm())
                        && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
                        && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getLastTerm().getCourseEndDate()))
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_2D: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }


    /**
     * Rule: CP0139-2E
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid
     *             - the last term of 'the Child'  is present
     *             - the course start date of ( the current term of 'the Child' ) is after the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             -  the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' ) ,
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_2E() {
        if (
            noFcdr()
                    && RbpsXomUtil.isPresent(child.getCurrentTerm())
                    && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                    && childDecisionVariables.isPriorSchoolTermValid()
                    && RbpsXomUtil.isPresent(child.getLastTerm())
                    && child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate()))
                    && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365,  child.getCurrentTerm().getCourseStudentStartDate()))<= 0
                    && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_2E: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }


    /**
     * Rule: CP0139-2F
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is after '1 Year of childs 18th birth day'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' ) ,
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_2F() {
        if (
                noFcdr()
                        && veteranCommonDates.getClaimDate().after(childDecisionVariables.getChild19BirthDay())
                        && RbpsXomUtil.isPresent(child.getCurrentTerm())
                        && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                        && !childDecisionVariables.isPriorSchoolTermValid()
                        && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365,  child.getCurrentTerm().getCourseStudentStartDate()))<= 0
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_2F: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }


    /**
     * Rule: CP0139-2G
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is after '1 Year of childs 18th birth day'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is after the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course end date of ( the current term of 'the Child' ) is after 'the Veteran' claim received date
     *             - 'the Veteran' claim received date is after the marriage date of 'the Veteran' plus 365 Days
     *             - the marriage date of 'the Veteran' is before or the same as 'the Veteran' claim received date ,
     * then
     *     set the event date of 'the Award' to 'the Veteran' claim received date ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_2G() {
        if (
            noFcdr()
                    && veteranCommonDates.getClaimDate().after(childDecisionVariables.getChild19BirthDay())
                    && RbpsXomUtil.isPresent(child.getCurrentTerm())
                    && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                    && !childDecisionVariables.isPriorSchoolTermValid()
                    && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365,  child.getCurrentTerm().getCourseStudentStartDate()))
                    && child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getClaimDate())
                    && veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365,  veteranCommonDates.getMarriageDate()))
                    && veteranCommonDates.getMarriageDate().compareTo(veteranCommonDates.getClaimDate()) <= 0
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getClaimDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_2G: Event date of the award set to the veteran claim received date: " + veteranCommonDates.getClaimDate());
        }
    }


    /**
     * Rule: CP0139-2H
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the marriage date of 'the Veteran' is after the course start date of ( the current term of 'the Child' )
     *             - 'the Veteran' claim received date is before or the same as the marriage date of 'the Veteran' plus 365 Days ,
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_2H() {
        if (
            noFcdr()
                && RbpsXomUtil.isPresent(child.getCurrentTerm())
                && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
                && veteranCommonDates.getMarriageDate().after(child.getCurrentTerm().getCourseStudentStartDate())
                && veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365,  veteranCommonDates.getMarriageDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_2H: Event date of the award set to the veteran marriage date: " + veteranCommonDates.getMarriageDate());
        }
    }


    /**
     * Rule: CP0139-4A
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the marriage date of 'the Veteran' is present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the FCDR of 'the Veteran'
     *             - the course end date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran'  ,
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_4A() {
        if (
            singleRating()
                && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                && child.getCurrentTerm().getCourseStudentStartDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0
                && child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getFirstChangedDateofRating())
                && veteranCommonDates.getMarriageDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0

        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("determineSchoolChildStepChildEventDate_139_4A: Event date of the award set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }


    /**
     * Rule: CP0139-4B
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the marriage date of 'the Veteran' is present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             -  priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the course start date of ( the current term of 'the Child' ) is after 'childs 18th birth day' plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' ) ,
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_4B() {
        if (
                singleRating()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && !childDecisionVariables.isPriorSchoolTermValid()
                        && child.getCurrentTerm().getCourseStudentStartDate().after(veteranCommonDates.getFirstChangedDateofRating())
                        && child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay()))
                        && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0

        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_4B: Event date of the award set to the course start date of the current term of the child: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }



    /**
     * Rule: CP0139-4C
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the marriage date of 'the Veteran' is present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as 'childs 18th birth day' plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the FCDR of 'the Veteran' ,
     * then
     *     set the event date of 'the Award' to the FCDR of 'the Veteran'
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_4C() {
        if (
            singleRating()
                && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                && !childDecisionVariables.isPriorSchoolTermValid()
                && child.getCurrentTerm().getCourseStudentStartDate().after(veteranCommonDates.getFirstChangedDateofRating())
                && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay())) <= 0
                && veteranCommonDates.getMarriageDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0

        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("determineSchoolChildStepChildEventDate_139_4C: Event date of the award set to the FCDR of the veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }




    /**
     * Rule: CP0139-4D
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the marriage date of 'the Veteran' is present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *             -  priorSchoolTermValid
     *             - the course end date of ( the last term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the course end date of ( the last term of 'the Child' ) ,
     * then
     *     set the event date of 'the Award' to the course end date of ( the last term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_4D() {
        if (
                singleRating()
                        && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                        && RbpsXomUtil.isPresent(child.getLastTerm())
                        && childDecisionVariables.isPriorSchoolTermValid()
                        && RbpsXomUtil.isPresent(child.getLastTerm().getCourseEndDate())
                        && child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
                        && veteranCommonDates.getMarriageDate().compareTo(child.getLastTerm().getCourseEndDate()) <= 0

        ) {
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_4D: Event date of the award set to the course end date of the last term: " + child.getLastTerm().getCourseEndDate());
        }
    }

    /**
     * Rule: CP0139-4E
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the marriage date of 'the Veteran' is present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - the course start date of ( the current term of 'the Child' ) is after the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - the marriage date of 'the Veteran' is before or the same as the course start date of ( the current term of 'the Child' ) ,
     * then
     *     set the event date of 'the Award' to the course start date of ( the current term of 'the Child' ) ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_4E() {
        if (
            singleRating()
                && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                && RbpsXomUtil.isPresent(child.getLastTerm())
                && childDecisionVariables.isPriorSchoolTermValid()
                && child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate()))
                && veteranCommonDates.getMarriageDate().compareTo(child.getCurrentTerm().getCourseStudentStartDate()) <= 0

        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_4E: Event date of the award set to the course start date of the current term: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /**
     * Rule: CP0139-09
     *
     * if
     *     all of the following conditions are true :
     *             - the child type of 'the Child' is STEPCHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the marriage date of 'the Veteran' is after the course start date of ( the current term of 'the Child' )
     *             - the marriage date of 'the Veteran' is after the FCDR of 'the Veteran' ,
     * then
     *     set the event date of 'the Award' to the marriage date of 'the Veteran' ;
     */
    @Rule
    public void determineSchoolChildStepChildEventDate_139_09() {
        if (
            RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating())
            && RbpsXomUtil.isPresent(child.getCurrentTerm())
            && RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate())
            && veteranCommonDates.getMarriageDate().after(child.getCurrentTerm().getCourseStudentStartDate())
            && veteranCommonDates.getMarriageDate().after(veteranCommonDates.getFirstChangedDateofRating())

        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("determineSchoolChildStepChildEventDate_139_09: Event date of the award set to the marriage date of the veteran: " + veteranCommonDates.getMarriageDate());
        }
    }
}
