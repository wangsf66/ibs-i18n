package com.ibs.i18n.i18n;

import java.io.IOException;

public class ApiResultI18n{
	 
    /**
     * 返回码，1000 正常
     */
    private int code = 1000;

    public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public ApiResultI18n(int code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}


	/**
     * 返回信息
     */
    private String msg;
    
    /**
     * 返回数据
     */
    private Object data;

    public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	/**
     * api 返回结果
     */
    public ApiResultI18n() {}
    
    public ApiResultI18n(String language,int code){
        this.code = code;
        try {
            this.msg = I18nMessageUtil.getMessage(language,code);
        } catch (IOException e) {
            this.msg = "SUCCESS";
        }
    }

    public static ApiResultI18n failure(int code,String language) {
        return failureResult(code, language);
    }

    public static ApiResultI18n failureResult(int code, String language) {
        ApiResultI18n result = new ApiResultI18n(language,code);
        String msg = null;
        try {
            msg = I18nMessageUtil.getMessage(language,code);
        } catch (IOException e) {
            msg = "Error";
        }
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
