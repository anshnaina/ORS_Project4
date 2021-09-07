package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.CourseBean;
import com.sunilOS.ORSProject4.bean.MarksheetBean;
import com.sunilOS.ORSProject4.bean.TimeTableBean;
import com.sunilOS.ORSProject4.model.CourseModel;
import com.sunilOS.ORSProject4.model.SubjectModel;
import com.sunilOS.ORSProject4.model.TimeTableModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "TimeTableListCtl", urlPatterns = { "/ctl/TimeTableListCtl" })
public class TimeTableListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log=Logger.getLogger(TimeTableListCtl.class);

	@Override
	protected void preload(HttpServletRequest request)
	{
		log.debug("TimeTableListCtl preload method started");

		TimeTableModel tm = new TimeTableModel();
		SubjectModel sm = new SubjectModel();
		CourseModel cm = new CourseModel();
		List timeTableList;
		List subjectList;
		List courseList;	
		try {
			timeTableList = tm.list();
			subjectList = sm.list();
			courseList = cm.list();
			request.setAttribute("timeTableList", timeTableList);
			request.setAttribute("subjectList", subjectList);
			request.setAttribute("courseList", courseList);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("TimeTableListCtl Do Get method started");

		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		TimeTableBean bean = (TimeTableBean) populateBean(request);
		TimeTableModel model = new TimeTableModel();
		
		try {
			List timeTableList = model.search(bean, pageNo, pageSize);
			if (timeTableList == null || timeTableList.size() == 0) {

				ServletUtility.setErrorMessage("No record found", request);

			}
			ServletUtility.setList(timeTableList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.debug("TimeTableListCtl Do Post method started");

		int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
		int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
		
		String operation = request.getParameter("operation");
		String[] chk = request.getParameterValues("chk");
		
		TimeTableBean bean = (TimeTableBean) populateBean(request);
		TimeTableModel model = new TimeTableModel();
		
		if(operation.equals(OP_DELETE))
		{
		try {
			if(chk!=null)
			{
				TimeTableBean delBean = new TimeTableBean();
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
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		}
	
		
	if(operation.equals(TimeTableListCtl.OP_PREVIOUS)||operation.equals(TimeTableListCtl.OP_NEXT)||operation.equals(TimeTableListCtl.OP_SEARCH))
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
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_NEW)) 
		{
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
	
	try {
		List TimeTableList = model.search(bean, pageNo, pageSize);
		
		if (!operation.equals(OP_DELETE))
		{
		if (TimeTableList == null || TimeTableList.size() == 0) 
		{
			ServletUtility.setErrorMessage("No record found", request);
		}
		}
		ServletUtility.setList(TimeTableList, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		
	} catch (Exception e) {
		e.printStackTrace();
	} 
	ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("TimeTableListCtl populate bean method started");

		TimeTableBean bean = new TimeTableBean();

		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));

		bean.setExamDate(DataUtility.getStringToDate(request.getParameter("examDate")));

		return bean;
	}

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_LIST_VIEW;
	}

	
}
