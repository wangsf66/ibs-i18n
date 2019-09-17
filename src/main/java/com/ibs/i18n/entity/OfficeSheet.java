package com.ibs.i18n.entity;


import com.douglei.tools.utils.naming.column.Column;

public class OfficeSheet {
	@Column("ID")
	private String ID;
	@Column("OFFICENAME")
	private String OFFICENAME;
	@Column("PID")
	private String PID;
	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		ID = ID;
	}
	public String getOFFICENAME() {
		return OFFICENAME;
	}
	public void setOFFICENAME(String OFFICENAME) {
		OFFICENAME = OFFICENAME;
	}
	public String getPID() {
		return PID;
	}
	public void setPID(String PID) {
		PID = PID;
	}
	
}
