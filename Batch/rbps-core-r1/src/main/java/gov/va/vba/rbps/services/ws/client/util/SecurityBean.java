package gov.va.vba.rbps.services.ws.client.util;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Security", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
public class SecurityBean implements Serializable {

	private static final long serialVersionUID = -170317687865080587L;

	@XmlElement(name="UsernameToken", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
	UserTokenBean usertoken;

	//@XmlElement(name="VaServiceHeaders", namespace = "http://vbawebservices.vba.va.gov/vawss")
	ServiceHeaderBean serviceheader;

/*	public UserTokenBean getUsertoken() {
		return usertoken;
	}*/

	public void setUsertoken(final UserTokenBean usertoken) {
		this.usertoken = usertoken;
	}
	@XmlElement(name="VaServiceHeaders", namespace = "http://vbawebservices.vba.va.gov/vawss")
	public ServiceHeaderBean getServiceheader() {
		return serviceheader;
	}

	public void setServiceheader(final ServiceHeaderBean serviceheader) {
		this.serviceheader = serviceheader;
	}
}
