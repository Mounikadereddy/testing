
<%@ page import="java.util.*" %>
<%@ page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="gov.va.vba.rbps.services.impl.RbpsServiceFacade"%>
<%@ page import="gov.va.vba.rbps.coreframework.util.CommonUtils"%>
<%@ page import="gov.va.vba.rbps.coreframework.dto.RbpsRepository"%>
<%@ page import="gov.va.vba.rbps.services.interfaces.RbpsServiceInterface"%>
<%@ page import="gov.va.vba.rbps.services.ws.client.mapping.rbps.ProcessRbpsAmendDependency"%>
<%@ page import="gov.va.vba.rbps.services.ws.client.mapping.rbps.ProcessRbpsAmendDependencyResponse"%>
<%@ page import="javax.xml.bind.JAXBElement" %>
<%@ page import="gov.va.vba.rbps.services.ws.*" %>
<%@ page import="java.lang.reflect.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="javax.xml.bind.JAXBContext" %>
<%@ page import="javax.xml.bind.Marshaller" %>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to RBPS (Rules Based Processing System)</title>

<script type="text/javascript">
function setInit() {
    document.allData.init.value = "1";
}
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
    out.println("Date: [" + new Date() + "] Starting RBPS Services... <br>");
    WebApplicationContext webAppCtx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()); 
%>


<form  method="get" name="execute" action="rbps-execute.jsp">
<input type="hidden" name="init"/>
<br/>
<font color="##FF0000">Run RBPS </font>
    <br/>
    <div id="procId">
    <font color="#FF0000">Optional field: </font>
    <br/>
    Proc ID: <input type="input" name="procId">
    <br/>
    </div>
    <br/>
    <input type="submit" value="Run Above Proc ID" onclick="setProcId()"/>
    <br/>
    <br/>
    <input type="submit" value="Run With Out Proc ID" onclick="setInit()"/>
    <br/>
</form>

<%
if( request.getParameter("init") != null ){

    long     time = System.currentTimeMillis();
    procId = request.getParameter("procId");

    RbpsServiceFacade rbpsServiceFacade;
    rbpsServiceFacade = (RbpsServiceFacade) webAppCtx.getBean("rbpsServiceFacade");
    String journal = rbpsServiceFacade.execute(procId, new RbpsRepository());

    out.print("<br/> Request took " + (System.currentTimeMillis() - time) + "ms <br/>");
%>

<font color="##FF0000">Response: </font>
    <xmp>
    <%
        out.print(journal);
    %>
    </xmp>

<% } %>

</body>
</html>
