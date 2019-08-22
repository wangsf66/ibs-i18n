package com.ibs.i18n.entity;


import com.douglei.tools.utils.naming.column.Column;

public class OfficeSheet {
	@Column("ID")
	private String id;
	@Column("OFFICENAME")
	private String officeName;
	@Column("PID")
	private String pId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
}
