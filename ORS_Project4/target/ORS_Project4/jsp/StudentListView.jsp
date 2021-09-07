<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List"%>
<%@page import="com.sunilOS.ORSProject4.controller.StudentListCtl"%>
<%@page import="com.sunilOS.ORSProject4.controller.StudentCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.bean.StudentBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.SimpleDateFormat"%>

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
<form action = "<%=ORSView.STUDENT_LIST_CTL%>" method = "post">
<%@include file="Header.jsp"%>
<center>

<h2 class = "header"><u>Student List</u></h2>

<h3 align="center" style="margin: 2.5mm">
	<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
<h3 align="center" style="margin: 2.5mm">
	<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<% 
List totalStudentList = (List) request.getAttribute("studentList");
int totalListSize = totalStudentList.size();

List pageList = ServletUtility.getList(request);
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = (pageNo - 1) * pageSize + 1;
Iterator it = pageList.iterator();
if(pageList.size()!=0){
%>
<input type="text" name = "firstName" placeholder = "Enter First Name"  value="<%=ServletUtility.getParameter("firstName", request)%>" class = "tbox">
<input type="text" name = "lastName" placeholder = "Enter Last Name" value="<%=ServletUtility.getParameter("lastName", request)%>" class = "tbox">
&nbsp;<input type="submit" name = "operation" value="<%=StudentListCtl.OP_SEARCH %>" class = "srpnbtn">&nbsp; 
<input type="submit" name="operation" value="<%=StudentListCtl.OP_RESET%>" class = "srpnbtn">

<table class="table table-hover" border="4" style="width: 90%;">
<thead>
<tr>
	<th><input type = "checkbox" onclick = "checkAll(this)"> Select All</th>
	<th>S.No.</th>
	<th>First Name</th>
	<th>Last Name</th>
	<th>College Name</th>
	<th>Date of Birth</th>
	<th>Mobile No.</th>
	<th>Email Id</th>
	<th>Edit</th>
</tr>
</thead>
<tbody>
<%
while(it.hasNext())
{	
	StudentBean sb = (StudentBean)it.next();
	
%>
	<tr>
	<td><input type = "checkbox" name = "chk" value="<%=sb.getId()%>"></td>
	<td><%=index++%></td>
	<td><%=sb.getFirstName()%></td>
	<td><%=sb.getLastName()%></td>
	<td><%=sb.getCollegeName()%></td>
	<td><%=sb.getDob()%></td>
	<td><%=sb.getMobileNo()%></td>
	<td><%=sb.getEmail()%></td> 
	<td><button type = "button" value = "<%=StudentCtl.OP_EDIT%>" onclick="location.href='StudentCtl?id=<%=sb.getId()%>'" class="glyphicon glyphicon-edit">Edit</button></td>
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
<button style=" width:1.2cm; height: .8cm; margin-right: 38%; margin-left: 5%;" type="submit" name = "operation" value = "<%=StudentListCtl.OP_DELETE%>" class="glyphicon glyphicon-trash btn"></button>

<input type = "submit" name = "operation" value = "<%=StudentListCtl.OP_PREVIOUS%>" <%=(pageNo == 1) ? "disabled" : ""%> class = "srpnbtn">

<input type = "submit" name = "operation" value = "<%=StudentListCtl.OP_NEXT%>" <%=(pageNo==lastPage) || (pageList.size() < pageSize)  ? "disabled" : ""%> class = "srpnbtn">

<input style="margin-right: 6%; margin-left: 34%;" type = "submit" name = "operation" value = "<%=StudentListCtl.OP_NEW%>" class = "srpnbtn" >

<%
				}
				if (pageList.size() == 0) {
			%>
			<input type="submit" name="operation" value="<%=StudentListCtl.OP_BACK%>">

			<%
				}
			%>
<input type = "hidden" name = "pageNo" value="<%=pageNo%>"> 
<input type = "hidden" name = "pageSize" value="<%=pageSize%>">

<%@include file="Footer.jsp"%>
</form>
</body>
</html>