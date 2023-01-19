package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

public class ChildDetermineAwardStatusRuleSet extends ChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public ChildDetermineAwardStatusRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-01
     *
     *   	the FCDR of 'the Veteran' is present
     *      and the age of 'the Child' on the FCDR of 'the Veteran' is less than 18
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_01() {
        if(RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) < 18
        ) {
            childResponse.getAward().setDependencyStatusType(DependentStatusType.MINOR_CHILD);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.ELIGIBLE_MINOR_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_01: child award dependency status type set to " + DependentStatusType.MINOR_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_01: child award dependency decision type set to " + DependentDecisionType.ELIGIBLE_MINOR_CHILD);
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-02-A
     *
     *   	the FCDR of 'the Veteran' is present
     *      and the age of 'the Child' on the FCDR of 'the Veteran' is at least 18
     *      and the age of 'the Child' on the FCDR of 'the Veteran' is less than 23
     *      and  the last term of 'the Child' is present
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_02_A() {
        if(RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) >= 18 &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) <  23 &&
                RbpsXomUtil.isPresent(child.getLastTerm())
        ) {
            childResponse.getPriorSchoolChild().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getPriorSchoolChild().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getPriorSchoolChild().setEndDate(child.getLastTerm().getCourseEndDate());

            logger.debug("determineChildAwardStatus_CP0134_02_A: Prior school child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_02_A: Prior school child award dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_02_A: Prior school child award end date set to child's last term course end date: " + child.getLastTerm().getCourseEndDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-02
     *
     *   	the FCDR of 'the Veteran' is present
     *      and the age of 'the Child' on the FCDR of 'the Veteran' is at least 18
     *      and the age of 'the Child' on the FCDR of 'the Veteran' is less than 23
     *      and the current term of 'the Child' is present
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_02() {
        if(RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) >= 18 &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) <  23 &&
                RbpsXomUtil.isPresent(child.getCurrentTerm())
        ) {
            childResponse.getAward().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getAward().setEndDate(child.getCurrentTerm().getExpectedGraduationDate());

            logger.debug("determineChildAwardStatus_CP0134_02: Child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_02: Child award dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_02: Child award end date set to child's current term expected graduation date: " + child.getCurrentTerm().getExpectedGraduationDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-03
     *
     *   	all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the age of 'the Child' on the FCDR of 'the Veteran' is at least 18
     *             - the age of 'the Child' on the FCDR of 'the Veteran' is less than 23
     *             - it is not true that 'the Child' is school child
     *             - it is not true that 'the Child' is seriously disabled
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_03() {
        if(RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) >= 18 &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) <  23 &&
                !child.isSchoolChild() &&
                !child.isSeriouslyDisabled()
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getAward().setDependencyStatusType(DependentStatusType.NOT_AN_AWARD_DEPENDENT);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.OVER_18_NOT_IN_SCHOOL_OR_HELPLESS);
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());

            logger.debug("determineChildAwardStatus_CP0134_03: Child award dependency status type set to " + DependentStatusType.NOT_AN_AWARD_DEPENDENT);
            logger.debug("determineChildAwardStatus_CP0134_03: Child award dependency decision type set to " + DependentDecisionType.OVER_18_NOT_IN_SCHOOL_OR_HELPLESS);
            logger.debug("determineChildAwardStatus_CP0134_03: Child award event date set to " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-04
     *
     *   	all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the age of 'the Child' on the FCDR of 'the Veteran' is less than 18
     *             - 'the Child' is school child
     *             - the current term of 'the Child' is present
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_04() {
        if(RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) < 18 &&
                child.isSchoolChild() &&
                RbpsXomUtil.isPresent(child.getCurrentTerm())
        ) {
            childResponse.getMinorSchoolChildAward().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getMinorSchoolChildAward().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getMinorSchoolChildAward().setEndDate(child.getCurrentTerm().getExpectedGraduationDate());

            logger.debug("determineChildAwardStatus_CP0134_04: Minor school child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_04: Minor school child award dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_04: Minor school child award end date set to child's current term expected graduation date: " + child.getCurrentTerm().getExpectedGraduationDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-04A
     *
     *   	all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the age of 'the Child' on the FCDR of 'the Veteran' is less than 18
     *             - 'the Child' is school child
     *             - the current term of 'the Child' is present
     *             - 'the Child' has attended school last term
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_04A() {
        if(RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) < 18 &&
                child.isSchoolChild() &&
                RbpsXomUtil.isPresent(child.getCurrentTerm()) &&
                child.getAttendedSchoolLastTerm()
        ) {
            childResponse.getAward().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getAward().setEndDate(child.getCurrentTerm().getExpectedGraduationDate());

            logger.debug("determineChildAwardStatus_CP0134_04A: Child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_04A: Child award dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_04A: Child award end date set to child's current term expected graduation date: " + child.getCurrentTerm().getExpectedGraduationDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-04B
     *
     *   	all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the age of 'the Child' on the FCDR of 'the Veteran' is less than 18
     *             - 'the Child' is school child
     *             - the last term of 'the Child' is present
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_04B() {
        if(RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) < 18 &&
                child.isSchoolChild() &&
                RbpsXomUtil.isPresent(child.getLastTerm())
        ) {
            childResponse.getPriorSchoolChild().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getPriorSchoolChild().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getPriorSchoolChild().setEndDate(child.getLastTerm().getCourseEndDate());

            logger.debug("determineChildAwardStatus_CP0134_04B: Prior school child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_04B: Prior school child award dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_04B: Prior school child award end date set to child's last term course end date: " + child.getLastTerm().getCourseEndDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-05
     *
     *   	all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is present
     *             - the age of 'the Child' on the FCDR of 'the Veteran' is at least 23
     *             - it is not true that 'the Child' is seriously disabled
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_05() {
        if(RbpsXomUtil.isPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) >= 23 &&
                !child.isSeriouslyDisabled()
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getAward().setDependencyStatusType(DependentStatusType.NOT_AN_AWARD_DEPENDENT);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.RATED_NOT_HELPLESS);
            childResponse.getAward().setEventDate(veteranCommonDates.getFirstChangedDateofRating());

            logger.debug("determineChildAwardStatus_CP0134_05: Child award dependency status type set to " + DependentStatusType.NOT_AN_AWARD_DEPENDENT);
            logger.debug("determineChildAwardStatus_CP0134_05: Child award dependency decision type set to " + DependentDecisionType.RATED_NOT_HELPLESS);
            logger.debug("determineChildAwardStatus_CP0134_05: Child award event date set to " + veteranCommonDates.getFirstChangedDateofRating());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-06
     *
     *   	the FCDR of 'the Veteran' is not present
     *      and 'age of the child on date of claim' is less than 18
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_06() {
        if(RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                childDecisionVariables.getChildAgeOnDateOfClaim() < 18
        ) {
            childResponse.getAward().setDependencyStatusType(DependentStatusType.MINOR_CHILD);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.ELIGIBLE_MINOR_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_06: Child award dependency status type set to " + DependentStatusType.MINOR_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_06: Child award dependency decision type set to " + DependentDecisionType.ELIGIBLE_MINOR_CHILD);
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-07
     *
     *   	the FCDR of 'the Veteran' is not present
     *      and 'age of the child on date of claim' is at least 18
     *      and 'age of the child on date of claim' is less than 23
     *      and the current term of 'the Child' is present
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_07() {
        if(RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                childDecisionVariables.getChildAgeOnDateOfClaim() >= 18 &&
                childDecisionVariables.getChildAgeOnDateOfClaim() < 23 &&
                RbpsXomUtil.isPresent(child.getCurrentTerm())
        ) {
            childResponse.getAward().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getAward().setEndDate(child.getCurrentTerm().getExpectedGraduationDate());

            logger.debug("determineChildAwardStatus_CP0134_07: Child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_07: Child award dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_07: Child award end date set to child's current term expected graduation date: " + child.getCurrentTerm().getExpectedGraduationDate());

        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-07A
     *
     *   	the FCDR of 'the Veteran' is not present
     *      and 'age of the child on date of claim' is at least 18
     *      and 'age of the child on date of claim' is less than 23
     *      and the last term of 'the Child' is present
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_07A() {
        if(RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                childDecisionVariables.getChildAgeOnDateOfClaim() >= 18 &&
                childDecisionVariables.getChildAgeOnDateOfClaim() < 23 &&
                RbpsXomUtil.isPresent(child.getLastTerm())
        ) {
            childResponse.getPriorSchoolChild().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getPriorSchoolChild().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getPriorSchoolChild().setEndDate(child.getLastTerm().getCourseEndDate());

            logger.debug("determineChildAwardStatus_CP0134_07A: Prior school child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_07A: Prior school child award dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_07A: Prior school child award end date set to child's last term course end date: " + child.getLastTerm().getCourseEndDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-08
     *
     *   	all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is not present
     *             - 'age of the child on date of claim' is at least 18
     *             - 'age of the child on date of claim' is less than 23
     *             - it is not true that 'the Child' is school child
     *             - it is not true that 'the Child' is seriously disabled
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_08() {
        if(RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                childDecisionVariables.getChildAgeOnDateOfClaim() >= 18 &&
                childDecisionVariables.getChildAgeOnDateOfClaim() < 23 &&
                !child.isSchoolChild() &&
                !child.isSeriouslyDisabled()
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getAward().setDependencyStatusType(DependentStatusType.NOT_AN_AWARD_DEPENDENT);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.OVER_18_NOT_IN_SCHOOL_OR_HELPLESS);
            childResponse.getAward().setEventDate(veteranCommonDates.getClaimDate());

            logger.debug("determineChildAwardStatus_CP0134_08: Child award dependency status type set to " + DependentStatusType.NOT_AN_AWARD_DEPENDENT);
            logger.debug("determineChildAwardStatus_CP0134_08: Child award dependency decision type set to " + DependentDecisionType.OVER_18_NOT_IN_SCHOOL_OR_HELPLESS);
            logger.debug("determineChildAwardStatus_CP0134_08: Child event date set to " + veteranCommonDates.getClaimDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-09
     *
     *   	the FCDR of 'the Veteran' is not present
     *      and 'age of the child on date of claim' is less than 18
     *      and 'the Child' is school child
     *      and the current term of 'the Child' is present
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_09() {
        if(RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                childDecisionVariables.getChildAgeOnDateOfClaim() < 18 &&
                child.isSchoolChild() &&
                RbpsXomUtil.isPresent(child.getCurrentTerm())
        ) {
            childResponse.getMinorSchoolChildAward().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getMinorSchoolChildAward().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getMinorSchoolChildAward().setEndDate(child.getCurrentTerm().getExpectedGraduationDate());

            logger.debug("determineChildAwardStatus_CP0134_09: Minor school child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_09: Minor school child award  dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_09: Minor school child award  end date set to child's current term expected graduation date: " + child.getCurrentTerm().getExpectedGraduationDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-10
     *
     *   	all of the following conditions are true :
     *             - the FCDR of 'the Veteran' is not present
     *             - 'age of the child on date of claim' is at least 23
     *             - it is not true that 'the Child' is seriously disabled
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_10() {
        if(RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                childDecisionVariables.getChildAgeOnDateOfClaim() >= 23 &&
                !child.isSeriouslyDisabled()
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getAward().setDependencyStatusType(DependentStatusType.NOT_AN_AWARD_DEPENDENT);
            childResponse.getAward().setDependencyDecisionType(DependentDecisionType.RATED_NOT_HELPLESS);
            childResponse.getAward().setEventDate(veteranCommonDates.getClaimDate());

            logger.debug("determineChildAwardStatus_CP0134_10: Child award dependency status type set to " + DependentStatusType.NOT_AN_AWARD_DEPENDENT);
            logger.debug("determineChildAwardStatus_CP0134_10: Child award  dependency decision type set to " + DependentDecisionType.RATED_NOT_HELPLESS);
            logger.debug("determineChildAwardStatus_CP0134_10: Child event date set to " + veteranCommonDates.getClaimDate());
        }
    }

    /*************************************************************
     *
     *   RuleSet: CP0134-09A
     *
     *   	the FCDR of 'the Veteran' is not present
     *      and 'age of the child on date of claim' is less than 18
     *      and 'the Child' is school child
     *      and the last term of 'the Child' is present
     *
     *************************************************************/
    @Rule
    public void determineChildAwardStatus_CP0134_09A() {
        if(RbpsXomUtil.isNotPresent(veteranCommonDates.getFirstChangedDateofRating()) &&
                childDecisionVariables.getChildAgeOnDateOfClaim() < 18 &&
                child.isSchoolChild() &&
                RbpsXomUtil.isPresent(child.getLastTerm())
        ) {
            childResponse.getPriorSchoolChild().setDependencyStatusType(DependentStatusType.SCHOOL_CHILD);
            childResponse.getPriorSchoolChild().setDependencyDecisionType(DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            childResponse.getPriorSchoolChild().setEndDate(child.getLastTerm().getCourseEndDate());

            logger.debug("determineChildAwardStatus_CP0134_09A: Prior school child award dependency status type set to " + DependentStatusType.SCHOOL_CHILD);
            logger.debug("determineChildAwardStatus_CP0134_09A: Prior school child award  dependency decision type set to " + DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE);
            logger.debug("determineChildAwardStatus_CP0134_09A: Prior school child award  end date set to child's last term course end date: " + child.getLastTerm().getCourseEndDate());
        }
    }
}
