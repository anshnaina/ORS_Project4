package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.UserBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.model.RoleModel;
import com.sunilOS.ORSProject4.model.UserModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "UserListCtl", urlPatterns = { "/ctl/UserListCtl" })
public class UserListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(UserListCtl.class);


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.debug("UserListCtl Do Get method started");

		UserModel um = new UserModel();
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		UserBean bean = (UserBean) populateBean(request);
	try {
			List userList = um.search(bean, pageNo, pageSize);
			
			ServletUtility.setList(userList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
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
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("UserListCtl Do Post method started");

		UserModel um = new UserModel();
		UserBean bean = (UserBean) populateBean(request);
		
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String []chk = request.getParameterValues("chk");
		
		String operation = request.getParameter("operation");
		
		if(operation.equals(OP_DELETE))
		{
			try {
				
			if(chk!=null)
			{
				UserBean delBean = new UserBean();
				for(String id : chk)
				{
					delBean.setId(DataUtility.getLong(id));
					um.delete(delBean);
					ServletUtility.setSuccessMessage("Selected record(s) deleted successfully", request);
				}
			}
			else 
			{
				ServletUtility.setErrorMessage("Select at least one record", request);
			}
			if (OP_BACK.equalsIgnoreCase(operation)) {
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			}
			}
			 catch (ApplicationException e) 
			{
					log.error(e);
					e.printStackTrace();
					ServletUtility.handleException(e, request, response);
					return;
				}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if(operation.equals(UserListCtl.OP_PREVIOUS) || operation.equals(UserListCtl.OP_NEXT) || operation.equals(UserListCtl.OP_SEARCH))
		{
			if(operation.equals(UserListCtl.OP_SEARCH))
			{
				pageNo = 1;
			}
			if(operation.equals(UserListCtl.OP_PREVIOUS))
			{
				pageNo--;
			}
			if(operation.equals(UserListCtl.OP_NEXT))
			{
				pageNo++;
			}
		}	
		else if (operation.equals(OP_RESET)) 
		{
			request.setAttribute("id", 0);
			ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_NEW)) 
		{
			ServletUtility.redirect(ORSView.USER_CTL, request, response);
			return;
		}
		try {
			List userList = um.search(bean, pageNo, pageSize);
					
			if (!operation.equals(OP_DELETE))
			{
			if (userList == null || userList.size() == 0) {
				ServletUtility.setErrorMessage("No record found", request);
			}
			}
			ServletUtility.setList(userList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("UserListCtl populate bean method started");

		UserBean bean = new UserBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
		return bean;
	}
	
	@Override
	protected void preload(HttpServletRequest request) 
	{
		log.debug("UserListCtl preload method started");

		UserModel um = new UserModel();
		RoleModel rm = new RoleModel();
	try {
			List userList = um.list();
			request.setAttribute("userList", userList);
			List roleList = rm.list();
			request.setAttribute("roleList", roleList);
		}
	
	catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected String getView() {
		return ORSView.USER_LIST_VIEW;
	}

}
