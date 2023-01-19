package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;

public class ChildWithNoEventDateRuleSet extends ChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public ChildWithNoEventDateRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    /*************************************************************
     *
     * Minor Child with No Event Date
     *
     * if
     *     all of the following conditions are true :
     *     		- 'the Award' is present
     *             - the dependency status type of 'the Award' is present
     *             - the dependency status type of 'the Award' is MINOR_CHILD
     *             - the event date of 'the Award' is not present
     *             - it is not true that 'the Child' is on award as minor child
     *
     *
     *************************************************************/
    @Rule
    public void minorChildWithNoEventDate () {
        if (
                RbpsXomUtil.isPresent(childResponse.getAward())
                    && RbpsXomUtil.isPresent(childResponse.getAward().getDependencyStatusType())
                    && childResponse.getAward().getDependencyStatusType().equals(DependentStatusType.MINOR_CHILD)
                    && RbpsXomUtil.isNotPresent(childResponse.getAward().getEventDate())
                    && !child.isOnAwardAsMinorChild()
        ) {
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.NO_EVENT_DATE_MINOR_CHILD, child.getLastName(), child.getFirstName()));
        }
    }


    /*************************************************************
     *
     * Minor to School Child with No Event Date
     *
     * if
     *     all of the following conditions are true :
     *     		- 'the minorSchoolChildAward' is present
     *             - the dependency status type of 'the minorSchoolChildAward' is present
     *             - the dependency status type of 'the minorSchoolChildAward' is SCHOOL_CHILD
     *             - the event date of 'the minorSchoolChildAward' is not present
     *
     *************************************************************/
    @Rule
    public void minorToSchoolChildWithNoEventDate () {
        if (
                RbpsXomUtil.isPresent(childResponse.getMinorSchoolChildAward())
                        && RbpsXomUtil.isPresent(childResponse.getMinorSchoolChildAward().getDependencyStatusType())
                        && childResponse.getMinorSchoolChildAward().getDependencyStatusType().equals(DependentStatusType.SCHOOL_CHILD)
                        && RbpsXomUtil.isNotPresent(childResponse.getMinorSchoolChildAward().getEventDate())
        ) {
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.NO_EVENT_DATE_MINOR_TO_SCHOOL_CHILD, child.getLastName(), child.getFirstName()));
        }
    }


    /*************************************************************
     *
     * Prior School Child with No Event Date
     *
     * if
     *     all of the following conditions are true :
     *     		- 'PriorSchoolChild' is present
     *             - the dependency status type of 'PriorSchoolChild' is present
     *             - the dependency status type of 'PriorSchoolChild' is SCHOOL_CHILD
     *             - the event date of 'PriorSchoolChild' is not present
     * then
     *    set PriorSchoolTermStatus to "ignore";
     *
     *
     *************************************************************/
    @Rule
    public void priorSchoolChildWithNoEventDate () {

        if (
                RbpsXomUtil.isPresent(childResponse.getPriorSchoolChild())
                        && RbpsXomUtil.isPresent(childResponse.getPriorSchoolChild().getDependencyStatusType())
                        && childResponse.getPriorSchoolChild().getDependencyStatusType().equals(DependentStatusType.SCHOOL_CHILD)
                        && RbpsXomUtil.isNotPresent(childResponse.getPriorSchoolChild().getEventDate())
        ) {
            childResponse.setPriorSchoolTermStatus("ignore");
            childResponse.getLogMessage().addException(String.format(ChildMessages.NO_EVENT_DATE_PRIOR_SCHOOL_CHILD, child.getLastName(), child.getFirstName()));
            logger.debug("priorSchoolChildWithNoEventDate: Prior School Term Status set to ignore.");
        }
    }


    /*************************************************************
     *
     * School Child with No Event Date
     *
     * if
     *     all of the following conditions are true :
     *     		- 'the Award' is present
     *             - the dependency status type of 'the Award' is present
     *             - the dependency status type of 'the Award' is SCHOOL_CHILD
     *             - the event date of 'the Award' is not present
     *
     *
     *************************************************************/
    @Rule
    public void schoolChildWithNoEventDate () {
        if (
                RbpsXomUtil.isPresent(childResponse.getAward())
                        && RbpsXomUtil.isPresent(childResponse.getAward().getDependencyStatusType())
                        && childResponse.getAward().getDependencyStatusType().equals(DependentStatusType.SCHOOL_CHILD)
                        && RbpsXomUtil.isNotPresent(childResponse.getAward().getEventDate())
        ) {
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.NO_EVENT_DATE_SCHOOL_CHILD, child.getLastName(), child.getFirstName()));
        }
    }


    /*************************************************************
     *
     * Verify Minor School Child Event Date
     *
     * if all of the following conditions are true :
     *  - the dependency status type of 'the Award' is present
     *  - the dependency status type of 'the Award' is SCHOOL_CHILD
     *  - the event date of 'the Award' is present
     *  - the event date of 'the Award' is greater than or equal to the 18th birthday of 'the Child'
     *
     *
     *************************************************************/
    @Rule
    public void verifyMinorSchoolChildEventDate() {
        Award award = childResponse.getAward();
        if (
                RbpsXomUtil.isPresent(award.getDependencyStatusType())
                        && award.getDependencyStatusType().equals(DependentStatusType.MINOR_CHILD)
                        && RbpsXomUtil.isPresent(award.getEventDate())
                        && (award.getEventDate().after(childDecisionVariables.getChild18BirthDay()) || award.getEventDate().equals(childDecisionVariables.getChild18BirthDay()))
        ) {
            childResponse.setAward(null);
            logger.debug("verifyMinorSchoolChildEventDate: Award set to null");
        }

    }


    /*************************************************************
     *
     * Verify Minor Step Child Second Event Date
     *
     * if all of the following conditions are true :
     *  - the child type of 'the Child' is STEPCHILD
     *  - the marriage date of 'the Veteran' is present
     *  - 'the minorSchoolChildAward' is present
     *  - the event date of 'the minorSchoolChildAward' is present
     *  - the marriage date of 'the Veteran' is after the event date of 'the minorSchoolChildAward' ,
     *
     * then
     *     set the event date of 'the minorSchoolChildAward' to the marriage date of 'the Veteran' ;
     *
     *************************************************************/
    @Rule
    public void verifyMinorStepChildSecondEventDate () {
        if (
            child.getChildType().equals(ChildType.STEPCHILD)
                && RbpsXomUtil.isPresent(veteranCommonDates.getMarriageDate())
                && RbpsXomUtil.isPresent(childResponse.getMinorSchoolChildAward())
                && RbpsXomUtil.isPresent(childResponse.getMinorSchoolChildAward().getEventDate())
                && veteranCommonDates.getMarriageDate().after(childResponse.getMinorSchoolChildAward().getEventDate())
        ) {
            childResponse.getMinorSchoolChildAward().setEventDate(veteranCommonDates.getMarriageDate());
            logger.debug("verifyMinorStepChildSecondEventDate: Minor school child award set to the marriage date of the Veteran");
        }
    }



}
