package com.ibs.i18n.i18n;

import java.util.ArrayList;

public class MessageResult{
	
ArrayList<ApiResultI18n> list = null;
	
    private boolean success = true;
    
    private String status;
    
    private Object data;
   
	private Object validation;
    
    private Object message;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getValidation() {
		return validation;
	}

	public void setValidation(Object validation) {
		this.validation = validation;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public void addObject(int code,Object data){
		if(list==null) {
			list = new ArrayList<ApiResultI18n>();
			this.data = new ApiResultI18n(code,data);
			list.add(new ApiResultI18n(code,data));
		}else if(list.size()>0){
			list.add(new ApiResultI18n(code,data));
			this.data = list;
		}
	}
}
