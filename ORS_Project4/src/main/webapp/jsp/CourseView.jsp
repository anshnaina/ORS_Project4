<%@page import="com.sunilOS.ORSProject4.controller.CourseCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.ORSView"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<body>
<form action="<%=ORSView.COURSE_CTL%>" method="post">
<%@include file="Header.jsp"%>
<jsp:useBean id = "bean" class = "com.sunilOS.ORSProject4.bean.CourseBean" scope = "request"></jsp:useBean>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>">
<center>
			<% 
			if(DataUtility.getLong(request.getParameter("id")) == 0)
			{
			%>
				<h2 class="header"><u>Add Course</u></h2>
			<%
			}
			else
			{
			%>
				<h2 class="header"><u>Update Course</u></h2>
			<%
			}
			%>

<br>
	
	<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
	<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3> 
					
<table>
	<tr>
		<td align="center">Course Name*:</td>
		<td align="left"><input type="text" name="courseName" value = "<%=DataUtility.getStringData(bean.getCourseName())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseName", request)%></font></td>
	</tr>
	
	<tr>	
		<td align="center">Course Description*: </td>
		<td align="left"><input type="text" name="courseDescription" value = "<%=DataUtility.getStringData(bean.getCourseDescription())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseDescription", request)%></font></td>
	</tr>
	
	<tr>	
		<td align="center">Course Duration*:</td>
					<td>
						<%
						LinkedHashMap<String, String> cDurationMap = new LinkedHashMap<String, String>(); 
					
						cDurationMap.put("1 Year", "1 Year");
						cDurationMap.put("2 Years", "2 Years");
						cDurationMap.put("3 Years", "3 Years");
						cDurationMap.put("4 Years", "4 Years");
						
						%>
							<%=HTMLUtility.getList("courseDuration", bean.getCourseDuration(), cDurationMap)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseDuration", request)%></font></td>
	</tr>
	
	<tr>
	<td colspan="2" align="center">
	
	<input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>" class="srpnbtn">
	
	&nbsp;
	
	<% 
			if(DataUtility.getLong(request.getParameter("id"))>0)
			{
				%>
				<input type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>" class="srpnbtn">
				
				<%
			}
			else
			{
				%>
				<input type="submit" name="operation" value="<%=CourseCtl.OP_RESET%>" class="srpnbtn">
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