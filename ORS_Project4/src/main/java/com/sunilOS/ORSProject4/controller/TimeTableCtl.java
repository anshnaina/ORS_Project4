package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.TimeTableBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.CourseModel;
import com.sunilOS.ORSProject4.model.SubjectModel;
import com.sunilOS.ORSProject4.model.TimeTableModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "TimeTableCtl", urlPatterns = { "/ctl/TimeTableCtl" })
public class TimeTableCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log=Logger.getLogger(TimeTableCtl.class);

	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("TimeTableCtl validate method started");

		boolean pass = true;

		String examDate = request.getParameter("examDate");

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course name"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("subjectId"))) {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject name"));
			pass = false;

		}
		if (DataValidator.isNull(request.getParameter("semesterId"))) {
			request.setAttribute("semesterId", PropertyReader.getValue("error.require", "Semester"));
			pass = false;

		}
		if (DataValidator.isNull(examDate)) {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam date"));
			pass = false;
		} else if (!DataValidator.isDate(examDate)) {
			request.setAttribute("examDate", PropertyReader.getValue("error.date", "Exam date"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("examTime"))) {
			request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam time"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("description"))) {
			request.setAttribute("description", PropertyReader.getValue("error.require", "Description"));
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected void preload(HttpServletRequest request) 
	{
		log.debug("TimeTableCtl preload method started");

		SubjectModel subjectModel = new SubjectModel();
		CourseModel courseModel = new CourseModel();
		
		try 
		{
		List subjectList = subjectModel.list();
		List courseList = courseModel.list();
		
		request.setAttribute("subjectList", subjectList);
		
		request.setAttribute("courseList", courseList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("TimeTableCtl Do Get method started");

		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		TimeTableModel model = new TimeTableModel();
		
		if(id>0 || operation!=null)
		{
			try {
				TimeTableBean bean = model.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ServletUtility.forward(getView(), request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("TimeTableCtl Do Post method started");

		String operation = request.getParameter("operation");
		TimeTableModel model = new TimeTableModel();
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (operation.equals(OP_SAVE))
		{
			if (id > 0)
			{
				TimeTableBean bean = (TimeTableBean) populateBean(request);
				TimeTableBean bean4;

				try {

					bean4 = model.checkByExamTime(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
					bean.getExamDate(), bean.getExamTime());
					if (id > 0 && bean4 == null) {
						model.update(bean);
						ServletUtility.setBean(bean, request);
						ServletUtility.setSuccessMessage("Data is successfully updated", request);
					} else {
						bean = (TimeTableBean) populateBean(request);
						ServletUtility.setBean(bean, request);
						ServletUtility.setErrorMessage("Exam already exist!", request);
					}

				} catch (ApplicationException e) {
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
				} catch (DuplicateRecordException e) {
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Exam already exist!", request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
				else {

			TimeTableBean bean = (TimeTableBean) populateBean(request);

			TimeTableBean bean1;
			TimeTableBean bean2;
			TimeTableBean bean3;
			try {
				bean1 = model.checkByCourseName(bean.getCourseId(), bean.getExamDate());

				bean2 = model.checkBySubjectName(bean.getCourseId(), bean.getSubjectId(), bean.getExamDate());

				bean3 = model.checkBysemester(bean.getCourseId(), bean.getSubjectId(), bean.getSemester(),
						bean.getExamDate());

				if (bean1 == null && bean2 == null && bean3 == null) {

					long pk = model.add(bean);
					bean.setId(pk);
					ServletUtility.setSuccessMessage("Data is successfully saved", request);
				} else {
					bean = (TimeTableBean) populateBean(request);
					ServletUtility.setBean(bean, request);
					ServletUtility.setErrorMessage("Exam already exist!", request);
				}

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Exam already exist!", request);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		}
		else if (operation.equals(OP_RESET))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_CANCEL))
		{
			ServletUtility.redirect(ORSView.TIMETABLE_LIST_CTL, request, response);
			return;
		}
			
			ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("TimeTableCtl populate bean method started");

		TimeTableBean bean = new TimeTableBean();
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		bean.setExamDate(DataUtility.getStringToDate(request.getParameter("examDate")));
		bean.setExamTime(DataUtility.getString(request.getParameter("examTime")));
		bean.setSemester(DataUtility.getString(request.getParameter("semesterId")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean, request);

		return bean;
	
	}

	@Override
	protected String getView() {
		return ORSView.TIMETABLE_VIEW;
	}

}
