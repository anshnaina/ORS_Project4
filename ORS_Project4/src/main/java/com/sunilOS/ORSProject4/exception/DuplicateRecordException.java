package com.sunilOS.ORSProject4.exception;

/**
 * DuplicateRecordException thrown when a duplicate record occurred
 * @author Anshul
 *
 */
public class DuplicateRecordException extends Exception{
	
	/**
	 * @param msg
	 * error msg
	 */
	public DuplicateRecordException(String msg){
		super(msg);
	}

}
