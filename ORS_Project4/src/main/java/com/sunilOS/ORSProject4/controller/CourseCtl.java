package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.CourseBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.CourseModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

/**
 * Course functionality ctl.to perform add,delete ,update operation
 * @author Anshul
 *
 */

@WebServlet(name = "CourseCtl", urlPatterns = { "/ctl/CourseCtl" })
public class CourseCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CourseCtl.class);
	
	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("CourseCtl validate method started");

		
		boolean pass=true;
		
		if(DataValidator.isNull(request.getParameter("courseName"))){
			request.setAttribute("courseName",PropertyReader.getValue("error.require","Course name"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("courseDescription"))){
			request.setAttribute("courseDescription",PropertyReader.getValue("error.require","Description"));
			pass=false;
		}
		if(DataValidator.isNull(request.getParameter("courseDuration"))){
			request.setAttribute("courseDuration",PropertyReader.getValue("error.require","Duration"));
			pass=false;
		}
		
		return pass;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("CourseCtl Do Get method started");

		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		CourseModel cm = new CourseModel();
		
		if(id>0 || operation!=null)
		{
			try {
				CourseBean cb = cm.findByPK(id);
				ServletUtility.setBean(cb, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ServletUtility.forward(getView(), request, response);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("CourseCtl Do Post method started");

		CourseModel cm = new CourseModel();
		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		CourseBean cb = (CourseBean) populateBean(request);
		if(operation.equals(OP_SAVE))
		{
			try {
				if(id>0)
				{
					cm.update(cb);
					ServletUtility.setBean(cb, request);
					ServletUtility.setSuccessMessage("Record updated successfully", request);
				}
				else
				{
					cm.add(cb);
					ServletUtility.setBean(cb, request);
					ServletUtility.setSuccessMessage("Record added successfully", request);
				}
			}
			catch(ApplicationException e){
				 e.printStackTrace();
				 ServletUtility.handleException(e, request, response);
				 return;
			 }catch(DuplicateRecordException e){
				 ServletUtility.setBean(cb, request);
				 ServletUtility.setErrorMessage("Course already exists",request);
				 
			 } catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		else if (operation.equals(OP_RESET))
		{
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_CANCEL))
		{
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		}
			
			ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("CourseCtl PopulateBean method started");

		CourseBean bean = new CourseBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		bean.setCourseDescription(DataUtility.getString(request.getParameter("courseDescription")));
		bean.setCourseDuration(DataUtility.getString(request.getParameter("courseDuration")));
		
		populateDTO(bean, request);
		
		return bean;
	}

	@Override
	protected String getView() {
		
		return ORSView.COURSE_VIEW;
	}

	
}
