@XmlSchema(namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",  
	    xmlns = {   
	        @XmlNs(namespaceURI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", prefix = "wsse"),  
	        @XmlNs(namespaceURI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", prefix = "wsu"),
	        @XmlNs(namespaceURI = "http://vbawebservices.vba.va.gov/vawss", prefix = "vaws")
	    },  
	    elementFormDefault = javax.xml.bind.annotation.XmlNsForm.QUALIFIED)  
	  
	package gov.va.vba.rbps.services.ws.client.util;  
	  
	import javax.xml.bind.annotation.XmlNs;  
	import javax.xml.bind.annotation.XmlSchema;