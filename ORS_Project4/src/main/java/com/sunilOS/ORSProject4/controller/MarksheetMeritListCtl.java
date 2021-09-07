package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.lang.management.OperatingSystemMXBean;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.MarksheetBean;
import com.sunilOS.ORSProject4.model.MarksheetModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "MarksheetMeritListCtl", urlPatterns = { "/ctl/MarksheetMeritListCtl" })
public class MarksheetMeritListCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(MarksheetMeritListCtl.class);
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("MarksheetMeritListCtl Do Get method started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		MarksheetModel mm = new MarksheetModel();
		try {
			List list = mm.getMeritList(pageNo, pageSize);
			ServletUtility.setList(list, request);;
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("MarksheetMeritListCtl Do Post method started");

		String operation = request.getParameter("operation");
		
		if(operation.equals(OP_BACK))
		{
			ServletUtility.redirect(ORSView.WELCOME_CTL, request, response);
			return;
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("MarksheetMeritListCtl populate bean method started");

		MarksheetBean bean = new MarksheetBean();
		return bean;
	}

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_MERIT_LIST_VIEW;
	}
}
