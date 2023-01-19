
<%@ page import="java.util.*"%>
<%@ page
	import="org.springframework.context.support.ClassPathXmlApplicationContext"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="gov.va.vba.rbps.services.impl.RbpsServiceFacade"%>
<%@ page import="gov.va.vba.rbps.coreframework.util.CommonUtils"%>
<%@ page import="gov.va.vba.rbps.coreframework.dto.RbpsRepository"%>
<%@ page
	import="gov.va.vba.rbps.services.interfaces.RbpsServiceInterface"%>
<%@ page
	import="gov.va.vba.rbps.services.ws.client.mapping.rbps.ProcessRbpsAmendDependency"%>
<%@ page
	import="gov.va.vba.rbps.services.ws.client.mapping.rbps.ProcessRbpsAmendDependencyResponse"%>
<%@ page import="javax.xml.bind.JAXBElement"%>
<%@ page import="gov.va.vba.rbps.services.ws.*"%>
<%@ page import="java.lang.reflect.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.StringUtils"%>
<%@ page import="javax.xml.bind.JAXBContext"%>
<%@ page import="javax.xml.bind.Marshaller"%>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome to RBPS (Rules Based Processing System)</title>

<script type="text/javascript">
function turnOnRbps() {
    if (document.onOffForm.userName.value == ""){
      alert("please enter user name");
      document.onOffForm.userName.setFocus();
      return;
    }else{
 
    document.onOffForm.init.value = "1";
    document.onOffForm.onOff.value = "Y";
    document.onOffForm.submit();
   }
}
function turnOffRbps() {
      if (document.onOffForm.userName.value == ""){
      alert("please enter user name");
      document.onOffForm.userName.setFocus();
      return;
    }else{
     document.onOffForm.init.value = "1";
      document.onOffForm.onOff.value = "N";
      document.onOffForm.submit();
   }
}
</script>
</head>
<body>
	<%
     WebApplicationContext webAppCtx = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext()); 
      RbpsServiceFacade rbpsServiceFacade;
    rbpsServiceFacade = (RbpsServiceFacade) webAppCtx.getBean("rbpsServiceFacade");
%>

	<br />
	<form method="get" name="onOffForm" action="rbps-on-off.jsp">
		<input type="hidden" name="init" /> <input type="hidden" name="onOff" />
		<%
	long     time;
	String isRbpsOn="";
	 String onOff = null;
	if( request.getParameter("init") == null ){
    
     time = System.currentTimeMillis();
    isRbpsOn = rbpsServiceFacade.isRbpsOn();
 
 }
  out.println("Date: [" + new Date() + "] <br><br><br>");
 if(isRbpsOn.equalsIgnoreCase("Y")){
    out.println("RBPS is ON, do you want to turn RBPS OFF? <br><br><br>");
    out.println("User Name: <input type='input' name='userName'>");
    out.println("<input type='button' id='offSwtich' value='Turn RBPS OFF' onclick='turnOffRbps()' /> ");
    
   } %>


		<% if(isRbpsOn.equalsIgnoreCase("N")){  
    out.println("RBPS is OFF, do you want to turn RBPS ON?<br><br><br>");
    out.println("User Name: <input type='input' name='userName'>");
   
     out.println(" <input type='button' id='onSwtich' value='Turn RBPS ON' onclick='turnOnRbps()' />");
   }
%>



	</form>

	<% 
if( request.getParameter("init") != null ){
	   time = System.currentTimeMillis();
     onOff = request.getParameter("onOff")+"|"+request.getParameter("userName");
     String status = rbpsServiceFacade.tunOnOffRbps(onOff);
     out.println(status);
  
   }
  
%>
	
</body>
</html>
