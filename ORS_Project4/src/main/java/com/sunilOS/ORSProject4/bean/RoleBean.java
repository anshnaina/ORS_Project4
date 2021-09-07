package com.sunilOS.ORSProject4.bean;

/**
 * Role JavaBean encapsulates Role attributes
 * @author Anshul
 *
 */
public class RoleBean extends BaseBean{

	private String roleName;
	private String description;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
		return roleName;
	}
	
}
