package com.ibs.i18n.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.tools.utils.IdentityUtil;
import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.i18n.ApiResultI18n;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.util.TransactionComponentAutoRegistry;
import com.ibs.i18n.util.getMessageUtil;

@TransactionComponent
public class InformationService {
	
//	@Autowired
//	private InforService inforService;
	
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
		ApiResultI18n Api = null;
		Map<String, Object> map = null;
		InformationSheet informationSheet  = null;
		MessageResult MR = new MessageResult();
		MR.setStatus(messageResult.getStatus());
		MR.setSuccess(messageResult.isSuccess());
		String message = "";
		if (language != null && language != "") {
			this.languageS = language;
		} else {
			this.languageS = getMessageUtil.getLocalLanguage();
		}
		if(messageResult.getData() instanceof List){
		     //searchMapList(messageResult,message,informationSheet,Api,map,list,MR);
			ArrayList<ApiResultI18n> listApi = (ArrayList<ApiResultI18n>)messageResult.getData();
			  for (ApiResultI18n obj :listApi) {
					// 根据code获取配置文件中的message
					message = getMessageUtil.getMessageA(obj.getCode(),languageS);
					if(message == null || message == ""){
//						inforService.getMessage(obj, Api, list,languageS);
						informationSheet = new InformationSheet();
						informationSheet.setCode(obj.getCode());
						// 获取到数据库中的信息对象
						Object objA = SessionContext.getSession().getSqlSession()
								.query("select * from INFORMATION_SHEET where code = " + informationSheet.getCode()
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
        }else {
        	 //searchMapObject(messageResult,message,informationSheet,Api,map,list,MR);	
        	ApiResultI18n apiResultI18n = (ApiResultI18n)messageResult.getData();
        	message = getMessageUtil.getMessageA(apiResultI18n.getCode(),languageS);
        	if (message == null || message == "") {
    			informationSheet = new InformationSheet();
    			informationSheet.setCode(apiResultI18n.getCode());
    			// 获取到数据库中的信息对象
    			Object objA = SessionContext.getSession().getSqlSession()
    					.query("select * from INFORMATION_SHEET where code = " + informationSheet.getCode()
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
    		} else {
    			Api = new ApiResultI18n();
    			Api.setCode(apiResultI18n.getCode());
    			Api.setMsg(message);
    			Api.setData(apiResultI18n.getData());
    		}
        	MR.setMessage(Api);
    		MR.setValidation(Api);
    		MR.setData(messageResult.getData());
        }
		return MR;
	}
	
	//将数据库中获得的国际化存入内存
	@SuppressWarnings("unchecked")
	public void addmaplist(Object object){
		Map<String, Object> map =  (Map<String, Object>) object;
		String language = map.get("LANGUAGE").toString();
	    Map<String, String> mapA  = TransactionComponentAutoRegistry.mapList.get(language);
		mapA.put(map.get("CODE").toString(), map.get("MESSAGE").toString());
	}
	
//	@SuppressWarnings("unchecked")
//	@Transaction
//	public void searchMapList(MessageResult messageResult,String message,InformationSheet informationSheet,ApiResultI18n Api,Map<String, Object> map,ArrayList<ApiResultI18n> list,MessageResult MR) {
//		  ArrayList<ApiResultI18n> listApi = (ArrayList<ApiResultI18n>)messageResult.getData();
//		  for (ApiResultI18n obj :listApi) {
//				// 根据code获取配置文件中的message
//				message = getMessageUtil.getMessage(obj.getCode());
//				if (message == null || message == "") {
////					inforService.getMessage(obj, Api, list,languageS);
//					informationSheet = new InformationSheet();
//					informationSheet.setCode(obj.getCode());
//					// 获取到数据库中的信息对象
//					Object objA = SessionContext.getSession().getSqlSession()
//							.query("select * from INFORMATION_SHEET where code = " + informationSheet.getCode()
//									+ "and language= '" + languageS + "'");
//					ArrayList<Map<String, Object>> messageList = (ArrayList<Map<String, Object>>) objA;
//					for (Object object : messageList){
//						addmaplist(object);
//						map = (Map<String, Object>) object;
//						Api = new ApiResultI18n();
//						Api.setCode(obj.getCode());
//						Api.setMsg(map.get("MESSAGE") + "");
//						Api.setData(obj.getData());
//						list.add(Api);
//					}
//					
//				} else {
//					Api = new ApiResultI18n();
//					Api.setCode(obj.getCode());
//					Api.setMsg(message);
//					Api.setData(obj.getData());
//					list.add(Api);
//				}
//			}
//		    MR.setMessage(list);
//			MR.setValidation(list);
//			MR.setData(messageResult.getData());
//	}
	
//	@SuppressWarnings("unchecked")
//	@Transaction
//	public void searchMapObject(MessageResult messageResult,String message,InformationSheet informationSheet,ApiResultI18n Api,Map<String, Object> map,ArrayList<ApiResultI18n> list,MessageResult MR) {
//		ApiResultI18n apiResultI18n = (ApiResultI18n)messageResult.getData();
//    	message = getMessageUtil.getMessage(apiResultI18n.getCode());
//    	if (message == null || message == "") {
//			informationSheet = new InformationSheet();
//			informationSheet.setCode(apiResultI18n.getCode());
//			// 获取到数据库中的信息对象
//			Object objA = SessionContext.getSession().getSqlSession()
//					.query("select * from INFORMATION_SHEET where code = " + informationSheet.getCode()
//							+ "and language= '" + languageS + "'");
//			ArrayList<Map<String, Object>> messageList = (ArrayList<Map<String, Object>>) objA;
//			for (Object object : messageList){
//				addmaplist(object);
//				map = (Map<String, Object>) object;
//				Api = new ApiResultI18n();
//				Api.setCode(apiResultI18n.getCode());
//				Api.setMsg(map.get("MESSAGE") + "");
//				Api.setData(apiResultI18n.getData());
//			}
//		} else {
//			Api = new ApiResultI18n();
//			Api.setCode(apiResultI18n.getCode());
//			Api.setMsg(message);
//			Api.setData(apiResultI18n.getData());
//		}
//    	MR.setMessage(Api);
//		MR.setValidation(Api);
//		MR.setData(messageResult.getData());
//	}


}
