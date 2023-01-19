package gov.va.vba.rbps.coreframework.util;

/**
 * EmailSender.class is utility class to send email for RBPS.
 * 
 * @author Xiangfeng	Dong
 * @since 04/10/2019
 * @version %PCMS_HEADER_SUBSTITUTION_START%%PM%,v %PR%, %PRT% %AUTHOR%
 * @schistory %PID%<br>
 * <br>
 * 
 *            %PL% %PCMS_HEADER_SUBSTITUTION_END%
 */

import gov.va.vba.rbps.coreframework.util.RbpsConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import gov.va.vba.framework.common.SystemUtils;


//import org.apache.commons.lang.SystemUtils;

//import org.apache.log4j.Logger;
import gov.va.vba.framework.logging.Logger;


public class EmailSender  {
	
	public static final Logger LOGGER = Logger.getLogger(EmailSender.class);
	
	Session session;
	private String propFileName = "rbps";
	private static int err_arr_length = 30;
    public static String[] err_msg = new String[err_arr_length] ;
    private static int err_counter = 0;
    static final String pageNotFound = "404";
    private final String eMailSubject = "RBPS Service issue identified - Action Required";
    private final String eMailSubOne = "RBPS Service issue identified ";
    private final String eMailSubTwo = "- Action Required";
    private static String lineSep = "\n\r";
    private static String envName = null;
    private Properties properties;
	
	/**
	 * 
	 */
	public EmailSender() {
		envName = SystemUtils.getProperty(SystemUtils.Key.ENVIRONMENT);
		properties = System.getProperties();
		session = Session.getDefaultInstance(properties, null);
	}
	
	/**
	 * @param fileName  -- define a property file name by user
	 * 
	 */
	public EmailSender(String fileName) {
		envName = SystemUtils.getProperty(SystemUtils.Key.ENVIRONMENT);
		propFileName = fileName;
		properties = System.getProperties();
		session = Session.getDefaultInstance(properties, null);		
	}


	/**
	 * @param to
	 * @param from
	 * @param subject
	 * @param mes
	 * @throws Exception
	 */
	public void emailSender(String to, String from, String CC, String subject, String mes) throws Exception {
		
		// validate input email addresses
		if (!validateEmailAddress(from)) {
			throw new AddressException("Bad sender address: " + from);
		}
		if (!validateEmailAddress(to)) {
			throw new AddressException("Bad reciever address: " + to);
		}
		if (!validateEmailAddress(CC)) {
			throw new AddressException("Bad reciever address: " + CC);
		}
		
		try {
			// setup SMTP host name instead of use the javax.email default value
			properties.put("mail.smtp.host", "smtp.va.gov");
			session = Session.getDefaultInstance(properties);
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(CC));
	
			message.setSubject(subject);
			message.setText(mes);
			cleanErr();
			// Send message
			Transport.send(message);
			LOGGER.debug("email sent successfully....");
		} catch (Exception e) {
			LOGGER.error("*** Failed to send an email: [" + e.getMessage() + "]");
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * @param subject
	 * @param mes
	 * @throws Exception
	 * get to, from and cclist from property file set by either default and user defined.
	 */
	public void emailSender(String subject, String mes) throws Exception {
		Properties props = getServices();
		String to = props.getProperty("EMAILTO");
		String from = props.getProperty("EMAILFROM");
		String ccList = props.getProperty("EMAILCC");
		if (to == null) {
			throw new AddressException ("Reciever's email is null");
		}
		if (from == null) {
			throw new AddressException ("Sender's email is null");
		}
		if (ccList == null) {
			throw new AddressException ("CC list's email is null");
		}
		
		// Get Ignore Email(s) list from rbps.properties
		String ignoreEmails = props.getProperty("ignore.emails");
		boolean isIgnoreEmail = false;
		
		// Check if msg contains any ignored email listed in rbps properties 
		if(ignoreEmails != null)
			isIgnoreEmail = Arrays.stream(ignoreEmails.split(" OR ")).anyMatch(ignoreEmailRootCause -> mes.contains(ignoreEmailRootCause));
		
		if(isIgnoreEmail){
			LOGGER.debug("email ignored successfully...." + mes);
			cleanErr();
		}
		else{
			emailSender(to, from, ccList, subject, mes);
		}
	}
	
	/**
	 * @throws Exception
	 */
	public void rbpsEmailSender(String processNum) throws Exception {
		/* get error messages if there is any */
		String err_msg = getErrorMessages();
		if ( err_msg == null || err_msg == "" ) {
			return;
		}
		
		// Reused existing method to send email which fetching the email addresses from rbps.properties file
		emailSender(getEmailSubject(), err_msg);
	}
	
    /**
     * @param emailAddress
     * @return boolean value
     */
	public boolean validateEmailAddress(String emailAddress) {
		boolean flag = true;
		String[] sArry = emailAddress.split(",");
		for (String s : sArry) {
			if (!emailAddressCheck(s)) {
				flag = false;
			}
		}
		return flag;
	}

    /**
     * @param emailAddress
     * @return boolean value
     */
    public boolean emailAddressCheck(String emailAddress) {
    	Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
    	Matcher regMatcher   = regexPattern.matcher(emailAddress);
    	 if(regMatcher.matches()) {
    		 return true;
    	 }
    	 return false;
    }
    
	/**
	 * @return Properties
	 */
	public Properties getServices() {
		Properties props = new Properties();
		try (InputStream in = getClass().getResourceAsStream("/" + propFileName + ".properties")) {
			if(in == null){
				throw new RuntimeException("Property File " + propFileName  + ".properties is not found");
			}
			props.load(in);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return props;
	}
	
	public static void addErrorMsg (String errStr, String rootCause) {
		LOGGER.debug("Exception message: " + errStr + " RootCause:" + rootCause);
		String detailMsg = checkServiceDown (errStr, rootCause);
		if ( detailMsg != null) {
			errStr = errStr.concat(detailMsg + " Action required" + lineSep + "Root Cause: " + rootCause);
			// LOGGER.debug("Sending email on exception: " + rootCause);
			err_msg[err_counter++] = envName + " " + errStr;
		}
	}
	public static void addInfoMsg (String errStr) {
		LOGGER.debug("Exception message: " + errStr );
		String detailMsg = errStr;
		if ( detailMsg != null) {
			errStr = errStr.concat(detailMsg );
			// LOGGER.debug("Sending email on exception: " + rootCause);
			err_msg[err_counter++] = envName + " " + errStr;
		}
	}
	
	/**
	 * method: cleanErr
	 * clean all context in the array
	 */
	public static void cleanErr () {
		err_msg = new String[err_arr_length];
		err_counter = 0;
	}
	
	private String getErrorMessages() {
		String error = "";
		for (String s: err_msg) {
			if (s == null) break;
			error = error.concat(s + lineSep);
		}
		return error;
	}
	
	public int getErrCount() {	
		return err_counter;
	}
	
	/*
	 * exam the rootCause.
	 */
	private static String checkServiceDown (String errStr, String rootCause) {
		if (rootCause == null || rootCause.isEmpty()) {
			return null;
		}
		if (rootCause.contains(pageNotFound)) {
			return "service is down";
		}
		if (rootCause.contains("You Do Not Have Claim Jurisdiction") ) {
			return "No Claim Jurisdiction to Process Award Action";
		}
		
		if (rootCause.contains("GUIE99998") ) {
			return "Oracle Deadlock Detected";
		}
		if (rootCause.toLowerCase().contains("service is down") || rootCause.contains("TPESYSTEM") || rootCause.contains("TPTODMIV") ) {
			return "service is down";
		}
		if (rootCause.toLowerCase().contains("service error") && !rootCause.contains("NULL") ) {
			return "service is down";
		}
		//check cutoff string
		if (errStr.toLowerCase().contains("cutoff")) {
			return "no record found for cutoff";
		}
		//check letter error
		if (rootCause.toLowerCase().contains("letter failed to generate")) {
			return "letter failed to generate";
		}
		return null;
	}
	
	private String getEmailSubject () {
		String sub = eMailSubOne + " in " + getEnvName() + " " + eMailSubTwo;
		return sub;
	}
	
	private String getEnvName() {
		if (envName == null) {
			envName = SystemUtils.getProperty(SystemUtils.Key.ENVIRONMENT);
		}
		return envName;
	}

}
