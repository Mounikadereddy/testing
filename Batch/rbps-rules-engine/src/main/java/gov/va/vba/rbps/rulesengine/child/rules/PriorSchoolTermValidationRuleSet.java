package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;

import java.util.Date;

public class PriorSchoolTermValidationRuleSet extends ChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());
    
    public PriorSchoolTermValidationRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables) {
        super(child, childResponse, childDecisionVariables);
    }

    @Override
    public void run() throws RuleEngineException {
        super.run();
        if (childDecisionVariables.isPriorSchoolTermValid()) {
            courseStartEndDateCheck();

            if (childDecisionVariables.isPriorSchoolTermValid()) {
                priorTermSameAsCurrentTerm();

                if (childDecisionVariables.isPriorSchoolTermValid()) {
                    priorEndDateBeforePriorStartDateCheck ();

                    if (childDecisionVariables.isPriorSchoolTermValid()) {
                        priorCourseStartFutureDateCheck();

                        if (childDecisionVariables.isPriorSchoolTermValid()) {
                            tuitionFeePaidByGovt();

                            if (childDecisionVariables.isPriorSchoolTermValid()) {
                                childSchoolNameVerification();

                                if (childDecisionVariables.isPriorSchoolTermValid()) {
                                    childSchoolAddressVerification();

                                    if (childDecisionVariables.isPriorSchoolTermValid()) {
                                        priorTermIncludedInAward();
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }

    }

    /*************************************************************
     *
     * Rule: PriorSchoolTermPresent
     *
     * if
     * 	    the last term of 'the Child' is not present
     * then
     *      add "last term not present" to the list of 'LogMessage' ;
     *      set  priorSchoolTermValid to false ;
     * else
     *    add "last term  present" to the list of 'LogMessage' ;
     *    set  priorSchoolTermValid to true ;
     *
     *************************************************************/
    @Rule
    public void priorSchoolTermPresent() {
        if (RbpsXomUtil.isNotPresent(child.getLastTerm())) {
            childResponse.getLogMessage().addException("last term not present");
            childDecisionVariables.setPriorSchoolTermValid(false);
        } else {
            childResponse.getLogMessage().addException("last term present");
            childDecisionVariables.setPriorSchoolTermValid(true);
        }

    }

    /*************************************************************
     *
     *  Rule: CP0140.2 - Part Time Validation Prior School Term
     * 
     * SR#1164080
     *
     * if
     *      and the number of sessions per week < 3
     *      and the hours per week < 3
     * then
     *      send for exception processing.
     *
     *************************************************************/
    @Rule
    public void partTimeValidation() {
        if(
            RbpsXomUtil.isPresent(child.getLastTerm()) &&
            child.getLastTerm().getSessionsPerWeek() < 3 &&
            child.getLastTerm().getHoursPerWeek() < 3
        ) {
            childDecisionVariables.setCompleted(true);
            childDecisionVariables.setPriorSchoolTermValid(false);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.PART_TIME, child.getLastName(), child.getFirstName()));
        }
    }


    /*************************************************************
     *
     *  Rule: CP0129 - Course Start-End Date Check
     *
     * if
     *
     * 	    the course start date of the last term of 'the Child' is not present  or
     * 	    the course end date of the last term of 'the Child' is not present
     * then
     * 	    set priorSchoolTermValid to false;
     * 	    set PriorSchoolTermStatus to "ignore";
     *  	add "last term  not valid" to the list of 'LogMessage' ;
     *
     *************************************************************/
    public void courseStartEndDateCheck() {
        if (RbpsXomUtil.isPresent(child.getLastTerm())) {
            if (RbpsXomUtil.isNotPresent(child.getLastTerm().getCourseStudentStartDate()) ||  RbpsXomUtil.isNotPresent(child.getLastTerm().getCourseEndDate())) {
                childDecisionVariables.setPriorSchoolTermValid(false);
                childResponse.setPriorSchoolTermStatus("ignore");
                childResponse.getLogMessage().addException(ChildMessages.LAST_TERM_NOT_VALID);
            }
        }

    }

    /*************************************************************
     *
     * Rule: CP0129 - Prior Term Same As  Current Term
     *
     * if
     * 	    the course start date of the last term of 'the Child' is present  and
     * 	    the course end date of the last term of 'the Child' is  present and
     * 	    the course start date of the current term of 'the Child' is present and
     * 	    the course end date of the current term of 'the Child' is present and
     * 	    the course start date of the last term of 'the Child' is on the course start date of the current term of 'the Child' and
     * 	    the course end date of the last term of 'the Child' is on the course end date of the current term of 'the Child'
     * then
     * 	    set priorSchoolTermValid to false;
     * 	    set PriorSchoolTermStatus to "ignore";
     *  	add "last term  not valid. it is same as current term" to the list of 'LogMessage' ;
     *
     *************************************************************/
    public void priorTermSameAsCurrentTerm() {
        if (RbpsXomUtil.isPresent(child.getLastTerm()) && RbpsXomUtil.isPresent(child.getCurrentTerm())) {
            Education lastTerm = child.getLastTerm();
            Education currentTerm = child.getCurrentTerm();
            if (
                RbpsXomUtil.isPresent(lastTerm.getCourseStudentStartDate())
                && RbpsXomUtil.isPresent(lastTerm.getCourseEndDate())
                && RbpsXomUtil.isPresent(currentTerm.getCourseStudentStartDate())
                && RbpsXomUtil.isPresent(currentTerm.getCourseEndDate())
                && lastTerm.getCourseStudentStartDate().equals(currentTerm.getCourseStudentStartDate())
                && lastTerm.getCourseEndDate().equals(currentTerm.getCourseEndDate())
            ) {
                childDecisionVariables.setPriorSchoolTermValid(false);
                childResponse.setPriorSchoolTermStatus("ignore");
                childResponse.getLogMessage().addException(ChildMessages.LAST_TERM_SAME_AS_CURRENT);
            }
        }
    }

    /*************************************************************
     *
     * Rule: CP0118 Prior End Date before Prior Start Date Check
     *
     * if
     *      the course end date of the last term of 'the Child'  is before or the same as
     *  	the course start date of the last term of 'the Child'
     * then
     * 	    set priorSchoolTermValid to false;
     * 	    set PriorSchoolTermStatus to "ignore";
     *  	add "last term  not valid" to the list of 'LogMessage' ;
     *
     *************************************************************/
    public void priorEndDateBeforePriorStartDateCheck() {
        if (RbpsXomUtil.isPresent(child.getLastTerm())) {
            Education lastTerm = child.getLastTerm();
            if (lastTerm.getCourseEndDate().before(lastTerm.getCourseStudentStartDate()) || lastTerm.getCourseEndDate().equals(lastTerm.getCourseStudentStartDate())) {
                childDecisionVariables.setPriorSchoolTermValid(false);
                childResponse.setPriorSchoolTermStatus("ignore");
                childResponse.getLogMessage().addException(ChildMessages.LAST_TERM_NOT_VALID);
            }
        }
    }




    /*************************************************************
     *
     * Rule: Prior Course Start-future date Check
     *
     * if
     * 	    the course start date of the last term of 'the Child'     is present  and
     * 	    the course start date of the last term of 'the Child'   is after  Today
     * then
     * 	    set priorSchoolTermValid to false;
     * 	    set PriorSchoolTermStatus to "ignore";
     *  	add "last term  not valid" to the list of 'LogMessage' ;
     *
     *************************************************************/
    public void priorCourseStartFutureDateCheck() {
        if (RbpsXomUtil.isPresent(child.getLastTerm())) {
            Education lastTerm = child.getLastTerm();
            if (RbpsXomUtil.isPresent(lastTerm.getCourseStudentStartDate()) && lastTerm.getCourseStudentStartDate().after(new Date())) {
                childDecisionVariables.setPriorSchoolTermValid(false);
                childResponse.setPriorSchoolTermStatus("ignore");
                childResponse.getLogMessage().addException(ChildMessages.LAST_TERM_NOT_VALID);
            }
        }
    }


    /*************************************************************
     *
     *  Rule: CP0119 - Tution Feed Paid By Govt Check
     *
     *  if
     *      'the Child' tuition is paid by government
     *  then
     *      set priorSchoolTermValid to false;
     *      set PriorSchoolTermStatus to "ignore";
     *      add "last term  not valid" to the list of 'LogMessage' ;
     *
     *************************************************************/
    public void tuitionFeePaidByGovt() {
        if (child.isTuitionPaidByGovt()) {
            childDecisionVariables.setPriorSchoolTermValid(false);
            childResponse.setPriorSchoolTermStatus("ignore");
            childResponse.getLogMessage().addException(ChildMessages.LAST_TERM_NOT_VALID);
        }
    }


    /*************************************************************
     *
     *   Rule: CP0118 - Child School Name Verification
     *
     *
     *    if
     *      the school of the  last term of  'the Child' is present and
     *      the name of the school of the  last term of 'the Child' is not present
     *    then
     *      set  priorSchoolTermValid to false  ;
     *      set PriorSchoolTermStatus to "Reject";
     *      add "last term  not valid" to the list of 'LogMessage' ;
     *      set Completed to true ;
     *      add "Auto Dependency Processing Reject Reason - Child : "+ the last name of 'the Child' +", "+the first name of 'the Child' +" for prior school term - School Name is missing. Please review." to the list of Exceptions ;
     *
     *************************************************************/
    public void childSchoolNameVerification() {
        if (RbpsXomUtil.isPresent(child.getLastTerm())) {
            Education lastTerm = child.getLastTerm();
            if (RbpsXomUtil.isPresent(lastTerm.getSchool()) && RbpsXomUtil.isNotPresent(lastTerm.getSchool().getName())) {
                childDecisionVariables.setPriorSchoolTermValid(false);
                childDecisionVariables.setCompleted(true);
                childResponse.setPriorSchoolTermStatus("Reject");
                childResponse.getLogMessage().addException(ChildMessages.LAST_TERM_NOT_VALID);
                childResponse.getExceptionMessages().addException(String.format(ChildMessages.MISSING_SCHOOL_NAME, child.getLastName(), child.getFirstName()));
            }
        }
    }

    /*************************************************************
     *
     *   Rule: CP0118 - Child School Address Verification
     *
     *  if
     *      the address of the school of the last term of 'the Child' is not present
     *
     *  then
     *      set priorSchoolTermValid to  false;
     *      set PriorSchoolTermStatus to "Reject";
     *      add "last term  not valid" to the list of 'LogMessage' ;
     *      set Completed to true ;
     *      add "Auto Dependency Processing Reject Reason - Child : "+ the last name of 'the Child' +", "+the first name of 'the Child' +" for prior school term - School address is missing. Please review." to the list of Exceptions ;
     *
     *************************************************************/
    public void childSchoolAddressVerification() {
        if (RbpsXomUtil.isPresent(child.getLastTerm())) {
            Education lastTerm = child.getLastTerm();
            if (RbpsXomUtil.isPresent(lastTerm.getSchool()) && RbpsXomUtil.isNotPresent(lastTerm.getSchool().getAddress())) {
                childDecisionVariables.setPriorSchoolTermValid(false);
                childResponse.setPriorSchoolTermStatus("Reject");
                childDecisionVariables.setCompleted(true);
                childResponse.getLogMessage().addException(ChildMessages.LAST_TERM_NOT_VALID);
                childResponse.getExceptionMessages().addException(String.format(ChildMessages.MISSING_SCHOOL_ADDRESS, child.getLastName(), child.getFirstName()));
            }
        }
    }

    /*************************************************************
     *
     *   Rule: CP235-Prior Term included  in award
     *
     *   if
     * 	    the last term in corp begin date of 'the Child'  is not null and
     * 	    the last term in corp end date of 'the Child'   is not null and
     * 	    the course start date of the last term of 'the Child'  is after or the same as  the last term in corp begin date of 'the Child' and
     * 	    the course end date of the last term of 'the Child'  is on the last term in corp end date of 'the Child'
     *  then
     * 	    set priorSchoolTermValid to false ;
     *      add "RBPS ignoring prior term as it is inculded in current award for prior school child : "+ the last name of 'the Child' +", "+the first name of 'the Child' +" Please review." to the list of  'LogMessage' ;
     *      set PriorSchoolTermStatus to "ignore";
     *      add "last term  not valid" to the list of 'LogMessage' ;
     *
     *
     *************************************************************/
    public void priorTermIncludedInAward() {
        if (RbpsXomUtil.isPresent(child.getLastTerm())) {
            Education lastTerm = child.getLastTerm();
            if (
                    RbpsXomUtil.isPresent(child.getLastTermInCorpBeginDate())
                    && RbpsXomUtil.isPresent(child.getLastTermInCorpEndDate())
                    && (lastTerm.getCourseStudentStartDate().after(child.getLastTermInCorpBeginDate()) || lastTerm.getCourseStudentStartDate().equals(child.getLastTermInCorpBeginDate()))
                    && lastTerm.getCourseEndDate().equals(child.getLastTermInCorpEndDate())
            ) {
                childDecisionVariables.setPriorSchoolTermValid(false);
                childResponse.setPriorSchoolTermStatus("ignore");
                childResponse.getLogMessage().addException(String.format(ChildMessages.PRIOR_TERM_INCLUDED_IN_CURRENT_AWARD, child.getLastName(), child.getFirstName()));
                logger.debug("priorTermIncludedInAward: prior term is included in the current award. Set priorSchoolTermStatus to ignore");
            }
        }

    }
}
