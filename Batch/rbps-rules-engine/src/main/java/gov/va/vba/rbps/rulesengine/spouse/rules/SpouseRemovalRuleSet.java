package gov.va.vba.rbps.rulesengine.spouse.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.rulesengine.engine.BaseRuleSet;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.spouse.SpouseDecisionVariables;
import gov.va.vba.rbps.rulesengine.spouse.SpouseMessages;
import gov.va.vba.rbps.rulesengine.spouse.SpouseResponse;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.MarriageTerminationType;
import gov.va.vba.rbps.coreframework.xom.Spouse;

public class SpouseRemovalRuleSet extends BaseRuleSet {

    protected final Spouse spouse;
    protected final SpouseResponse spouseResponse;
    protected final SpouseDecisionVariables spouseDecisionVariables;
    private Logger logger = Logger.getLogger(this.getClass());

    public SpouseRemovalRuleSet(Spouse spouse, SpouseResponse spouseResponse, SpouseDecisionVariables spouseDecisionVariables) {
        this.spouse = spouse;
        this.spouseResponse = spouseResponse;
        this.spouseDecisionVariables = spouseDecisionVariables;

    }


    /*************************************************************
     *
     * Rule: CP0160 - Spouse Removal Award Decision Determination due to Death
     *
     * if
     * 	the termination type of the marriage of 'the Spouse' is DEATH
     * then
     * 	set the event date of 'the Award' to the end date of the marriage of 'the Spouse' ;
     * 	set the dependency decision type of 'the Award' to DEATH ;
     * 	set the dependency status type of 'the Award' to NOT_AN_AWARD_DEPENDENT  ;
     *
     *************************************************************/
    @Rule
    public void awardDecisionDueToDeath (){
        if (spouse.getCurrentMarriage().getTerminationType().equals(MarriageTerminationType.DEATH)){
            spouseResponse.getAward().setEventDate(spouse.getCurrentMarriage().getEndDate());
            spouseResponse.getAward().setDependencyDecisionType(DependentDecisionType.DEATH);
            spouseResponse.getAward().setDependencyStatusType(DependentStatusType.NOT_AN_AWARD_DEPENDENT);
        
            logger.debug("Rule: awardDecisionDueToDeath");
            logger.debug("Message: Award event date set to marriage end date");
            logger.debug("Message: Award Dependency Decision Type set to DEATH");
            logger.debug("Message: Award Dependency Status Type set to NOT_AN_AWARD_DEPENDENT");
        }
    }

    /*************************************************************
     *
     * Rule: CP0161 - Spouse Removal Award Decision Determination due to Divorce
     *
     * if
     * 	it is not true that child is present for 'the Spouse' and
     * 	the termination type of the marriage of 'the Spouse' is DIVORCE
     * then
     * 	set the event date of 'the Award' to the end date of the marriage of 'the Spouse' ;
     * 	set the dependency decision type of 'the Award' to MARRIAGE_TERMINATED ;
     * 	set the dependency status type of 'the Award' to NOT_AN_AWARD_DEPENDENT ;
     *
     *************************************************************/
    @Rule
    public void awardDecisionDueToDivorce (){
        if (spouse.getChildren().size() == 0 && spouse.getCurrentMarriage().getTerminationType().equals(MarriageTerminationType.DIVORCE)){
            spouseResponse.getAward().setEventDate(spouse.getCurrentMarriage().getEndDate());
            spouseResponse.getAward().setDependencyDecisionType(DependentDecisionType.MARRIAGE_TERMINATED);
            spouseResponse.getAward().setDependencyStatusType(DependentStatusType.NOT_AN_AWARD_DEPENDENT);
        
            logger.debug("Rule: awardDecisionDueToDivorce");
            logger.debug("Message: Award event date set to marriage end date");
            logger.debug("Message: Award Dependency Decision Type set to MARRIAGE_TERMINATED");
            logger.debug("Message: Award Dependency Status Type set to NOT_AN_AWARD_DEPENDENT");
        }
    }


    /*************************************************************
     *
     * Rule: CP0163 - Spouse Removal before Award effective date
     *
     * if the award effective date of 'the Spouse' is not null and
     *    the end date of the marriage of 'the Spouse'is before
     *     the award effective date of 'the Spouse'
     *  then
     *    add "Auto Dependency Processing Validation Reject Reason - Attempted spouse removal date is before spouse's add date. Please review" to the list of Exceptions ;
     * 	  set 'exception generated' to true ;
     *
     *
     *************************************************************/
    @Rule
    public void beforeAwardEffectiveDate () {
        if (spouse.getAwardEffectiveDate() != null && spouse.getCurrentMarriage().getEndDate().before(spouse.getAwardEffectiveDate())){
            spouseResponse.getExceptionMessages().addException(SpouseMessages.BEFORE_AWARD_EFFECTIVE_DATE);
            spouseDecisionVariables.setExceptionGenerated(true);
        }
    }
}  