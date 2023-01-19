package gov.va.vba.rbps.rule.service.util; 

/*
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.exception.RbpsRuleExecutionException;
import ilog.rules.res.model.IlrPath;
import ilog.rules.res.session.IlrSessionRequest;
import ilog.rules.res.session.IlrSessionResponse;
import ilog.rules.res.session.IlrStatelessSession;

import java.util.Map;
*/

/**
 *
 * @since March 1, 2011
 * @version 1.0
 * @author SumanRaja.Chiluveru
 *
 */
 
public class RuleEngineWrapper {
/*
    private Logger logger = Logger.getLogger(this.getClass());


    private RBPSSessionFactory ruleSessionFactory;


    public IlrSessionResponse executeRules( final Map<String, Object>   inputParams,
                                            final IlrPath               path) throws RbpsRuleExecutionException {

        try {
            IlrSessionRequest request = ruleSessionFactory.createRequest();

            request.setRulesetPath(path);
            request.setInputParameters(inputParams);
            request.setTraceEnabled(true); // added for rule trace
            request.getTraceFilter().setInfoAllFilters(true); // added for rule trace

            IlrStatelessSession session     = ruleSessionFactory.createStatelessSession();
            IlrSessionResponse  response    = session.execute(request);

            return response;
        }
        catch (Throwable ex) {

            logger.error( "Exception happened when commincating with rule engine in RuleEngineWrapper." + ex.getMessage() );

            throw new RbpsRuleExecutionException( "Exception occurred while communicating with rule engine in RuleEngineWrapper . ",
                                                  ex );
        }
    }




    public RBPSSessionFactory getRuleSessionFactory() {
        return ruleSessionFactory;
    }

    public void setRuleSessionFactory(final RBPSSessionFactory ruleSessionFactory) {
        this.ruleSessionFactory = ruleSessionFactory;
    }
*/
}
