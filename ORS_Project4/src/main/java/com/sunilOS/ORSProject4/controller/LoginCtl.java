package com.sunilOS.ORSProject4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.RoleBean;
import com.sunilOS.ORSProject4.bean.UserBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.model.RoleModel;
import com.sunilOS.ORSProject4.model.UserModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

/**
 * login functionality controller. perform login operation
 * @author Anshul
 *
 */

@WebServlet(name = "LoginCtl", urlPatterns = { "/LoginCtl" })
public class LoginCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(LoginCtl.class);

	public static final String OP_SIGN_IN = "SignIn";
	public static final String OP_SIGN_UP = "SignUp";
	public static final String OP_LOG_OUT = "LogOut";

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("LoginCtl validate method started");

		boolean pass = true;

		String operation = request.getParameter("operation");

		if (OP_LOG_OUT.equals(operation) || OP_SIGN_UP.equals(operation)) {
			return pass;
		}

		String email = request.getParameter("email");
		if (OP_SIGN_IN.equals(operation)) {
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
			}
		}
		return pass;

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		log.debug("LoginCtl populate bean method started");

		UserBean bean = new UserBean();
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setPassword(DataUtility.getString(request.getParameter("password")));

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("LoginCtl Do Get method started");

		HttpSession session = request.getSession();
		String operation = DataUtility.getString(request.getParameter("operation"));

		if (OP_LOG_OUT.equals(operation)) {
			session = request.getSession(false);
			session.invalidate();
			request.setAttribute("success", "YOU HAVE BEEN LOGGED-OUT SUCCESSFULLY");
			ServletUtility.forward(getView(), request, response);
			return;
		}

		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		log.debug("LoginCtl Do Post method started");

		HttpSession session = request.getSession();
		String operation = DataUtility.getString(request.getParameter("operation"));
		UserModel userModel = new UserModel();
		RoleModel roleModel = new RoleModel();
		
		if (OP_SIGN_IN.equalsIgnoreCase(operation)) 
		{
			UserBean userBean = (UserBean) populateBean(request);
			
			try {
				userBean = userModel.authenticate(userBean.getEmail(), userBean.getPassword());
				if(userBean!=null)
					{
					session.setAttribute("user", userBean);
					long roleId = userBean.getRoleId();
					RoleBean roleBean = roleModel.findByPK(roleId);
					if(roleBean != null)
					{
					session.setAttribute("role", roleBean);
					}
					// Code of The URI
					String str = (String) session.getAttribute("URI");
					if (str == null || "null".equalsIgnoreCase(str)) {
						ServletUtility.forward(ORSView.WELCOME_VIEW, request, response);
						return;
					} else {
						ServletUtility.redirect(str, request, response);
						return;
					}}
				else {
					userBean = (UserBean) populateBean(request);
					ServletUtility.setBean(userBean, request);
					ServletUtility.setErrorMessage("Invalid Login Id and Password", request);
				}
			} catch (ApplicationException e) 
				{
					log.error(e);
					e.printStackTrace();
					ServletUtility.handleException(e, request, response);
					return;
				}	
			catch (Exception e) 
				{
					e.printStackTrace();
					return;
				}
		}
			else if (OP_SIGN_UP.equalsIgnoreCase(operation)) 
			{
				ServletUtility.redirect(ORSView.USER_REGISTRATION_CTL, request, response);
				return;
			}			
			ServletUtility.forward(getView(), request, response);	
		}

	@Override
	protected String getView() {
		return ORSView.LOGIN_VIEW;
	}

}
