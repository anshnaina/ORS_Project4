<%@page import="com.sunilOS.ORSProject4.controller.MarksheetMeritListCtl"%>
<%@page import="com.sunilOS.ORSProject4.bean.MarksheetBean"%>
<%@page import="java.util.Iterator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="java.util.List"%>
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
<%@include file = "Header.jsp"%>
<form action = "<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>" method = "post">
<center>
<h2 class = "header"><u>Marksheet Merit List</u></h2>
<br>
<table class="table table-hover" border="4" style="width: 90%;">
<thead>
<tr>
<th>S.No.</th>
<th>Roll No.</th>
<th>Student Name</th>
<th>Physics</th>
<th>Maths</th>
<th>Chemistry</th>
<th>Total</th>
<th>Percentage(%)</th>
</tr>
</thead>
<tbody>
<%
List list = ServletUtility.getList(request);
Iterator it = list.iterator();
int i = 1;
while(it.hasNext())
{
	MarksheetBean mb = (MarksheetBean) it.next();
	int total=mb.getChemistry()+mb.getMaths()+mb.getPhysics();
	float percentage = (total * 100) / 300;
	%>
	<tr>
	<td><%=i++%></td>
	<td><%=mb.getRollNo()%></td>
	<td><%=mb.getName()%></td>
	<td><%=mb.getPhysics()%></td>
	<td><%=mb.getChemistry()%></td>
	<td><%=mb.getMaths()%></td>
	<td><%=total%></td>
	<td><%=percentage%></td>
	 </tr>
<%
}
%>

</tbody>

</table>

<input type = "submit" name = "operation" value = "<%=MarksheetMeritListCtl.OP_BACK%>" class = "srpnbtn">

</center>
<%@include file = "Footer.jsp"%>
</form>
</body>
</html>