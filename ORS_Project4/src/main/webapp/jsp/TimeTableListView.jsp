<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.TimeTableListCtl"%>
<%@page import="com.sunilOS.ORSProject4.bean.TimeTableBean"%>
<%@page import="java.util.Iterator"%>
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
<%@ include file="Header.jsp"%> 
<form action = "<%=ORSView.TIMETABLE_LIST_CTL%>" method = "post">


<jsp:useBean id="bean" class="com.sunilOS.ORSProject4.bean.TimeTableBean" scope="request"></jsp:useBean>
<center>
<h2 class = "header"><u>TimeTable List</u></h2>

<h3 align="center" style="margin: 2.5mm">
	<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
<h3 align="center" style="margin: 2.5mm">
	<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<%
	List courseList = (List) request.getAttribute("courseList");
	List subjectList =(List) request.getAttribute("subjectList");

	List totalTimeTableList = (List) request.getAttribute("timeTableList");
	int totalListSize = totalTimeTableList.size();

	List pageList = ServletUtility.getList(request);
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);

	int lastPage = totalListSize/pageSize;
	if(totalListSize % pageSize != 0)
		lastPage += 1;

	int index = (pageNo - 1) * pageSize + 1;
	Iterator it = pageList.iterator();
	if(pageList.size()!=0){
		%>

<%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList, "Course")%>
<%=HTMLUtility.getList("subjectId", String.valueOf(bean.getSubjectId()), subjectList, "Subject")%>
<input type = "text" name = "examDate" placeholder="Exam Date" value = "<%=DataUtility.getDateToString(bean.getExamDate())%>" class = "datepicker tbox">
&nbsp;<input type = "submit" name = "operation" value="<%=TimeTableListCtl.OP_SEARCH %>" class = "srpnbtn">&nbsp;&nbsp; 
<input type="submit" name="operation" value="<%=TimeTableListCtl.OP_RESET%>" class = "srpnbtn">


<table class="table table-hover" border="4" style="width: 90%;">
<thead>
<tr>
<th><input type = "checkbox" onclick="checkAll(this)"> Select All</th>
<th>S. No.</th>
<th>Course Name</th>
<th>Subject Name</th>
<th>Semester</th>
<th>Exam Date</th>
<th>Exam Time</th>
<th>Description</th>
<th>Edit</th>
</tr>
</thead>
<tbody>
<%
while(it.hasNext())
{
	TimeTableBean tb = (TimeTableBean) it.next();
%>
<tr>
	<td><input type = "checkbox" name = "chk" value="<%=tb.getId()%>" ></td>
	<td><%=index++%></td>
	<td><%=tb.getCourseName()%></td>
	<td><%=tb.getSubjectName()%></td>
	<td><%=tb.getSemester()%></td>
	<td><%=DataUtility.getDateToString(tb.getExamDate())%></td>
	<td><%=tb.getExamTime()%></td>
	<td><%=tb.getDescription()%></td>
	<td><button type = "button" name = "operation" value = "<%=TimeTableListCtl.OP_EDIT%>" onclick="location.href='TimeTableCtl?id=<%=tb.getId()%>'" class="glyphicon glyphicon-edit">Edit</button></td>
</tr>
<%
	}
%>
</tbody>
</table>
</center>
<button style=" width:1.2cm; height: .8cm; margin-right: 38%; margin-left: 5%;" type="submit" name = "operation" value = "<%=TimeTableListCtl.OP_DELETE%>" class="glyphicon glyphicon-trash btn"></button>

<input type = "submit" name = "operation" value = "<%=TimeTableListCtl.OP_PREVIOUS%>" <%=(pageNo == 1) ? "disabled" : ""%> class = "srpnbtn">

<input type = "submit" name = "operation" value = "<%=TimeTableListCtl.OP_NEXT%>" <%=(pageNo==lastPage) || (pageList.size() < pageSize)  ? "disabled" : ""%> class = "srpnbtn">

<input style="margin-right: 5%; margin-left: 33%;" type = "submit" name = "operation" value = "<%=TimeTableListCtl.OP_NEW%>" class = "srpnbtn" >

<%
				}
				if (pageList.size() == 0) {
			%>
			<input type="submit" name="operation" value="<%=TimeTableListCtl.OP_BACK%>">

			<%
				}
			%>
<input type = "hidden" name = "pageNo" value="<%=pageNo%>"> 
<input type = "hidden" name = "pageSize" value="<%=pageSize%>">

<%@ include file="Footer.jsp"%> 
</form>
</body>
</html>