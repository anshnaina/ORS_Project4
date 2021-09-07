	<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.CollegeCtl"%>
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
<form action="<%=ORSView.COLLEGE_CTL%>" method="post">
<%@include file="Header.jsp"%>

<jsp:useBean id = "bean" class = "com.sunilOS.ORSProject4.bean.CollegeBean" scope = "request"></jsp:useBean>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>">

<%
	List collegeList = (List)request.getAttribute("collegeList"); 
%>
<center>
		<% 
			if(DataUtility.getLong(request.getParameter("id")) == 0)
			{
				%>
				<h2 class="header"><u>Add College</u></h2>
				<%
			}
			else
			{
				
				%>
				
				<h2 class="header"><u>Update College</u></h2>
				<%
			}
		%>
		
		
	<br>
	
	<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
	<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3> 
					
<table>
	<tr>
		<th align="center">College Name*:</th>
		<td align="left"><input type="text" name="collegeName" value = "<%=DataUtility.getStringData(bean.getCollegeName())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("collegeName", request)%></font></td>
	</tr>
	
	<tr>	
		<th align="center">Address*:</th>
		<td align="left"><input type="text" name="address" value = "<%=DataUtility.getStringData(bean.getAddress())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("address", request)%></font></td>
	</tr>
	
	<tr>	
		<th align="center">City*:</th>
		<td align="left"><input type="text" name="city" value = "<%=DataUtility.getStringData(bean.getCity())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("city", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">State*:</th>
		<td align="left"><input type="text" name="state" value = "<%=DataUtility.getStringData(bean.getState())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("state", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Mobile No*:</th>
		<td align="left"><input type="number" name="mobileNo" value = "<%=DataUtility.getStringData(bean.getMobileNo())%>" class="tbox"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
	</tr>
	
	<tr>
	<td colspan="2" align="center">
	
	<input type="submit" name="operation" value="<%=CollegeCtl.OP_SAVE %>" class="srpnbtn">
	
	&nbsp;
	
	<% 
			if(DataUtility.getLong(request.getParameter("id"))>0)
			{
				%>
				<input type="submit" name="operation" value="<%=CollegeCtl.OP_CANCEL%>" class="srpnbtn">
				
				<%
			}
			else
			{
				%>
				<input type="submit" name="operation" value="<%=CollegeCtl.OP_RESET%>" class="srpnbtn">
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