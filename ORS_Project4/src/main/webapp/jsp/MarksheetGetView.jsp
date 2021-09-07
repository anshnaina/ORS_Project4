<%@page import="com.sunilOS.ORSProject4.utility.DataUtility"%>
<%@page import="com.sunilOS.ORSProject4.controller.MarksheetGetCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.utility.ServletUtility"%>
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
<form action="<%=ORSView.MARKSHEET_GET_CTL%>" method="post">
<%@include file="Header.jsp"%>
<jsp:useBean id="bean" class="com.sunilOS.ORSProject4.bean.MarksheetBean" scope="request"></jsp:useBean>

<center>

		<h2 class="header"><u>Get Marksheet</u></h2>
		<br>

<h3><font color="green"> <%=ServletUtility.getSuccessMessage(request)%></font></h3>
<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3> 

<table>	
	<tr>
		<td align="center"><input type="text" name="rollNo" value = "<%=DataUtility.getStringData(bean.getRollNo())%>" placeholder = "Enter Roll No." class="tbox"> &nbsp; 
		<input type="submit" class="srpnbtn" name="operation" value="<%=MarksheetGetCtl.OP_GO%>"></td>
	</tr>
</table>
<br>
<%
int total = 0;
String division = "";
String result = "";
if (bean.getRollNo() != null && bean.getRollNo().trim().length() > 0) 
{

	int p = bean.getPhysics();
	int c = bean.getChemistry();
	int m = bean.getMaths();
	total = p + c + m;

	if (p >= 35 && c >= 35 && m >= 35) 
	{
		if (total >= 195) 
		{
			result = "PASS";
			division = "FIRST DIVISION";
		} 
		else if (total < 195 && total >= 150) 
		{
			division = "SECOND DIVISION";
			result = "PASS";
		} 
		else 
		{
			division = "THIRD DIVISION";
			result = "PASS";
		}
	} else 
	{
		result = "FAIL";
	}

%>
<table border="5">

				<colgroup>
					<col style="width: 30%">
					<col style="width: 30%">
					<col style="width: 30%">
				</colgroup>

			<tr>
					<th>Name</th>
					<td><%=bean.getName()%></td>
					<td></td>
			</tr>
				<tr>
					<th>Roll No</th>
					<td><%=bean.getRollNo()%></td>
					<td></td>
				</tr>
				<tr>
				<td colspan="3"></td>
				</tr>
				
				<tr>
					<th>Subject</th>
					<th>Total</th>
					<th>Obtained</th>
				</tr>
				<tr>
					<td>Physics</td>
					<td><%=100%></td>
					<td><%=bean.getPhysics()%><%=(bean.getPhysics() >= 35) ? "" : "*"%></td>
				</tr>
				<tr>
					<td>Maths</td>
					<td>100</td>
					<td><%=bean.getMaths()%><%=(bean.getMaths() >= 35) ? "" : "*"%></td>
				</tr>
				<tr>
					<td>Chemistry</td>
					<td>100</td>
					<td><%=bean.getChemistry()%><%=(bean.getChemistry() >= 35) ? "" : "*"%></td>
				</tr>
				
				<tr>
				<td colspan="3"></td>
				</tr>
				
				<tr>
					<td><b>Total Marks Obtained : </b> <%=total%></td>
					<td><b>Result : </b> <%
 	if (result.equals("PASS")) 
 	{
 %> <font color="green"><b><%=result%></b></font> <%
 	} 
 	else 
 	{
 %> <font color="red"><b><%=result%></b></font> <%
 	}
 %></td>
					<%
						if (result.equals("PASS")) 
						{
					%>
					<td><b>Division:</b> <%=division%></td>
					
					<%
						}
						else 
						{
					%>
						
						<td></td>
				</tr>
					
</table>
				<font color="red">Note:   *  Indicate Failed Subject(s)</font>
				<% } %>
			
				<br>
				<br>
				<button  name = "operation" value = "<%=MarksheetGetCtl.OP_PRINT%>" onclick = "window.print()" class = "srpnbtn" >Print</button>
			<%	
				}
			%>

</center>

</form>		
<%@include file="Footer.jsp"%>
</body>
</html>