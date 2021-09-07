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
import com.sunilOS.ORSProject4.model.StudentModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "StudentListCtl", urlPatterns = { "/ctl/StudentListCtl" })
public class StudentListCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
       
	private static Logger log=Logger.getLogger(StudentListCtl.class);

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("StudentListCtl Do Get method started");

		
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		StudentBean sb = (StudentBean) populateBean(request);
		StudentModel sm = new StudentModel();
		try {
			List studentList = sm.search(sb, pageNo, pageSize);
			ServletUtility.setList(studentList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} 
		catch (ApplicationException e)
		{
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("StudentListCtl Do Post method started");

		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		StudentModel sm = new StudentModel();
		String operation = request.getParameter("operation");
		String[] chk = request.getParameterValues("chk");
 		
		if(operation.equals(OP_DELETE))
		{
			try {
			if(chk != null)
			{
				StudentBean delBean = new StudentBean();
				for(String id : chk)
				{
					delBean.setId(DataUtility.getLong(id));
					sm.delete(delBean);
					ServletUtility.setSuccessMessage("Selected record(s) deleted successfully", request);
				}
			}
			else
			{
        		ServletUtility.setErrorMessage("Select at least one record", request);

			}
			if (OP_BACK.equalsIgnoreCase(operation)) {
				ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
				return;
			}
				}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			}
		
		if(operation.equals(StudentListCtl.OP_PREVIOUS) || operation.equals(StudentListCtl.OP_NEXT) || operation.equals(StudentListCtl.OP_SEARCH))
		{
			if(operation.equals(StudentListCtl.OP_SEARCH))
			{
				pageNo = 1;
			}
			if(operation.equals(StudentListCtl.OP_PREVIOUS))
			{
				pageNo--;
			}
			if(operation.equals(StudentListCtl.OP_NEXT))
			{
				pageNo++;
			}
		}	
		else if (operation.equals(StudentListCtl.OP_RESET)) 
		{
			ServletUtility.redirect(ORSView.STUDENT_LIST_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_NEW)) 
		{
			ServletUtility.redirect(ORSView.STUDENT_CTL, request, response);
			return;
		}
		
		StudentBean sb = (StudentBean) populateBean(request);
	
		try {
			List studentList = sm.search(sb, pageNo, pageSize);
			
			ServletUtility.setList(studentList, request);
			
			if (!operation.equals(OP_DELETE))
			{
			if (studentList == null || studentList.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			}
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);

		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void preload(HttpServletRequest request) {
	
		log.debug("StudentListCtl preload method started");

		StudentModel sm = new StudentModel();
		try {
			List list = sm.list();
			request.setAttribute("studentList", list);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("StudentListCtl populate bean method started");

		StudentBean bean = new StudentBean();
		 bean.setFirstName(request.getParameter("firstName"));
		 bean.setLastName(request.getParameter("lastName"));

		return bean;
	}
	
	@Override
	protected String getView() {
		return ORSView.STUDENT_LIST_VIEW;
	}

	
}
