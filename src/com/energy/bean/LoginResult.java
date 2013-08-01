package com.energy.bean;

import java.io.Serializable;

public class LoginResult implements Serializable{
	private static final long serialVersionUID = 5808161996623461862L;
	private int uid;
	private int companyId;
	private int departmentId;
	private int roleId;
	private String dataIds;
	private int status;
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getDataIds() {
		return dataIds;
	}
	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
