<%@ page import="gov.va.vba.rbps.coreframework.util.RbpsConstants"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head><title>RBPS Testing Tools</title></head>

<body>
<p>
<img src="va_seal.jpg" alt="RBPS Testing Tools"/> 
Welcome to RBPS (Rules Based Processing System). 

<% out.print( "Version: " + RbpsConstants.VERSION ); %>
</p>
</body>

</html>
