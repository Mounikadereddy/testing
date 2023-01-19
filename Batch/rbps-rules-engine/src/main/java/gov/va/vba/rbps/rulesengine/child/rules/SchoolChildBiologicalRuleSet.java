package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.child.SchoolChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.engine.Rule;

public class SchoolChildBiologicalRuleSet extends SchoolChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public SchoolChildBiologicalRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    /*************************************************************
     *                 FCDR outside 365 days                     *
     *************************************************************/

    /*************************************************************
     *
     *   RuleSet: CP0139-5A
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the FCDR of 'the Veteran'
     *             - the course end date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_5A() {
        if(
            fcdrOutside365Days() &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0 &&
            child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getFirstChangedDateofRating())
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("setBioChildEventDate_CP0139_5A: Set the event date of the Award to the FCDR of the Veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-5B
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             -  priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_5B() {
        if(
            fcdrOutside365Days() &&
            !childDecisionVariables.isPriorSchoolTermValid() &&
            child.getCurrentTerm().getCourseStudentStartDate().after(veteranCommonDates.getFirstChangedDateofRating())
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_5B: Set the event date of the Award to Course Start date of the Current Term: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-5C
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - the course end date of ( the last term of 'the Child' ) is present
     *             - the course end date of ( the last term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_5C() {
        if(
            fcdrOutside365Days() &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            RbpsXomUtil.isPresent(child.getLastTerm().getCourseEndDate()) &&
            child.getLastTerm().getCourseEndDate().after(veteranCommonDates.getFirstChangedDateofRating()) &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("setBioChildEventDate_CP0139_5C: Set the event date of the Award to the End Date of Last Term: " + child.getLastTerm().getCourseEndDate());

        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-5D
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *              -  priorSchoolTermValid
     *             - the course end date of ( the last term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the course start date of ( the current term of 'the Child' ) is after the course end date of ( the last term of 'the Child' ) plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_5D() {
        if(
            fcdrOutside365Days() &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            RbpsXomUtil.isPresent(child.getLastTerm().getCourseEndDate()) &&
            child.getCurrentTerm().getCourseStudentStartDate().after(veteranCommonDates.getFirstChangedDateofRating()) &&
            child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate()))
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_5D: Set the event date of the Award to Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-5E
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is after the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - the course end date of ( the last term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - the course end date of ( the last term of 'the Child' ) is before or the same as the FCDR of 'the Veteran'
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_5E() {
        if(
            fcdrOutside365Days() &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            RbpsXomUtil.isPresent(child.getLastTerm().getCourseEndDate()) &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0 &&
            child.getLastTerm().getCourseEndDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("setBioChildEventDate_CP0139_5E: Set the event date of the Award to the FCDR of the Veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }

    /*************************************************************
     *                 FCDR within 365 days                      *
     *************************************************************/

    /*************************************************************
     *
     *   RuleSet: CP0139-7A
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *            - priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_7A() {
        if(
            fcdrWithin365Days() &&
            !childDecisionVariables.isPriorSchoolTermValid() &&
            veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_7A: Set the event date of the Award to the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-7B
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             -  priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is after the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course end date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_7B() {
        if(
                fcdrWithin365Days() &&
                        !childDecisionVariables.isPriorSchoolTermValid() &&
                        veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) &&
                        child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getFirstChangedDateofRating())
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("setBioChildEventDate_CP0139_7B: Set the event date of the Award to the of the Veteran: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-7C
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *              -  priorSchoolTermValid
     *             - the course end date of ( the last term of 'the Child' ) is present
     *             - 'the Veteran' claim received date is before or the same as the course end date of ( the last term of 'the Child' ) plus 365 Days
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_7C() {
        if(
            fcdrWithin365Days() &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            RbpsXomUtil.isPresent(child.getLastTerm().getCourseEndDate()) &&
            veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getLastTerm().getCourseEndDate())) <= 0 &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("setBioChildEventDate_CP0139_7C: Set the event date of the Award to the Last Term Course End Date: " + child.getLastTerm().getCourseEndDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-7D
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *            	- priorSchoolTermValid
     *            	- 'the Veteran' claim received date is after the course end date of ( the last term of 'the Child' ) plus 365 Days
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_7D() {
        if(
                fcdrWithin365Days() &&
                        RbpsXomUtil.isPresent(child.getLastTerm()) &&
                        childDecisionVariables.isPriorSchoolTermValid() &&
                        veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getLastTerm().getCourseEndDate())) &&
                        veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0 &&
                        child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_7D: Set the event date of the Award to the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-7E
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is not the rating effective date of 'the Veteran'
     *             - 'the Veteran' claim received date is before or the same as the FCDR of 'the Veteran' plus 365 Days
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course start date of ( the current term of 'the Child' ) is after the course end date of ( the last term of 'the Child' ) plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_7E() {
        if(
            fcdrWithin365Days() &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0 &&
            child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate()))
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_7E: Set the event date of the Award to the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *                          No FCDR                          *
     *************************************************************/

    /*************************************************************
     *
     *   RuleSet: CP0139-1A
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is before or the same as '1 Year of childs 18th birth day'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as 'childs 18th birth day' plus 5 months
     *             - the course end date of ( the current term of 'the Child' ) is after 'childs 18th birth day'
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_1A() {
        if(
            noFcdr() &&
            veteranCommonDates.getClaimDate().compareTo(childDecisionVariables.getChild19BirthDay()) <= 0 &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate()) &&
            !childDecisionVariables.isPriorSchoolTermValid() &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay())) <= 0 &&
            child.getCurrentTerm().getCourseEndDate().after(childDecisionVariables.getChild18BirthDay())
        ) {
            childResponse.getAward().setEventDate(childDecisionVariables.getChild18BirthDay());
            logger.debug("setBioChildEventDate_CP0139_1A: Set the event date of the Award to the Child's 18th Birthday: " + childDecisionVariables.getChild18BirthDay());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-1B
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is before or the same as '1 Year of childs 18th birth day'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             -  priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is after 'childs 18th birth day' plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_1B() {
        if(
            noFcdr() &&
            veteranCommonDates.getClaimDate().compareTo(childDecisionVariables.getChild19BirthDay()) <= 0 &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate()) &&
            !childDecisionVariables.isPriorSchoolTermValid() &&
            child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay()))
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_1B: Set the event date of the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-1C
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - 'the Veteran' claim received date is before or the same as the course end date of ( the last term of 'the Child' ) plus 365 Days
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_1C() {
        if(
                noFcdr() &&
                RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
                RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate()) &&
                RbpsXomUtil.isPresent(child.getLastTerm()) &&
                childDecisionVariables.isPriorSchoolTermValid() &&
                veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getLastTerm().getCourseEndDate())) <= 0 &&
                child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("setBioChildEventDate_CP0139_1C: Set the event date of the Last Term Course End Date: " + child.getLastTerm().getCourseEndDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-1D
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - 'the Veteran' claim received date is after the course end date of ( the last term of 'the Child' ) plus 365 Days
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_1D() {
        if(
            noFcdr() &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate()) &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0 &&
            veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getLastTerm().getCourseEndDate())) &&
            veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_1D: Set the event date of the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-1E
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - the course start date of ( the current term of 'the Child' ) is after the course end date of ( the last term of 'the Child' ) plus 5 months
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_1E() {
        if(
            noFcdr() &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate()) &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) &&
            veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_1E: Set the event date of the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-1F
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is after '1 Year of childs 18th birth day'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is before or the same as the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course end date of ( the current term of 'the Child' ) is after 'the Veteran' claim received date
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_1F() {
        if(
            noFcdr() &&
            veteranCommonDates.getClaimDate().after(childDecisionVariables.getChild19BirthDay()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate()) &&
            !childDecisionVariables.isPriorSchoolTermValid() &&
            veteranCommonDates.getClaimDate().compareTo(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) <= 0 &&
            child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getClaimDate())
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_1F: Set the event date of the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-1G
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is not present
     *             - 'the Veteran' claim received date is after '1 Year of childs 18th birth day'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - 'the Veteran' claim received date is after the course start date of ( the current term of 'the Child' ) plus 365 Days
     *             - the course end date of ( the current term of 'the Child' ) is after 'the Veteran' claim received date
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_1G() {
        if(
            noFcdr() &&
            veteranCommonDates.getClaimDate().after(childDecisionVariables.getChild19BirthDay()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getCourseStudentStartDate()) &&
            !childDecisionVariables.isPriorSchoolTermValid() &&
            veteranCommonDates.getClaimDate().after(RbpsXomUtil.addDaysToDate(365, child.getCurrentTerm().getCourseStudentStartDate())) &&
            child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getClaimDate())
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getClaimDate());
            logger.debug("setBioChildEventDate_CP0139_1G: Set the event date of the Veteran Claim Date: " + veteranCommonDates.getClaimDate());
        }
    }

    /*************************************************************
     *                       Single Rating                       *
     *************************************************************/

    /*************************************************************
     *
     *   RuleSet: CP0139-3A
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the FCDR of 'the Veteran'
     *             - the course end date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_3A() {
        if(
            singleRating() &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(veteranCommonDates.getFirstChangedDateofRating()) <= 0 &&
            child.getCurrentTerm().getCourseEndDate().after(veteranCommonDates.getFirstChangedDateofRating())
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("setBioChildEventDate_CP0139_3A: Set the event date of the Veteran FCDR: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-3B
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the course start date of ( the current term of 'the Child' ) is after 'childs 18th birth day' plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_3B() {
        if(
            singleRating() &&
            !childDecisionVariables.isPriorSchoolTermValid() &&
            child.getCurrentTerm().getCourseStudentStartDate().after(veteranCommonDates.getFirstChangedDateofRating()) &&
            child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay()))
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_3B: Set the event date of the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-3C
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - priorSchoolTermValid is false
     *             - the course start date of ( the current term of 'the Child' ) is after the FCDR of 'the Veteran'
     *             - the course start date of ( the current term of 'the Child' ) is after 'childs 18th birth day' plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_3C() {
        if(
            singleRating() &&
            !childDecisionVariables.isPriorSchoolTermValid() &&
            child.getCurrentTerm().getCourseStudentStartDate().after(veteranCommonDates.getFirstChangedDateofRating()) &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay())) <= 0
        ) {
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());
            logger.debug("setBioChildEventDate_CP0139_3C: Set the event date of the Veteran FCDR: " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-3D
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *              - the last term of 'the Child'  is present
     *              - priorSchoolTermValid
     *             - the course end date of ( the last term of 'the Child' ) is present
     *             - the course start date of ( the current term of 'the Child' ) is before or the same as the course end date of ( the last term of 'the Child' ) plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_3D() {
        if(
            singleRating() &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            RbpsXomUtil.isPresent(child.getLastTerm().getCourseEndDate()) &&
            child.getCurrentTerm().getCourseStudentStartDate().compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate())) <= 0
        ) {
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("setBioChildEventDate_CP0139_3D: Set the event date of the Last Term Course End Date: " + child.getLastTerm().getCourseEndDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0139-3E
     *
     *   	all of the following conditions are true :
     *             - the child type of 'the Child' is BIOLOGICAL_CHILD
     *             - the FCDR of 'the Veteran' is present
     *             - the FCDR of 'the Veteran' is the rating effective date of 'the Veteran'
     *             - the current term of 'the Child' is present
     *             - the course start date of ( the current term of 'the Child' ) is present
     *             - the last term of 'the Child'  is present
     *             - priorSchoolTermValid
     *             - the course start date of ( the current term of 'the Child' ) is after the course end date of ( the last term of 'the Child' ) plus 5 months
     *
     *************************************************************/
    @Rule
    public void setBioChildEventDate_CP0139_3E() {
        if(
            singleRating() &&
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            childDecisionVariables.isPriorSchoolTermValid() &&
            RbpsXomUtil.isPresent(child.getLastTerm().getCourseEndDate()) &&
            child.getCurrentTerm().getCourseStudentStartDate().after(RbpsXomUtil.addMonthsToDate(5, child.getLastTerm().getCourseEndDate()))
        ) {
            childResponse.getAward().setEventDate(child.getCurrentTerm().getCourseStudentStartDate());
            logger.debug("setBioChildEventDate_CP0139_3E: Set the event date of the Current Term Course Start Date: " + child.getCurrentTerm().getCourseStudentStartDate());
        }
    }

}
