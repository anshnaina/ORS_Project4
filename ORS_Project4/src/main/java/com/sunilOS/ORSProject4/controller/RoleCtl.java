package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.CollegeBean;
import com.sunilOS.ORSProject4.bean.RoleBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.RoleModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;


@WebServlet(name = "RoleCtl", urlPatterns = { "/ctl/RoleCtl" })
public class RoleCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log=Logger.getLogger(RoleCtl.class);

	
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("RoleCtl validate method started");

		boolean pass = true;
		String roleName = request.getParameter("roleName");
		
		if(DataValidator.isNull(roleName))
		{
			request.setAttribute("roleName", PropertyReader.getValue("error.require" , "Role name"));
			pass = false;
		}
		else if(DataValidator.isSpecial(roleName))
		{
			request.setAttribute("roleName", PropertyReader.getValue("error.specialchar" , "Role name"));
			pass = false;
		}
		else if (!DataValidator.isNameWithSpace(roleName)) 
		{
			request.setAttribute("roleName", PropertyReader.getValue("error.alphabet", "Role name"));
			pass = false;
		} 
		else if (DataValidator.isWhiteSpace(roleName)) 
		{
			request.setAttribute("roleName", PropertyReader.getValue("error.whitespace", "Role name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("description")))
		{
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}
		
		return pass;
	}


	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("RoleCtl populate bean method started");

		RoleBean bean = new RoleBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRoleName(DataUtility.getString(request.getParameter("roleName")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean, request);

		return bean;
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("RoleCtl Do Get method started");

		RoleBean bean = new RoleBean();
		RoleModel model = new RoleModel();

		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		
		if(id>0 || operation!=null)
		{
			try {
				bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		log.debug("RoleCtl Do Post method started");

		RoleModel model = new RoleModel();
		
		String operation = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (operation.equals (OP_SAVE))
		{
			RoleBean bean = (RoleBean) populateBean(request);
		try {
				if(id > 0)
				{
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Role updated successfully", request);
				}
				else
				{
					model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Role added successfully", request);
				}	
			}
		catch (ApplicationException e) 
		{
			ServletUtility.handleException(e, request, response);
			return;
		} 
		catch (DuplicateRecordException e) 
		{
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Role already exists", request);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
				
		}
		else if (operation.equals(OP_CANCEL)) 
		{
			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, request, response);
			return;
		}
		else if(operation.equals(OP_RESET))
		{
			ServletUtility.redirect(ORSView.ROLE_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}

	@Override
	protected String getView() 
	{
		return ORSView.ROLE_VIEW;
	}
	
}
