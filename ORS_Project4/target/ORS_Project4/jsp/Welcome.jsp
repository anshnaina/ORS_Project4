<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rays Technologies</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
</head>
<body>
	<form action = "<%=ORSView.WELCOME_CTL%>">

<%@include file="Header.jsp"%>
  <center>
		<img alt="Online Result System" src="<%=ORSView.APP_CONTEXT%>/img/ORSLogo.png" height="300" width="600">
		<h2 style="font-family: monospace;"><b>Welcome to ORS</b></h2>
	</center>
<%@include file="Footer.jsp"%>
	</form>
</body>
</html>