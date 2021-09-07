<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.controller.TimeTableCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.HashMap"%>
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
   <link rel="stylesheet" href="<%=ORSView.APP_CONTEXT%>/css/FormView.css">

   </head>
<body>
<form action="<%=ORSView.TIMETABLE_CTL%>" method="post">
<%@include file = "Header.jsp"%>
<jsp:useBean id = "bean" class = "com.sunilOS.ORSProject4.bean.TimeTableBean" scope = "request"></jsp:useBean>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>">

<%
	List courseList = (List) request.getAttribute("courseList");
	List subjectList= (List) request.getAttribute("subjectList");
%>
<center>
			<% 
			if(DataUtility.getLong(request.getParameter("id")) == 0)
			{
			%>
				<h2 class="header"><u>Add TimeTable</u></h2>
			<%
			}
			else
			{
			%>
				<h2 class="header"><u>Update TimeTable</u></h2>
			<%
			}
			%>

<br>
	
	<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
	<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3> 
		
					
<table>
	<tr>
		<th align="center">Course Name*:</th>
		<td align="left"><%=HTMLUtility.getList("courseId" , String.valueOf(bean.getCourseId()) , courseList , null)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
	</tr>
	
	<tr>
			<th align="center">Subject Name*</th>
			<td align="left"><%=HTMLUtility.getList("subjectId" , String.valueOf(bean.getSubjectId()) , subjectList , null)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("subjectId", request)%></font>
			</td>
	</tr>
	<tr>
			<th align="center">Semester*</th>
			<td align="left"><%
						HashMap <String, String> SemMap = new HashMap();
						SemMap.put("1", "1");
						SemMap.put("2", "2");
						SemMap.put("3", "3");
						SemMap.put("4", "4");
						SemMap.put("5", "5");
						SemMap.put("6", "6");
						SemMap.put("7", "7");
						SemMap.put("8", "8");
						SemMap.put("9", "9");
						SemMap.put("10", "10");
						%>
						<%=HTMLUtility.getList("semesterId", bean.getSemester(), SemMap)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("semesterId", request)%></font>
			</td>
	</tr>
	<tr>
			<th align="center">Exam Date*</th>
			<td align="left"><input type = "text" name = "examDate" value = "<%=DataUtility.getDateToString(bean.getExamDate())%>" class = "datepicker tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("examDate", request)%></font></td>
	</tr>
	<tr>
					<th align="center">Exam Time *</th>
					<td align="left">
						<%
							LinkedHashMap<String, String> examTimeMap = new LinkedHashMap<String, String>();
						
							examTimeMap.put("07:00AM-10:00AM", "07:00AM-10:00AM");
							examTimeMap.put("08:00AM-11:00AM", "08:00AM-11:00AM");
							examTimeMap.put("09:00AM-12:00PM", "09:00AM-12:00PM");
							examTimeMap.put("10:00AM-01:00PM", "10:00AM-01:00PM");
							examTimeMap.put("11:00AM-02:00PM", "11:00AM-02:00PM");
							examTimeMap.put("12:00PM-03:00PM", "12:00PM-03:00PM");
							examTimeMap.put("01:00PM-04:00PM", "01:00PM-04:00PM");
							examTimeMap.put("02:00PM-05:00PM", "02:00PM-05:00PM");
						%> <%=HTMLUtility.getList("examTime", bean.getExamTime(), examTimeMap)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("examTime", request)%></font></td>
				</tr>
				
				<tr>
			<th align="center">Description</th>
			<td align="left"><input type = "text" name = "description" value = "<%=DataUtility.getStringData(bean.getDescription())%>" class = "tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font>
			</td>
	</tr>
	
	<tr>
	<td colspan="2" align="center">
	
	<input type="submit" name="operation" value="<%=TimeTableCtl.OP_SAVE%>" class="srpnbtn">
	
	&nbsp;
	
	<% 
			if(DataUtility.getLong(request.getParameter("id"))>0)
			{
				%>
				<input type="submit" name="operation" value="<%=TimeTableCtl.OP_CANCEL%>" class="srpnbtn">
				
				<%
			}
			else
			{
				%>
				<input type="submit" name="operation" value="<%=TimeTableCtl.OP_RESET%>" class="srpnbtn">
				<%
			}
		%>
		
	</td>
	</tr>
</table>	
</center>
<%@include file = "Footer.jsp"%>
</form>
</body>
</html>