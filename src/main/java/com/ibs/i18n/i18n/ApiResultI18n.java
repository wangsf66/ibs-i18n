package com.ibs.i18n.i18n;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibs.i18n.service.InformationService;

public class ApiResultI18n{
	
	
	@Autowired
	private InformationService informationService;
	 
    /**
     * 返回码，1000 正常
     */
    private String code ;

  
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
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
	
	public ApiResultI18n(String code, Object data, String msg) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public ApiResultI18n(String code, Object data) {
		super();
		this.code = code;
		this.data = data;
	}

	/**
     * api 返回结果
     */
    public ApiResultI18n() {}
    
    public static ApiResultI18n failure(String code,String language) {
        return failureResult(code, language);
    }

    public static ApiResultI18n failureResult(String code, String language) {
        ApiResultI18n result = new ApiResultI18n();
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
