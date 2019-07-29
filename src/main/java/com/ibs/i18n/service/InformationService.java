package com.ibs.i18n.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.tools.utils.IdentityUtil;
import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.i18n.ApiResultI18n;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.redis.RedisUtil;
import com.ibs.i18n.util.getMessageUtil;
import com.ibs.parent.code.entity.column.Columns;

@TransactionComponent
public class InformationService {
	
	@Autowired
	private InformationService informationService;
	
	@Autowired
	private  RedisUtil redisUtil;
	
	private String languageS;

	@Transaction
	public InformationSheet insert(InformationSheet informationSheet) {
		informationSheet.setId(IdentityUtil.get32UUID());
		SessionContext.getTableSession().save(informationSheet);
		return informationSheet;	
		}
	
	@Transaction
	public InformationSheet update(InformationSheet informationSheet) {
		SessionContext.getTableSession().update(informationSheet);
		return informationSheet;
	}
	@Transaction
	public InformationSheet delete(InformationSheet informationSheet) {
		SessionContext.getTableSession().delete(informationSheet);
		return informationSheet;
	}
	
	@Transaction
	public Object query(InformationSheet informationSheet) {
		Object obj = null;
		obj = SessionContext.getSqlSession().query("select "+Columns.getNames(InformationSheet.class)+" from INFORMATION_SHEET");
//		obj = SessionContext.getSession().getTableSession().pageQuery(SysUser.class, 1, 10, "select * from sys_user");
//		obj = SessionContext.getSession().getSQLSession().pageQuery(1, 10, "com.test", "queryUser");
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	@Transaction
	public String getMessage(String code) {
		String str = "";
    	InformationSheet informationSheet = new InformationSheet(); 
    	String message = getMessageUtil.getMessage(code);
 		if(message=="") { 
 			informationSheet.setCode(code); 
 			Object obj = SessionContext.getSqlSession().query("select "+Columns.getNames(InformationSheet.class)+" from INFORMATION_SHEET where code = "+informationSheet.getCode()+"and language= '"+getMessageUtil.getLocalLanguage()+"'");
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
		Object obj = SessionContext.getSqlSession().query("select "+Columns.getNames(InformationSheet.class)+" from INFORMATION_SHEET where code = "+informationSheet.getCode());
		return obj;
	}
	
	public MessageResult getMessageResult(MessageResult messageResult, String language) {
		ArrayList<ApiResultI18n> list = new ArrayList<ApiResultI18n>();
		MessageResult MR = new MessageResult();
		MR.setStatus("200");
		MR.setSuccess(1);
		String temp = "";
		Object object = null;
		if (language != null && language != "") {
			this.languageS = language;
		} else {
			this.languageS = getMessageUtil.getLocalLanguage();
		}
		if(messageResult.getData()!=null) {
			temp = "Data";
			object = messageResult.getData();
			if(messageResult.getData() instanceof List){
				searchMapList(messageResult,list,MR,object,temp);
	        }else {
	        	searchMapObject(messageResult,list,MR,object,temp);	
	        }
		}
		if(messageResult.getError()!=null) { 
			temp = "Message";
			object = messageResult.getError();
			if(messageResult.getError() instanceof List){
				searchMapList(messageResult,list,MR,object,temp);
	        }else {
	        	searchMapObject(messageResult,list,MR,object,temp);	
	        }
		}
		if(messageResult.getValidation()!=null) { 
			temp = "Validation";
			object = messageResult.getValidation();
			if(messageResult.getValidation() instanceof List){
				searchMapList(messageResult,list,MR,object,temp);
	        }else {
	        	searchMapObject(messageResult,list,MR,object,temp);	
	        }
		}
		
		if(MR.getData()==null) {
			if(MR.getError()==null&&MR.getValidation()==null) {
				MR.setSuccess(1);
			}else {
				MR.setSuccess(0);
			}
		}else {
			if(MR.getError()==null&&MR.getValidation()==null) {
				MR.setSuccess(1);
			}else {
				MR.setSuccess(2);
			}
		}
		return MR;
	}
	
	@SuppressWarnings("unchecked")
	public void searchMapList(MessageResult messageResult,ArrayList<ApiResultI18n> list,MessageResult MR,Object object,String temp ) {
		ApiResultI18n Api = null;
		ApiResultI18n apiResultI18n = null;
		list = new ArrayList<ApiResultI18n>();
		ArrayList<ApiResultI18n> listApi = null;
		HashMap<String,String> map = null;
		String message = "";
		ArrayList<Object> ObjectList =(ArrayList<Object>)object;
		for(Object obj:ObjectList) {
			//判断数据是map对象还是ApiResultI18n对象
			 if(obj instanceof java.util.LinkedHashMap){
				    map  = (HashMap<String,String>)obj;
					message = getMessageUtil.getMessage(map.get("code"));
					if (message == null || message == "") {
						apiResultI18n = new ApiResultI18n();
						apiResultI18n.setCode(map.get("code"));
						apiResultI18n.setData(map.get("data"));
						listApi = informationService.addsqlListMessage(apiResultI18n,list);
						returnMRList(MR,listApi,messageResult,temp);
		    		} else {
		    			Api = new ApiResultI18n();
		    			Api.setCode(map.get("code").toString());
		    			Api.setMsg(message);
		    			Api.setData(map.get("data"));
		    			list.add(Api);
		    			returnMRList(MR,list,messageResult,temp);
		    		}
		        }else {
		        	apiResultI18n = (ApiResultI18n)obj;
		        	message = getMessageUtil.getMessage(apiResultI18n.getCode());
		        	if (message == null || message == "") {
		        		listApi = informationService.addsqlListMessage(apiResultI18n,list);
		        		returnMRList(MR,listApi,messageResult,temp);
		    		} else {
		    			Api = new ApiResultI18n();
		    			Api.setCode(apiResultI18n.getCode());
		    			Api.setMsg(message);
		    			Api.setData(apiResultI18n.getData());
		    			list.add(Api);
		    			returnMRList(MR,list,messageResult,temp);
		    		}
		         }
		    }	  
	}
	
	//当data为数组时，为message和validation赋值
	public void returnMRList(MessageResult MR,ArrayList<ApiResultI18n> list,MessageResult messageResult,String temp) {
		if(temp.contains("Message")) {
			MR.setError(list);
		}else if(temp.contains("Validation")) {
			MR.setValidation(list);
		}else {
			MR.setData(list);
		}
	  }
	
	@SuppressWarnings("unchecked")
	public void searchMapObject(MessageResult messageResult,ArrayList<ApiResultI18n> list,MessageResult MR,Object object,String temp) {
		ApiResultI18n Api = null; 
		ApiResultI18n apiResultI18n = null;
		String message = "";
		Object ob = object;
		if(ob instanceof java.util.LinkedHashMap){
			HashMap<String,String> map  = (HashMap<String,String>)ob;
			message = getMessageUtil.getMessage(map.get("code"));
			if (message == null || message == "") {
				apiResultI18n = new ApiResultI18n();
				apiResultI18n.setCode(map.get("code"));
				apiResultI18n.setData(map.get("data"));
				Api = informationService.addsqlObjectMessage(apiResultI18n);
        		returnMR(MR,Api,messageResult,temp);
    		} else {
    			Api = new ApiResultI18n();
    			Api.setCode(map.get("code").toString());
    			Api.setMsg(message);
    			Api.setData(map.get("data"));
    			returnMR(MR,Api,messageResult,temp);
    		}
        }else {
        	apiResultI18n = (ApiResultI18n)ob;
        	message = getMessageUtil.getMessage(apiResultI18n.getCode());
        	if (message == null || message == "") {
        		Api = informationService.addsqlObjectMessage(apiResultI18n);
        		returnMR(MR,Api,messageResult,temp);
    		} else {
    			Api = new ApiResultI18n();
    			Api.setCode(apiResultI18n.getCode());
    			Api.setMsg(message);
    			Api.setData(apiResultI18n.getData());
    			returnMR(MR,Api,messageResult,temp);
    		}
         }
	  }
	    //当data为单个对象时，为message和validation赋值
		public void returnMR(MessageResult MR,ApiResultI18n Api,MessageResult messageResult,String temp) {
			if(temp.contains("Message")) {
				MR.setError(Api);
			}else if(temp.contains("Validation")) {
				MR.setValidation(Api);
			}else {
				MR.setData(Api);  
			}
			
		  }
	
	
	    //将数据库中获得的国际化存入内存
		@SuppressWarnings("unchecked")
		public void addmaplist(Object object){
			Map<String, Object> map =  (Map<String, Object>) object;
			String language = map.get("LANGUAGE").toString();
			redisUtil.hmset(language, map);
			System.out.println(redisUtil.hmget("zh_CN"));
		}
		
		@SuppressWarnings("unchecked")
		@Transaction
		public ApiResultI18n addsqlObjectMessage(ApiResultI18n apiResultI18n) {
			ApiResultI18n Api = null;
			Map<String, Object> map = null;
			//获取到数据库中的信息对象
			Object objA = SessionContext.getSqlSession()
					.query("select "+Columns.getNames(MessageResult.class)+" from INFORMATION_SHEET where code = " + apiResultI18n.getCode()
							+ "and language= '" + languageS + "'");
			ArrayList<Map<String, Object>> messageList = (ArrayList<Map<String, Object>>) objA;
			for (Object object : messageList){
				addmaplist(object);
				map = (Map<String, Object>) object;
				Api = new ApiResultI18n();
				Api.setCode(apiResultI18n.getCode());
				Api.setMsg(map.get("MESSAGE") + "");
				Api.setData(apiResultI18n.getData());
			}
			return Api;
		}
		
		@SuppressWarnings("unchecked")
		@Transaction
		public ArrayList<ApiResultI18n> addsqlListMessage(ApiResultI18n obj,ArrayList<ApiResultI18n> list) {
			Map<String, Object> map = null;
			ApiResultI18n Api = null;
			// 获取到数据库中的信息对象
			Object objA = SessionContext.getSqlSession()
					.query("select "+Columns.getNames(MessageResult.class)+" from INFORMATION_SHEET where code = " + obj.getCode()
							+ "and language= '" + languageS + "'");
			ArrayList<Map<String, Object>> messageList = (ArrayList<Map<String, Object>>) objA;
			for (Object object : messageList){
				addmaplist(object);
				map = (Map<String, Object>) object;
				Api = new ApiResultI18n();
				Api.setCode(obj.getCode());
				Api.setMsg(map.get("MESSAGE") + "");
				Api.setData(obj.getData());
				list.add(Api);
			}
			return list;
		}

		
}
