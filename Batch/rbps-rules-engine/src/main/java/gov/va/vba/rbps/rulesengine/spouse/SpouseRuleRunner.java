package gov.va.vba.rbps.rulesengine.spouse;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;
import gov.va.vba.rbps.rulesengine.engine.RuleRunner;
import gov.va.vba.rbps.rulesengine.engine.RulesEngineUtil;
import org.apache.commons.lang.text.StrSubstitutor;
import gov.va.vba.rbps.rulesengine.spouse.rules.SpouseAwardRuleSet;
import gov.va.vba.rbps.rulesengine.spouse.rules.SpouseRemovalRuleSet;
import gov.va.vba.rbps.rulesengine.spouse.rules.SpouseValidationRuleSet;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;
import gov.va.vba.rbps.coreframework.xom.util.VeteranCommonDates;

import java.util.Map;


public class SpouseRuleRunner implements RuleRunner {
    private final Spouse spouse;
    private final VeteranCommonDates commonDates;
    private final SpouseDecisionVariables spouseDecisionVariables;
    private final SpouseResponse spouseResponse = new SpouseResponse();
    private Logger logger = Logger.getLogger(this.getClass());

    public SpouseRuleRunner (Spouse spouse, VeteranCommonDates commonDates) {
        this.spouse = spouse;
        this.commonDates = commonDates;
        this.spouseDecisionVariables = RulesEngineUtil.getSpouseDecisionVariables(commonDates);
    }

    @Override
    public Response executeRules() throws RuleEngineException {
        logger.debug("*** Starting Spouse Validation Rule Set***");
        new SpouseValidationRuleSet(spouse, spouseResponse,spouseDecisionVariables).run();
        logger.debug("*** End Spouse Validation Rule Set***");
        if (!spouseDecisionVariables.isExceptionGenerated()) {
            if (RbpsXomUtil.isPresent(spouse.getCurrentMarriage().getEndDate())) {
                // log message
                logger.debug("*** Starting Spouse Removal Rule Set***");
                new SpouseRemovalRuleSet(spouse, spouseResponse,spouseDecisionVariables).run();
                logger.debug("*** End Spouse Removal Rule Set***");
            } else {
               // log message
                logger.debug("*** Starting Spouse Award Rule Set***");
                new SpouseAwardRuleSet(spouse, spouseResponse,spouseDecisionVariables, commonDates).run();
                logger.debug("*** End Spouse Award Rule Set***");
            }

           if (RbpsXomUtil.isNotPresent(spouseResponse.getAward().getEventDate())){
               // log message
               logger.debug("*** Spouse has no event date, adding exception ***");
               spouseResponse.getExceptionMessages().addException(SpouseMessages.SPOUSE_WITH_NO_EVENT_RULE);
           }
        }

       return spouseResponse;
    }

}
