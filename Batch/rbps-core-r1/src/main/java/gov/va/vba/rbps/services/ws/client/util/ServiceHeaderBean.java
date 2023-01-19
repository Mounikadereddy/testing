package gov.va.vba.rbps.services.ws.client.util;

import java.io.Serializable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Security", namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd")
public class ServiceHeaderBean implements Serializable {

	private static final long serialVersionUID = -170317687865080587L;
	String clientMachine;
	
	@XmlElement(name="CLIENT_MACHINE", namespace = "http://vbawebservices.vba.va.gov/vawss")
	public String getClientMachine() {
		return clientMachine;
	}
	public void setClientMachine(String clientMachine) {
		this.clientMachine = clientMachine;
	}
	
	@XmlElement(name="STN_ID", namespace = "http://vbawebservices.vba.va.gov/vawss")
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	
	@XmlElement(name="applicationName", namespace = "http://vbawebservices.vba.va.gov/vawss")
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	String stationId;
	String appName;
}
