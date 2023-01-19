package gov.va.vba.rbps.rulesengine.child.rules;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;
import gov.va.vba.rbps.rulesengine.child.ChildBaseRuleSet;
import gov.va.vba.rbps.rulesengine.child.ChildDecisionVariables;
import gov.va.vba.rbps.rulesengine.child.ChildMessages;
import gov.va.vba.rbps.rulesengine.child.ChildResponse;
import gov.va.vba.rbps.rulesengine.engine.Rule;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;
import gov.va.vba.framework.logging.Logger;

public class PostMinorSchoolChildRuleSet extends ChildBaseRuleSet {

    private Logger logger = Logger.getLogger(this.getClass());

    public PostMinorSchoolChildRuleSet(Child child, ChildResponse childResponse, ChildDecisionVariables childDecisionVariables, VeteranCommonDates veteranCommonDates) {
        super(child, childResponse, childDecisionVariables, veteranCommonDates);
    }

    @Override
    public void run() throws RuleEngineException {
        super.run();

        if(!childDecisionVariables.isConditionSatisfied()) {
            minorToSchoolCurrentAndPriorContinuousTerm();
            if(!childDecisionVariables.isConditionSatisfied()) {
                lastTermInCorpAndMinorToSchoolCurrentContinuous();
            }
            priorTermContinuousWithMinorChild();

            logger.debug("*** Running ChildWithNoEventDateRuleSet");
            new ChildWithNoEventDateRuleSet(child, childResponse, childDecisionVariables, veteranCommonDates).run();
        }
    }

    /**
     * Rule: CP0237  C- -A-Prior term included but not continous with Minor to School current
     *
     * if
     * 	 priorSchoolTermValid and
     *  	the last term of 'the Child'  is not null and
     *      the last term in corp end date of 'the Child' is not null and
     *    	the current term of 'the Child' is not null and
     *      the end date of 'PriorSchoolChild' is not null and
     *      the event date of  'the minorSchoolChildAward'  is not null and
     *  	getOmnibusedDate (the end date of 'PriorSchoolChild') is before
     *   	the last term in corp end effective date of 'the Child'  and
     *  	getOmnibusedDate(the end date of 'PriorSchoolChild') plus 5 months  is before
     *  	getOmnibusedDate(the event date of  'the minorSchoolChildAward' )
     *  then
     *      set 'Condition Satisfied' to true ;
     *      set Completed to true ;
     *      add "Auto Processing Dependency Processing Reject - Prior school term submitted is shorter than on the award and would result in school attendance not being continuous. Please Review." to the list of Exceptions ;
     */
    @Rule
    public void priorTermIncludedButNotContinuousMinorChild() {
        if (
            childDecisionVariables.isPriorSchoolTermValid()
            && RbpsXomUtil.isPresent(child.getLastTerm())
            && RbpsXomUtil.isPresent(child.getLastTermInCorpEndDate())
            && RbpsXomUtil.isPresent(child.getCurrentTerm())
            && RbpsXomUtil.isPresent(childResponse.getPriorSchoolChild().getEndDate())
            && RbpsXomUtil.isPresent(childResponse.getMinorSchoolChildAward().getEventDate())
            && RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEndDate()).before(child.getLastTermInCorpEndEffectiveDate())
            && RbpsXomUtil.addMonthsToDate(5, RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEndDate())).before(RbpsXomUtil.getOmnibusedDate(childResponse.getMinorSchoolChildAward().getEventDate()))
        ) {
            childDecisionVariables.setConditionSatisfied(true);
            childDecisionVariables.setCompleted(true);
            childResponse.getExceptionMessages().addException(ChildMessages.INVALID_PRIOR_SCHOOL_TERM);
        }
    }

    /**
     * Rule: CP0237 B-A Minor to School  Current and Prior Continous term
     *
     * if
     *   	priorSchoolTermValid and
     *    	the current term of 'the Child' is not null and
     *    	the end date of 'PriorSchoolChild' is not null and
     *    	the event date of 'the minorSchoolChildAward' is not null and
     *   	getOmnibusedDate(the end date of 'PriorSchoolChild') plus 5 months
     *   	 is after or the same as getOmnibusedDate( the event date of 'the minorSchoolChildAward' )
     *  then
     *  set the event date of 'the minorSchoolChildAward'  to the course end date of the last term of 'the Child' ;
     *  set 'Condition Satisfied' to true ;
     */
    public void minorToSchoolCurrentAndPriorContinuousTerm() {
        if (
            childDecisionVariables.isPriorSchoolTermValid()
            && RbpsXomUtil.isPresent(child.getCurrentTerm())
            && RbpsXomUtil.isPresent(childResponse.getPriorSchoolChild().getEndDate())
            && RbpsXomUtil.isPresent(childResponse.getMinorSchoolChildAward().getEventDate())
            && RbpsXomUtil.addMonthsToDate(5, RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEndDate()))
            .compareTo(RbpsXomUtil.getOmnibusedDate(childResponse.getMinorSchoolChildAward().getEventDate())) >= 0
        ) {
            childDecisionVariables.setConditionSatisfied(true);
            childResponse.getMinorSchoolChildAward().setEventDate(child.getLastTerm().getCourseEndDate());
            logger.debug("minorToSchoolCurrentAndPriorContinuousTerm: Event date of the minor school child award set to the last term course end date: " + child.getLastTerm().getCourseEndDate());
        }
    }

    /**
     * Rule: CP0237 -A-B- Last term in Corp and Minor to School  Current Continuous
     *
     * if
     *    the last term in corp begin date of 'the Child' is not null and
     *    the last term in corp end date of 'the Child' is not null and
     *    the current term of 'the Child' is not null and
     *    the event date of  'the minorSchoolChildAward' is not null and
     *    getOmnibusedDate (the event date of  'the minorSchoolChildAward') is after the last term in corp begin effective date of 'the Child' and
     *    getOmnibusedDate (the event date of  'the minorSchoolChildAward') is before or the same as the last term in corp end effective date of 'the Child' plus 5 months
     *  then
     *  set the event date of  'the minorSchoolChildAward' to the last term in corp end date of 'the Child' ;
     *  set 'Condition Satisfied' to true;
     */
    public void lastTermInCorpAndMinorToSchoolCurrentContinuous() {
        if (
            RbpsXomUtil.isPresent(child.getLastTermInCorpEndDate())
            && RbpsXomUtil.isPresent(child.getLastTermInCorpBeginDate())
            && RbpsXomUtil.isPresent(child.getCurrentTerm())
            && RbpsXomUtil.isPresent(childResponse.getMinorSchoolChildAward().getEventDate())
            && RbpsXomUtil.getOmnibusedDate(childResponse.getMinorSchoolChildAward().getEventDate()).after(child.getLastTermInCorpBeginEffectiveDate())
            && RbpsXomUtil.getOmnibusedDate(childResponse.getMinorSchoolChildAward().getEventDate()).compareTo(RbpsXomUtil.addMonthsToDate(5, child.getLastTermInCorpEndEffectiveDate())) <= 0

        ) {
            childDecisionVariables.setConditionSatisfied(true);
            childResponse.getMinorSchoolChildAward().setEventDate(child.getLastTermInCorpEndDate());
            logger.debug("lastTermInCorpAndCurrentContinuous: Event date of the minor school child award set to the last term in corp end date of the child: " + child.getLastTermInCorpEndDate());
        }
    }

    /**
     * Rule : CP0227-C- Prior term Continous with Minor Child
     *
     * if  the dependency status type of 'the Award' is present and
     *      the dependency status type of 'the Award' is MINOR_CHILD and
     *      priorSchoolTermValid and
     *      the birth date of 'the Child' plus 18 Year ( s ) plus 5  months is after  or the same as
     *      getOmnibusedDate(the event date of 'PriorSchoolChild')
     *  then
     *   set the event date of 'PriorSchoolChild' to the birth date of 'the Child' plus 18 Year ( s ) ;
     *
     *
     */
    public void priorTermContinuousWithMinorChild() {
        logger.debug("*** Running PriorTermContinuousWithMinorChild Rule***");
        if (childDecisionVariables.isPriorSchoolTermValid()
                && (RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay()).after(RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEventDate()))
                || RbpsXomUtil.addMonthsToDate(5, childDecisionVariables.getChild18BirthDay()).equals(RbpsXomUtil.getOmnibusedDate(childResponse.getPriorSchoolChild().getEventDate())))
        ) {
            logger.debug("Setting prior school child award event date to child's 18th birthday");
            childResponse.getPriorSchoolChild().setEventDate(childDecisionVariables.getChild18BirthDay());
        }
    }
}
