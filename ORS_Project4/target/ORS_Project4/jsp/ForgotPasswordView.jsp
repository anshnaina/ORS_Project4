<%@page import="com.sunilOS.ORSProject4.controller.ForgotPasswordCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Rays Technologies</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
	<link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/FormView.css">
</head>
<body>
<%@include file="Header.jsp"%>
<form  action="<%=ORSView.FORGOT_PASSWORD_CTL%>" method = "post">
<center>

<h2 class="header"><u>Forgot Password ?</u></h2>
<br>
<h3><font color="green"><%=ServletUtility.getSuccessMessage(request) %></font> </h3>
<h3><font color="red"><%=ServletUtility.getErrorMessage(request) %></font> </h3>

<table>
<label>No worries! We'll send your password</label><br>
<tr>	
		<td>
		 <input type = "email" name="email" placeholder="Enter Email Id" value = "<%=ServletUtility.getParameter("email" , request)%>" class="tbox">
	</td>
	<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font>
	</td>
</tr>
<tr>	
		<td align="center">
		 <input type = "submit" name="operation" value="<%=ForgotPasswordCtl.OP_GO%>" class="srpnbtn">
	</td>
</tr>
</table>
</center>
</form>
<%@include file="Footer.jsp"%>
</body>
</html>