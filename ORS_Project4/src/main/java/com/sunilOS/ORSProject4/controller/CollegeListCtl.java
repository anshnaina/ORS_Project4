package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.sunilOS.ORSProject4.bean.BaseBean;
import com.sunilOS.ORSProject4.bean.CollegeBean;
import com.sunilOS.ORSProject4.model.CollegeModel;
import com.sunilOS.ORSProject4.model.MarksheetModel;
import com.sunilOS.ORSProject4.utility.DataUtility;
import com.sunilOS.ORSProject4.utility.PropertyReader;
import com.sunilOS.ORSProject4.utility.ServletUtility;

/**
 * College list ctl.to perform search and show list operation
 * 
 * @author Anshul
 *
 */

@WebServlet(name = "CollegeListCtl", urlPatterns = { "/ctl/CollegeListCtl" })
public class CollegeListCtl extends BaseCtl{
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CollegeListCtl.class);

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("CollegeListCtl Do Get method starts");	

		CollegeModel cm = new CollegeModel();
		int pageNo = 1;
		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
		
		CollegeBean cb = (CollegeBean) populateBean(request);
		try {
			List collegeList = cm.search(cb, pageNo, pageSize);
			ServletUtility.setList(collegeList, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		log.debug("CollegeListCtl Do Post method starts");	

		CollegeModel cm = new CollegeModel();
		CollegeBean cb = (CollegeBean) populateBean (request);
		
		int pageNo = Integer.parseInt(request.getParameter("pageNo"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String[] chk = request.getParameterValues("chk");

		
		String operation = request.getParameter("operation");
		
		
		if(operation.equals(OP_DELETE))
			{
			try {
				if(chk!=null)
				{
					CollegeBean delBean = new CollegeBean();
					for(String id : chk)
					{
						delBean.setId(DataUtility.getLong(id));
						cm.delete(delBean);
						ServletUtility.setSuccessMessage("Selected record(s) deleted successfully", request);
					}
				}
				else
				{
            		ServletUtility.setErrorMessage("Select at least one record", request);

				}
				if (OP_BACK.equalsIgnoreCase(operation)) {
					ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
					return;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			}
		if(operation.equals(CollegeListCtl.OP_PREVIOUS)||operation.equals(CollegeListCtl.OP_NEXT)||operation.equals(CollegeListCtl.OP_SEARCH))
		{
			if(operation.equals(CollegeListCtl.OP_SEARCH))
			{
				pageNo = 1;
			}
			if(operation.equals(CollegeListCtl.OP_PREVIOUS))
			{
				pageNo--;
			}
			if(operation.equals(CollegeListCtl.OP_NEXT))
			{
				pageNo++;
			}
		}	
		
		else if (operation.equals(OP_RESET)) 
		{
        	request.setAttribute("id", 0);

			ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			return;
		}
		else if (operation.equals(OP_NEW)) 
		{
			ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			return;
		}
		
		try {
			List collegeList = cm.search(cb, pageNo, pageSize);
			ServletUtility.setList(collegeList, request);
			
			if (!operation.equals(OP_DELETE))
			{
			if (collegeList == null || collegeList.size() == 0) {
				ServletUtility.setErrorMessage("No Record Found", request);
			}
			}
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}

	
	
	@Override
	protected void preload(HttpServletRequest request)  {
		
		log.debug("CollegeListCtl preload method starts");	

		CollegeModel cm = new CollegeModel();
		try {
			List collegeList = cm.list();
			request.setAttribute("collegeList", collegeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("CollegeListCtl populate bean method starts");	

		CollegeBean bean = new CollegeBean();
		bean.setCollegeName(request.getParameter("collegeName"));
		bean.setCity(request.getParameter("city"));
		
		return bean;	
	}


	@Override
	protected String getView() {
		return ORSView.COLLEGE_LIST_VIEW;
	}

	
}
