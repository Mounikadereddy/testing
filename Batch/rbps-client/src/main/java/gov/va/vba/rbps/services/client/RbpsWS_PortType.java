/**
 * RbpsWS_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package gov.va.vba.rbps.services.client;

public interface RbpsWS_PortType extends java.rmi.Remote {
    public java.lang.String processRbpsAmendDependency(java.lang.String currentProcess, java.lang.String totalProcessCount) throws java.rmi.RemoteException, gov.va.vba.rbps.services.client.RbpsFaultBean;
    public java.lang.String processRbpsByProcId(java.lang.String procId) throws java.rmi.RemoteException, gov.va.vba.rbps.services.client.RbpsFaultBean;
}
