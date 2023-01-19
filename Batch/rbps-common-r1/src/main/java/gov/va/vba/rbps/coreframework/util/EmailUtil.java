package gov.va.vba.rbps.coreframework.util;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.framework.services.mail.ContentType;
import gov.va.vba.framework.services.mail.EmailServiceRemote;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import javax.rmi.PortableRemoteObject;



public class EmailUtil {
	
	private Context jndiContext;
	
	private static Logger logger = Logger.getLogger(EmailUtil.class);
	
	public void sendEmail(String fromName, String from, String to, String cc, String bcc, String subject, String body, ContentType contentType, Collection<File> attachments, boolean encrypt){
		
		try {
			createInitialContext();
			EmailServiceRemote emailServiceRemote = createEmailServiceRemote();
			emailServiceRemote.sendEmail(fromName, from, to, cc, bcc, subject, body, contentType, attachments, encrypt);
		} catch (NamingException e) {
			CommonUtils.log(logger, "Error finding Email Service EJB",e,false);
		} catch (Exception e) {
			CommonUtils.log(logger, "Error in EmailUtil",e,false);
		}
		
	}
	
	private EmailServiceRemote createEmailServiceRemote() {
		Object ref = null;
		try {
			ref = getJNDIContext().lookup("VbaEmailService#"
					+ EmailServiceRemote.class.getName());
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return (EmailServiceRemote) PortableRemoteObject
				.narrow(ref, EmailServiceRemote.class);

	}
	
	
	private void createInitialContext()
			throws NamingException {
		
	//For testing	
//		String contextFactory = "weblogic.jndi.WLInitialContextFactory";
//		String providerUrl = "t3://vbawbtappdev2.vba.va.gov:9015";
//		String providerUrl = "t3://vbawbtappdev2.vba.va.gov:23015";
//		Properties p = new Properties();
//		p.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
//		p.put(Context.PROVIDER_URL, providerUrl);
//		jndiContext = new InitialContext(p);
		
		jndiContext = new InitialContext();
	}

	
	private Context getJNDIContext()
	{
		return jndiContext;
	}
	
	// test
	public static void main(String[] args) {
		new EmailUtil().sendEmail("Srinivas Nandula", "srinivas.nandula@va.gov", "srinivas.nandula@va.gov", null, null, "From RBPS", "Email From RBPS", ContentType.TEXT, null, false);
		
	}
	
}
