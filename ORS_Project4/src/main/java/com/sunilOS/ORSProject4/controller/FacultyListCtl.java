package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.FacultyBean;
import com.sunilOS.ORSProject4.model.CollegeModel;
import com.sunilOS.ORSProject4.model.FacultyModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;


@WebServlet(name = "FacultyListCtl", urlPatterns = { "/ctl/FacultyListCtl" })
public class FacultyListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	private static Logger log=Logger.getLogger(FacultyListCtl.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("FacultyListCtl Do Get method started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		FacultyBean bean = (FacultyBean) populateBean(request);
		FacultyModel model = new FacultyModel();
	try {
			List facultyList = model.search(bean, pageNo, pageSize);
			
			ServletUtility.setList(facultyList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);	
		} 
	catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("FacultyListCtl Do Post method started");

		FacultyModel fm = new FacultyModel();
		FacultyBean bean = (FacultyBean) populateBean (request);
		
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String[] chk = request.getParameterValues("chk");
		
		String operation = request.getParameter("operation");
		
		if(operation.equals(OP_DELETE))
			{
			try {
				if(chk!=null)
				{
					FacultyBean delBean = new FacultyBean();
					for(String id : chk)
					{
						delBean.setId(DataUtility.getLong(id));
						fm.delete(delBean);;
						ServletUtility.setSuccessMessage("Selected record(s) deleted successfully", request);
					}
				}
				else
				{
            		ServletUtility.setErrorMessage("Select at least one record", request);

				}
				if (OP_BACK.equalsIgnoreCase(operation)) {
					ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
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
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_NEW)) 
		{
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		}
		
		try {
			List facultyList = fm.search(bean, pageNo, pageSize);
			
			if (!operation.equals(OP_DELETE))
			{
			if (facultyList == null || facultyList.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}	
			}
			ServletUtility.setList(facultyList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void preload(HttpServletRequest request)  
	{
		
		log.debug("FacultyListCtl preload method started");

		CollegeModel cm = new CollegeModel();
		FacultyModel fm = new FacultyModel();
		try {
			List collegeList = cm.list();
			request.setAttribute("collegeList", collegeList);
			List facultyList = fm.list();
			request.setAttribute("facultyList", facultyList);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("FacultyListCtl populate bean method started");

		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));

		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));

		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));

		return bean;
		
	}
	@Override
	protected String getView() {
		return ORSView.FACULTY_LIST_VIEW;
	}
	
	

}
