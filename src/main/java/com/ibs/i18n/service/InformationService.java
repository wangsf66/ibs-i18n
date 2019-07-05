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
import com.ibs.i18n.util.getMessageUtil;

@TransactionComponent
public class InformationService {
	
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
	public List<ApiResultI18n> getMessage(ArrayList<Object> list) {
		ArrayList<ApiResultI18n> listdata = new ArrayList<ApiResultI18n>();
		ApiResultI18n apiResultI18n = null;
		int code;
		String str = "";
		Map<String, Object> map = null;
		for(Object obj:list) {
			map = (Map<String, Object>) obj;
			if(map.get("CODE")!=null&&map.get("CODE")!="") {
				InformationSheet informationSheet = new InformationSheet(); 
		    	String message = getMessageUtil.getMessage(Integer.parseInt(map.get("CODE").toString()));
		 		if(message=="") { 
		 			informationSheet.setCode(Integer.parseInt(map.get("CODE").toString())); 
		 			Object objA = SessionContext.getSession().getSqlSession().query("select * from INFORMATION_SHEET where code = "+informationSheet.getCode()+"and language= '"+getMessageUtil.getLocalLanguage()+"'");
		 			ArrayList<Map<String,Object>> messageList = (ArrayList<Map<String,Object>>)obj;
		 			Map<String, Object> mapA = null;
		 			for (Object object : messageList) {
		 				mapA = (Map<String, Object>) object;
		 				str = mapA.get("MESSAGE")+"";
		 			    apiResultI18n  = new ApiResultI18n();
		 			    apiResultI18n.setCode(Integer.parseInt(map.get("CODE").toString()));
		 			    apiResultI18n.setData(map.get("data"));
		 			    apiResultI18n.setMsg(str);
		 			    listdata.add(apiResultI18n);
		 			    
		 			}
		 		}else {
		 			 apiResultI18n  = new ApiResultI18n();
		 			 apiResultI18n.setCode(Integer.parseInt(map.get("CODE").toString()));
		 			 apiResultI18n.setData(map.get("data"));
		 			 apiResultI18n.setMsg(message);
		 			 listdata.add(apiResultI18n);
		 		}
			}else {
				 apiResultI18n = new ApiResultI18n();
	 			 apiResultI18n.setCode(Integer.parseInt(map.get("CODE").toString()));
	 			 apiResultI18n.setData(map.get("data"));
	 			 apiResultI18n.setMsg("");
	 			 listdata.add(apiResultI18n);
			}  
		}
		return listdata;
	}
	
	
	@Transaction
	public Object querycode(InformationSheet informationSheet) {
		Object obj = SessionContext.getSession().getSqlSession().query("select * from INFORMATION_SHEET where code = "+informationSheet.getCode());
		return obj;
	}
	
}
