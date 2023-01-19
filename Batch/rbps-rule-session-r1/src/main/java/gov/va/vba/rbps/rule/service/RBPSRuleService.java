package gov.va.vba.rbps.rule.service;

import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuleExecutionException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.xom.Veteran;

import java.io.Serializable;
import java.util.Map;
/**
 * 
 * @since March 1, 2011
 * @version 1.0
 * @author SumanRaja.Chiluveru
 * 
 */
public interface RBPSRuleService extends Serializable {
	
	public Map<String, Object> executeEP130(Veteran veteran, RbpsRepository repository)
			throws RbpsRuleExecutionException;

}
 