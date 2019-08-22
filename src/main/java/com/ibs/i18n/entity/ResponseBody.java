package com.ibs.i18n.entity;

import java.util.Map;

public class ResponseBody {
	/**
	 * 请求的url内置参数键值对
	 */
	private Map<String, String> requestBuiltinParams;
	/**
	 * 请求的resource参数键值对
	 */
	private Map<String, String> requestResourceParams;
	/**
	 * 请求的parentResource参数键值对
	 */
	private Map<String, String> requestParentResourceParams;
	
	
	
	public Map<String, String> getRequestBuiltinParams() {
		return requestBuiltinParams;
	}
	public void setRequestBuiltinParams(Map<String, String> requestBuiltinParams) {
		this.requestBuiltinParams = requestBuiltinParams;
	}
	public Map<String, String> getRequestResourceParams() {
		return requestResourceParams;
	}
	public void setRequestResourceParams(Map<String, String> requestResourceParams) {
		this.requestResourceParams = requestResourceParams;
	}
	public Map<String, String> getRequestParentResourceParams() {
		return requestParentResourceParams;
	}
	public void setRequestParentResourceParams(Map<String, String> requestParentResourceParams) {
		this.requestParentResourceParams = requestParentResourceParams;
	}
}
