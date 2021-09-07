<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.LoginCtl"%>
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
<style>
 
body{font-family: cursive;}
	
.login-box{border: 4px; width: 400px;}
			
.tbox{width: 100%; height: 40px; padding-left: 20px; border-radius: 50px; border:2px solid; 
margin-bottom: 15px; outline: none; font-size: medium; transition: 0.8s; color: gray;}
	 
.tbox:focus{border: 2px solid black; transition: 0.8s; color: black;}
	
.login{margin: 10px 20px;}
	
.btn{width: 80%; border-radius: 50px; margin-top: 10px; margin-bottom: 10px; background-color: red; 
color: black; font-size: medium; outline: none; transition: 0.6s; font: bolder;}

.btn:hover{background-color: yellow; transition: 0.6s;}
	
.forgot-box{text-align: center;}
	
.link{color: black;}

</style>

</head>
<body>
<%@include file="Header.jsp"%>
<form action="<%=ORSView.LOGIN_CTL%>" method="post">
<jsp:useBean id="bean" class="com.sunilOS.ORSProject4.bean.UserBean" scope="request"></jsp:useBean>
	<input type ="hidden" name="id" value="<%=bean.getId()%>">
	<input type ="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
	<input type ="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
	<input type ="hidden" name="createdDateTime" value="<%=DataUtility.getTimestamp(bean.getCreatedDateTime())%>">
	<input type ="hidden" name="modifiedDateTime" value="<%=DataUtility.getTimestamp(bean.getModifiedDateTime())%>">
	
<div class="container-fluid">
<center>
	<table class = "login-box">
		
		
		<img style="margin-top: 80px" src = "<%=ORSView.APP_CONTEXT%>/img/main_logo_RAYS.png" alt = "Rays Technologies" height = "100">
    		 
    	<h4 align="center">
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h4>
			<h4 align="center">
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h4>
			<%-- <%
					String uri=(String)request.getAttribute("uri");
			
			%> --%>
	
	
			<tr>
			<td>
			<input type = "text" name = "email" placeholder = "Enter email"  value="<%=DataUtility.getStringData(bean.getEmail())%>" class = "tbox"></td>
			<td style="position: fixed;">
			<font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
			</td>
			</tr>
				
			<tr>
			<td>
			<input type = "password" name = "password" placeholder = "Enter password"  value="<%=DataUtility.getStringData(bean.getPassword())%>" class = "tbox"></td>
			<td style="position: fixed;">
			<font color="red"><%=ServletUtility.getErrorMessage("password", request)%></font>
			</td>
			</tr>
			
			<tr>
			<td align = "center">
			<input type = "submit" value = "<%=LoginCtl.OP_SIGN_IN%>" name = "operation" class="btn">
			</td>
			</tr>
			
			<br>		
			<tr>

			<td class="forgot-box">
			<a href="<%=ORSView.FORGOT_PASSWORD_CTL%>" class="link">Forgot Password ?</a>
			</td>
		</table>
   	
</center>
   	</div>
<%-- <input type="hidden" name="uri" value="<%=uri%>">
 --%><%@include file="Footer.jsp"%>
</form>
</body>
</html>