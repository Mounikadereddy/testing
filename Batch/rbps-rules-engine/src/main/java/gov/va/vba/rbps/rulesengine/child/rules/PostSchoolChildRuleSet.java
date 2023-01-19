package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;


public class PostSchoolChildRuleSet extends ChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public PostSchoolChildRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    @Override
    public void run() throws RuleEngineException {
        super.run();
        if (!childDecisionVariables.isConditionSatisfied()) {
            currentAndPriorContinuousTerm();
            if (!childDecisionVariables.isConditionSatisfied()) {
                lastTermInCorpAndCurrentContinuous();
            }
            
            logger.debug("*** Running ChildWithNoEventDateRuleSet");
            new ChildWithNoEventDateRuleSet(child, childResponse, childDecisionVariables, veteranCommonDates).run();
        }
    }


    /**
     * Rule: CP0237  C-Prior term included but not continous with current
     *
     * if
     *  	priorSchoolTermValid and
     *  	the last term of 'the Child'  is not null and
     *     the last term in corp end date of 'the Child' is not null and
     *    	the current term of 'the Child' is not null and
     *    	the end date of 'PriorSchoolChild' is not null and
     *    	the event date of 'the Award' is not null and
     *  	 getOmnibusedDate (the end date of 'PriorSchoolChild')  is before
     *   	the last term in corp end  effective date of 'the Child'  and
     *  	getOmnibusedDate(the end date of 'PriorSchoolChild') plus 5 months  is before
     *  	getOmnibusedDate(the event date of 'the Award')
     *  then
     *      set 'Condition Satisfied' to true ;
     *      set Completed to true ;
     *      add "Auto Processing Dependency Processing Reject - Prior school term submitted is shorter than on the award and would result in school attendance not being continuous. Please Review." to the list of Exceptions ;
     */
    @Rule
    public void priorTermIncludedButNotContinuousWithCurrent() {
        if (
            childDecisionVariables.isPriorSchoolTermValid()
            && RbpsXomUtil.isPresent(child.getLastTerm())
            && RbpsXomUtil.isPresent(child.getLastTermInCorpEndDate())
            && RbpsXomUtil.isPresent(child.getCurrentTerm())
            && RbpsXomUtil.isPresent(childResponse.getPriorSchoolChild().getEndDate())
            && RbpsXomUtil.isPresent(childResponse.getAward().getEventDate())
            && RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEndDate()).before(child.getLastTermInCorpEndEffectiveDate())
            && RbpsXomUtil.addMonthsToDate(5, RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEndDate())).before(RbpsXomUtil.getOmnibusedDate(childResponse.getAward().getEventDate()))
        ) {
            childDecisionVariables.setConditionSatisfied(true);
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(ChildMessages.INVALID_PRIOR_SCHOOL_TERM);
        }
    }

    /**
     * Rule: CP0237 B Current and Prior Continous term
     *
     * if
     *   	priorSchoolTermValid and
     *   	the current term of 'the Child' is not null and
     *   	the end date of 'PriorSchoolChild' is not null and
     *     the event date of 'the Award' is not null and
     *     getOmnibusedDate(the end date of 'PriorSchoolChild') plus 5 months
     *     is after or the same as getOmnibusedDate( the event date of 'the Award')
     *  then
     *      set the event date of 'the Award' to the course end date of the last term of 'the Child';
     *      set 'Condition Satisfied' to true;
     */
    public void currentAndPriorContinuousTerm() {
        if (
                childDecisionVariables.isPriorSchoolTermValid()
                && RbpsXomUtil.isPresent(child.getCurrentTerm())
                && RbpsXomUtil.isPresent(childResponse.getPriorSchoolChild().getEndDate())
                && RbpsXomUtil.isPresent(childResponse.getAward().getEventDate())
                && RbpsXomUtil.addMonthsToDate(5, RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEndDate()))
                    .compareTo(RbpsXomUtil.getOmnibusedDate(childResponse.getAward().getEventDate())) >= 0
        ) {
            childDecisionVariables.setConditionSatisfied(true);
            childResponse.getAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("currentAndPriorContinuousTerm: Event date of the last term course end date: " + child.getLastTerm().getCourseEndDate());
        }
    }



    /**
     * Rule: CP0237 -A- Last term in Corp and Current Continous
     *
     * if
     *    the last term in corp begin date of 'the Child' is not null and
     *    the last term in corp end date of 'the Child' is not null and
     *    the current term of 'the Child' is not null and
     *    the event date of 'the Award' is not null and
     *    getOmnibusedDate (the event date of 'the Award') is after
     *    the last term in corp begin effective date of 'the Child' and
     *    getOmnibusedDate (the event date of 'the Award')
     *   is before or the same as  the last term in corp end effective date of 'the Child' plus 5 months
     *  then
     *      set the event date of 'the Award' to the last term in corp end date of 'the Child' ;
     *      set 'Condition Satisfied' to true;
     */
    public void lastTermInCorpAndCurrentContinuous() {
        if (
            RbpsXomUtil.isPresent(child.getLastTermInCorpEndDate())
            && RbpsXomUtil.isPresent(child.getLastTermInCorpBeginDate())
            && RbpsXomUtil.isPresent(child.getCurrentTerm())
            && RbpsXomUtil.isPresent(childResponse.getAward().getEventDate())
            && RbpsXomUtil.getOmnibusedDate(childResponse.getAward().getEventDate()).after(child.getLastTermInCorpBeginEffectiveDate())
            && RbpsXomUtil.getOmnibusedDate(childResponse.getAward().getEventDate()).compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTermInCorpEndEffectiveDate())) <= 0
        ) {
            childDecisionVariables.setConditionSatisfied(true);
            childResponse.getAward().setEventDate(child.getLastTermInCorpEndDate());
            logger.debug("lastTermInCorpAndCurrentContinuous: Event date of the last term in corp end date of the child: " + child.getLastTermInCorpEndDate());
        }
    }
}
