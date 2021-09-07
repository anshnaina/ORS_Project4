package com.sunilOS.ORSProject4.bean;

/**
 * College JavaBean encapsulates College attributes
 * @author Anshul
 *
 */

public class CollegeBean extends BaseBean {

	private String collegeName;
	private String address;
	private String state;
	private String city;
	private String mobileNo;
	
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getKey() {
		return id + "";
	}
	public String getValue() {
		return collegeName;

	}
	
}
