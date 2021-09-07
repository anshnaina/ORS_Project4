<%@page import="com.sunilOS.ORSProject4.controller.FacultyCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.bean.CollegeBean"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.ORSView"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.sunilOS.ORSProject4.bean.CollegeBean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
<form action = "<%=ORSView.FACULTY_CTL%>" method = "post">
<%@include file = "Header.jsp" %>
<jsp:useBean id = "bean" class = "com.sunilOS.ORSProject4.bean.FacultyBean" scope = "request"></jsp:useBean>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>">
<center>
<%
List collegeList = (List)request.getAttribute("collegeList");
List courseList = (List)request.getAttribute("courseList");
List subjectList = (List)request.getAttribute("subjectList");
%>

		<% 
			if(DataUtility.getLong(request.getParameter("id")) == 0)
			{
				%>
				<h2 class="header"><u>Add Faculty</u></h2>
				<%
			}
			else
			{
				%>
				<h2 class="header"><u>Update Faculty</u></h2>
				<%
			}
		%>
<br>

<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3> 

<table>
	<tr>
		<th align="center">College Name*:</th>
		<td align="left"><%=HTMLUtility.getList("collegeId" , String.valueOf(bean.getCollegeId()) , collegeList , null)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("collegeId", request)%></font></td>
	</tr>
	<tr>
		<th align="center">Course Name*:</th>
		<td align="left"><%=HTMLUtility.getList("courseId" , String.valueOf(bean.getCourseId()) , courseList , null)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
	</tr>
	<tr>
		<th align="center">First Name*:</th>
		<td align="left"><input type="text" name="firstName" value = "<%=DataUtility.getStringData(bean.getFirstName())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font></td>
	</tr>
	<tr>
		<th align="center">Last Name*:</th>
		<td align="left"><input type="text" name="lastName" value = "<%=DataUtility.getStringData(bean.getLastName())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
	</tr>
	<tr>
		<th align="center">Date of Birth*:</th>
		<td align="left"><input type="text" name="dob" class = "datepicker tbox" value = "<%=DataUtility.getDateToString(bean.getDob())%>" ></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font></td>
	</tr>
	<tr>
		<th align="center">Email Id*:</th>
		<td align="left"><input type="text" name="email" value = "<%=DataUtility.getStringData(bean.getEmail())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font></td>
	</tr>
	<tr>
		<th align="center">Mobile No*:</th>
		<td align="left"><input type="text" name="mobileNo" value = "<%=DataUtility.getStringData(bean.getMobileNo())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Gender*:</th>
		<td align="left">
	<%
		HashMap<String, String> genMap = new HashMap<String, String>();
		genMap.put("Male", "Male");
		genMap.put("Female", "Female");
	%> <%=HTMLUtility.getList("gender", bean.getGender(), genMap)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font></td>
	</tr>
	<tr>
		<th align="center">Joining Date*:</th>
		<td align="left"><input type="text" name="joiningDate" class = "datepicker tbox" value = "<%=DataUtility.getDateToString(bean.getJoiningDate())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("joiningDate", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Qualification*:</th>
		<td align="left">
		<%
		HashMap<String, String> qualMap = new HashMap<String, String>();
		qualMap.put("PHD", "PHD");
		qualMap.put("MTECH", "M.TECH.");
		qualMap.put("ME", "M.E.");
		qualMap.put("BE", "B.E.");
		qualMap.put("MCA", "MCA");
		qualMap.put("MSC", "M.SC.");
		%><%=HTMLUtility.getList("qualification", bean.getQualification(), qualMap)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("qualification", request)%></font></td>
		</tr>
		
		<tr>
			<td colspan="2" align="center">
	
			<input type="submit" name="operation" value="<%=FacultyCtl.OP_SAVE %>" class="srpnbtn">
			&nbsp;
	
	<% 
			if(DataUtility.getLong(request.getParameter("id"))>0)
			{
				%>
				<input type="submit" name="operation" value="<%=FacultyCtl.OP_CANCEL%>" class="srpnbtn">
				
				<%
			}
			else
			{
				%>
				<input type="submit" name="operation" value="<%=FacultyCtl.OP_RESET%>" class="srpnbtn">
				<%
			}
		%>
		
	</td>
	</tr>
</table>
</center>
<%@include file = "Footer.jsp" %>
</form>
</body>
</html> 