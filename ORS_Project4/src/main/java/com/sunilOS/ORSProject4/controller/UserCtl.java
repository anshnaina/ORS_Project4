package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Savepoint;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.UserBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.RoleModel;
import com.sunilOS.ORSProject4.model.UserModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "UserCtl", urlPatterns = { "/ctl/UserCtl" })
public class UserCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log=Logger.getLogger(UserCtl.class);


	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("UserCtl validate method started");

		
		boolean pass = true;
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");

		if (DataValidator.isNull(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (DataValidator.isSpecial(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.specialchar", "First Name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.alphabet", "First Name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.whitespace", "First Name"));
			pass = false;
		}
		
		if (DataValidator.isNull(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
			pass = false;
		} else if (DataValidator.isSpecial(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.specialchar", "Last Name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.alphabet", "Last Name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.whitespace", "Last Name"));
			pass = false;
		}

		if (DataValidator.isNull(email)) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email Id"));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email Id"));
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
			request.setAttribute("confirmPassword", PropertyReader.getValue("error.require", "Confirm Password"));
			pass = false;
		}
		if (!request.getParameter("password").equals(request.getParameter("confirmPassword"))
				&& !"".equals(request.getParameter("confirmPassword"))) {
			request.setAttribute("confirmPassword", "New Password & Confirm password must be same");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile No"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.phoneno", "Mobile No."));
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile Number must be numbers");
			pass = false;
		} else if (!DataValidator.isMob(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile Number must be starts with 7/8/9");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", "Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("roleId"))) {
			request.setAttribute("roleId", PropertyReader.getValue("error.require", "Role "));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date Of Birth"));
			pass = false;
		} else if (!DataValidator.ageLimit(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.validate", "Age"));
			pass = false;
		}
		return pass;
	}
	
	@Override
	protected void preload(HttpServletRequest request) 
	{
		log.debug("UserCtl preload method started");

		RoleModel model = new RoleModel();
		try {
			List roleList = model.list();
			request.setAttribute("roleList", roleList);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) 
	{
		log.debug("UserCtl populate bean method started");

		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getStringToDate(request.getParameter("dob")));
		
		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("UserCtl Do Get method started");

		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		UserModel um = new UserModel();
		
		if(id>0 || operation!=null)
		{
			try 
			{
			UserBean ub = um.findByPK(id);
			ServletUtility.setBean(ub, request);
			}
			catch (ApplicationException e) 
			{
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			catch(Exception e)
			{
				e.printStackTrace();	
			}
		}
		ServletUtility.forward(getView(), request, response);
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("UserCtl Do Post method started");

		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		UserModel model = new UserModel();
		
		if(operation.equals(OP_SAVE))
		{
			UserBean bean = (UserBean) populateBean(request);
			try {
			
				if(id>0)
			{
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Record updated successfully", request);
			}
			else
			{
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Record added successfully", request);
			}
			}
			catch (ApplicationException e) 
			{
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} 
			catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Login id already exist", request);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(operation.equals(OP_RESET))
		{
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_CANCEL)){
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
	}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() 
	{
		return ORSView.USER_VIEW;
	}
}