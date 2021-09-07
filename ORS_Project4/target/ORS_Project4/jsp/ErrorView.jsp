<%@page import="com.sunilOS.ORSProject4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>OOPS!</title>
	<link href= "<%=ORSView.APP_CONTEXT%>/img/warning_logo.png" rel= "icon" type="icon">
</head>
<body>
	<br>
	<br>
	<br>
	<div align="center">

		<h1>OOPS! something went wrong</h1>
		<font style="color: red; size: 25px;">Requested resource is not available </font>
		<div style="width: 25%; text-align: justify;">
			<h3>Try: </h3>
			<ul>
				<li>check the network cables,modem,and router</li>
				<li>reconnect to Wi-Fi</li>
			</ul>
		</div>
	</div>
	<h4 align="center">
		<font size="5px" color="black"> <a href="<%=ORSView.WELCOME_CTL%>"
			style="color: deepskyblue;">*Please click here to go back*</a></font>
	</h4>

</body>
</html>