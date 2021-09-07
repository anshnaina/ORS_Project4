package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.StudentBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.CollegeModel;
import com.sunilOS.ORSProject4.model.StudentModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "StudentCtl", urlPatterns = { "/ctl/StudentCtl" })
public class StudentCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(StudentCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

			log.debug("StudentCtl validate method started");
		
		boolean pass = true;

		String operation = DataUtility.getString(request.getParameter("operation"));
		String email = request.getParameter("email");
		String dob = request.getParameter("dob");

		if (DataValidator.isNull(request.getParameter("collegeId"))) {
			request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
			pass = false;
		} else if (DataValidator.isSpecial(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.specialchar", "First Name"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.alphabet", "First Name"));
			pass = false;
		} else if (DataValidator.isWhiteSpace(request.getParameter("firstName"))) {
			request.setAttribute("firstName", PropertyReader.getValue("error.whitespace", "firstName"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last name"));
			pass = false;
		} else if (!DataValidator.isName2(request.getParameter("lastName"))) {
			request.setAttribute("lastName", PropertyReader.getValue("error.alphabet", "Last name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile no."));
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile number must be in numbers");
			pass = false;
		} else if (!DataValidator.isMob(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "Mobile number must be starts with 7/8/9");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.phoneno", "Mobile no."));
			pass = false;
		}
		if (DataValidator.isNull(email)) {
			request.setAttribute("email", PropertyReader.getValue("error.require", "Email id"));
			pass = false;
		} else if (!DataValidator.isEmail(email)) {
			request.setAttribute("email", PropertyReader.getValue("error.email", "Email id"));
			pass = false;
		}
		if (DataValidator.isNull(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.require", "Date of birth"));
			pass = false;
		} else if (!DataValidator.ageLimit(dob)) {
			request.setAttribute("dob", PropertyReader.getValue("error.validate", "Age"));
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected void preload(HttpServletRequest request) 
	{
		log.debug("StudentCtl preload method started");

		CollegeModel model = new CollegeModel();
		try {
			List collegeList = model.list();
			request.setAttribute("collegeList", collegeList);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("StudentCtl Do Get method started");

		StudentModel sm = new StudentModel();
		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(operation != null || id >0)
		{
			try {
				StudentBean bean = sm.findByPK(id);
				ServletUtility.setBean(bean, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ServletUtility.forward(getView(), request, response);
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.debug("StudentCtl Do Post method started");

		String operation = DataUtility.getString(request.getParameter("operation"));
		long id = DataUtility.getLong(request.getParameter("id"));
		StudentModel model = new StudentModel();
		
		if(operation.equals(OP_SAVE))
		{
			StudentBean bean = (StudentBean) populateBean(request);
			
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
					ServletUtility.setSuccessMessage("Record added successfully", request);
				}
				} 
			catch (ApplicationException e) 
			{
				ServletUtility.handleException(e, request, response);
				return;
			} 
			catch (DuplicateRecordException e) 
			{
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Student email id already exists", request);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		else if (operation.equals(OP_CANCEL))
		{
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_RESET)) 
		{
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);
		}
		
	@Override
	protected BaseBean populateBean(HttpServletRequest request) 
	{
		log.debug("StudentCtl populate bean method started");

		StudentBean bean = new StudentBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));		
		bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
		bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
		bean.setDob(DataUtility.getStringToDate(request.getParameter("dob")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));
		bean.setEmail(DataUtility.getString(request.getParameter("email")));

		populateDTO(bean, request);

		return bean;
	}

	@Override
	protected String getView() {
		return ORSView.STUDENT_VIEW;
	}

}
