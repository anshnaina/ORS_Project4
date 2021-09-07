package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.management.MBeanServerDelegateMBean;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.MarksheetBean;
import com.sunilOS.ORSProject4.model.MarksheetModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "MarksheetListCtl", urlPatterns = { "/ctl/MarksheetListCtl" })
public class MarksheetListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(MarksheetListCtl.class);

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("MarksheetListCtl Do Get method started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
				
		MarksheetBean bean = (MarksheetBean)populateBean(request);
				
		MarksheetModel model = new MarksheetModel();
		try {
			List marksheetList = model.search(bean, pageNo, pageSize);
		
			ServletUtility.setList(marksheetList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("MarksheetListCtl Do Post method started");

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		
		MarksheetModel model = new MarksheetModel();
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		
		String operation = request.getParameter("operation");
		String [] chk = request.getParameterValues("chk");
		if(operation.equals(MarksheetListCtl.OP_DELETE))
		{
			try {
			if(chk!=null)
			{
				MarksheetBean delBean = new MarksheetBean();
				for(String id : chk)
					{
						delBean.setId(DataUtility.getLong(id));
						model.delete(delBean);
						ServletUtility.setSuccessMessage("Selected record(s) deleted successfully", request);
					} 
			}
			else
			{
        		ServletUtility.setErrorMessage("Select at least one record", request);

			}
			if (OP_BACK.equalsIgnoreCase(operation)) {
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
			
		
		if(operation.equals(OP_PREVIOUS)||operation.equals(OP_NEXT)||operation.equals(OP_SEARCH))
		{
			if(operation.equals(OP_SEARCH))
			{
				pageNo = 1;
			}
			if(operation.equals(OP_PREVIOUS))
			{
				pageNo--;
			}
			if(operation.equals(OP_NEXT))
			{
				pageNo++;
			}
		}
		else if (operation.equals(OP_RESET)) 
		{
			request.setAttribute("id", 0);
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_NEW)) 
		{
			ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_BACK)) 
		{
			ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
			return;
		}
		try {
			List marksheetList = model.search(bean, pageNo, pageSize);
			ServletUtility.setList(marksheetList, request);
		
			if (!operation.equals(OP_DELETE))
			{
			if (marksheetList == null || marksheetList.size() == 0)
				{		
				ServletUtility.setErrorMessage("No Record Found", request);
				}
			}
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (Exception e) {
			
			e.printStackTrace();
		}	
	}
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("MarksheetListCtl populate bean method started");

		MarksheetBean bean = new MarksheetBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("studentName")));
		return bean;
	}

	@Override
	protected void preload(HttpServletRequest request) {
	
		log.debug("MarksheetListCtl preload method started");

		MarksheetModel model = new MarksheetModel();
		try {
			List marksheetList = model.list();
			request.setAttribute("marksheetList", marksheetList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String getView() {

		return ORSView.MARKSHEET_LIST_VIEW;
	}

}
