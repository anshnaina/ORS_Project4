package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.CourseBean;
import com.sunilOS.ORSProject4.bean.SubjectBean;
import com.sunilOS.ORSProject4.model.CourseModel;
import com.sunilOS.ORSProject4.model.SubjectModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "SubjectListCtl", urlPatterns = { "/ctl/SubjectListCtl" })
public class SubjectListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log=Logger.getLogger(SubjectListCtl.class);

	@Override
	protected void preload(HttpServletRequest request) {
	
		log.debug("SubjectListCtl preload method started");

		SubjectModel model = new SubjectModel();
		
		List subjectList;
		try {
			subjectList = model.list();
			request.setAttribute("subjectList", subjectList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("SubjectListCtl Do Get method started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		SubjectBean bean = (SubjectBean) populateBean(request);
		SubjectModel model = new SubjectModel();
		
		try {
			List subjectList = model.search(bean, pageNo, pageSize);
			if (subjectList == null || subjectList.size() == 0) {

				ServletUtility.setErrorMessage("No record found", request);

			}
			ServletUtility.setList(subjectList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		ServletUtility.forward(getView(), request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("SubjectListCtl Do Post method started");

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		String operation = request.getParameter("operation");
		String[] chk = request.getParameterValues("chk");
		SubjectModel model = new SubjectModel();
		
		if(operation.equals(OP_DELETE))
		{
		try {
			if(chk!=null)
			{
				SubjectBean delBean = new SubjectBean();
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
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		}
	
		
	if(operation.equals(CourseListCtl.OP_PREVIOUS)||operation.equals(CourseListCtl.OP_NEXT)||operation.equals(CourseListCtl.OP_SEARCH))
		{
		if(operation.equals(OP_SEARCH))
		{
			pageNo = 1; 
		}
		if(operation.equals(OP_NEXT))
		{
			pageNo++;
		}
		if(operation.equals(OP_PREVIOUS))
		{
			pageNo--;
		}
		}
	else if (operation.equals(OP_RESET)) 
	{
		ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
		return;
	}
	else if (operation.equals(OP_NEW)) 
	{
		ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
		return;
	}
	SubjectBean bean = (SubjectBean) populateBean(request);
	
	try {
		List subjectList = model.search(bean, pageNo, pageSize);
		if (!operation.equals(OP_DELETE))
		{
		if (subjectList == null || subjectList.size() == 0) 
		{
			ServletUtility.setErrorMessage("No record found", request);
		}
		}
		ServletUtility.setList(subjectList, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		
	} catch (Exception e) {
		e.printStackTrace();
	} 
	ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("SubjectListCtl populate bean method started");
		
		SubjectBean bean = new SubjectBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		return bean;
	}
		
	
	@Override
	protected String getView() 
	{
		return ORSView.SUBJECT_LIST_VIEW;
	}

}
