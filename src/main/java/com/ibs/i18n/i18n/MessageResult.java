package com.ibs.i18n.i18n;

import java.util.ArrayList;
import java.util.List;

import com.ibs.parent.code.entity.column.Column;

public class MessageResult{
	
	@Column("success")
	private int success = 1;
	@Column("status")
    private String status;
	@Column("data")
    private Object data;
	@Column("validation")
	private Object validation;
	@Column("error")
    private Object error;

    public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
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
		public void addError(String code,Object data){
			if(this.error==null) {
				this.error = new ApiResultI18n(code,data);
			}else {
				Object obj = this.error;
				if(obj instanceof List) {
					((List)this.error).add(new ApiResultI18n(code,data));
				}else {
					this.error = new ArrayList<ApiResultI18n>();
					((List)this.error).add(obj);
					((List)this.error).add(new ApiResultI18n(code,data));
				}
			}
		}
}
