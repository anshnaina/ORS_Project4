package com.sunilOS.ORSProject4.exception;

/**
 * ApplicationException is propogated from Service classes when an business
 * logic exception occurered.
 * @author Anshul
 *
 */
public class ApplicationException extends Exception {
	
	/**
	 * @param msg
	 *      : Error message
	 */
	public ApplicationException(String msg){
		super(msg);
	}
	
}
