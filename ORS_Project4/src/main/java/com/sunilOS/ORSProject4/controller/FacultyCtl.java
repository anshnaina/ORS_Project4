package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.FacultyBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.CollegeModel;
import com.sunilOS.ORSProject4.model.CourseModel;
import com.sunilOS.ORSProject4.model.FacultyModel;
import com.sunilOS.ORSProject4.model.SubjectModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "FacultyCtl", urlPatterns = { "/ctl/FacultyCtl" })
public class FacultyCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(FacultyCtl.class);
	
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("FacultyCtl validate method started");

		boolean pass = true;

		String email = request.getParameter("email");
		String dob = request.getParameter("dob");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String joiningDate = request.getParameter("joiningDate");

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("courseId"))) {
			request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course name"));
			pass = false;
		}

		if (DataValidator.isNull(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First name"));
			pass = false;
		} else if (DataValidator.isSpecial(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.specialchar", "First name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.alphabet", "First name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(firstName)) {
			request.setAttribute("firstName", PropertyReader.getValue("error.whitespace", "First name"));
			pass = false;
		}
		if (DataValidator.isNull(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last name"));
			pass = false;
		} else if (DataValidator.isSpecial(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.specialchar", "Last name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.alphabet", "Last name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(lastName)) {
			request.setAttribute("lastName", PropertyReader.getValue("error.whitespace", "Last name"));
			pass = false;
		}
		if (DataValidator.isNull(joiningDate)) {
			request.setAttribute("joiningDate", PropertyReader.getValue("error.require", "Joining date"));
			pass = false;
		}
		
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of birth"));
			pass = false;
		} else if (!DataValidator.ageLimit(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.validate", "Age"));
			pass = false;
		}
		if (DataValidator.isNull(email)) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email id"));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			request.setAttribute("email", PropertyReader.getValue("error.email", " Email id"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile no"));
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.phoneno", "Mobile no."));
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile Number must be numbers");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("gender"))) {
			request.setAttribute("gender", PropertyReader.getValue("error.require", " Gender"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("qualification"))) {

			request.setAttribute("qualification", PropertyReader.getValue("error.require", " Qualification"));

			pass = false;

		}

		return pass;
	}
	
	@Override
	protected void preload(HttpServletRequest request) 
	{
		
		log.debug("FacultyCtl preload method started");

		CollegeModel collegeModel = new CollegeModel();
		CourseModel courseModel = new CourseModel();
		
		try 
		{
		List collegeList = collegeModel.list();
		List courseList = courseModel.list();
		
		request.setAttribute("collegeList", collegeList);
		
		request.setAttribute("courseList", courseList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("FacultyCtl populate bean method started");

		FacultyBean bean = new FacultyBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setJoiningDate(DataUtility.getStringToDate(request.getParameter("joiningDate")));
		bean.setQualification(DataUtility.getString(request.getParameter("qualification")));
		bean.setGender(DataUtility.getString(request.getParameter("gender")));
		bean.setDob(DataUtility.getStringToDate(request.getParameter("dob")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		populateDTO(bean, request);
		
		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("FacultyCtl Do Get method started");

		FacultyModel fm = new FacultyModel();
		
		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
			
		if(id>0 || operation!=null)
		{
		 try {
				FacultyBean bean = fm.findByPK(id);
				ServletUtility.setBean(bean, request);
			 } 
			 catch (Exception e) 
		 	{
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("FacultyCtl Do Post method started");

		FacultyModel model = new FacultyModel();
		
		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));

		if(operation.equals(OP_SAVE))
		{
			FacultyBean bean = (FacultyBean) populateBean(request);
		try {
				if(id>0)
				{
					model.update(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Record updated successfully", request);					
				}
				else
				{
					model.add(bean);
					ServletUtility.setBean(bean, request);
					ServletUtility.setSuccessMessage("Record Added successfully", request);
				}
			}	
			catch (ApplicationException e) {
			ServletUtility.handleException(e, request, response);
			return;
			} 
			catch (DuplicateRecordException e) {
			ServletUtility.setBean(bean, request);
			ServletUtility.setErrorMessage("Faculty already exists", request);
			}	
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (operation.equals(OP_CANCEL)) {
			ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
			return;

		} else if (operation.equals(OP_RESET)) {
			ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
	}
	

	@Override
	protected String getView() {
		return ORSView.FACULTY_VIEW;
	}

	
}
