package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class PriorSchoolTermProcessRuleSet extends ChildBaseRuleSet {


    private Logger logger = Logger.getLogger(this.getClass());

    public PriorSchoolTermProcessRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    @Override
    public void run() throws RuleEngineException {
        super.run();

        if(!childDecisionVariables.isPriorConditionSatisfied()) {
            // log message
           priorSchoolTermEndDateIsBeforeAllowableDate();

            if(!childDecisionVariables.isPriorConditionSatisfied()) {
                // log message
                setPriorSchoolBeginToAllowableDate();
                lastTermInCorpBeginDateNotSet();

                if(childDecisionVariables.isPriorConditionSatisfied()) {
                    // log message
                    priorTermContinuousWithMinorChildAward();
                } else {
                    // log message
                    setEventDateOfLastTermToStartDate();

                    if(childDecisionVariables.isPriorConditionSatisfied()) {
                        // log message
                        setEventDateOfLastTermToEndDate();
                    }
                }


            }
        }
    }

    /*************************************************************
     *
     *   RuleSet: setSchoolAllowableDate
     *
     *   	the child type of 'the Child' is STEPCHILD and
     *      the marriage date of 'the Veteran' is not null and
     *      the marriage date of 'the Veteran' is after  the allowable date of  'the Veteran'
     *
     *************************************************************/
    @Rule
    public void setSchoolAllowableDate() {
        if(child.getChildType() == ChildType.STEPCHILD && veteranCommonDates.getMarriageDate() != null && veteranCommonDates.getMarriageDate().after(veteranCommonDates.getAllowableDate())) {
            childDecisionVariables.setSchoolAllowableDate(veteranCommonDates.getMarriageDate());
            childResponse.getLogMessage().addException(ChildMessages.SET_SCHOOL_ALLOWABLE_DATE_TO_MARRIAGE);
            logger.debug("setSchoolAllowableDate: Setting school allowable date to veteran's marriage date: " + veteranCommonDates.getMarriageDate());
        } else {
            childDecisionVariables.setSchoolAllowableDate(veteranCommonDates.getAllowableDate());
            childResponse.getLogMessage().addException(ChildMessages.SET_SCHOOL_ALLOWABLE_DATE);
            logger.debug("setSchoolAllowableDate: Setting school allowable date to veteran's allowable date: " + veteranCommonDates.getAllowableDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: checkSchoolAllowableDateTo18thBirthday
     *
     *   	'School Allowable date' is not null and
     *      'childs 18th birth day'   is not null and
     *      'School Allowable date' is before  the birth date of 'the Child' plus 18 Year ( s )
     *
     *************************************************************/
    @Rule
    public void checkSchoolAllowableDateTo18thBirthday() {
        if(childDecisionVariables.getSchoolAllowableDate() != null && childDecisionVariables.getChild18BirthDay() != null) {
            if(childDecisionVariables.getSchoolAllowableDate().before(RbpsXomUtil.addYearsToDate(18, child.getBirthDate()))) {
                childDecisionVariables.setSchoolAllowableDate(RbpsXomUtil.addYearsToDate(18, child.getBirthDate()));
                logger.debug("checkSchoolAllowableDateTo18thBirthday: Setting school allowable date to child's 18th birthday: " + RbpsXomUtil.addYearsToDate(18, child.getBirthDate()));
            }
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0236 - Prior begin date is before the begin date of the school term on the award
     *
     *   	the last term in corp begin date of 'the Child' is not null and
     *      the course start date of the last term of 'the Child'  is before the last term in corp begin date of 'the Child'
     *
     *************************************************************/
    @Rule
    public void checkPriorBeginDateBeforeSchoolTerm() {
        if(child.getLastTermInCorpBeginDate() != null) {
            if(child.getLastTerm().getCourseStudentStartDate().before(child.getLastTermInCorpBeginDate())) {
                childResponse.getExceptionMessages().addException(ChildMessages.PRIOR_DATE_IS_BEFORE_DATE_OF_SCHOOL_TERM);
                childDecisionVariables.setPriorConditionSatisfied(true);
                childDecisionVariables.setCompleted(true);
            }
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0225-1 - Prior school term end date is before the allowable date
     *
     *   	the course end date of the last term of 'the Child' is before 'School Allowable date'
     *
     *************************************************************/
    public void priorSchoolTermEndDateIsBeforeAllowableDate() {
        if(child.getLastTerm().getCourseEndDate().before(childDecisionVariables.getSchoolAllowableDate())) {
            childResponse.setPriorSchoolTermStatus("ignore");
            childDecisionVariables.setPriorSchoolTermValid(false);
            childDecisionVariables.setPriorConditionSatisfied(true);
            logger.debug("priorSchoolTermEndDateIsBeforeAllowableDate: Prior school term status set to ignore");
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0225 - Set Prior School Term begin to Allowable date
     *
     *   	the course start date of the last term of 'the Child'   is before or the same as  'School Allowable date'
     *
     *************************************************************/
    public void setPriorSchoolBeginToAllowableDate() {
        if(child.getLastTerm().getCourseStudentStartDate().compareTo(childDecisionVariables.getSchoolAllowableDate()) <= 0) {
            child.getLastTerm().setCourseStudentStartDate(childDecisionVariables.getSchoolAllowableDate());
            childResponse.getPriorSchoolChild().setEventDate(childDecisionVariables.getSchoolAllowableDate());
            logger.debug("setPriorSchoolBeginToAllowableDate: Child's last term course student start date set to school allowable date:  " + childDecisionVariables.getSchoolAllowableDate());
            logger.debug("setPriorSchoolBeginToAllowableDate: Prior school child award event date set to school allowable date:  " + childDecisionVariables.getSchoolAllowableDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: No Lasttermin Corp Set Prior Begin date to eventdate
     *
     *   	the last term in corp begin date of 'the Child' is null
     *
     *************************************************************/
    public void lastTermInCorpBeginDateNotSet() {
        if(child.getLastTermInCorpBeginDate() == null) {
            childResponse.getPriorSchoolChild().setEventDate(child.getLastTerm().getCourseStudentStartDate());
            childDecisionVariables.setPriorConditionSatisfied(true);
            logger.debug("lastTermInCorpBeginDateNotSet: Prior school child award event date set to last term course student start date:  " + child.getLastTerm().getCourseStudentStartDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0226-B - Difference between the end date of the last term on the award GT 5m
     *
     *   	'the last term in corp end date of 'the Child'  is not null and
     *       the last term in corp end effective date of 'the Child' plus 5 months is before
     *       getOmnibusedDate (the course start date of the last term of 'the Child')
     *
     *************************************************************/
    public void setEventDateOfLastTermToStartDate() {
        if(child.getLastTermInCorpEndDate() != null) {
            if(RbpsXomUtil.addMonthsToDate(5, child.getLastTermInCorpEndEffectiveDate()).
                    before(RbpsXomUtil.getOmnibusedDate(child.getLastTerm().getCourseStudentStartDate()))) {
                childResponse.getPriorSchoolChild().setEventDate(child.getLastTerm().getCourseStudentStartDate());
                childDecisionVariables.setPriorConditionSatisfied(true);
                logger.debug("setEventDateOfLastTermToStartDate: Prior school child award event date set to last term course student start date:  " + child.getLastTerm().getCourseStudentStartDate());
            }
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0227-A - End date of teh last term on the award is less than or equal to 5 months before the prior school begin date
     *
     *   	the last term in corp end date of 'the Child'is  not null and
     * 	    the last term in corp end effective date of  'the Child'  plus 5 months  is after or the same as
     *      getOmnibusedDate (the course start date of the last term of 'the Child')
     *
     *************************************************************/
    public void setEventDateOfLastTermToEndDate() {
        if(child.getLastTermInCorpEndDate() != null) {
            Date addMonthsToCorpEndDate = RbpsXomUtil.addMonthsToDate(5, child.getLastTermInCorpEndEffectiveDate());
            if(addMonthsToCorpEndDate.compareTo(RbpsXomUtil.getOmnibusedDate(child.getLastTerm().getCourseStudentStartDate())) >= 0) {
                childResponse.getPriorSchoolChild().setEventDate(child.getLastTermInCorpEndDate());
                childDecisionVariables.setPriorConditionSatisfied(true);
                logger.debug("setEventDateOfLastTermToEndDate: Prior school child award event date set to last term in corp end date of child:  " + child.getLastTermInCorpEndDate());
            }
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0227-B - Prior term continous with minor child award
     *
     *   	'the Child'  is on award as minor child and
     *      'the Child' is terminated on18th birthday and
     *      the birth date of 'the Child' plus 18 Year ( s ) plus 5  months is after  or the same as getOmnibusedDate(the event date of 'PriorSchoolChild')
     *
     *************************************************************/
    public void priorTermContinuousWithMinorChildAward() {
        Date add18YearsAnd5Months = RbpsXomUtil.addYearsToDate(18, child.getBirthDate());
        add18YearsAnd5Months = RbpsXomUtil.addMonthsToDate(5, add18YearsAnd5Months);

        Date omnibusedDate = RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEventDate());

        if(child.isOnAwardAsMinorChild() &&
                child.isTerminatedOn18thBirthday() &&
                add18YearsAnd5Months.compareTo(omnibusedDate) >= 0
        ){
            childResponse.getPriorSchoolChild().setEventDate(RbpsXomUtil.addYearsToDate(18, child.getBirthDate()));
            logger.debug("priorTermContinuousWithMinorChildAward: Prior school child award event date set to child's 18th birthday:  " + RbpsXomUtil.addYearsToDate(18, child.getBirthDate()));
        }
    }
}
