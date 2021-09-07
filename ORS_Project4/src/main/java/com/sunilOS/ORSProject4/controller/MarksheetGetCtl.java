package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.MarksheetBean;
import com.sunilOS.ORSProject4.model.MarksheetModel;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "MarksheetGetCtl", urlPatterns = { "/ctl/MarksheetGetCtl" })
public class MarksheetGetCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(MarksheetGetCtl.class);

	public static final String OP_PRINT = "Print";
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("MarksheetGetCtl validate method started");

		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("rollNo"))){
			request.setAttribute("rollNo",PropertyReader.getValue("error.require", "Roll number"));
			pass=false;
		}		
		return pass;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("MarksheetGetCtl Do Get method started");

		
		ServletUtility.forward(getView(), request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("MarksheetGetCtl Do Post method started");
		
		MarksheetBean bean = (MarksheetBean) populateBean(request);
		MarksheetModel mm = new MarksheetModel();
		String operation = request.getParameter("operation");
		
	if(operation.equals(OP_GO)) 
		{
		try {
			bean = mm.findByRollNo(bean.getRollNo());
			if(bean!=null)
			{
				ServletUtility.setBean(bean, request);	
			}
			else
			{
				ServletUtility.setErrorMessage("Roll no. does not exist",request);
			}
			} 
		catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	else if (operation.equals(OP_PRINT))
	{
		ServletUtility.setBean(bean, request);
		ServletUtility.redirect(ORSView.MARKSHEET_GET_CTL, request, response);
		return;
	}
		ServletUtility.forward(getView(), request, response);
	}

	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("MarksheetGetCtl populate bean method started");

		MarksheetBean bean = new MarksheetBean();
		bean.setRollNo(request.getParameter("rollNo"));
		return bean;
	}


	@Override
	protected String getView() {
		return ORSView.MARKSHEET_GET_VIEW;
	}
	
}
