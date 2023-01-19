package gov.va.vba.rbps.services.client;

public class RbpsWSProxy implements gov.va.vba.rbps.services.client.RbpsWS_PortType {
  private String _endpoint = null;
  private gov.va.vba.rbps.services.client.RbpsWS_PortType rbpsWS_PortType = null;
  
  public RbpsWSProxy() {
    _initRbpsWSProxy();
  }
  
  public RbpsWSProxy(String endpoint) {
    _endpoint = endpoint;
    _initRbpsWSProxy();
  }
  
  private void _initRbpsWSProxy() {
    try {
      rbpsWS_PortType = (new gov.va.vba.rbps.services.client.RbpsWS_ServiceLocator()).getRbpsWSPort();
      if (rbpsWS_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)rbpsWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)rbpsWS_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (rbpsWS_PortType != null)
      ((javax.xml.rpc.Stub)rbpsWS_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public gov.va.vba.rbps.services.client.RbpsWS_PortType getRbpsWS_PortType() {
    if (rbpsWS_PortType == null)
      _initRbpsWSProxy();
    return rbpsWS_PortType;
  }
  
  public java.lang.String processRbpsAmendDependency(java.lang.String currentProcess, java.lang.String totalProcessCount) throws java.rmi.RemoteException, gov.va.vba.rbps.services.client.RbpsFaultBean{
    if (rbpsWS_PortType == null)
      _initRbpsWSProxy();
    return rbpsWS_PortType.processRbpsAmendDependency(currentProcess, totalProcessCount);
  }
  
  public java.lang.String processRbpsByProcId(java.lang.String procId) throws java.rmi.RemoteException, gov.va.vba.rbps.services.client.RbpsFaultBean{
    if (rbpsWS_PortType == null)
      _initRbpsWSProxy();
    return rbpsWS_PortType.processRbpsByProcId(procId);
  }
  
  
}