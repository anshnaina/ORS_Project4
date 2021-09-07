<%@page import="com.sunilOS.ORSProject4.bean.CourseBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.sunilOS.ORSProject4.controller.CourseListCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
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
<jsp:useBean id="bean" class="com.sunilOS.ORSProject4.bean.CourseBean" scope="request"></jsp:useBean>
<form action = "<%=ORSView.COURSE_LIST_CTL%>" method = "post">

<center>
<h2 class = "header"><u>Course List</u></h2>


<h3 align="center" style="margin: 2.5mm">
	<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>

<h3 align="center" style="margin: 2.5mm">
	<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>

<%
	List totalCourseList = (List)request.getAttribute("courseList");
	int totalListSize = totalCourseList.size();

	List pageList = ServletUtility.getList(request);
	int pageNo = ServletUtility.getPageNo(request);
	int pageSize = ServletUtility.getPageSize(request);		
	int index = ((pageNo - 1) * pageSize) + 1;
	
	int lastPage = totalListSize / pageSize;
	if(totalListSize % pageSize != 0)
	lastPage += 1;
	
	Iterator it = pageList.iterator();
	if(pageList.size()!=0){%>

<input type = "text" name = "courseName" placeholder="Enter course name" value="<%=ServletUtility.getParameter("courseName", request)%>" class = "tbox">
<input type = "text" name = "courseDuration" placeholder="Enter course duration" value="<%=ServletUtility.getParameter("courseDuration", request)%>" class = "tbox">
&nbsp;<input type = "submit" name = "operation" value="<%=CourseListCtl.OP_SEARCH %>" class = "srpnbtn">&nbsp; 
<input type="submit" name="operation" value="<%=CourseListCtl.OP_RESET%>" class = "srpnbtn">

<table class="table table-hover" border="4" style="width: 90%;">
<thead>
<tr>
<th><input type = "checkbox" onclick="checkAll(this)"> Select All</th>
<th>S. No.</th>
<th>Course Name</th>
<th>Course Description</th>
<th>Course Duration</th>
<th>Edit</th>
</tr>
</thead>
<tbody>
<%
	while(it.hasNext())
	{
		CourseBean cb = (CourseBean) it.next();
%>
<tr>
	<td><input type = "checkbox" name = "chk" value="<%=cb.getId()%>" ></td>
	<td><%=index++%></td>
	<td><%=cb.getCourseName()%></td>
	<td><%=cb.getCourseDescription()%></td>
	<td><%=cb.getCourseDuration()%></td>
	<td><button type = "button" name = "operation" value = "<%=CourseListCtl.OP_EDIT%>" onclick="location.href='CourseCtl?id=<%=cb.getId()%>'" class="glyphicon glyphicon-edit">Edit</button></td>
</tr>
	 <% } %>
</tbody>
</table>
</center>
<button style=" width:1.2cm; height: .8cm; margin-right: 38%; margin-left: 5%;" type="submit" name = "operation" value = "<%=CourseListCtl.OP_DELETE%>" class="glyphicon glyphicon-trash btn"></button>

<input type = "submit" name = "operation" value = "<%=CourseListCtl.OP_PREVIOUS%>" <%=(pageNo == 1) ? "disabled" : ""%> class = "srpnbtn">

<input type = "submit" name = "operation" value = "<%=CourseListCtl.OP_NEXT%>" <%=(pageNo==lastPage) || (pageList.size() < pageSize)  ? "disabled" : ""%> class = "srpnbtn">

<input style="margin-right: 6%; margin-left: 31%;" type = "submit" name = "operation" value = "<%=CourseListCtl.OP_NEW%>" class = "srpnbtn" >

<%
				}
				if (pageList.size() == 0) {
			%>
			<input type="submit" name="operation" value="<%=CourseListCtl.OP_BACK%>">

			<%
				}
			%>
<input type = "hidden" name = "pageNo" value="<%=pageNo%>"> 
<input type = "hidden" name = "pageSize" value="<%=pageSize%>">
<%@ include file="Footer.jsp"%> 
</form>	
</body>
</html>