package com.sunilOS.ORSProject4.utility;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.sunilOS.ORSProject4.bean.DropDownListBean;




/**
 * HTML Utility class to produce HTML contents like Dropdown List.
 * @author Neeraj
 *
 */
public class HTMLUtility {

	/**
     * Create HTML SELECT list from MAP paramters values
     * @param name
     * @param selectedVal
     * @param map
     * @return
     */ 
	public static String getList(String name, String selectedVal , HashMap<String, String> map) 
	{

		  StringBuffer sb = new StringBuffer("<select class='tbox' name='" + name + "'>");

	        Set<String> keys = map.keySet();
	       
	        String val = null;
	        boolean select=true;
	        if (select) {

	            sb.append("<option selected value=''>-------------Select---------------</option>");
	        }

	        for (String key : keys) {
	            val = map.get(key);
	            if (key.trim().equals(selectedVal)) {
	                sb.append("<option selected value='" + val + "'>" + val
	                        + "</option>");
	            } else {
	                sb.append("<option value='" + key + "'>" + val + "</option>");
	            }
	          
	        }
	        sb.append("</select>");
	        
	        return sb.toString();
	    }

	    /**
	     * Create HTML SELECT List from List parameter
	     *
	     * @param name
	     * @param selectedVal
	     * @param list
	     * @return
	     */
	    public static String getList(String name, String selectedVal, List list, String selectType) {
	    	Collections.sort(list);       
	        StringBuffer sb = new StringBuffer("<select class='tbox' name='" + name + "'>");

	        boolean select=true;
	        if (select)
	        {
	        	if(selectType != null) {
		            sb.append("<option selected value=''>Select " + selectType + "</option>");
	        	}
	        	else {
		            sb.append("<option selected value=''>-----------Select-------------</option>");
	        	}
	        }
	        List<DropDownListBean> dd = (List<DropDownListBean>) list;
	        
	        String key = null;
	        String val = null;

	        for (DropDownListBean obj : dd) {
	            key = obj.getKey();
	            val = obj.getValue();

	            if (key.trim().equals(selectedVal)) {
	            	sb.append("<option selected value='" + key + "'>" + val + "</option>");
	            } else {
	                sb.append("<option value='" + key + "'>" + val + "</option>");
	            }
	        }
	        sb.append("</select>");
	        return sb.toString();
	    }

	    /**
	     * Create HTML SELECT List from List parameter
	     * 
	     * @param name
	     * @param selectedVal
	     * @param list
	     * @return
	     */
	    public static String getList(String name, String selectedVal, HashMap<String, String> map, boolean select) {

	        StringBuffer sb = new StringBuffer(
	                "<select class='tbox' name='" + name + "'>");

	        Set<String> keys = map.keySet();
	        String val = null;

	        if (select) {

	            sb.append("<option selected value=''> --Select-- </option>");
	        }

	        for (String key : keys) {
	            val = map.get(key);
	            if (key.trim().equals(selectedVal)) {
	                sb.append("<option selected value='" + key + "'>" + val
	                        + "</option>");
	            } else {
	                sb.append("<option value='" + key + "'>" + val + "</option>");
	            }
	        }
	        sb.append("</select>");
	        return sb.toString();
	    }

	    public static String getInputErrorMessages(HttpServletRequest request) {

	        Enumeration<String> e = request.getAttributeNames();

	        StringBuffer sb = new StringBuffer("<UL>");
	        String name = null;

	        while (e.hasMoreElements()) {
	            name = e.nextElement();
	            if (name.startsWith("error.")) {
	                sb.append("<LI class='error'>" + request.getAttribute(name)
	                        + "</LI>");
	            }
	        }
	        sb.append("</UL>");
	        return sb.toString();
	    }

	    /**
	     * Returns Error Message with HTML tag and CSS
	     *
	     * @param request
	     * @return msg
	     */
	    public static String getErrorMessage(HttpServletRequest request) {
	        String msg = ServletUtility.getErrorMessage(request);
	        if (!DataValidator.isNull(msg)) {
	            msg = "<p class='st-error-header'>" + msg + "</p>";
	        }
	        return msg;
	    }

	    /**
	     * Returns Success Message with HTML tag and CSS
	     *
	     * @param request
	     * @return msg
	     */

	    public static String getSuccessMessage(HttpServletRequest request) {
	        String msg = ServletUtility.getSuccessMessage(request);
	        if (!DataValidator.isNull(msg)) {
	            msg = "<p class='st-success-header'>" + msg + "</p>";
	        }
	        return msg;
	    }

	    /**
	     * Creates submit button if user has access permission.
	     *
	     * @param label
	     * @param access
	     * @param request
	     * @return button
	     */
	    public static String getSubmitButton(String label, boolean access,
	            HttpServletRequest request) {

	        String button = "";

	        if (access) {
	            button = "<input type='submit' name='operation' value='" + label
	                    + "' >";
	        }
	        return button;
	    }

}