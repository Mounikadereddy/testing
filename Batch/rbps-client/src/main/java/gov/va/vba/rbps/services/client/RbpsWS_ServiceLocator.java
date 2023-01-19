/**
 * RbpsWS_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.va.vba.rbps.services.client;

public class RbpsWS_ServiceLocator extends org.apache.axis.client.Service implements gov.va.vba.rbps.services.client.RbpsWS_Service {

    public RbpsWS_ServiceLocator() {
    }


    public RbpsWS_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public RbpsWS_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for RbpsWSPort
    private java.lang.String RbpsWSPort_address = "http://localhost:7001/RbpsServices/RbpsWS";

    public java.lang.String getRbpsWSPortAddress() {
        return RbpsWSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String RbpsWSPortWSDDServiceName = "RbpsWSPort";

    public java.lang.String getRbpsWSPortWSDDServiceName() {
        return RbpsWSPortWSDDServiceName;
    }

    public void setRbpsWSPortWSDDServiceName(java.lang.String name) {
        RbpsWSPortWSDDServiceName = name;
    }

    public gov.va.vba.rbps.services.client.RbpsWS_PortType getRbpsWSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(RbpsWSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getRbpsWSPort(endpoint);
    }

    public gov.va.vba.rbps.services.client.RbpsWS_PortType getRbpsWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            gov.va.vba.rbps.services.client.RbpsWSPortBindingStub _stub = new gov.va.vba.rbps.services.client.RbpsWSPortBindingStub(portAddress, this);
            _stub.setPortName(getRbpsWSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setRbpsWSPortEndpointAddress(java.lang.String address) {
        RbpsWSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (gov.va.vba.rbps.services.client.RbpsWS_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                gov.va.vba.rbps.services.client.RbpsWSPortBindingStub _stub = new gov.va.vba.rbps.services.client.RbpsWSPortBindingStub(new java.net.URL(RbpsWSPort_address), this);
                _stub.setPortName(getRbpsWSPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("RbpsWSPort".equals(inputPortName)) {
            return getRbpsWSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws.services.rbps.vba.va.gov/", "RbpsWS");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws.services.rbps.vba.va.gov/", "RbpsWSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("RbpsWSPort".equals(portName)) {
            setRbpsWSPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
