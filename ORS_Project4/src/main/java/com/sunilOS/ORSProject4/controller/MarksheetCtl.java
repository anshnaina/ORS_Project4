
package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.MarksheetBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.MarksheetModel;
import com.sunilOS.ORSProject4.model.StudentModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "MarksheetCtl", urlPatterns = { "/ctl/MarksheetCtl" })
public class MarksheetCtl extends BaseCtl {
	
	private static final long serialVersionUID = 1L;
	
	private static Logger log=Logger.getLogger(MarksheetCtl.class);


	@Override
	protected void preload(HttpServletRequest request) 
	{
		log.debug("MarksheetCtl preload method started");

		MarksheetModel marksheetModel = new MarksheetModel();
		StudentModel studentModel  = new StudentModel();
		try {
			List marksheetList = (List) marksheetModel.list();
			List studentList = (List) studentModel.list();
			
			request.setAttribute("marksheetList", marksheetList);
			request.setAttribute("studentList", studentList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		log.debug("MarksheetCtl Method validate Started");

		boolean pass = true;
		String op = request.getParameter("operation");
		if (BaseCtl.OP_RESET.equalsIgnoreCase(op)) {
			return pass;
		}
		String rollNo = request.getParameter("rollNo");
		String physics = request.getParameter("physics");
		String chemistry = request.getParameter("chemistry");
		String maths = request.getParameter("maths");

		if (DataValidator.isNull(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.require", "Roll No"));
			pass = false;
		} else if (!DataValidator.isRollNo(request.getParameter("rollNo"))) {
			request.setAttribute("rollNo", PropertyReader.getValue("error.alphanumeric", "Roll No."));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("studentId"))) {
			request.setAttribute("studentId", PropertyReader.getValue("error.require", " Student Name"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.require", "Physics Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("physics"))
				&& !DataValidator.isInteger(request.getParameter("physics"))) {
			request.setAttribute("physics", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if ((DataUtility.getInt(request.getParameter("physics")) > 100)
				|| (DataUtility.getInt(request.getParameter("physics")) <= 0)) {
			request.setAttribute("physics", " Marks must be between 1-100");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.require", "Maths Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("maths"))
				&& !DataValidator.isInteger(request.getParameter("maths"))) {
			request.setAttribute("maths", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if ((DataUtility.getInt(request.getParameter("maths")) > 100)
				|| (DataUtility.getInt(request.getParameter("maths")) <= 0)) {
			request.setAttribute("maths", "Marks must be between 1-100");
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.require", "Chemistry Marks"));
			pass = false;
		} else if (DataValidator.isNotNull(request.getParameter("chemistry"))
				&& !DataValidator.isInteger(request.getParameter("chemistry"))) {
			request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Marks"));
			pass = false;
		} else if ((DataUtility.getInt(request.getParameter("chemistry")) > 100)
				|| (DataUtility.getInt(request.getParameter("chemistry")) <= 0)) {
			request.setAttribute("chemistry", "Marks must be between 1-100");
			pass = false;
		}
		
		log.debug("MarksheetCtl Method validate Ended");

		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) 
	{
		
		log.debug("MarksheetCtl populate bean method started");

		MarksheetBean bean =  new MarksheetBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));
		bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));
		bean.setName(DataUtility.getString(request.getParameter("name"))); 
		if ((!"".equals(request.getParameter("physics")) && !DataValidator.isName(request.getParameter("physics")))) {
			bean.setPhysics(DataUtility.getInt(request.getParameter("physics")));
		}

		if ((!"".equals(request.getParameter("maths")) && !DataValidator.isName(request.getParameter("maths")))) {
			bean.setMaths(DataUtility.getInt(request.getParameter("maths")));
		}

		if ((!"".equals(request.getParameter("chemistry"))
				&& !DataValidator.isName(request.getParameter("chemistry")))) {
			bean.setChemistry(DataUtility.getInt(request.getParameter("chemistry")));
		}
		populateDTO(bean, request);
		
		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("MarksheetCtl Do Get method started");

		long id = DataUtility.getLong(request.getParameter("id"));
		
		MarksheetModel mm = new MarksheetModel();

		if (id>0)
		{
			MarksheetBean mb ;
			try {
				mb = mm.findByPK(id);
				ServletUtility.setBean(mb, request);
			}catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		ServletUtility.forward(getView(), request, response);
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("MarksheetCtl Do Post method started");
		
		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		MarksheetModel model = new MarksheetModel();
		
		if(operation.equals(OP_SAVE))
		{
			MarksheetBean bean = (MarksheetBean) populateBean(request);
			try {
				if (id > 0) {
					model.update(bean);
				} else {
					long pk = model.add(bean);

					bean.setId(pk);
				}
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Record successfully saved", request);

			}
				catch(ApplicationException e){
				ServletUtility.handleException(e, request, response);
				return;
				}
				catch(DuplicateRecordException e)
				{
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Roll no already exists", request);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(operation.equals(OP_RESET))
			{
				ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
				return;
			}
			else if(operation.equals(OP_CANCEL))
			{
				ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
				return;
			}
		ServletUtility.forward(getView(), request, response);
	}
		
		
	
	
	@Override
	protected String getView() {
		return ORSView.MARKSHEET_VIEW;
	}

}
