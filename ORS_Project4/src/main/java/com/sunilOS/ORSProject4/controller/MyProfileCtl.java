package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.UserBean;
import com.sunilOS.ORSProject4.model.UserModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "MyProfileCtl", urlPatterns = { "/ctl/MyProfileCtl" })
public class MyProfileCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(MyProfileCtl.class);

	
	public static final String OP_CHANGE_MY_PASSWORD = "ChangePassword";
	
	
	protected boolean validate(HttpServletRequest request)
	{
		log.debug("MyProfileCtl validate method started");

		boolean pass = true;

		String operation = DataUtility.getString(request.getParameter("operation")); 
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");

		if (operation.equals(OP_CHANGE_MY_PASSWORD) || operation == null) {

			return pass;

		}
		if (DataValidator.isNull(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email id"));
			pass = false;
		} else if (!DataValidator.isEmail(request.getParameter("email"))) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email id"));
			pass = false;
		}

		if (DataValidator.isNull(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First name"));
			pass = false;
		} else if (DataValidator.isSpecial(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.specialchar", "First name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.alphabet", "First name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.whitespace", "First name"));
			pass = false;
		}
		if (DataValidator.isNull(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last name"));
			pass = false;
		} else if (DataValidator.isSpecial(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.specialchar", "Last name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.alphabet", "Last name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.whitespace", "Last name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile no."));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.phoneno", "Mobile no."));
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
		if (DataValidator.isNull(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of birth"));
			pass = false;
		} else if (!DataValidator.ageLimit(request.getParameter("dob"))) {
			request.setAttribute("dob", PropertyReader.getValue("error.validate", "Age"));
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("MyProfileCtl Do Get method started");

		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		long id = userBean.getId();
		String operation = request.getParameter("operation");
		
		UserModel model = new UserModel();
		
		if (id > 0 || operation != null)
		{
			try {
				UserBean bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		log.debug("MyProfileCtl Do Post method started");

		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		long id = userBean.getId();
		String operation = request.getParameter("operation");
		
		UserModel model = new UserModel();
		
		if(operation.equals(OP_SAVE))
		{
			UserBean popBean = (UserBean) populateBean(request);
			try 
			{
				if(id > 0)
				{
					userBean.setFirstName(popBean.getFirstName());
					userBean.setLastName(popBean.getLastName());
					userBean.setGender(popBean.getGender());
					userBean.setMobileNo(popBean.getMobileNo());
					userBean.setDob(popBean.getDob());
					
					model.update(userBean);
				}
				ServletUtility.setBean(userBean, request);
				ServletUtility.setSuccessMessage("Profile has been updated Successfully", request);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (operation.equals(OP_CHANGE_MY_PASSWORD))
		{
			ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
			return;	
		}
		ServletUtility.forward(getView(), request, response);	
	}
	
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("MyProfileCtl populate bean method started");

		UserBean bean = new UserBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getStringToDate(request.getParameter("dob")));

		populateDTO(bean, request);
		
		return bean;
	}

	@Override
	protected String getView() 
	{
		return ORSView.MY_PROFILE_VIEW;
	}

}
