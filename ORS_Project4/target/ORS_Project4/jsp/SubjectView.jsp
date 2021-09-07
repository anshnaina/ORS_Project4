<%@page import="com.sunilOS.ORSProject4.controller.SubjectCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<title>Rays Technologies</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
   <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
   <link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/FormView.css">
   </head>
<body>
<form action="<%=ORSView.SUBJECT_CTL%>" method="post">
<%@include file="Header.jsp"%>
<jsp:useBean id = "bean" class = "com.sunilOS.ORSProject4.bean.SubjectBean" scope = "request"></jsp:useBean>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>"><%
List courseList = (List)request.getAttribute("courseList");
%>
<center>
			<% 
			if(DataUtility.getLong(request.getParameter("id")) == 0)
			{
			%>
				<h2 class="header"><u>Add Subject</u></h2>
			<%
			}
			else
			{
			%>
				<h2 class="header"><u>Update Subject</u></h2>
			<%
			}
			%>

<br>
	
	<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
	<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3> 
					
<table>
	<tr>
		<td align="center">Course Name*:</td>
		<td align="left"><%=HTMLUtility.getList("courseId" , String.valueOf(bean.getCourseId()) , courseList , null)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
	</tr>
	
	<tr>	
		<td align="center">Subject Name*: </td>
		<td align="left"><input type="text" name="subjectName" value = "<%=DataUtility.getStringData(bean.getSubjectName())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectName", request)%></font></td>
	</tr>
	
	<tr>	
		<td align="center">Description*: </td>
		<td align="left"><input type="text" name="description" value = "<%=DataUtility.getStringData(bean.getDescription())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font></td>
	</tr>
		
	<tr>
	<td colspan="2" align="center">
	
	<input type="submit" name="operation" value="<%=SubjectCtl.OP_SAVE%>" class="srpnbtn">
	
	&nbsp;
	
	<% 
			if(DataUtility.getLong(request.getParameter("id"))>0)
			{
				%>
				<input type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL%>" class="srpnbtn">
				
				<%
			}
			else
			{
				%>
				<input type="submit" name="operation" value="<%=SubjectCtl.OP_RESET%>" class="srpnbtn">
				<%
			}
		%>
		
	</td>
	</tr>
</table>	
</center>

<%@include file="Footer.jsp"%>

</form>
</body>
</html>