package com.sunilOS.ORSProject4.bean;


/**
 * Subject JavaBean encapsulates Subject attributes
 * @author Anshul
 *
 */

public class SubjectBean extends BaseBean{

	private String courseName;
	private long courseId;
	private String subjectName;
	private String description;
	
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public long getCourseId() {
		return courseId;
	}
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getKey() {
		return id + "";
		}
	public String getValue() {
		return subjectName;
	}	
}
