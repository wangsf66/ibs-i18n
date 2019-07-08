package com.ibs.i18n.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;

import com.douglei.tools.utils.IdentityUtil;
import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.i18n.ApiResultI18n;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.util.getMessageUtil;

@TransactionComponent
public class InformationService {
	
	private String languageS;

	@Transaction
	public InformationSheet insert(InformationSheet informationSheet) {
		informationSheet.setId(IdentityUtil.get32UUID());
		SessionContext.getSession().getTableSession().save(informationSheet);
		return informationSheet;	
		}
	
	@Transaction
	public InformationSheet update(InformationSheet informationSheet) {
		SessionContext.getSession().getTableSession().update(informationSheet);
		return informationSheet;
	}
	@Transaction
	public InformationSheet delete(InformationSheet informationSheet) {
		SessionContext.getSession().getTableSession().delete(informationSheet);
		return informationSheet;
	}
	
	@Transaction
	public Object query(InformationSheet informationSheet) {
		Object obj = null;
		obj = SessionContext.getSession().getSqlSession().query("select * from INFORMATION_SHEET");
//		obj = SessionContext.getSession().getTableSession().pageQuery(SysUser.class, 1, 10, "select * from sys_user");
//		obj = SessionContext.getSession().getSQLSession().pageQuery(1, 10, "com.test", "queryUser");
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	@Transaction
	public String getMessage(int code) {
		String str = "";
    	InformationSheet informationSheet = new InformationSheet(); 
    	String message = getMessageUtil.getMessage(code);
 		if(message=="") { 
 			informationSheet.setCode(code); 
 			Object obj = SessionContext.getSession().getSqlSession().query("select * from INFORMATION_SHEET where code = "+informationSheet.getCode()+"and language= '"+getMessageUtil.getLocalLanguage()+"'");
 			ArrayList<Map<String,Object>> messageList = (ArrayList<Map<String,Object>>)obj;
 			Map<String, Object> map = null;
 			for (Object object : messageList) {
 				map = (Map<String, Object>) object;
 				str = map.get("MESSAGE")+"";
 			}
 		}else {
 			str = message;
 		}
 		return str; 
	}
	
	@Transaction
	public Object querycode(InformationSheet informationSheet) {
		Object obj = SessionContext.getSession().getSqlSession().query("select * from INFORMATION_SHEET where code = "+informationSheet.getCode());
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	@Transaction
	public MessageResult getMessageResult(MessageResult messageResult, String language) {
		ArrayList<ApiResultI18n> list = new ArrayList<ApiResultI18n>();
		InformationSheet informationSheet = null;
		ApiResultI18n Api = null;
		MessageResult MR = new MessageResult();
		MR.setStatus(messageResult.getStatus());
		MR.setSuccess(messageResult.isSuccess());
		String message = "";
		Map<String, Object> map = null;
		if (language != null && language != "") {
			this.languageS = language;
		} else {
			this.languageS = getMessageUtil.getLocalLanguage();
		}
		for (ApiResultI18n obj : messageResult.getData()) {
			// 根据code获取配置文件中的message
			message = getMessageUtil.getMessage(obj.getCode());
			if (message == null || message == "") {
				informationSheet = new InformationSheet();
				informationSheet.setCode(obj.getCode());
				// 获取到数据库中的信息对象
				Object objA = SessionContext.getSession().getSqlSession()
						.query("select * from INFORMATION_SHEET where code = " + informationSheet.getCode()
								+ "and language= '" + languageS + "'");
				ArrayList<Map<String, Object>> messageList = (ArrayList<Map<String, Object>>) objA;
				for (Object object : messageList) {
					map = (Map<String, Object>) object;
					Api = new ApiResultI18n();
					Api.setCode(obj.getCode());
					Api.setMsg(map.get("MESSAGE") + "");
					Api.setData(obj.getData());
					list.add(Api);
				}
			} else {
				Api = new ApiResultI18n();
				Api.setCode(obj.getCode());
				Api.setMsg(message);
				Api.setData(obj.getData());
				list.add(Api);
			}
		}
		MR.setMessage(list);
		MR.setValidation(list);
		MR.setData(messageResult.getData());
		return MR;
	}
	
}
