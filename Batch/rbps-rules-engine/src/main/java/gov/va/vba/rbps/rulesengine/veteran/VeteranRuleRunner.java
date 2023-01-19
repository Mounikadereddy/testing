package gov.va.vba.rbps.rulesengine.veteran;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import gov.va.vba.rbps.rulesengine.engine.Response;
import gov.va.vba.rbps.rulesengine.engine.RuleEngineException;
import gov.va.vba.rbps.rulesengine.engine.RuleRunner;
import gov.va.vba.rbps.rulesengine.veteran.rules.VeteranMarriageRuleSet;
import gov.va.vba.rbps.rulesengine.veteran.rules.VeteranRuleSet;
import gov.va.vba.rbps.coreframework.xom.*;
import gov.va.vba.rbps.coreframework.xom.util.RbpsXomUtil;


public class VeteranRuleRunner implements RuleRunner {
	
	private final Veteran veteran;
	private final RuleExceptionMessages exceptionMessages = new RuleExceptionMessages();

	public VeteranRuleRunner(Veteran veteran) {
		this.veteran = veteran;
	}
	
	@Override
	public Response executeRules() throws RuleEngineException {
		VeteranResponse response = new VeteranResponse();
		
		// run veteran rules 
		new VeteranRuleSet(veteran, exceptionMessages).run();
		
		/*************************************************************
		 *   Conditional Check: 
		 *   	the current marriage of 'the Veteran' is present  
		 *************************************************************/
		if(RbpsXomUtil.isPresent(veteran.getCurrentMarriage())) {
			new VeteranMarriageRuleSet(veteran, exceptionMessages).run();
		}


		response.setExceptionMessages(exceptionMessages);

		return response;
	}
}
