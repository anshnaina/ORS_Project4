<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.StudentCtl"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<title>Rays Technologies</title>
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
    <link href= "<%=ORSView.APP_CONTEXT%>/css/FormView.css" rel="stylesheet">
</head>
<body>

<%@include file="Header.jsp"%>
<form action="<%=ORSView.STUDENT_CTL%>" method="post">
<jsp:useBean id = "bean" class = "com.sunilOS.ORSProject4.bean.StudentBean" scope = "request"></jsp:useBean>
<%
	List collegeList = (List)request.getAttribute("collegeList");
%>
<center>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>">		
		<% 
			if(DataUtility.getLong(request.getParameter("id")) == 0)
			{
				%>
				<h2 class="header"><u>Add Student</u></h2>
				<%
			}
			else
			{
				%>
				<h2 class="header"><u>Update Student</u></h2>
				<%
			}
		%>
		
		<br>
		
		<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
		<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
<table>	
	<tr>	
		<th align="center">College Name*:</th>
		<td align="left"><%=HTMLUtility.getList("collegeId", String.valueOf(bean.getCollegeId()) , collegeList , null)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
	</tr>
		
	<tr>
		<th align="center">First Name*:</th>
		<td align="left"><input type="text" name="firstName" value="<%=DataUtility.getStringData(bean.getFirstName())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
	</tr>
	
	<tr>	
		<th align="center">Last Name*:</th>
		<td align="left"><input type="text" name="lastName" value="<%=DataUtility.getStringData(bean.getLastName())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
	</tr>
	
	<tr>	
		<th align="center">Date Of Birth:</th>
		<td align="left"><input type="text" name="dob" id = "datepicker" value="<%=DataUtility.getDateToString(bean.getDob())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>
	</tr>
	
	<tr>	
		<th align="center">Mobile:</th>
		<td align="left"><input type="tel" name="mobileNo" value="<%=DataUtility.getStringData(bean.getMobileNo())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Email ID*:</th>
		<td align="left"><input type="text" name="email" value="<%=DataUtility.getStringData(bean.getEmail())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font></td>
	</tr>
	
	<tr>
	<td  colspan="2" align="center">
	<input type="submit" class="srpnbtn" name="operation" value=<%=StudentCtl.OP_SAVE%>>
	&nbsp;
	
	<% 
			if(DataUtility.getLong(request.getParameter("id"))>0)
			{
				%>
				<input type="submit" name="operation" value="<%=StudentCtl.OP_CANCEL%>" class="srpnbtn">
				
				<%
			}
			else
			{
				%>
				<input type="submit" name="operation" value="<%=StudentCtl.OP_RESET%>" class="srpnbtn">
				<%
			}
		%>
	
	</td>
	</tr>
	
	
</table>
</form>
</center>
<%@include file="Footer.jsp"%>	
</body>
</html>