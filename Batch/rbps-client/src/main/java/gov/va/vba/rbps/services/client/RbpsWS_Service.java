/**
 * RbpsWS_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.va.vba.rbps.services.client;

public interface RbpsWS_Service extends javax.xml.rpc.Service {
    public java.lang.String getRbpsWSPortAddress();

    public gov.va.vba.rbps.services.client.RbpsWS_PortType getRbpsWSPort() throws javax.xml.rpc.ServiceException;

    public gov.va.vba.rbps.services.client.RbpsWS_PortType getRbpsWSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
