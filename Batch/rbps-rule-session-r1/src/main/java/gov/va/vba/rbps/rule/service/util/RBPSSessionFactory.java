package gov.va.vba.rbps.rule.service.util; 
/*
import gov.va.vba.framework.logging.Logger;
import ilog.rules.res.session.IlrEJB3SessionFactory;
import ilog.rules.res.session.IlrSessionCreationException;
import ilog.rules.res.session.IlrStatelessSession;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
*/
/**
 *
 * @since March 1, 2011
 * @version 1.0
 * @author SumanRaja.Chiluveru
 *
 */
 
public class RBPSSessionFactory /*extends IlrEJB3SessionFactory*/ {
/*
	private RBPSRuleServiceUtil ruleServiceUtil;
	private Logger log = Logger.getLogger(this.getClass());

	@Override
    public IlrStatelessSession createStatelessSession()
			throws IlrSessionCreationException {
		log.debug("Creating the session inside RBPSSesssionFactory....");
		this.setRemote(true);
		Hashtable<String, String> ht = new Hashtable<String, String>();
		Object obj = null;
		if (ruleServiceUtil.getResContextFactory() != null) {
			ht.put(Context.INITIAL_CONTEXT_FACTORY,
					ruleServiceUtil.getResContextFactory());
		}
		if (ruleServiceUtil.getResProviderUrl() != null) {
			ht.put(Context.PROVIDER_URL, ruleServiceUtil.getResProviderUrl());
		}
		if (ruleServiceUtil.getResSecurityPrincipal() != null) {
			ht.put(Context.SECURITY_PRINCIPAL,
					ruleServiceUtil.getResSecurityPrincipal());
		}
		if (ruleServiceUtil.getResSecurityCredential() != null) {
			ht.put(Context.SECURITY_CREDENTIALS,
					ruleServiceUtil.getResSecurityCredential());
		}
		try {
			InitialContext ic = new InitialContext(ht);
			obj = ic.lookup(ruleServiceUtil.getResJNDIName());
		} catch (NamingException e) {
			log.error(
					"Exception occurred while creating the rule session inside RBPSSessionFactory. ",
					e);
			throw new IlrSessionCreationException(e);
		}
		return (IlrStatelessSession) PortableRemoteObject.narrow(obj,
				IlrStatelessSession.class);
	}

	public RBPSRuleServiceUtil getRuleServiceUtil() {
		return ruleServiceUtil;
	}

	public void setRuleServiceUtil(final RBPSRuleServiceUtil ruleServiceUtil) {
		this.ruleServiceUtil = ruleServiceUtil;
	}
*/
}
