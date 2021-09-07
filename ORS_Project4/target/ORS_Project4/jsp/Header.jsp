<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.sunilOS.ORSProject4.bean.UserBean"%>
<%@page import="com.sunilOS.ORSProject4.controller.ORSView"%>
<%@page import="com.sunilOS.ORSProject4.bean.RoleBean"%>
<%@page import="com.sunilOS.ORSProject4.controller.LoginCtl"%>
<%@page import="com.sunilOS.ORSProject4.utility.AppRole"%>

<!DOCTYPE html>
<html>
<head>
  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

   <style>
   .datepicker{
    
   }
 </style>
 <script>
  
  function checkAll(bx) {
		var form = bx.form;
		var isChecked = bx.checked;
		for (var i = 0; i < form.length; i++) {
			if (form[i].type == 'checkbox') {
				form[i].checked = isChecked;
			}
		}
	}

   $(function() {
		$(".datepicker").datepicker({
			changeMonth : true,
			changeYear : true,
			yearRange : '1980:2022',
			dateFormat : 'dd/mm/yy'
		});
	});

	
	function capitalize(name) {
		var s = name.value;
		name.value = s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
</script>

</head>
<body>
<%UserBean user = (UserBean) session.getAttribute("user");
RoleBean role = (RoleBean) session.getAttribute("role");
			boolean userLoggedIn = user != null;
			%>
<nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="<%=ORSView.WELCOME_CTL%>"><p><img src="<%=ORSView.APP_CONTEXT%>/img/main_logo.png" alt="Rays" height="25"><font> Welcome  |</font></p></a>
            <% if (!userLoggedIn)
			{  %>  
			
			<ul class="nav navbar-nav navbar">
			 <li><a href="<%=ORSView.LOGIN_CTL%>"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
			</ul>
        <%   }  %></div>
           <%
           if (userLoggedIn)
			{
        	   if (user.getRoleId() == AppRole.ADMIN)
        	   {
			%>
            <ul class="nav navbar-nav">
       			 <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> User
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.USER_CTL%>">Add User</a></li>
              <li><a href="<%=ORSView.USER_LIST_CTL%>">User List</a></li>
            </ul>
          </li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Role
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.ROLE_CTL%>">Add Role</a></li>
              <li><a href="<%=ORSView.ROLE_LIST_CTL%>">Role List</a></li>
            </ul>
          </li>
    	<li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> College
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.COLLEGE_CTL%>">Add College</a></li>
              <li><a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</a></li>
            </ul>
          </li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Student
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.STUDENT_CTL%>">Add Student</a></li>
              <li><a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</a></li>
            </ul>
          </li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Marksheet
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</a></li>
              <li><a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</a></li>
              <li><a href="<%=ORSView.MARKSHEET_GET_CTL%>">Get Marksheet</a></li>
              <li><a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet Merit List</a></li>
            </ul>
          </li>
          
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Faculty
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.FACULTY_CTL%>">Add Faculty</a></li>
              <li><a href="<%=ORSView.FACULTY_LIST_CTL%>">Faculty List</a></li>
            </ul>
          </li>
          
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Course
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.COURSE_CTL%>">Add Course</a></li>
              <li><a href="<%=ORSView.COURSE_LIST_CTL%>">Course List</a></li>
            </ul>
          </li>
          
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Time Table
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.TIMETABLE_CTL%>">Add TimeTable</a></li>
              <li><a href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</a></li>
            </ul>
          </li>
          
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Subject
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.SUBJECT_CTL%>">Add Subject</a></li>
              <li><a href="<%=ORSView.SUBJECT_LIST_CTL%>">Subject List</a></li>
            </ul>
          </li>
         <li><a href="<%=ORSView.JAVA_DOC_VIEW%>">Java Doc</a></li> 
          </ul>

            <ul class="nav navbar-nav navbar-right">
            <li class="dropdown active">
           	 <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span><%=" "+user.getFirstName()+" ( "+role.getRoleName()+" ) "%><span class="caret"></span></a>
              <ul class="dropdown-menu">
                  <li><a href="<%=ORSView.MY_PROFILE_CTL%>"><i class="glyphicon glyphicon-edit"></i> My Profile</a></li>
                  <li><a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><i class="glyphicon glyphicon-cog"></i> Change Password</a></li>
                  <li><a href="<%=ORSView.LOGOUT_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
              </li>
            </ul>
		
           	 <%
        	   }
        	   if (user.getRoleId() == AppRole.STAFF)    	   	
        	   {
        	   %>
        		   <ul class="nav navbar-nav">
        	  		<li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> College
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.COLLEGE_CTL%>">Add College</a></li>
              <li><a href="<%=ORSView.COLLEGE_LIST_CTL%>">College List</a></li>
            </ul>
          </li>
          <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Student
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.STUDENT_CTL%>">Add Student</a></li>
              <li><a href="<%=ORSView.STUDENT_LIST_CTL%>">Student List</a></li>
            </ul>
          </li>
           <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Marksheet
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.MARKSHEET_CTL%>">Add Marksheet</a></li>
              <li><a href="<%=ORSView.MARKSHEET_LIST_CTL%>">Marksheet List</a></li>
              <li><a href="<%=ORSView.MARKSHEET_GET_CTL%>">Get Marksheet</a></li>
              <li><a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet Merit List</a></li>
            </ul>
          </li>
           <li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Time Table
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.TIMETABLE_CTL%>">Add TimeTable</a></li>
              <li><a href="<%=ORSView.TIMETABLE_LIST_CTL%>">TimeTable List</a></li>
            </ul>
          </li>
        	  </ul>
        	  
        	  <ul class="nav navbar-nav navbar-right">
            <li class="dropdown active">
           	 <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span><%=" "+user.getFirstName()+" ( "+role.getRoleName()+" ) "%><span class="caret"></span></a>
              <ul class="dropdown-menu">
                  <li><a href="<%=ORSView.MY_PROFILE_CTL%>"><i class="glyphicon glyphicon-edit"></i> My Profile</a></li>
                  <li><a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><i class="glyphicon glyphicon-cog"></i> Change Password</a></li>
                  <li><a href="<%=ORSView.LOGOUT_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
              </li>
            </ul>
            <%
        	   }
        	   if (user.getRoleId() == AppRole.STUDENT)    	   	
        	   {
        	   %>
        	   <ul class="nav navbar-nav">
            	<li class="dropdown">
            <a class="dropdown-toggle" data-toggle="dropdown" href="#"> Marksheet
            <span class="caret"></span></a>
            <ul class="dropdown-menu">
              <li><a href="<%=ORSView.MARKSHEET_GET_CTL%>">Get Marksheet</a></li>
              <li><a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>">Marksheet Merit List</a></li>
            </ul>
          </li>
        	  </ul>
        	  <ul class="nav navbar-nav navbar-right">
            <li class="dropdown active">
           	 <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span><%=" "+user.getFirstName()+" ( "+role.getRoleName()+" ) "%><span class="caret"></span></a>
              <ul class="dropdown-menu">
                  <li><a href="<%=ORSView.MY_PROFILE_CTL%>"><i class="glyphicon glyphicon-edit"></i> My Profile</a></li>
                  <li><a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><i class="glyphicon glyphicon-cog"></i> Change Password</a></li>
                  <li><a href="<%=ORSView.LOGOUT_CTL%>?operation=<%=LoginCtl.OP_LOG_OUT%>"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
              </li>
            </ul>
            	
        	<%		}
        		}
            
            else 
            {
            %>
    		<ul class="nav navbar-nav navbar-right">
                <li><a href="<%=ORSView.USER_REGISTRATION_CTL%>"><span class="glyphicon glyphicon-registration-mark"></span> User Registration</a></li>
           		 <li><a><span class="glyphicon glyphicon-user"></span> Hi Guest</a>
              
            </ul>
<%  
}
%>
	  </div>
       </nav>	
 </body>
</html>