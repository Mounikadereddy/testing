package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

public class ChildValidationRuleSet extends ChildBaseRuleSet {
    

    public ChildValidationRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);

    }


    /*************************************************************
     *
     *   Rule: CP0100 - Child First Name Verification
     *
     *
     *    if
     *      the first name of 'the Child' is not present
     *    then
     *      add "Auto Dependency Processing Reject Reason - Child First Name is not provided. Please review." to the list of Exceptions ;
     *      set Completed  to true ;
     *      set the first name of 'the Child' to "First Name Not Provided" ;
     *
     *
     *************************************************************/
    @Rule
    public void childFirstNameVerification() {
        if (RbpsXomUtil.isNotPresent(child.getFirstName())) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(ChildMessages.MISSING_FIRST_NAME);
            child.setFirstName("First Name Not Provided");
        }
    }

    /*************************************************************
     *
     *   Rule: CP0100 - Child Last Name Verification
     *
     *
     *  if
     *      the last name of 'the Child' is not present
     *  then
     *      add "Auto Dependency Processing Reject Reason - Child last name is not provided. Please review." to the list of Exceptions ;
     *      set Completed  to true ;
     *      set the last name of 'the Child' to "Last Name Not Provided" ;
     *
     *
     *************************************************************/
    @Rule
    public void childLastNameVerification() {
        if (RbpsXomUtil.isNotPresent(child.getLastName())) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(ChildMessages.MISSING_LAST_NAME);
            child.setLastName("Last Name Not Provided");
        }
    }

    /*************************************************************
     *
     *   Rule: CP0100 - Child DOB Verification
     *
     *
     *  if
     *      the birth date of 'the Child' is not present
     *  then
     *      set Completed to true ;
     *      add "Auto Dependency Processing Reject Reason - Child : "+ the last name of 'the Child' +", "+the first name of 'the Child' +" DOB was not provided. Please review." to the list of Exceptions ;
     *
     *
     *************************************************************/
    @Rule
    public void childDobVerification() {
        if (RbpsXomUtil.isNotPresent(child.getBirthDate())) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.MISSING_DOB, child.getLastName(), child.getFirstName()));
        }
    }



    /*************************************************************
     *
     *   Rule: CP0111 - Evaluate a Stepchild
     *
     *   if
     * 	    the child type of 'the Child' is STEPCHILD and
     * 	    it is not true that the marriage date of 'the Veteran' is present
     *  then
     * 	    set Completed to true ;
     * 	    add "Auto Dependency Processing Reject Reason - Date of marriage of the Veteran was not provided for stepchild. Please review" to the list of Exceptions ;
     *
     *************************************************************/
    @Rule
    public void evaluateAstepChild() {
        if (child.getChildType().equals(ChildType.STEPCHILD) && RbpsXomUtil.isNotPresent(veteranCommonDates.getMarriageDate())) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(ChildMessages.MISSING_VETERAN_MARRIAGE_DATE_FOR_STEP_CHILD);
        }
    }


    /*************************************************************
     *
     *   Rule: CP0112 - Is Child Previously Married
     *
     *
     * if
     *      'the Child' is previously married
     * then
     *      set Completed to true ;
     *      add "Auto Dependency Processing Reject Reason - Child : "+ the last name of 'the Child' +", "+the first name of 'the Child' +" reported as previously married. Please review." to the list of Exceptions ;
     *
     *************************************************************/
    @Rule
    public void isChildPreviouslyMarried() {
        if (child.isPreviouslyMarried()) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.CHILD_PREVIOUSLY_MARRIED, child.getLastName(), child.getFirstName()));
        }
    }


    /*************************************************************
     *
     *   Rule: CP0114 - Biological Child Address Verification
     *
     *
     *  if
     *      the child type of 'the Child' is BIOLOGICAL_CHILD and
     *      it is not true that 'the Child' is living with veteran and
     *      the mailing address of 'the Child' is not present
     *  then
     *      set Completed to true ;
     *      add "Auto Dependency Processing Reject Reason - Child : "+ the last name of 'the Child' +", "+the first name of 'the Child' +" reported as not living with Veteran but no address for child was submitted. Please review" to the list of Exceptions ;
     *
     *************************************************************/
    @Rule
    public void biologicalChildAddressVerification () {

        if (
            child.getChildType().equals(ChildType.BIOLOGICAL_CHILD)
            && !child.isLivingWithVeteran()
            && RbpsXomUtil.isNotPresent(child.getMailingAddress())
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.BIOLOGICAL_CHILD_NO_MAILING_ADDRESS, child.getLastName(), child.getFirstName()));
        }
    }

    /*************************************************************
     *
     *   Rule: CP0110 - Adopted Child Check
     *
     *  if
     *      the child type of 'the Child' is ADOPTED_CHILD
     *  then
     *      set Completed to true ;
     *      add "Auto Dependency Processing Reject Reason - Child : "+ the last name of 'the Child' +", "+the first name of 'the Child' +" reported as adopted. Please develop for adoption paperwork." to the list of Exceptions ;
     *
     *
     *************************************************************/
    @Rule
    public void adoptedChildCheck() {
        if (child.getChildType().equals(ChildType.ADOPTED_CHILD)) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.ADOPTED_CHILD, child.getLastName(), child.getFirstName()));
        }
    }

    /*************************************************************
     *
     *   Rule: CP0115A - Child Age And SD Check
     *
     *   if
     *     all of the following conditions are true :
     *             - 'the Veteran' claim received date is before or the same as '1 Year of rating date'
     *             - the age of 'the Child' on the FCDR of 'the Veteran' is at least 18
     *             - 'the Child' is seriously disabled ,
     * then
     *     set Completed to true ;
     *     add "Auto Dependency Processing Reject Reason - Child : " + the last name of 'the Child' + ", " + the first name of 'the Child' + " is reported as seriously disabled. Please review." to the list of Exceptions ;
     *
     *************************************************************/
    @Rule
    public void childAgeAndSdCheck_A() {
        if (
            (veteranCommonDates.getClaimDate().before(childDecisionVariables.getRatingPlusOneYear()) || veteranCommonDates.getClaimDate().equals(childDecisionVariables.getRatingPlusOneYear()))
            && RbpsXomUtil.getAgeOn(child, veteranCommonDates.getFirstChangedDateofRating()) >= 18
            && child.isSeriouslyDisabled()
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.CHILD_SERIOUSLY_DISABLED, child.getLastName(), child.getFirstName()));
        }
    }

    /*************************************************************
     *
     *   Rule: CP0115B - Child Age And SD Check
     *
     *   if
     *     all of the following conditions are true :
     *             - 'the Veteran' claim received date is after '1 Year + 7 days of rating date'
     *             - 'age of the child on date of claim' is at least 18
     *             - 'the Child' is seriously disabled ,
     * then
     *     set Completed to true ;
     *     add "Auto Dependency Processing Reject Reason - Child : " + the last name of 'the Child' + ", " + the first name of 'the Child' + " is reported as seriously disabled. Please review." to the list of Exceptions ;
     *
     *************************************************************/
    @Rule
    public void childAgeAndSdCheck_B() {
        if (
            veteranCommonDates.getClaimDate().after(childDecisionVariables.getRatingPlusOneYearSevenDays())
            && RbpsXomUtil.getAgeOn(child, veteranCommonDates.getClaimDate()) >= 18
            && child.isSeriouslyDisabled()
        ) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.CHILD_SERIOUSLY_DISABLED, child.getLastName(), child.getFirstName()));
        }
    }

    /*************************************************************
     *
     *   Rule: CP0135 - Deny Married Child
     *
     *   if
     *     the current marriage of 'the Child' is present
     * then
     *     set Completed to true ;
     *     add "Auto Dependency Processing Reject Reason - Child : " + the last name of 'the Child' + ", " + the first name of 'the Child' + " reported as married. Please review." to the list of Exceptions ;
     *
     *************************************************************/
    @Rule
    public void denyMarriedChild() {
        if (RbpsXomUtil.isPresent(child.getCurrentMarriage())) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.MARRIED_CHILD, child.getLastName(), child.getFirstName()));
        }
    }


    /*************************************************************
     *
     *   Rule: CP0142 - Child SSN Verification
     *
     *   if
     * 	 the ssn of 'the Child' is not present
     *
     * then
     * 	 set Completed to true ;
     * 	 add "Auto Dependency Processing Reject Reason - Dependent : "+ the last name of 'the Child' +", "+the first name of 'the Child' +" SSN was not provided. Please Review." to the list of Exceptions ;
     *
     *************************************************************/
    @Rule
    public void childSsnVerification () {
        if (RbpsXomUtil.isNotPresent(child.getSsn())) {
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(String.format(ChildMessages.SSN_NOT_PROVIDED, child.getLastName(), child.getFirstName()));
        }
    }

}
