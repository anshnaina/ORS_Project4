package com.sunilOS.ORSProject4.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunilOS.ORSProject4.utility.ServletUtility;

@WebServlet(name = "ErrorCtl", urlPatterns = { "/ErrorCtl" })
public class ErrorCtl extends BaseCtl {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletUtility.forward(getView(), request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			
	}

	@Override
	protected String getView() {
		return ORSView.ERROR_VIEW;
	}
	
	
}
