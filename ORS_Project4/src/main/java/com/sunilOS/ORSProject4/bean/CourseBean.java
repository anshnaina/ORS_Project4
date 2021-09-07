package com.sunilOS.ORSProject4.bean;

/**
 * Course JavaBean encapsulates course attributes
 * @author Anshul
 *
 */
public class CourseBean extends BaseBean{

	private String courseName;
	private String courseDescription;
	private String courseDuration;
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseDescription() {
		return courseDescription;
	}
	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}
	public String getCourseDuration() {
		return courseDuration;
	}
	public void setCourseDuration(String courseDuration) {
		this.courseDuration = courseDuration;
	}
	public String getKey() {
		return id + "";
		}
	public String getValue() {
		return courseName;
	}
}
