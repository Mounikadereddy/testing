
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.FindBenefitClaimResponse"%>
<%@ page import="gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.FindByDataSuppliedResponse"%>
<%@ page import="gov.va.vba.rbps.services.ws.client.handler.vonapp.UserInformationWSHandler"%>
<%@ page import="gov.va.vba.rbps.coreframework.dto.RbpsRepository"%>
<%@ page import="javax.xml.bind.JAXBElement" %>
<%@ page import="gov.va.vba.rbps.services.ws.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="javax.xml.bind.JAXBContext" %>
<%@ page import="javax.xml.bind.Marshaller" %>
<%@ page import="org.springframework.ws.soap.client.SoapFaultClientException"%>



<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>RBPS Services Facade V-2.0</title>

<script type="text/javascript">
function setProcId(procId) {
    procId = procId
    document.allData.init.value = "1";
}
</script>
</head>
<body>
<br/>

<%
 String procId = null;
%>

<%
    out.println("Date: [" + new Date() + "] Starting RBPS Services... <br/>");
    WebApplicationContext webAppCtx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()); 
%>

<form  method="get" name="userInfo" action="rbps-userinfo.jsp">
<font color="##FF0000">Get Claim Data for a specific Proc ID: </font>
<input type="hidden" name="init"/>
<br>
<font color="##FF0000">To get the latest Claim in the queue, leave the Proc ID field blank: </font>
<br>
    <br/>
    <div id="procId">
    <font color="#FF0000">Optional field: </font>
    <br/>
    Proc ID: <input type="input" name="procId">
    <br/>
    </div>
    <br/>
        <input type="submit" onclick="setProcId()"/>
    <br/>
</form>

<%
if( request.getParameter("init") != null ) {
    long            time            = System.currentTimeMillis();
    Marshaller      marshaller      = null;
    JAXBContext     jaxbContext     = null;
    RbpsRepository  repo            = null;

    jaxbContext = JAXBContext.newInstance(FindByDataSuppliedResponse.class);
    marshaller  = jaxbContext.createMarshaller();
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

    UserInformationWSHandler            userInformationWSHandler;
    FindByDataSuppliedResponse          userInformationResponse;

    procId                      = request.getParameter("procId");
    userInformationWSHandler    = (UserInformationWSHandler) webAppCtx.getBean("userInformationWSHandler");
    repo                        = (RbpsRepository) webAppCtx.getBean("repository");
    userInformationResponse     = userInformationWSHandler.getFindByDataSuppliedResponse(repo, procId, null, null);

    out.print("<br/> Request for procId " + procId + " took " + (System.currentTimeMillis() - time) + "  ms <br/>");
%>

<font color="##FF0000">Response: </font>
  <xmp>
  <%
    marshaller.marshal(userInformationResponse, out);
  %>
  </xmp>

<% } %>



</body>
</html>
