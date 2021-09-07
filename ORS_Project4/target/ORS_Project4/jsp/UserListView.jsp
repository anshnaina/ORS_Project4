<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.model.RoleModel"%>
<%@ page import="java.util.List"%>
<%@page import="com.sunilOS.ORSProject4.controller.UserListCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.bean.UserBean"%>
<%@page import="java.util.Iterator"%>
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
<%@include file="Header.jsp"%>
<form action = "<%=ORSView.USER_LIST_CTL%>" method = "post">
<jsp:useBean id="bean" class="com.sunilOS.ORSProject4.bean.UserBean" scope="request"></jsp:useBean>
<%
	List roleList = (List) request.getAttribute("roleList");
%>
<center>
<h2 class = "header"><u>User List</u></h2>	

<h3 align="center" style="margin: 2.5mm">
	<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
</h3>
<h3 align="center" style="margin: 2.5mm">
	<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
</h3>
<%
RoleModel rm = new RoleModel();
List totalUserList = (List) request.getAttribute("userList");
int totalListSize = totalUserList.size();

List pageList = ServletUtility.getList(request);
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);

int index = (pageNo - 1) * pageSize + 1;
Iterator it = pageList.iterator();

if(pageList.size()!=0){
%>


<input type="text" name = "firstName" placeholder = "Enter First Name" value="<%=ServletUtility.getParameter("firstName", request)%>" class = "tbox">
<input type="text" name = "lastName" placeholder = "Enter Last Name" value="<%=ServletUtility.getParameter("lastName", request)%>" class = "tbox">
<%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), roleList, "Role")%>

&nbsp;<input type="submit" name = "operation" value="<%=UserListCtl.OP_SEARCH%>" class = "srpnbtn">&nbsp;
<input type="submit" name="operation" value="<%=UserListCtl.OP_RESET%>" class = "srpnbtn">

<table class="table table-hover" border="4" style="width: 90%;">
<thead>
<tr>
	<th><input type = "checkbox" onclick = "checkAll(this)"> Select All</th>
	<th>S.No.</th>
	<th>First Name</th>
	<th>Last Name</th>
	<th>Email Id</th>
	<th>Role</th>
	<th>Date of Birth</th>
	<th>Gender</th>
	<th>Edit</th>
</tr>
</thead>
<tbody>
<%
while(it.hasNext())
{
	UserBean ub = (UserBean)it.next();
%>
	<tr>
	<td><input type = "checkbox" name = "chk" value = "<%=ub.getId()%>"></td>
	<td><%=index++%></td>
	<td><%=ub.getFirstName()%></td>
	<td><%=ub.getLastName()%></td>
	<td><%=ub.getEmail()%></td>
	<td><%=rm.findByPK(ub.getRoleId()).getRoleName()%></td>
	<td><%=DataUtility.getDateToString(ub.getDob())%></td>
	<td><%=ub.getGender()%></td>
		
	<td><button type="button" name = "operation" value = "<%=UserListCtl.OP_EDIT%>" onclick="location.href='UserCtl?id=<%=ub.getId()%>'" <%=(ub.getRoleId() == AppRole.ADMIN) ? "disabled" : ""%> class="glyphicon glyphicon-edit">Edit</button></td>
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
<button style=" width:1.2cm; height: .8cm; margin-right: 38%; margin-left: 5%;" type="submit" name = "operation" value = "<%=UserListCtl.OP_DELETE%>" class="glyphicon glyphicon-trash btn"></button>

<input type = "submit" name = "operation" value = "<%=UserListCtl.OP_PREVIOUS%>" <%=(pageNo == 1) ? "disabled" : ""%> class = "srpnbtn">

<input type = "submit" name = "operation" value = "<%=UserListCtl.OP_NEXT%>" <%=(pageNo==lastPage) || (pageList.size() < pageSize)  ? "disabled" : ""%> class = "srpnbtn">

<input style="margin-right: 6%; margin-left: 33%;" type = "submit" name = "operation" value = "<%=UserListCtl.OP_NEW%>" class = "srpnbtn" >

<%
				}
				if (pageList.size() == 0) {
			%>
			<input type="submit" name="operation" value="<%=UserListCtl.OP_BACK%>">

			<%
				}
			%>
<input type = "hidden" name = "pageNo" value="<%=pageNo%>"> 
<input type = "hidden" name = "pageSize" value="<%=pageSize%>">

<%@include file="Footer.jsp"%>
</form>
</body>
</html>