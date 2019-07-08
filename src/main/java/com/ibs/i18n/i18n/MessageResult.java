package com.ibs.i18n.i18n;

import java.io.Serializable;
import java.util.List;

public class MessageResult implements Serializable{
	
	private static final long serialVersionUID = 4518290031778225230L;
	
    private boolean success = true;
    
    private String status;
    
    private List<ApiResultI18n> data;
    
    private List<ApiResultI18n> validation;
    
    private List<ApiResultI18n> message;

    public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ApiResultI18n> getData() {
		return data;
	}

	public void setData(List<ApiResultI18n> data) {
		this.data = data;
	}

	public List<ApiResultI18n> getValidation() {
		return validation;
	}

	public void setValidation(List<ApiResultI18n> validation) {
		this.validation = validation;
	}

	public List<ApiResultI18n> getMessage() {
		return message;
	}

	public void setMessage(List<ApiResultI18n> message) {
		this.message = message;
	}

    
}
