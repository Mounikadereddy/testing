package gov.va.vba.rbps.rulesengine.spouse.rules;

import gov.va.vba.rbps.rulesengine.engine.BaseRuleSet;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.spouse.SpouseDecisionVariables;
import gov.va.vba.rbps.rulesengine.spouse.SpouseMessages;
import gov.va.vba.rbps.rulesengine.spouse.SpouseResponse;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;

public class SpouseValidationRuleSet extends BaseRuleSet {


    protected final Spouse spouse;
    protected final SpouseResponse spouseResponse;
    protected final SpouseDecisionVariables spouseDecisionVariables;


    public SpouseValidationRuleSet(Spouse spouse, SpouseResponse spouseResponse, SpouseDecisionVariables spouseDecisionVariables) {
        this.spouse = spouse;
        this.spouseResponse = spouseResponse;
        this.spouseDecisionVariables = spouseDecisionVariables;
    }


    /*************************************************************
     *
     *   Rule: CP0100 - Spouse DOB Verification
     *
     *   if
     *     the birth date of 'the Spouse' is not present and
     *     the end date of the marriage of 'the Spouse' is not present
     *  then
     *      add "Auto Dependency Processing Reject Reason - Spouse DOB was not provided. Please review." to the list of Exceptions ;
     *      set 'exception generated' to true ;
     *
     *
     *************************************************************/
    @Rule
    public void spouseDobVerification () {

        if (RbpsXomUtil.isNotPresent(spouse.getBirthDate() ) && RbpsXomUtil.isPresent(spouse.getCurrentMarriage()) &&  RbpsXomUtil.isNotPresent(spouse.getCurrentMarriage().getEndDate())) {
            this.spouseResponse.getExceptionMessages().addException(SpouseMessages.DOB_NOT_PROVIDED);
            this.spouseDecisionVariables.setExceptionGenerated(true);
        }
    }

    /*************************************************************
     *
     *   Rule: CP0100 - Spouse First Name Verification
     *
     *
     *    if
     *      the first name of 'the Spouse' is not present
     *    then
     *      add "Auto Dependency Processing Reject Reason - Spouse First Name is not provided. Please review." to the list of Exceptions ;
     *      set 'exception generated' to true ;
     *
     *************************************************************/
    @Rule
    public void spouseFirstNameVerification () {
        if (RbpsXomUtil.isNotPresent(spouse.getFirstName())) {
            this.spouseResponse.getExceptionMessages().addException(SpouseMessages.FIRST_NAME_NOT_PROVIDED);
            this.spouseDecisionVariables.setExceptionGenerated(true);
        }
    }


    /*************************************************************
     *
     *   Rule: CP0100 - Spouse Last Name Verification
     *
     *
     *    if
     *       the last name of 'the Spouse' is not present
     *    then
     *      add "Auto Dependency Processing Reject Reason - Spouse last name is not provided. Please review." to the list of Exceptions ;
     *      set 'exception generated' to true ;
     *
     *************************************************************/
    @Rule
    public void spouseLastNameVerification () {
        if (RbpsXomUtil.isNotPresent(spouse.getLastName())) {
            this.spouseResponse.getExceptionMessages().addException(SpouseMessages.LAST_NAME_NOT_PROVIDED);
            this.spouseDecisionVariables.setExceptionGenerated(true);
        }
    }

    /*************************************************************
     *
     *   Rule: CP0103 - Is Spouse Existing On Award
     *
     *   if
     * 	   the end date of the marriage of 'the Spouse' is not present and
     * 	   'the Spouse' is existing on current award
     *   then
     * 	    add "Auto Dependency Processing Reject Reason - Spouse is already existing on award. Please review." to the list of Exceptions ;
     * 	    set 'exception generated' to true ;
     *
     *
     *************************************************************/
    @Rule
    public void isSpouseExistingOnAward (){
        if (RbpsXomUtil.isPresent(spouse.getCurrentMarriage()) && RbpsXomUtil.isNotPresent(spouse.getCurrentMarriage().getEndDate()) && spouse.isOnCurrentAward()){
            this.spouseResponse.getExceptionMessages().addException(SpouseMessages.ALREADY_ON_EXISTING_AWARD);
            this.spouseDecisionVariables.setExceptionGenerated(true);
        }
    }



    /*************************************************************
     *
     *   Rule: CP0106 - Marriage Verification
     *
     *   if
     * 	    the marriage of 'the Spouse' is present and
     * 	    the latest previous marriage of 'the Spouse' is present and
     * 	    the end date of the marriage of 'the Spouse' is not present and
     * 	    the start date of the marriage of 'the Spouse' is before the end date of the latest previous marriage of 'the Spouse'
     *  then
     * 	   add "Auto Dependency Processing Reject Reason - Submitted spouse's prior marriage date(s) do not terminate before date of current marriage. Please review." to the list of Exceptions ;
     * 	   set 'exception generated' to true ;
     *
     *************************************************************/
    @Rule
    public void spouseMarriageVerification () {
        if (
            RbpsXomUtil.isPresent(spouse.getCurrentMarriage()) && 
            RbpsXomUtil.isPresent(spouse.getLatestPreviousMarriage()) && 
            spouse.getCurrentMarriage().getEndDate() == null &&
            spouse.getCurrentMarriage().getStartDate().before(spouse.getLatestPreviousMarriage().getEndDate())
        ){
            this.spouseResponse.getExceptionMessages().addException(SpouseMessages.MARRIAGE_VERIFICATION_FAIL);
            this.spouseDecisionVariables.setExceptionGenerated(true);
        }
    }

    /*************************************************************
     *
     *   Rule: CP0143 - SSN Verification
     *
     *
     *    if
     *       the ssn of 'the Spouse' is not present and
     *       the end date of the marriage of 'the Spouse' is not present
     *    then
     *       add "Auto Dependency Processing Reject Reason - Spouse SSN is not provided. Please review." to the list of Exceptions ;
     *       set 'exception generated' to true ;
     *
     *************************************************************/
    @Rule
    public void spouseSsnVerification () {
        if (RbpsXomUtil.isNotPresent(spouse.getSsn()) && RbpsXomUtil.isPresent(spouse.getCurrentMarriage())  && RbpsXomUtil.isNotPresent(spouse.getCurrentMarriage().getEndDate())){
            this.spouseResponse.getExceptionMessages().addException("Auto Dependency Processing Reject Reason - Spouse SSN is not provided. Please review.");
            this.spouseDecisionVariables.setExceptionGenerated(true);
        }
    }

}
