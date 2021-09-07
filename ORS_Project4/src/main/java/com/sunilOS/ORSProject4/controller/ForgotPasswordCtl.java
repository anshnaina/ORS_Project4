package com.sunilOS.ORSProject4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.UserBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.RecordNotFoundException;
import com.sunilOS.ORSProject4.model.UserModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;


@WebServlet(name = "ForgotPasswordCtl", urlPatterns = { "/ForgotPasswordCtl" })
public class ForgotPasswordCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log=Logger.getLogger(ForgotPasswordCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("ForgotPasswordCtl validate method started");

		boolean pass = true;
		String email = request.getParameter("email");
		
		if(DataValidator.isNull(email))
		{
			request.setAttribute("email", PropertyReader.getValue("error.require" , "Email Id"));
			pass = false;
		}
		else if(!DataValidator.isEmail(email))
		{
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email Id"));
			pass = false;
		}
		return pass;
	}


	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("ForgotPasswordCtl populate bean method started");

		UserBean bean = new UserBean();
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		return bean;
	}

	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.debug("ForgotPasswordCtl Do Get method started");

		ServletUtility.forward(getView(), req, resp);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		boolean flag;
		
		log.debug("ForgotPasswordCtl Do Post method started");

		String operation = DataUtility.getString(request.getParameter("operation"));
		
		UserBean bean = (UserBean) populateBean(request);
		
		UserModel um = new UserModel();
		
		if (OP_GO.equalsIgnoreCase(operation))
		{
			try {
				
				um.forgotPassword(bean.getEmail());				
				ServletUtility.setSuccessMessage("Password has been sent to your email id", request);
			}
			catch (RecordNotFoundException e) 
			{	
				ServletUtility.setErrorMessage(e.getMessage(), request);
			} 
			catch (ApplicationException e) 
			{
				ServletUtility.handleException(e, request, response);
				return;
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			ServletUtility.forward(getView(), request, response);
		}
		}


	@Override
	protected String getView()
	{
		return ORSView.FORGOT_PASSWORD_VIEW;
	}
}
