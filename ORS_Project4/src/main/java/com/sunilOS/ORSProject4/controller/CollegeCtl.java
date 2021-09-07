package com.sunilOS.ORSProject4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.CollegeBean;
import com.sunilOS.ORSProject4.exception.ApplicationException;
import com.sunilOS.ORSProject4.exception.DuplicateRecordException;
import com.sunilOS.ORSProject4.model.CollegeModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.DataValidator;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

/**
 * College functionality Ctl. To perform add,delete ,update operation
 * @author Anshul
 * 
 */

@WebServlet(name = "CollegeCtl", urlPatterns = { "/ctl/CollegeCtl" })
public class CollegeCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger(CollegeCtl.class);
	
	@Override
	protected boolean validate(HttpServletRequest request) {
		
		log.debug("CollegeCtl validate method starts");	
		
		boolean pass = true;
		String collegeName = request.getParameter("collegeName");

		if (DataValidator.isNull(collegeName)) {
			request.setAttribute("collegeName", PropertyReader.getValue("error.require", "College name"));
			pass = false;
		} else if (DataValidator.isSpecial(collegeName)) {
			request.setAttribute("collegeName", PropertyReader.getValue("error.specialchar", "College name"));
			pass = false;
		} else if (DataValidator.isNumber(collegeName)) {
			request.setAttribute("collegeName", PropertyReader.getValue("error.number", "College name"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address", PropertyReader.getValue("error.require", "Address"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.require", "State"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(request.getParameter("state"))) {
			request.setAttribute("state", PropertyReader.getValue("error.alphabet", "State"));
			pass = false;
		}

		if (DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.require", "City"));
			pass = false;
		} else if (!DataValidator.isNameWithSpace(request.getParameter("city"))) {
			request.setAttribute("city", PropertyReader.getValue("error.alphabet", "City"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile no."));
			pass = false;
		} else if (!DataValidator.isLong(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", "mobile No must be digits");
			pass = false;
		} else if (!DataValidator.isPhoneNo(request.getParameter("mobileNo"))) {
			request.setAttribute("mobileNo", PropertyReader.getValue("error.mobileNo", "Mobile no."));
			pass = false;
		}

		return pass;
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("CollegeCtl populate bean method starts");	

		CollegeBean bean = new CollegeBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setCollegeName(DataUtility.getString(request.getParameter("collegeName")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

		populateDTO(bean, request);
		
		return bean;
	}
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("CollegeCtl Do Get method starts");	

		CollegeModel model  = new CollegeModel();
		
		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));		
		
			if(id>0 || operation!=null)
			{
				try {
					CollegeBean bean = model.findByPK(id);
					ServletUtility.setBean(bean, request);
				} 
				catch (ApplicationException e) 
				{
					log.error(e);
					ServletUtility.handleException(e, request, response);
					return;
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
		log.debug("CollegeCtl Do Post method starts");
		
		CollegeModel model = new CollegeModel();
		
		String operation = request.getParameter("operation");
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if (operation.equals(OP_SAVE))
		{
			CollegeBean bean = (CollegeBean) populateBean(request);
		try
			{
				if(id > 0) 
				{
					
					model.update(bean);
					ServletUtility.setBean(bean , request);
					ServletUtility.setSuccessMessage("Record updated successfully", request);
				}
				else
				{
					model.add(bean);
					ServletUtility.setBean(bean , request);
					ServletUtility.setSuccessMessage("Record added successfully", request);
				}
				}
				
			catch (ApplicationException e) {
				e.printStackTrace();
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("College Name already exists", request);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
			
		else if (operation.equals(OP_CANCEL))
		{
			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
		}
		else if(operation.equals(OP_RESET))
		{
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		}		
		ServletUtility.forward(getView(), request, response);		
			
	}
	
	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
