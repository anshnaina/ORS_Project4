package com.sunilOS.ORSProject4.exception;

/**
 *  RecordNotFoundException thrown when a record not found occurred
 * @author Anshul
 *
 */
public class RecordNotFoundException extends Exception{

	/**
	 * @param msg
	 *      : Error message
	 */
	public RecordNotFoundException(String msg){
		
		super(msg);
	}
}
