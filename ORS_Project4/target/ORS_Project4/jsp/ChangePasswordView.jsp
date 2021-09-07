<%@page import="com.sunilOS.ORSProject4.controller.ChangePasswordCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
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
<form action = "<%=ORSView.CHANGE_PASSWORD_CTL%>" method = "post">
<%@include file="Header.jsp"%>
<jsp:useBean id="bean" class="com.sunilOS.ORSProject4.bean.UserBean" scope="request"></jsp:useBean>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>"><center>

		<h2 class="header"><u>Change Password</u></h2>
	<br>
		<h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>
		<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3> 
	
<table>	
	<tr>
		<th align="center">Old Password*:</th>
		<td align="left"><input type="text" name="oldPassword" value = "<%=DataUtility.getStringData(request.getParameter("oldPassword"))%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("oldPassword", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">New Password*:</th>
		<td align="left"><input type="text" name="newPassword" value = "<%=DataUtility.getStringData(request.getParameter("newPassword"))%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("newPassword", request)%></font></td>
	</tr>
	
		<tr>
		<th align="center">Confirm Password*:</th>
		<td align="left"><input type="text" name="confirmPassword" value = "<%=DataUtility.getStringData(request.getParameter("confirmPassword"))%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
	</tr>
	
	<tr>
	<td  colspan="2" align="center">
	<input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_SAVE %>" class="srpnbtn"> &nbsp;
	<input type="submit" name="operation" value="<%=ChangePasswordCtl.OP_RESET%>" class="srpnbtn">
	</td>
	</tr>
	
</table>
</center>

<%@include file="Footer.jsp"%>
</form>
</body>
</html>