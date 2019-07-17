package com.ibs.i18n.i18n;

import java.util.ArrayList;
import java.util.List;

public class MessageResult{
	
	
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

	//为data添加数据，数据类型可为list可为一个对象
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addObject(String code,Object data){
		if(this.data==null) {
			this.data = new ApiResultI18n(code,data);
		}else {
			Object obj = this.data;
			if(obj instanceof List) {
				((List)this.data).add(new ApiResultI18n(code,data));
			}else {
				this.data = new ArrayList<ApiResultI18n>();
				((List)this.data).add(obj);
				((List)this.data).add(new ApiResultI18n(code,data));
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addData(String code,Object data){
		if(this.data==null) {
			this.data = new ApiResultI18n(code,data);
		}else {
			Object obj = this.data;
			if(obj instanceof List){
				((List)this.data).add(new ApiResultI18n(code,data));
			}else{
				this.data = new ArrayList<ApiResultI18n>();
				((List)this.data).add(obj);
				((List)this.data).add(new ApiResultI18n(code,data));
			}
		}
	}
	//为data添加数据，数据类型可为list可为一个对象
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void addValidation(String code,Object data){
			if(this.validation==null) {
				this.validation = new ApiResultI18n(code,data);
			}else {
				Object obj = this.validation;
				if(obj instanceof List) {
					((List)this.validation).add(new ApiResultI18n(code,data));
				}else {
					this.validation = new ArrayList<ApiResultI18n>();
					((List)this.validation).add(obj);
					((List)this.validation).add(new ApiResultI18n(code,data));
				}
				
			}
		}
		//为data添加数据，数据类型可为list可为一个对象
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void addMessage(String code,Object data){
			if(this.message==null) {
				this.message = new ApiResultI18n(code,data);
			}else {
				Object obj = this.message;
				if(obj instanceof List) {
					((List)this.message).add(new ApiResultI18n(code,data));
				}else {
					this.message = new ArrayList<ApiResultI18n>();
					((List)this.message).add(obj);
					((List)this.message).add(new ApiResultI18n(code,data));
				}
			}
		}
}
