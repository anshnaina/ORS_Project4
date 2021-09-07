<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.controller.ChangePasswordCtl"%>
<%@page import="com.sunilOS.ORSProject4.controller.MyProfileCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.HTMLUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
<%@page import = "java.sql.Date"%>
<%@page import="java.util.Locale"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.HashMap"%>


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
<%@include file="Header.jsp"%>
<form action="<%=ORSView.MY_PROFILE_CTL%>" method="post">
<jsp:useBean id="bean" class="com.sunilOS.ORSProject4.bean.UserBean" scope="request"></jsp:useBean>

		<input type = "hidden" name = "id" value = "<%=bean.getId()%>">
		<input type = "hidden" name = "createdBy" value = "<%=bean.getCreatedBy()%>">
		<input type = "hidden" name = "modifiedBy" value = "<%=bean.getModifiedBy()%>"> 
		<input type = "hidden" name = "createdDateTime" value = "<%=bean.getCreatedDateTime()%>">
		<input type = "hidden" name = "modifiedDateTime" value = "<%=bean.getModifiedDateTime()%>">
	

<center>
		<h2 class="header"><u>My Profile</u></h2>
<br>
	<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
	<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
<table>	

	<tr>	
		<th align="center">Email*:</th>
		<td align="left">
		<input type="email" name="email" class="tbox" value="<%=DataUtility.getStringData(bean.getEmail())%>" readonly="readonly"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("email", request)%></font>
		</td>
	</tr>
	<tr>
		<th align="center">First Name*:</th>
		<td align="left">
		<input type="text" name="firstName" class="tbox" onchange="capitalize(this)" value = "<%=DataUtility.getStringData(bean.getFirstName())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("firstName", request)%></font>
		</td>
	</tr>
	
	<tr>
		<th align="center">Last Name*:</th>
		<td align="left">
		<input type="text" name="lastName" class="tbox" onchange="capitalize(this)" value = "<%=DataUtility.getStringData(bean.getLastName())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("lastName", request)%></font>
		</td>
	</tr>
	
	<tr>
		<th align="center">Mobile No.*:</th>
		<td align="left">
		<input type="text" name="mobileNo" maxlength="10" min="10" class="tbox" onchange="capitalize(this)" value = "<%=DataUtility.getStringData(bean.getMobileNo())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("mobileNo", request)%></font>
		</td>
	</tr>
	
	<tr>
		<th align="center">Date Of Birth*</th>
		<td align="left"><input type="text" class="datepicker tbox" name="dob" value="<%=new SimpleDateFormat("dd/MM/yyyy").format(bean.getDob())%>"></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("dob", request)%></font>
		</td>
	</tr>
		
	<tr>
	<th align="center">Gender:</th>
	<td align="left">
		<%
			HashMap genMap = new HashMap();
			genMap.put("Male", "Male"); 
			genMap.put("Female", "Female");
		%>
		<%=HTMLUtility.getList("gender", bean.getGender(), genMap)%></td>
		<td style="position: fixed;"><font color="red"><%=ServletUtility.getErrorMessage("gender", request)%></font>
	</td>
	</tr>
	
	
	<tr>
	<td colspan="2" align="center">
	<input type="submit" name="operation" value="<%=MyProfileCtl.OP_SAVE %>" class="srpnbtn">
	&nbsp;
	<input type="submit" name="operation" value="<%=MyProfileCtl.OP_CHANGE_MY_PASSWORD%>" class="srpnbtn">
	</td>
	</tr>	
	
</table>
</center>

<%@include file="Footer.jsp"%>
</form>
</body>
</html>