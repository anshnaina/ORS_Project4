package com.sunilOS.ORSProject4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

/**
 * Change password operation functionality perform
 * @author Anshul
 *
 */

@WebServlet(name = "ChangePasswordCtl", urlPatterns = { "/ctl/ChangePasswordCtl" })
public class ChangePasswordCtl extends BaseCtl 
{
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ChangePasswordCtl.class);
	
	@Override
	protected boolean validate(HttpServletRequest request){
		
		log.debug("ChangePasswordCtl validate method starts");
		boolean pass=true;		
		
		if(DataValidator.isNull(request.getParameter("oldPassword"))){
			request.setAttribute("oldPassword",PropertyReader.getValue("error.require","Old password"));
			pass=false;
		}else if (!DataValidator.isPassword(request.getParameter("oldPassword"))) {
			request.setAttribute("oldPassword", "Please Enter valid Password");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("newPassword"))){
			request.setAttribute("newPassword",PropertyReader.getValue("error.require","New password"));
			pass=false;
		}else if (!DataValidator.isPassword(request.getParameter("newPassword"))) {
			request.setAttribute("newPassword", "Please Enter vaild Password");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("confirmPassword"))){
			request.setAttribute("confirmPassword",PropertyReader.getValue("error.require","Confirm password"));
			pass=false;
		}
		if(!request.getParameter("newPassword").equals(request.getParameter("confirmPassword"))&&!"".equals(request.getParameter("confirmPassword"))){
			ServletUtility.setErrorMessage("New and confirm passwords not matched", request);
			pass=false;
		}
		return pass;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("ChangePasswordCtl Do Get method starts");
		ServletUtility.forward(getView(), request, response);
	}


	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("ChangePasswordCtl Do Post method starts");

			HttpSession session = request.getSession(true);
			UserBean userBean = (UserBean) session.getAttribute("user");
			UserBean popBean = (UserBean) populateBean(request);
			
			long id = userBean.getId();
			
			UserModel model = new UserModel();
			
			String operation = request.getParameter("operation");
			String newPassword = (String) request.getParameter("newPassword");
			
			
			if(operation.equals(OP_SAVE))
			{
				try
				{
					boolean flag = model.changePassword(id, popBean.getPassword(), newPassword);
					if(flag == true)
					{
						popBean = model.findByEmail(userBean.getEmail());
						session.setAttribute("user", popBean);
						ServletUtility.setBean(popBean, request);
						ServletUtility.setSuccessMessage("Password has been changed successfully", request);
					}
					
				} 
				catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;

				}
				catch (RecordNotFoundException e) {
					ServletUtility.setErrorMessage("Old PassWord is Invalid", request);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			 else if (operation.equals(OP_RESET)) 
				{
					ServletUtility.redirect(ORSView.CHANGE_PASSWORD_CTL, request, response);
					return;
				}
			ServletUtility.forward(getView(), request, response);
	}

	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("ChangePasswordCtl populate bean method starts");

		UserBean bean = new UserBean();
		bean.setPassword(DataUtility.getString(request.getParameter("oldPassword")));
		bean.setConfirmPassword(DataUtility.getString(request.getParameter("confirmPassword")));
		
		populateDTO(bean, request);
		
		return bean;
	}
	
	@Override
	protected String getView() {
		
		return ORSView.CHANGE_PASSWORD_VIEW;
	}
	
}
	