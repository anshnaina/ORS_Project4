<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.MarksheetCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

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
<form action = "<%=ORSView.MARKSHEET_CTL %>" method = "post">

<%@include file="Header.jsp"%>
<jsp:useBean id = "bean" class = "com.sunilOS.ORSProject4.bean.MarksheetBean" scope = "request"></jsp:useBean>
			<%
			List marksheetList = (List) request.getAttribute("marksheetList");
			List studentList = (List) request.getAttribute("studentList");
			%>
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
				<h2 class="header"><u>Add Marksheet</u></h2>
				<%
			}
			else
			{
				%>
				<h2 class="header"><u>Update Marksheet</u></h2>
				<%
			}
		%>
		<br>
	<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
	<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3> 
	
<table>
	<tr>
		<th align="center">Roll No*:</th>
		<td align="left"><input type="text" name="rollNo" value = "<%=DataUtility.getStringData(bean.getRollNo())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("rollNo", request)%></font></td>
	</tr>
	
	<tr>	
		<th align="center">Name*:</th>
		<td align="left"><%=HTMLUtility.getList("studentId", String.valueOf(bean.getStudentId()), studentList, null)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("studentId", request)%></font></td>
	</tr>
	
	<% 
			if(DataUtility.getLong(request.getParameter("id")) == 0)
			{
				%>
	<tr>	
		<th align="center">Physics*:</th>
		<td align="left"><input type="text" name="physics" value = "<%=DataUtility.getStringData(bean.getPhysics()).equals("0") ? "" : DataUtility.getStringData(bean.getPhysics())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("physics", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Chemistry*:</th>
		<td align="left"><input type="text" name="chemistry" value = "<%=DataUtility.getStringData(bean.getChemistry()).equals("0") ? "" : DataUtility.getStringData(bean.getChemistry())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Maths*:</th>
		<td align="left"><input type="text" name="maths" value = "<%=DataUtility.getStringData(bean.getMaths()).equals("0") ? "" : DataUtility.getStringData(bean.getMaths())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("maths", request)%></font></td>
	</tr>
	<%
	}
			else
			{
	%>
	
	<tr>	
		<th align="center">Physics*:</th>
		<td align="left"><input type="text" name="physics" value = "<%=DataUtility.getStringData(bean.getPhysics())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("physics", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Chemistry*:</th>
		<td align="left"><input type="text" name="chemistry" value = "<%=DataUtility.getStringData(bean.getChemistry())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("chemistry", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Maths*:</th>
		<td align="left"><input type="text" name="maths" value = "<%=DataUtility.getStringData(bean.getMaths())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("maths", request)%></font></td>
	</tr>
	
	<%}%>
	<tr>
	<td colspan="2" align="center">	
	<input type="submit" name="operation" value="<%=MarksheetCtl.OP_SAVE%>" class="srpnbtn">
	&nbsp;
	<% 
			if(DataUtility.getLong(request.getParameter("id"))>0)
			{	
				%>
				<input type="submit" name="operation" value="<%=MarksheetCtl.OP_CANCEL%>" class="srpnbtn">
				<%
			}
			else
			{
				%>
				<input type="submit" name="operation" value="<%=MarksheetCtl.OP_RESET%>" class="srpnbtn">
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