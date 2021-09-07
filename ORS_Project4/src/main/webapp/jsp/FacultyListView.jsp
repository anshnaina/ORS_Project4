<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.bean.FacultyBean"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.FacultyCtl"%>
<%@page import="com.sunilOS.ORSProject4.controller.FacultyListCtl"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>

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
<%@ include file="Header.jsp"%> 
<form action = "<%=ORSView.FACULTY_LIST_CTL%>" method = "post">
<jsp:useBean id="bean" class="com.sunilOS.ORSProject4.bean.FacultyBean" scope="request"></jsp:useBean>

			<%
			List collegeList = (List) request.getAttribute("collegeList");
			%>

<center>
<h2 class = "header"><u>Faculty List</u></h2>

<h3 align="center" style="margin: 2.5mm">
	<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>

<h3 align="center" style="margin: 2.5mm">
	<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>

<%List totalFacultyList = (List)request.getAttribute("facultyList");
int totalListSize = totalFacultyList.size();

List pageList = ServletUtility.getList(request);
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = (pageNo - 1) * pageSize + 1;
Iterator it = pageList.iterator();
if(pageList.size()!=0){ %>
<input type = "text" name = "firstName" placeholder="Enter first name" value="<%=ServletUtility.getParameter("firstName", request)%>" class = "tbox">
<input type = "text" name = "lastName" placeholder="Enter last name" value="<%=ServletUtility.getParameter("lastName", request)%>" class = "tbox">
<%=HTMLUtility.getList("collegeId" , String.valueOf(bean.getCollegeId()) ,collegeList ,"College Name")%>

&nbsp;<input type = "submit" name = "operation" value="<%=FacultyListCtl.OP_SEARCH %>" class = "srpnbtn">&nbsp; 
<input type="submit" name="operation" value="<%=FacultyListCtl.OP_RESET%>" class = "srpnbtn">

<table class="table table-hover" border="4" style="width: 90%;">
<thead>
<tr>
<th><input type = "checkbox" onclick="checkAll(this)"> Select All</th>
<th>S. No.</th>
<th>First Name</th>
<th>Last Name</th>
<th>Gender</th>
<th>Date Of Birth</th>
<th>Email Id</th>
<th>Joining Date</th>
<th>College Name</th>
<th>Course Name</th>
<th>Qualification</th>
<th>Edit</th>
</tr>
</thead>
<tbody>
<%
while(it.hasNext())
{
	FacultyBean fb = (FacultyBean) it.next();
%>	<tr>
	<td><input type = "checkbox" name = "chk" value="<%=fb.getId()%>" ></td>
	<td><%=index++%></td>
	<td><%=fb.getFirstName()%></td>
	<td><%=fb.getLastName()%></td>
	<td><%=fb.getGender()%></td>
	<td><%=DataUtility.getDateToString(fb.getDob())%></td>
	<td><%=fb.getEmail()%></td>
	<td><%=fb.getJoiningDate()%></td>
	<td><%=fb.getCollegeName()%></td>
	<td><%=fb.getCourseName()%></td>
	<td><%=fb.getQualification()%></td>
	<td><button type = "button" name = "operation" value = "<%=FacultyCtl.OP_EDIT%>" onclick="location.href='FacultyCtl?id=<%=fb.getId()%>'" class="glyphicon glyphicon-edit">Edit</button></td>
	</tr>
<%
		}
	int lastPage = totalListSize/pageSize;
	if(totalListSize % pageSize!=0)
		lastPage +=1;
	%>
</tbody>
</table>

</center>
<button style=" width:1.2cm; height: .8cm; margin-right: 38%; margin-left: 5%;" type="submit" name = "operation" value = "<%=FacultyListCtl.OP_DELETE%>" class="glyphicon glyphicon-trash btn"></button>

<input type = "submit" name = "operation" value = "<%=FacultyListCtl.OP_PREVIOUS%>" <%=(pageNo == 1) ? "disabled" : ""%> class = "srpnbtn">

<input type = "submit" name = "operation" value = "<%=FacultyListCtl.OP_NEXT%>" <%=(pageNo==lastPage) || (pageList.size() < pageSize)  ? "disabled" : ""%> class = "srpnbtn">

<input style="margin-right: 5%; margin-left: 35%;" type = "submit" name = "operation" value = "<%=FacultyListCtl.OP_NEW%>" class = "srpnbtn" >

<%
				}
				if (pageList.size() == 0) {
			%>
			<input type="submit" name="operation" value="<%=FacultyListCtl.OP_BACK%>">

			<%
				}
			%>
<input type = "hidden" name = "pageNo" value="<%=pageNo%>"> 
<input type = "hidden" name = "pageSize" value="<%=pageSize%>">
<%@ include file="Footer.jsp"%> 
</form>
</body>
</html>