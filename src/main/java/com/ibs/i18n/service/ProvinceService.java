package com.ibs.i18n.service;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.tools.utils.IdentityUtil;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.converter.ConverterUtil;
import com.ibs.i18n.entity.CitySheet;
import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.entity.ProvinceSheet;
import com.ibs.i18n.i18n.ApiResultI18n;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.parent.code.entity.column.Columns;

@TransactionComponent
public class ProvinceService {
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private InformationService informationService;
	
	 @Transaction
	  public MessageResult insert(ProvinceSheet provinceSheet) {
		MessageResult messageResult = new MessageResult();
		provinceSheet.setId(IdentityUtil.get32UUID());
		insertPro(provinceSheet,messageResult);
		return informationService.getMessageResult(messageResult, null);
		}

	  public void insertPro(ProvinceSheet provinceSheet,MessageResult messageResult) {
		  if(provinceSheet.getProvinceName()=="") {
			 messageResult.addValidation("api.response.code.notNull", provinceSheet);
		  }else{
			try {
				SessionContext.getTableSession().save(provinceSheet);
				messageResult.addData("api.response.code.success", provinceSheet);
			}catch(Exception e) {
			    messageResult.addError("api.response.code.error", provinceSheet);
			} 
		 }
	}
	
	@Transaction
	public MessageResult update(ProvinceSheet provinceSheet) {
		 MessageResult messageResult = new MessageResult();
		 MessageResult Mr = null;
		 if(provinceSheet.getProvinceName()!=""&& provinceSheet.getId()!="") {
			 try{
					SessionContext.getTableSession().update(provinceSheet);
					messageResult.addData("api.response.code.success", provinceSheet);
				}catch(Exception e) {
					e.printStackTrace();
				    messageResult.addError("api.response.code.error", provinceSheet);
				} 
		 }else{
			 messageResult.addValidation("api.response.code.notNull", provinceSheet);
		 }
		 Mr = informationService.getMessageResult(messageResult, null);
		 return Mr;
	}
	@Transaction
	public MessageResult delete(String ids,String deleteChildNode) {
		Object obj = null;
	    Map<String, Object> map = null;
	    List<Map<String, Object>> list = null;
		MessageResult messageResult = new MessageResult();
		MessageResult Mr = null;
		String id[] = ids.split(",");
		for(String Id:id) {
		if(deleteChildNode=="false"||deleteChildNode==null) {
			try {
				SessionContext.getSqlSession().executeUpdate("delete from PROVINCE_SHEET where ID = '"+Id+"'");
				messageResult.addData("api.response.code.success", Id);
			}catch(Exception e) {
				e.printStackTrace();
				messageResult.addError("api.response.code.error", Id);
			} 	
		}else {
			    obj = cityService.query2(Id);
			    if(obj!=null) {
			    	list = (List<Map<String,Object>>)obj;
			    	for(Object object : list) {
			    		map = (Map<String, Object>) object;
			    		SessionContext.getSqlSession().executeUpdate("delete from CITY_SHEET where ID = "+map.get("ID"));
			    		try {
							SessionContext.getSqlSession().executeUpdate("delete from PROVINCE_SHEET where ID = '"+Id+"'");
							messageResult.addData("api.response.code.success", Id);
						}catch(Exception e) {
							e.printStackTrace();
							messageResult.addError("api.response.code.error", Id);
						} 
			    	}
			    }else {
					try {
						SessionContext.getSqlSession().executeUpdate("delete from PROVINCE_SHEET where ID = '"+Id+"'");
						messageResult.addData("api.response.code.success", Id);
					}catch(Exception e) {
						e.printStackTrace();
						messageResult.addError("api.response.code.error", Id);
					} 	
				}
			}
		}
		Mr = informationService.getMessageResult(messageResult, null);
		return Mr;
	}
	
	@Transaction
	public MessageResult queryA() {
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		Object obj  = SessionContext.getTableSession().query(ProvinceSheet.class,"select "+Columns.getNames(ProvinceSheet.class)+" from PROVINCE_SHEET");
		mr.setData(obj);
		return mr;
	}
	
	@Transaction
	public Object query2() {
		Object obj = null;
		obj = SessionContext.getTableSession().query(ProvinceSheet.class,"select "+Columns.getNames(ProvinceSheet.class)+" from PROVINCE_SHEET");
		return obj;
	}
	
	
	@SuppressWarnings("unchecked")
	@Transaction
	public MessageResult query() {
		List<CitySheet> listCity = null;
		List<Map<String,Object>> list =(List<Map<String,Object>>)provinceService.query2();
		ProvinceSheet provinceSheet = null;
		for(Object object:list) {
			provinceSheet = (ProvinceSheet) object;
			listCity =(List<CitySheet>)cityService.query2(provinceSheet.getId());
			provinceSheet.setList(listCity);
		}
		 MessageResult mr = new MessageResult();
		 mr.setStatus("200");
		 mr.setData(list);	
		return mr;
	}
	
	@Transaction
	public MessageResult query(String id) {
		List<CitySheet> listCity = null;
		Object obj = SessionContext.getTableSession().query(ProvinceSheet.class,"select "+Columns.getNames(ProvinceSheet.class)+" from PROVINCE_SHEET WHERE ID='"+id+"'");
		List<Map<String,Object>> list =(List<Map<String,Object>>)obj;
		ProvinceSheet provinceSheet = null;
		if(obj!=null) {
			for(Object objectA:list) {
				provinceSheet = (ProvinceSheet) objectA;
				listCity =(List<CitySheet>)cityService.query2(provinceSheet.getId());
				provinceSheet.setList(listCity);
			}
		}
		MessageResult mr  = new MessageResult();
		mr.setStatus("200");
		mr.setData(obj);
		return mr;
	}
	
	@SuppressWarnings("unchecked")
	@Transaction
	public MessageResult insertMany(List<Map<String,Object>> list) {
		MessageResult messageResult = new MessageResult();
		MessageResult mr = new MessageResult();
		ProvinceSheet provinceSheet = null;
		ProvinceSheet ps = null;
		CitySheet citySheet = null;
		List<LinkedHashMap<String ,Object>> linkedmap = null;
		Map mm = null;
		Object obj = null;
		for(Map map:list){
			//为省信息添加id信息
			map.put("id",IdentityUtil.get32UUID());
			//将map转为class对象
			provinceSheet = ConverterUtil.mapToClass(map, ProvinceSheet.class);
			//做验证
			insertPro(provinceSheet,messageResult);
			   if(map.get("child")!=null){
					obj = map.get("child");
					if(obj instanceof java.util.LinkedHashMap) {
					   mm = (Map)obj;
					   mm.put("pId",provinceSheet.getId());
					   citySheet = ConverterUtil.mapToClass(mm, CitySheet.class);
					   insertCity(citySheet,messageResult);
					}else {
						linkedmap = (List<LinkedHashMap<String ,Object>>)obj;
						for(LinkedHashMap linked:linkedmap) {
							linked.put("pId",provinceSheet.getId());
							citySheet = ConverterUtil.mapToClass((Map)linked, CitySheet.class);
							insertCity(citySheet,messageResult);
						}
					}
				}
			}
		if(StringUtil.notEmpty(pdError(messageResult))||StringUtil.notEmpty(pdValidation(messageResult))) {
			addmr(messageResult,mr,list);
		}
		if(StringUtil.isEmpty(pdError(messageResult))&&StringUtil.isEmpty(pdValidation(messageResult))) {
			mr.addData("api.response.code.success", list);
		}
		return informationService.getMessageResult(mr,null);
	}
	
	
	 //组装新的mesageResult
	 public void addmr(MessageResult messageResult,MessageResult mr,List<Map<String,Object>> list) {
		 if(pdValidation(messageResult)!="") {
			mr.addValidation(pdValidation(messageResult), list); 
		 }
		 if(pdError(messageResult)!="") {
			mr.addError(pdError(messageResult), list); 
		 } 
	 }
	
	 //判断操作失败code值
	 public String pdError(MessageResult messageResult) {
		 String s = "";
		 String code = null;
		 String codes[]= null;
		 if(messageResult.getError()!=null) {
		    Object obj = messageResult.getError();
		    code = pjcode(obj); 
		    codes = code.split(",");
		    s = codes[0];
		 }
		 return s;
	 }
	 //判断验证错误code值
	 public String pdValidation(MessageResult messageResult) {
		 String s = "";
		 String code = null;
		 String codes[]= null;
		 if(messageResult.getValidation()!=null) {
		    Object obj = messageResult.getValidation();
		    code = pjcode(obj); 
		    codes = code.split(",");
		    s = codes[0];
		 }
		 return s;
	 }
	 
		 //拼接错误code值
	     public String pjcode(Object obj) {
	    	 String s = "";
			 if(obj instanceof List) {
			    	for(ApiResultI18n Api:(List<ApiResultI18n>)obj){
			    		s += Api.getCode();
			    	}
			    }else {
			    	ApiResultI18n Api = (ApiResultI18n)obj;
			    	s += Api.getCode();
			    }
			 return s;
	     }
	
	 
	   public void insertCity(CitySheet citySheet,MessageResult messageResult) {
		 if(citySheet.getCityName()==""||citySheet.getpId()=="") {
			 messageResult.addValidation("api.response.code.notNull", citySheet);
		 }else{
			try {
				SessionContext.getTableSession().save(citySheet);
				messageResult.addData("api.response.code.success", citySheet);
			}catch(Exception e) {
			    messageResult.addError("api.response.code.error", citySheet);
			} 
		 }
	}
	
	@Transaction
	public MessageResult queryBtn(String clumes) {
		Object obj  = SessionContext.getTableSession().query(ProvinceSheet.class,"select "+Columns.getNames(ProvinceSheet.class)+" from PROVINCE_SHEET WHERE 1=1"+clumes);
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(obj);
		return mr;
	}
	
	@Transaction
	public MessageResult Page(int pageNum,int pageSize) {
		PageResult<ProvinceSheet> page = SessionContext.getTableSession().pageQuery(ProvinceSheet.class,pageNum, pageSize, "select "+Columns.getNames(ProvinceSheet.class)+" from PROVINCE_SHEET");
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(page);
		return mr;
	}
	
	@Transaction
	public MessageResult query(String id, int pageNum, int pageSize) {
		Object obj = SessionContext.getTableSession().query(ProvinceSheet.class,"select "+Columns.getNames(ProvinceSheet.class)+" from PROVINCE_SHEET WHERE ID='"+id+"'");
		List<Map<String,Object>> list =(List<Map<String,Object>>)obj;
		Object object = null;
		ProvinceSheet provinceSheet = null;
		for(Object objectA:list) {
			provinceSheet = (ProvinceSheet) objectA;
			object = cityService.PageByPid(pageNum,pageSize,provinceSheet.getId());
		}
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(object);	
		return mr;
	}
}
