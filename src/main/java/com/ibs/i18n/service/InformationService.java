package com.ibs.i18n.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
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
	
	@Transaction
	public MessageResult Page(int pageNum,int pageSize) {
		PageResult<Map<String, Object>> page = SessionContext.getSqlSession().pageQuery(pageNum, pageSize, "select "+Columns.getNames(InformationSheet.class)+" from INFORMATION_SHEET");
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(page);
		return mr;
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
	//messageResult返回对象的类型
	public MessageResult getMessageResult(MessageResult messageResult, String language) {
		ArrayList<ApiResultI18n> list = null;
		MessageResult MR = new MessageResult();
		MR.setStatus("200");
		MR.setSuccess(1);
		//当前数据的类型标识
		String temp = "";
		//用于接收当前数据
		Object object = null;
		if (language != null && language != "") {
			this.languageS = language;
		} else {
			this.languageS = getMessageUtil.getLocalLanguage();
		}
		if(messageResult.getData()!=null) {
			object = messageResult.getData();
			searchDataMapList(object,MR);
		}
		if(messageResult.getError()!=null) { 
			temp = "Message";
			object = messageResult.getError();
			if(messageResult.getError() instanceof List){
				searchMapList(list,MR,object,temp);
	        }else {
	        	searchMapObject(list,MR,object,temp);	
	        }
		}
		if(messageResult.getValidation()!=null) { 
			temp = "Validation";
			object = messageResult.getValidation();
			if(messageResult.getValidation() instanceof List){
				searchMapList(list,MR,object,temp);
	        }else {
	        	searchMapObject(list,MR,object,temp);	
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
	public void searchDataMapList(Object object,MessageResult MR) {
		//接收成功的数据
		ArrayList<Object> list = new ArrayList<Object>();
		//用于转换
		ArrayList<ApiResultI18n> ObjectList =null;
		ApiResultI18n api = null;
		if(object instanceof List){
			ObjectList =(ArrayList<ApiResultI18n>)object;
			for(ApiResultI18n obj:ObjectList) {
				list.add(obj.getData());
			}
			MR.setData(list);
        }else{
        	api = (ApiResultI18n)object;
        	MR.setData(api.getData());
        }
	}
	
	@SuppressWarnings("unchecked")
	public void searchMapList(ArrayList<ApiResultI18n> list,MessageResult MR,Object object,String temp ) {
		ApiResultI18n Api = null;
		ApiResultI18n apiResultI18n = null;
		list = new ArrayList<ApiResultI18n>();
		ArrayList<ApiResultI18n> listApi = null;
		//接收map对象
		HashMap<String,String> map = null;
		//接收国际化信息
		String message = "";
		//将object对象转为集合
		ArrayList<Object> ObjectList =(ArrayList<Object>)object;
		for(Object obj:ObjectList) {
			 //判断数据是map对象还是ApiResultI18n对象
			 if(obj instanceof java.util.LinkedHashMap){
				    map  = (HashMap<String,String>)obj;
				    //从redis中获取国际化信息
					message = getMessageUtil.getMessage(map.get("code"));
					if (message == null || message == "") {
						apiResultI18n = new ApiResultI18n(map.get("code"),map.get("data"));
						listApi = informationService.addsqlListMessage(apiResultI18n,list);
						returnMRList(MR,listApi,temp);
		    		} else {
		    			Api = new ApiResultI18n(map.get("code").toString(),map.get("data"),message);
		    			list.add(Api);
		    			returnMRList(MR,list,temp);
		    		}
		        }else {
		        	apiResultI18n = (ApiResultI18n)obj;
		        	message = getMessageUtil.getMessage(apiResultI18n.getCode());
		        	if (message == null || message == "") {
		        		listApi = informationService.addsqlListMessage(apiResultI18n,list);
		        		returnMRList(MR,listApi,temp);
		    		} else {
		    			Api = new ApiResultI18n(apiResultI18n.getCode(),apiResultI18n.getData(),message);
		    			list.add(Api);
		    			returnMRList(MR,list,temp);
		    		}
		         }
		    }	  
	}
	
	//当data为数组时，为message和validation赋值
	public void returnMRList(MessageResult MR,ArrayList<ApiResultI18n> list,String temp) {
		if(temp.contains("Message")) {
			MR.setError(list);
		}else if(temp.contains("Validation")) {
			MR.setValidation(list);
		}
	  }
	
	@SuppressWarnings("unchecked")
	public void searchMapObject(ArrayList<ApiResultI18n> list,MessageResult MR,Object object,String temp) {
		ApiResultI18n Api = null; 
		ApiResultI18n apiResultI18n = null;
		String message = "";
		Object ob = object;
		if(ob instanceof java.util.LinkedHashMap){
			HashMap<String,String> map  = (HashMap<String,String>)ob;
			message = getMessageUtil.getMessage(map.get("code"));
			if (message == null || message == "") {
				apiResultI18n = new ApiResultI18n(map.get("code"),map.get("data"));
				Api = informationService.addsqlObjectMessage(apiResultI18n);
        		returnMR(MR,Api,temp);
    		} else {
    			Api = new ApiResultI18n(map.get("code").toString(),map.get("data"),message);
    			returnMR(MR,Api,temp);
    		}
        }else {
        	apiResultI18n = (ApiResultI18n)ob;
        	message = getMessageUtil.getMessage(apiResultI18n.getCode());
        	if (message == null || message == "") {
        		Api = informationService.addsqlObjectMessage(apiResultI18n);
        		returnMR(MR,Api,temp);
    		} else {
    			Api = new ApiResultI18n(apiResultI18n.getCode(),apiResultI18n.getData(),message);
    			returnMR(MR,Api,temp);
    		}
         }
	  }
	    //当data为单个对象时，为message和validation赋值
		public void returnMR(MessageResult MR,ApiResultI18n Api,String temp) {
			if(temp.contains("Message")) {
				MR.setError(Api);
			}else if(temp.contains("Validation")) {
				MR.setValidation(Api);
			}
		  }
	
	
	    //将数据库中获得的国际化存入redis
		@SuppressWarnings("unchecked")
		public void addmaplist(Object object){
			Map<String, Object> map =  (Map<String, Object>) object;
			String language = map.get("LANGUAGE").toString();
			redisUtil.hmset(language, map);
		}
		
		@SuppressWarnings("unchecked")
		@Transaction
		public ApiResultI18n addsqlObjectMessage(ApiResultI18n apiResultI18n) {
			Map<String, Object> map = null;
			//获取到数据库中的信息对象
			Object objA = SessionContext.getSqlSession()
					.query("select "+Columns.getNames(MessageResult.class)+" from INFORMATION_SHEET where code = " + apiResultI18n.getCode()
							+ "and language= '" + languageS + "'");
			ArrayList<Map<String, Object>> messageList = (ArrayList<Map<String, Object>>) objA;
			for (Object object : messageList){
				addmaplist(object);
				map = (Map<String, Object>) object;
				apiResultI18n.setMsg(map.get("MESSAGE") + "");
			}
			return apiResultI18n;
		}
		
		@SuppressWarnings("unchecked")
		@Transaction
		public ArrayList<ApiResultI18n> addsqlListMessage(ApiResultI18n obj,ArrayList<ApiResultI18n> list) {
			Map<String, Object> map = null;
			// 获取到数据库中的信息对象
			Object objA = SessionContext.getSqlSession()
					.query("select "+Columns.getNames(MessageResult.class)+" from INFORMATION_SHEET where code = " + obj.getCode()
							+ "and language= '" + languageS + "'");
			ArrayList<Map<String, Object>> messageList = (ArrayList<Map<String, Object>>) objA;
			for (Object object : messageList){
				addmaplist(object);
				map = (Map<String, Object>) object;
				obj.setMsg(map.get("MESSAGE") + "");
				list.add(obj);
			}
			return list;
		}

		
}
