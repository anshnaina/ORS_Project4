package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Savepoint;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.RoleBean;
import com.sunilOS.ORSProject4.bean.UserBean;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.UserModel;
import com.sunilOS.ORSProject4.utility.AppRole;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;


@WebServlet(name = "UserRegistrationCtl", urlPatterns = { "/UserRegistrationCtl" })
public class UserRegistrationCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(UserRegistrationCtl.class);

	
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("UserRegistrationCtl validate method started");

		boolean pass = true;

		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(firstName)) {
		request.setAttribute("firstName", PropertyReader.getValue("error.require", "First name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(firstName)) {
	request.setAttribute("firstName", PropertyReader.getValue("error.alphabet", "First name"));
			pass = false;
		}
		if (DataValidator.isNull(lastName)) {
	request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last name"));
			pass = false;
		} else if (!DataValidator.isName2(lastName)) {
	request.setAttribute("lastName", PropertyReader.getValue("error.alphabet", "Last name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.whitespace", "Last name"));
			pass = false;
		}

		if (DataValidator.isNull(email)) {
	request.setAttribute("email", PropertyReader.getValue("error.require", "Email id"));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
	request.setAttribute("email", PropertyReader.getValue("error.email", "Email id"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("password"))) {
	request.setAttribute("password", PropertyReader.getValue("error.require", "Password"));
			pass = false;
		} else if (!DataValidator.isPasslength(request.getParameter("password"))) {
request.setAttribute("password", PropertyReader.getValue("error.pwdlength", "Password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm password"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of birth"));
			pass = false;
		} else if (!DataValidator.ageLimit(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.validate", "Age"));
			pass = false;
		}
		
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile no."));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Please Enter Valid Mobile Number");
			pass = false;
		}
		
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {

			request.setAttribute("confirmPassword", "Confirm password does not match with password");
			pass = false;

		}
	return pass;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.debug("UserRegistrationCtl Do Get method started");

		
		ServletUtility.forward(getView(), req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("UserRegistrationCtl Do Post method started");

		String operation = request.getParameter("operation");
		UserModel model = new UserModel();
		
		if(operation.equals(OP_SAVE))
		{
			UserBean bean = (UserBean) populateBean(request);
			try 
			{
				long pk = model.registerUser(bean);
				ServletUtility.setSuccessMessage("You have been registered successfully, Please check your email and login!", request);
				ServletUtility.forward(getView(), request, response);
				return;
			}
			catch (DuplicateRecordException e)
			{
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exists", request);
				ServletUtility.forward(getView(), request, response);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
			else if (operation.equals(OP_RESET))
		{
				ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("UserRegistrationCtl populate bean method started");

		UserBean bean = new UserBean();
		
		  // Default Role set while User Registration
				bean.setRoleId(AppRole.STUDENT);
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getStringToDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
	
		populateDTO(bean, request);
		
		return bean;
	}

	@Override
	protected String getView() 
	{
		return ORSView.USER_REGISTRATION_VIEW;
	}
}
