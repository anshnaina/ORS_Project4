package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.management.relation.Role;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.RoleBean;
import com.sunilOS.ORSProject4.model.RoleModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;


@WebServlet(name = "RoleListCtl", urlPatterns = { "/ctl/RoleListCtl" })
public class RoleListCtl extends BaseCtl{

	private static Logger log=Logger.getLogger(RoleCtl.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("RoleListCtl Do Get method started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		RoleModel rm = new RoleModel();
		RoleBean rb = (RoleBean) populateBean(request);
		try {
			List list = rm.search(rb, pageNo, pageSize);
			
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		log.debug("RoleListCtl Do Post method started");

		int pageNo = Integer.parseInt(req.getParameter("pageNo"));
		int pageSize = Integer.parseInt(req.getParameter("pageSize"));
		
		RoleModel rm = new RoleModel();
		RoleBean rb = (RoleBean) populateBean(req);
		String operation= req.getParameter("operation");
		String[] chk = req.getParameterValues("chk");
		
		if (operation.equals(RoleCtl.OP_DELETE))
		{
			try {
				if(chk!=null)
				{
				
					RoleBean delBean = new RoleBean();
				for(String id : chk)
				{
					delBean.setId(DataUtility.getLong(id));
					rm.delete(delBean);
					ServletUtility.setSuccessMessage("Selected record(s) deleted successfully", req);	
				}
				}
			else
			{
        		ServletUtility.setErrorMessage("Select at least one record", req);

			}
			
		}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			if (OP_BACK.equalsIgnoreCase(operation)) {
				ServletUtility.redirect(ORSView.ROLE_LIST_CTL, req, resp);
				return;
			}
		}
		
		if(operation.equals(RoleListCtl.OP_NEXT) || operation.equals(RoleListCtl.OP_PREVIOUS)||operation.equals(RoleListCtl.OP_SEARCH))
		{
		
			if(operation.equals(RoleListCtl.OP_SEARCH))
			{
				pageNo = 1;
			}
			else if(operation.equals(RoleListCtl.OP_NEXT))
			{
				pageNo++;

			}
			else if(operation.equals(RoleListCtl.OP_PREVIOUS))
			{
				pageNo--;
				
			}
		}
		else if (operation.equals(RoleListCtl.OP_RESET)) 
		{
			req.setAttribute("id", 0);

			ServletUtility.redirect(ORSView.ROLE_LIST_CTL, req, resp);
			return;
		}
		else if (operation.equals(OP_NEW)) 
		{
			ServletUtility.redirect(ORSView.ROLE_CTL, req, resp);
			return;
		}
		
		try {
			List roleList = rm.search(rb, pageNo, pageSize);

			ServletUtility.setList(roleList, req);
			
			if (!operation.equals(OP_DELETE))
			{
			if (roleList == null || roleList.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", req);
			}
			}
			ServletUtility.setPageNo(pageNo, req);
			ServletUtility.setPageSize(pageSize, req);
			ServletUtility.forward(getView(), req, resp);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}

	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("RoleListCtl populate bean method started");

		RoleBean bean = new RoleBean();
		bean.setRoleName(DataUtility.getString(request.getParameter("roleName")));
		return bean;
	}

	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("RoleListCtl preload method started");
		
		RoleModel rm = new RoleModel();
		
		try {
			List list = rm.list();
			request.setAttribute("roleList", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	protected String getView() {
		return ORSView.ROLE_LIST_VIEW;
	}
	


}
