package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.SubjectBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.CourseModel;
import com.sunilOS.ORSProject4.model.SubjectModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "SubjectCtl", urlPatterns = { "/ctl/SubjectCtl" })
public class SubjectCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log=Logger.getLogger(SubjectCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request){
				
		log.debug("SubjectCtl validate method started");

		boolean pass=true;
		
		
		  if(DataValidator.isNull(request.getParameter("courseId"))){
		  request.setAttribute("courseId",PropertyReader.getValue("error.require","Course name")); 
		  pass=false; 
		  }
		 
		  if(DataValidator.isNull(request.getParameter("subjectName"))){
			request.setAttribute("subjectName",PropertyReader.getValue("error.require","Subject name"));
			pass=false;
		  }
		
		  if(DataValidator.isNull(request.getParameter("description"))){
			request.setAttribute("description",PropertyReader.getValue("error.require","Description"));
			pass=false;
		  }
		
		  return pass;
	}
	
	@Override
	protected void preload(HttpServletRequest request) 
	{
		log.debug("SubjectCtl preload method started");

		CourseModel courseModel = new CourseModel();
		
		try 
		{
		List courseList = courseModel.list();
				
		request.setAttribute("courseList", courseList);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		log.debug("SubjectCtl Do Get method started");

		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		SubjectModel sm = new SubjectModel();
		
		if(id>0 || operation!=null)
		{
			try {
				SubjectBean sb = sm.findByPK(id);
				ServletUtility.setBean(sb, request);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ServletUtility.forward(getView(), request, response);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("SubjectCtl Do Post method started");

		String operation = request.getParameter("operation");
		SubjectModel sm = new SubjectModel();
		SubjectBean sb = (SubjectBean) populateBean(request);
		long id = DataUtility.getLong(request.getParameter("id"));
		if(operation.equals(OP_SAVE))
		{
			try {
				if(id>0)
				{
					sm.update(sb);
					ServletUtility.setBean(sb, request);
					ServletUtility.setSuccessMessage("Record updated successfully", request);
				}
				else
				{
					sm.add(sb);
					ServletUtility.setBean(sb, request);
					ServletUtility.setSuccessMessage("Record added successfully", request);
				}
			} catch(ApplicationException e){
				 e.printStackTrace();
				 ServletUtility.handleException(e, request, response);
				 return;
			 }catch(DuplicateRecordException e){
				 ServletUtility.setBean(sb, request);
				 ServletUtility.setErrorMessage("Subject already exists",request);
			 }
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		else if (operation.equals(OP_RESET))
		{
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_CANCEL))
		{
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
		}
			
			ServletUtility.forward(getView(), request, response);
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("SubjectCtl populate bean method started");

		SubjectBean bean = new SubjectBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean, request);

		return bean;
	
	}

	@Override
	protected String getView() {
		return ORSView.SUBJECT_VIEW;
	}

	
}
