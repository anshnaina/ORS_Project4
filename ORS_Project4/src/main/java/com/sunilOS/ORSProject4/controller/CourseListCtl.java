package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.CourseBean;
import com.sunilOS.ORSProject4.bean.FacultyBean;
import com.sunilOS.ORSProject4.model.CourseModel;
import com.sunilOS.ORSProject4.utility.ServletUtility;

/**
 * Course list functionality ctl.Toperform search and delete,show list operation
 * @author Anshul
 *
 */

@WebServlet(name = "CourseListCtl", urlPatterns = { "/ctl/CourseListCtl" })
public class CourseListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(CourseListCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request) {
		
		log.debug("CourseListCtl Preload method started");

		CourseModel model = new CourseModel();
		
		List courseList;
		try {
			courseList = model.list();
			request.setAttribute("courseList", courseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("CourseListCtl Do Get method started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		CourseBean bean = (CourseBean) populateBean(request);
		CourseModel model = new CourseModel();
		
		try {
			List courseList = model.search(bean, pageNo, pageSize);
			if (courseList == null || courseList.size() == 0) {

				ServletUtility.setErrorMessage("No record found", request);

			}
			ServletUtility.setList(courseList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		ServletUtility.forward(getView(), request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("CourseListCtl Do Post method started");
		
		CourseBean bean = (CourseBean) populateBean (request);
		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		String operation = request.getParameter("operation");
		String[] chk = request.getParameterValues("chk");
		CourseModel model = new CourseModel();
		
		if(operation.equals(OP_DELETE))
		{
		try {
			if(chk!=null)
			{
				CourseBean delBean = new CourseBean();
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
				ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
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
		request.setAttribute("id", 0);
		ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
		return;
	}
	
	else if (operation.equals(OP_NEW)) 
	{
		ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
		return;
	}
	
	try {
		List courseList = model.search(bean, pageNo, pageSize);
		if (!operation.equals(OP_DELETE))
		{
		if (courseList == null || courseList.size() == 0) 
		{
			ServletUtility.setErrorMessage("No record found", request);
		}
		}
		ServletUtility.setList(courseList, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		
	} catch (Exception e) {
		e.printStackTrace();
	} 
	ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("CourseListCtl PopulateBean method started");

		CourseBean bean = new CourseBean();

		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		
		bean.setCourseDuration(DataUtility.getString(request.getParameter("courseDuration")));

		return bean;
	}
	
	@Override
	protected String getView() {
		return ORSView.COURSE_LIST_VIEW;
	}

}
