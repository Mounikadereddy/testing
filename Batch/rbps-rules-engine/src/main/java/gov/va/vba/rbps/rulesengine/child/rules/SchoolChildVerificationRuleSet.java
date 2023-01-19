package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.EducationLevelType;
import gov.va.vba.rbps.coreframework.xom.EducationType;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;

public class SchoolChildVerificationRuleSet extends ChildBaseRuleSet {
    public SchoolChildVerificationRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    /*************************************************************
     *
     *   RuleSet: CP0118 - Child Course of Education Verification
     *
     *   	the current term of 'the Child' is present and
     *    	the education type of the current term of 'the Child' is present and
     *      the education type of the current term of 'the Child' is FULL_TIME and
     *      the education level type of the current term of 'the Child' is present and
     *      the education level type of the current term of 'the Child' is POST_SECONDARY and
     *      the course name of the current term of 'the Child' is not present
     *
     *************************************************************/
    @Rule
    public void childCourseEducationVerification() {
        if(
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getEducationType()) &&
            child.getCurrentTerm().getEducationType().equals(EducationType.FULL_TIME) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getEducationLevelType()) &&
            child.getCurrentTerm().getEducationLevelType().equals(EducationLevelType.POST_SECONDARY) &&
            RbpsXomUtil.isNotPresent(child.getCurrentTerm().getCourseName())
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.CHILD_COURSE_EDUCATION_VERIFICATION, child.getLastName(), child.getFirstName()));
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0118 - Child School Address Verification
     *
     *   	the current term of 'the Child' is present and
     * 	    the education type of the current term of 'the Child' is present and
     * 	    the education type of the current term of 'the Child' is FULL_TIME and
     * 	    the education level type of the current term of 'the Child' is present and
     * 	    the education level type of the current term of 'the Child' is POST_SECONDARY and
     * 	    (the school of the current term of 'the Child' is not present or
     * 	    (the school of the current term of 'the Child' is present and
     * 	    the address of the school of the current term of 'the Child' is not present))
     *
     *************************************************************/
    @Rule
    public void childSchoolAddressVerification() {
        if(
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getEducationType()) &&
            child.getCurrentTerm().getEducationType().equals(EducationType.FULL_TIME) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getEducationLevelType()) &&
            child.getCurrentTerm().getEducationLevelType().equals(EducationLevelType.POST_SECONDARY)
        ) {
            if(
                RbpsXomUtil.isNotPresent(child.getCurrentTerm().getSchool()) ||
                (RbpsXomUtil.isPresent(child.getCurrentTerm().getSchool()) &&
                RbpsXomUtil.isNotPresent(child.getCurrentTerm().getSchool().getAddress()))
            ) {
                childDecisionVariables.setCompleted(true);
                childResponse.getExceptionMessages().addException(String.format(ChildMessages.CHILD_SCHOOL_ADDRESS_VERIFICATION, child.getLastName(), child.getFirstName()));
            }
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0118 - Child School Name Verification
     *
     *   	the current term of 'the Child' is present and
     *      the education type of the current term of 'the Child' is present and
     * 	    the education type of the current term of 'the Child' is FULL_TIME and
     * 	    the education level type of the current term of 'the Child' is present and
     * 	    the education level type of the current term of 'the Child' is POST_SECONDARY and
     * 	    (the school of the current term of 'the Child' is not present or
     *  	(the school of the current term of 'the Child' is present and
     * 	    the name of the school of the current term of 'the Child' is not present))
     *
     *************************************************************/
    @Rule
    public void childSchoolNameVerification() {
        if(
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getEducationType()) &&
            child.getCurrentTerm().getEducationType().equals(EducationType.FULL_TIME) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getEducationLevelType()) &&
            child.getCurrentTerm().getEducationLevelType().equals(EducationLevelType.POST_SECONDARY)
        ) {
            if(
                RbpsXomUtil.isNotPresent(child.getCurrentTerm().getSchool()) ||
                (RbpsXomUtil.isPresent(child.getCurrentTerm().getSchool()) &&
                RbpsXomUtil.isNotPresent(child.getCurrentTerm().getSchool().getName()))
            ) {
                childDecisionVariables.setCompleted(true);
                childResponse.getExceptionMessages().addException(String.format(ChildMessages.CHILD_SCHOOL_NAME_VERIFICATION, child.getLastName(), child.getFirstName()));
            }
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0119 - Tuition Feed Paid by Govt Check
     *
     *   	'the Child' tuition is paid by government
     *
     *************************************************************/
    @Rule
    public void tuitionPaidByGovt() {
        if(child.isTuitionPaidByGovt()) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.TUITION_PAID_BY_GOVT, child.getLastName(), child.getFirstName()));
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0120 - Child Home Schooled Check
     *
     *   	the current term of 'the Child' is present and
     * 	    the education level type of the current term of 'the Child' is present and
     * 	    the education level type of the current term of 'the Child' is HOME_SCHOOLED
     *
     *************************************************************/
    @Rule
    public void childHomeSchooledCheck() {
        if(
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getEducationLevelType()) &&
            child.getCurrentTerm().getEducationLevelType().equals(EducationLevelType.HOME_SCHOOLED)
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.HOME_SCHOOLED_CHECK, child.getLastName(), child.getFirstName()));
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0129 - Course Start Date Check
     *
     *   	the current term of 'the Child' is present and
     * 	    the education type of the current term of 'the Child' is present and
     * 	    the education type of the current term of 'the Child' is FULL_TIME and
     * 	    the course start date of the current term of 'the Child' is not present
     *
     *************************************************************/
    @Rule
    public void courseStartDateCheck() {
        if(
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            RbpsXomUtil.isPresent(child.getCurrentTerm().getEducationType()) &&
            child.getCurrentTerm().getEducationType().equals(EducationType.FULL_TIME) &&
            RbpsXomUtil.isNotPresent(child.getCurrentTerm().getCourseStudentStartDate())
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.MISSING_COURSE_START_DATE, child.getLastName(), child.getFirstName()));
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0140 - Part Time Validation
     *
     *   	the current term of 'the Child' is present and
     * 	    the education type of the current term of 'the Child' is PART_TIME and
     * 	    ( the sessions per week of the current term of 'the Child' is less than 3 and
     * 	 	the hours per week of the current term of 'the Child' is less than 3 )
     *
     *************************************************************/
    @Rule
    public void partTimeValidation() {
        if(
            RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
            child.getCurrentTerm().getEducationType().equals(EducationType.PART_TIME) &&
            child.getCurrentTerm().getSessionsPerWeek() < 3 &&
            child.getCurrentTerm().getHoursPerWeek() < 3
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.PART_TIME, child.getLastName(), child.getFirstName()));
        }
    }
}
