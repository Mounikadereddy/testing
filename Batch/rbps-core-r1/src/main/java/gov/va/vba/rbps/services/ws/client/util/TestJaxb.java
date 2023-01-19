package gov.va.vba.rbps.services.ws.client.util;

import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

//@XmlRootElement(name="Security")
//@XmlRootElement(name = "Security", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
public class TestJaxb implements Serializable {

	private static final long serialVersionUID = 1L;

	private String password;
	private String UsernameToken;
	private static String s1 = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";

	public String getPassword() {
		return password;
	}

	public String getUsernameToken() {
		return UsernameToken;
	}

	public void setUsernameToken(String usernameToken) {
		UsernameToken = usernameToken;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TestJaxb() {
	}

	public static void main(String[] args) {  //void doIt()
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(SecurityBean.class);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		    
		    SecurityBean securityheader = new SecurityBean();
		    
		    UserTokenBean usertoken = new UserTokenBean();
		    usertoken.setUserName("281CEASL");
		    usertoken.setPassword("Buda110!");
		    securityheader.setUsertoken(usertoken);
		    
		    ServiceHeaderBean serviceheader = new ServiceHeaderBean();
		    serviceheader.setAppName("VBMS");
		    serviceheader.setClientMachine("10.224.104.174");
		    serviceheader.setStationId("281");
		    securityheader.setServiceheader(serviceheader);

		    marshaller.marshal(securityheader, System.out);
//Type="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText">Buda110!</wsse:Password
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
