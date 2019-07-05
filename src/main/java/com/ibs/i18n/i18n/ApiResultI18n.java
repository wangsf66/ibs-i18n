package com.ibs.i18n.i18n;

import java.io.IOException;
import java.io.Serializable;

public class ApiResultI18n implements Serializable{
	
	private static final long serialVersionUID = 4518290031778225230L;

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


	/**
     * 返回信息
     */
    private String msg = "成功";


    /**
     * api 返回结果
     */
    private ApiResultI18n() {}

    /**
     * api 返回结果,区分多语言
     *
     * @param language 语言类型,eg: en_us 表示美式英文
     */
    public ApiResultI18n(String language){
        this.code = ResponseCodeI18n.SUCCESS.getCode();
        try {
            this.msg = I18nMessageUtil.getMessage(language,"SUCCESS",ResponseCodeI18n.SUCCESS.getCode());
        } catch (IOException e) {
            this.msg = "SUCCESS";
        }
    }
    
    public ApiResultI18n(String language,int code){
        this.code = code;
        try {
            this.msg = I18nMessageUtil.getMessage(language,"SUCCESS",code);
        } catch (IOException e) {
            this.msg = "SUCCESS";
        }
    }

    /**
     * 获取成功状态结果,区分多语言(默认简体中文)
     *
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static ApiResultI18n success(String language) {
        return success1(language);
    }

    /**
     * 获取成功状态结果,区分多语言(默认简体中文)
     *
     * @param data 返回数据
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static ApiResultI18n success1(String language) {
        ApiResultI18n result = new ApiResultI18n(language);
        return result;
    }

    /**
     * 获取失败状态结果,区分多语言(默认简体中文)
     *
     * @param language 语言类型,eg: en_us 表示美式英文
     * @return
     */
    public static ApiResultI18n failure(int code,String language) {
        return failure1(code, language);
    }

    /**
     * 获取失败结果,区分多语言(默认中文)
     *
     * @param responseCodeI18n 返回码
     * @param language 语言类型
     * @return
     */
    public static ApiResultI18n failureA(int code, String language) {
        return failure(code, language);
    }

    /**
     * 获取失败状态结果,区分多语言(默认中文)
     *
     * @param code 返回状态码
     * @param msg 错误信息
     * @param language 语言类型,eg: en 表示英文
     * @return
     */
    public static ApiResultI18n failure(int code, String msg, String language) {
        return failure(code, language);
    }

    /**
     * 获取失败返回结果,区分多语言(默认中文)
     *
     * @param code 错误码
     * @param msg 错误信息
     * @param data 返回结果
     * @param language 语言类型,eg: en 表示英文
     * @return
     */
    public static ApiResultI18n failure1(int code, String language) {
        ApiResultI18n result = new ApiResultI18n(language,code);
        String msg = null;
        try {
            msg = I18nMessageUtil.getMessage(language, msg,code);
        } catch (IOException e) {
            msg = "Error";
        }
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
