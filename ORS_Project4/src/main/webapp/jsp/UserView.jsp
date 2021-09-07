<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.UserCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import = "java.sql.Date"%>
<%@page import = "java.util.HashMap"%>
<%@page import = "java.util.List"%>
<%@page import = "java.text.SimpleDateFormat"%>
<!DOCTYPE html>
<html>
<head>

<script type="text/javascript">
	function capitalize(name)
	{
		var s = name.value;
		name.value = s.substring(0, 1).toUpperCase() + s.substring(1);
	}
</script>

<title>Rays Technologies</title>
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
   <link href= "<%=ORSView.APP_CONTEXT%>/img/title.png" rel= "icon" type="icon">
    <link href= "<%=ORSView.APP_CONTEXT%>/css/FormView.css" rel="stylesheet">
</head>
<body>
<%@include file="Header.jsp"%>
<form action = "<%=ORSView.USER_CTL%>" method="post">
<jsp:useBean id = "bean" class = "com.sunilOS.ORSProject4.bean.UserBean" scope = "request"></jsp:useBean>
<%
	List roleList = (List) request.getAttribute("roleList");
%>
<center>
		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>">
		
		<% 
			if(DataUtility.getLong(request.getParameter("id")) == 0)
			{
				%>
				<h2 class="header"><u>Add User</u></h2>
				<%
			}
			else
			{
				
				%>
				
				<h2 class="header"><u>Update User</u></h2>
				<%
			}
		%>
		<br>
			<h2 align="center">
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h2>

<table>	
	<tr>
		<th align="center">First Name*:</th>
		<td align="left"><input type="text" name="firstName" class="tbox" onchange="capitalize(this)" value="<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("firstName", request)%></font></td> 
	</tr>
	
	<tr>	
		<th align="center">Last Name*:</th>
		<td align="left"><input type="text" name="lastName" class="tbox" onchange="capitalize(this)" value="<%=DataUtility.getStringData(bean.getLastName())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("lastName", request)%></font></td>
	</tr>
	
	<tr>	
		<th align="center">Email*:</th>
		<td align="left"><input type="email" name="email" class="tbox" value="<%=DataUtility.getStringData(bean.getEmail())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("email", request)%></font></td>
	</tr>
	<%if(bean.getId()==0)
	{%>
	<tr>
		<th align="center">Password*:</th>
		<td align="left"><input type="password" name="password" class="tbox" value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Confirm Password*:</th>
		<td align="left"><input type="password" name="confirmPassword" class="tbox" value="<%=DataUtility.getStringData(bean.getConfirmPassword())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("confirmPassword", request)%></font></td>
	</tr>
	<%}%>
	
	
	<tr>	
		<th align="center">Date Of Birth:</th>
		<td align="left"><input type = "text" name = "dob" class = "datepicker tbox" value="<%=DataUtility.getDateToString(bean.getDob())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("dob", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Mobile No.:</th>
		<td align="left"><input type= "text" name="mobileNo" class="tbox" value="<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("mobileNo", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Role:</th>
		<td align="left" >
		<%=HTMLUtility.getList("roleId", String.valueOf(bean.getRoleId()), roleList, null)%></td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("roleId", request)%></font></td>
	</tr>
	
	<tr>
		<th align="center">Gender*</th>
		<td align="left" >				
			<%
				HashMap<String, String> genMap = new HashMap<String, String>();
				genMap.put("Male", "Male");
				genMap.put("Female", "Female");
			%>
				<%=HTMLUtility.getList("gender", bean.getGender(), genMap)%> </td>
		<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("gender", request)%></font></td>
	</tr>

	<tr>
	<td colspan="2" align="center">
	<input type="submit" name="operation" value="<%=UserCtl.OP_SAVE%>" class="srpnbtn">
	&nbsp;
<% 
			if(DataUtility.getLong(request.getParameter("id"))>0)
			{
				%>
				<input type="submit" name="operation" value="<%=UserCtl.OP_CANCEL%>" class="srpnbtn">
				
				<%
			}
			else
			{
				%>
				<input type="submit" name="operation" value="<%=UserCtl.OP_RESET%>" class="srpnbtn">
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