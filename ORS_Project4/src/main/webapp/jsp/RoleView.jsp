<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.RoleCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>

<!DOCTYPE html>
<html>
<head>
<title>Rays Technologies</title>
  <link rel="stylesheet" href=" ">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
    <link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/FormView.css">
</head>
<body>
<form action="<%=ORSView.ROLE_CTL%>" method = "post">
<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class = "com.sunilOS.ORSProject4.bean.RoleBean" scope = "request"></jsp:useBean>

<center>
		<%	if ((DataUtility.getLong(request.getParameter("id"))) == 0)
		{
		%>
			<h2 class="header"><u>Add Role</u></h2>
		<% }
			else
		{
		%>
			<h2 class="header"><u>Update Role</u></h2>
		<% }
		%>
	
	<br>
	<h3 align="center">
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
			<h3 align="center">
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>">			
<table>	
	<tr>
		<th align="center">Role Name*:</th>
		<td align="left"><input type="text" name="roleName" value = "<%=DataUtility.getStringData(bean.getRoleName())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("roleName", request)%></font></td>
	</tr>
	
	<tr>	
		<th align="center">Description*:</th>
		<td align="left"><input type="text" name="description" value = "<%=DataUtility.getStringData(bean.getDescription())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font></td>
	</tr>
	<tr>
	<td  colspan="2" align="center">
	<input type="submit" class="srpnbtn" name="operation" value="<%=RoleCtl.OP_SAVE%>">
	&nbsp;
	<% if(bean.getId() > 0)
	{
	%>	
	<input type="submit" class="srpnbtn" name="operation" value="<%=RoleCtl.OP_CANCEL%>">
	<% } 
	else
	{%>
	<input type="submit" class="srpnbtn" name="operation" value="<%=RoleCtl.OP_RESET%>">
	<% } %>
	</td>
	</tr>
</table>
</center>
</form>
<%@include file="Footer.jsp"%>
</body>
</html>