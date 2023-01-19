package gov.va.vba.rbps.rule.service.util;

import gov.va.vba.framework.logging.Logger;

//import ilog.rules.res.model.IlrFormatException;
//import ilog.rules.res.model.IlrPath;

/**
 * 
 * @since March 1, 2011
 * @version 1.0
 * @author SumanRaja.Chiluveru
 * 
 */
public class RBPSRuleServiceUtil { 

	private Logger log = Logger.getLogger(this.getClass());

	private String resJNDIName;
	private String resContextFactory;
	private String resProviderUrl;
	private String resSecurityPrincipal;
	private String resSecurityCredential;
	private String ep130VeteranRules;
	private String ep130SpouseRules;
	private String ep130ChildRules;
/*
	public IlrPath getEP130VeteranRulePath() throws IlrFormatException {
		log.debug("RuleSet path for ep130 veteran rules is : "
				+ getEP130VeteranRules());
		return IlrPath.parsePath(getEP130VeteranRules());
	}

	public IlrPath getEP130SpouseRulePath() throws IlrFormatException {
		log.debug("ruleSet path for ep130 spouse rules is : "
				+ getEP130SpouseRules());
		return IlrPath.parsePath(getEP130SpouseRules());
	}

	public IlrPath getEP130ChildRulePath() throws IlrFormatException {
		log.debug("ruleSet path for ep130 child rules is : "
				+ getEP130ChildRules());
		return IlrPath.parsePath(getEP130ChildRules());
	}
*/
	public String getResJNDIName() {
		return resJNDIName;
	}

	public void setResJNDIName(String resJNDIName) {
		this.resJNDIName = resJNDIName;
	}

	public String getResContextFactory() {
		return resContextFactory;
	}

	public void setResContextFactory(String resContextFactory) {
		this.resContextFactory = resContextFactory;
	}

	public String getResProviderUrl() {
		return resProviderUrl;
	}

	public void setResProviderUrl(String resProviderUrl) {
		this.resProviderUrl = resProviderUrl;
	}

	public String getResSecurityPrincipal() {
		return resSecurityPrincipal;
	}

	public void setResSecurityPrincipal(String resSecurityPrincipal) {
		this.resSecurityPrincipal = resSecurityPrincipal;
	}

	public String getResSecurityCredential() {
		return resSecurityCredential;
	}

	public void setResSecurityCredential(String resSecurityCredential) {
		this.resSecurityCredential = resSecurityCredential;
	}

	public String getEP130VeteranRules() {
		return ep130VeteranRules.trim();
	}

	public void setEP130VeteranRules(String ep130VeteranRules) {
		this.ep130VeteranRules = ep130VeteranRules;
	}

	public String getEP130SpouseRules() {
		return ep130SpouseRules.trim();
	}

	public void setEP130SpouseRules(String ep130SpouseRules) {
		this.ep130SpouseRules = ep130SpouseRules;
	}

	public String getEP130ChildRules() {
		return ep130ChildRules.trim();
	}

	public void setEP130ChildRules(String ep130ChildRules) {
		this.ep130ChildRules = ep130ChildRules;
	}

}
