package com.ibs.i18n.entity;

public class InformationSheet {
	private String id;
	private String code;
	private String message;
	private String language;
	
	
	
	public InformationSheet() {
		super();
		// TODO Auto-generated constructor stub
	}
	public InformationSheet(String code, String message, String language) {
		super();
		this.code = code;
		this.message = message;
		this.language = language;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
