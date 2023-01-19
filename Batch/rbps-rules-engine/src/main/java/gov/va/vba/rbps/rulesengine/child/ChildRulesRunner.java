package gov.va.vba.rbps.rulesengine.child;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.rulesengine.child.rules.*;
import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;
import gov.va.vba.rbps.rulesengine.engine.RuleRunner;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;

import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

public class ChildRulesRunner implements RuleRunner {

    private Child child;
    private VeteranCommonDates commonDates;
    private ChildDecisionVariables childDecisionVariables;
    private ChildResponse childResponse = new ChildResponse();
    private Logger logger = Logger.getLogger(this.getClass());


    public ChildRulesRunner(Child child, VeteranCommonDates commonDates) {
        this.child = child;
        this.commonDates = commonDates;
        this.childDecisionVariables = RulesEngineUtil.getChildDecisionVariables(child, commonDates);

        logger.debug("======================================= ");
        logger.debug(  "Child18BirthDay : " + this.childDecisionVariables.getChild18BirthDay());
        logger.debug(  "Child19BirthDay : " + this.childDecisionVariables.getChild19BirthDay());
        logger.debug(  "ChildAgeOnDateOfClaim : " + this.childDecisionVariables.getChildAgeOnDateOfClaim());
        logger.debug(  "ChildAgeOnEffectiveDate : " + this.childDecisionVariables.getChildAgeOnEffectiveDate());
        logger.debug(  "DOBPlus1Year : " + this.childDecisionVariables.getDobPlusOneYear());
        logger.debug(  "RatingPlus1Year : " + this.childDecisionVariables.getRatingPlusOneYear());
        logger.debug(  "RatingPlus1Year30Days : " + this.childDecisionVariables.getRatingPlusOneYear30Days());
        logger.debug(  "ClaimReceivedDate : " + this.commonDates.getClaimDate());
        logger.debug("======================================= ");
    }

    @Override
    public Response executeRules() throws RuleEngineException {
        runChildValidationRules();

        if(!childDecisionVariables.isCompleted()) {
            determineAwardStatus();

            if(childResponse.getPriorSchoolChild().getEndDate() != null) {
                priorTermValidation();

                if(childDecisionVariables.isPriorSchoolTermValid()) {
                    priorTermProcess();
                }

            }

            if (childDecisionVariables.isCompleted()) {
                finalizeRules();

            } else if (
                    RbpsXomUtil.isPresent(childResponse.getAward().getDependencyStatusType()) &&
                            (childResponse.getAward().getDependencyStatusType().equals(DependentStatusType.MINOR_CHILD) ||
                                    childResponse.getAward().getDependencyStatusType().equals(DependentStatusType.SCHOOL_CHILD))
            ) {

                DependentStatusType statusType = childResponse.getAward().getDependencyStatusType();

                if (statusType.equals(DependentStatusType.MINOR_CHILD)) {
                    runMinorChildRuleSets();
                    if (RbpsXomUtil.isPresent(childResponse.getMinorSchoolChildAward().getDependencyStatusType()) && childResponse.getMinorSchoolChildAward().getDependencyStatusType().equals(DependentStatusType.SCHOOL_CHILD)) {
                        runSchoolChildValidationRuleSet();
                        if (childDecisionVariables.isCompleted()) {
                            finalizeRules();
                        } else {
                            runMinorToSchoolChild();
                        }
                    } else {
                        logger.debug("*** Running CP0227-C rule");
                        new PostMinorSchoolChildRuleSet(child, childResponse, childDecisionVariables, commonDates).priorTermContinuousWithMinorChild();

                        logger.debug("*** Running ChildWithNoEventDateRuleSet");
                        new ChildWithNoEventDateRuleSet(child, childResponse, childDecisionVariables, commonDates).run();

                    }
                } else if (statusType.equals(DependentStatusType.SCHOOL_CHILD)) {
                    runSchoolChildValidationRuleSet();
                    if (childDecisionVariables.isCompleted()) {
                        finalizeRules();
                    } else {
                        runSchoolChildRuleSets();
                    }
                }
            }
        } else {
            finalizeRules();
        }

        if(childResponse.getAward() != null && childResponse.getAward().getEventDate() != null) {
            checkIfBeforeThirtyPercent();
        }

        return childResponse;
    }

    /**
     * @throws RuleEngineException
     */
    private void priorTermProcess() throws RuleEngineException {
        logger.debug("*** Running PriorSchoolTermProcessRuleSet");
        new PriorSchoolTermProcessRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
        logger.debug("*** End PriorSchoolTermProcessRuleSet");
    }

    /**
     * @throws RuleEngineException
     */
    private void priorTermValidation() throws RuleEngineException {
        logger.debug("*** Running PriorSchoolTermValidationRuleSet");
        new PriorSchoolTermValidationRuleSet(child, childResponse, childDecisionVariables).run();
        logger.debug("*** End PriorSchoolTermValidationRuleSet");
    }

    /**
     * @throws RuleEngineException
     */
    private void determineAwardStatus() throws RuleEngineException {
        logger.debug("*** Running ChildDetermineAwardStatusRuleSet");
        new ChildDetermineAwardStatusRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
    }

    /**
     * @throws RuleEngineException
     */
    private void runChildValidationRules() throws RuleEngineException {
        logger.debug("*** Running ChildValidationRuleSet");
        new ChildValidationRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
    }

    /**
     * @throws RuleEngineException
     */
    private void runSchoolChildRuleSets() throws RuleEngineException {
        if (child.getChildType().equals(ChildType.BIOLOGICAL_CHILD)) {
            logger.debug("*** Running SchoolChildBiologicalRuleSet");
            new SchoolChildBiologicalRuleSet(child,childResponse, childDecisionVariables, commonDates).run();
        }

        if (child.getChildType().equals(ChildType.STEPCHILD)) {
            logger.debug("*** Running SchoolChildBiologicalRuleSet");
            new SchoolChildStepChildRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
        }

        new PostSchoolChildRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
    }

    /**
     * @throws RuleEngineException
     */
    private void runSchoolChildValidationRuleSet() throws RuleEngineException {
        // School Child Verification
        logger.debug("*** Running SchoolChildVerificationRuleSet");
        new SchoolChildVerificationRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
    }

    private void runMinorToSchoolChild() throws RuleEngineException {
        logger.debug("*** Running MinorToSchoolChildRuleSet");
        new MinorToSchoolChildRuleSet(child, childResponse, childDecisionVariables).run();

        new PostMinorSchoolChildRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
    }

    private void finalizeRules() {
        logger.debug("*** Finalizing Rules");
        if (RbpsXomUtil.isNotPresent(childResponse.getAward().getDependencyStatusType())) {
            logger.debug("- removing award due to denial");
            childResponse.setAward(null);
        } else if (
            RbpsXomUtil.isPresent(childResponse.getAward().getDependencyStatusType())
            && childResponse.getAward().getDependencyStatusType().equals(DependentStatusType.SCHOOL_CHILD)
        ) {
            logger.debug("- removing school child award");
            childResponse.setAward(null);
        }

        logger.debug("- removing minor school child award");
        childResponse.setMinorSchoolChildAward(null);
    }

    private void runMinorChildRuleSets() throws RuleEngineException {
        logger.debug("*** Running Minor Child Rule Sets");

        if (child.getChildType().equals(ChildType.BIOLOGICAL_CHILD)) {
            logger.debug("Running BiologicalRuleSet");
            new MinorChildBiologicalRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
        }

        if (child.getChildType().equals(ChildType.STEPCHILD)) {
            logger.debug("Running StepChildRuleSet");
            new MinorChildStepChildRuleSet(child, childResponse, childDecisionVariables, commonDates).run();
        }

        logger.debug("*** End Minor Child Rule Sets");
    }

    /*************************************************************
     *
     * CP0240
     *
     * if
     *      the event date of 'the Award' is before the first 30% date of the 'the Veteran' (rating effective date)
     * then
     *      set the event date of 'the Award' to the first 30% date of the 'the Veteran' (rating effective date)
     *
     *************************************************************/
    private void checkIfBeforeThirtyPercent() {
        if(childResponse.getAward().getEventDate().before(commonDates.getRatingEffectiveDate())) {
            logger.debug("Rule checkIfBeforeThirtyPercent: Current Award event date: " + childResponse.getAward().getEventDate() + ". Setting to Rating Effective Date.");
            childResponse.getAward().setEventDate(commonDates.getRatingEffectiveDate());
            logger.debug("Rule checkIfBeforeThirtyPercent: Award event date set to the rating effective date of the veteran: " + commonDates.getRatingEffectiveDate());
        }
    }
}
