package com.ibs.i18n.service;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.SimpleSessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.sessionfactory.sessions.Session;
import com.douglei.tools.utils.IdentityUtil;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.datatype.converter.ConverterUtil;
import com.douglei.tools.utils.naming.column.Columns;
import com.ibs.i18n.ConditionalQuery.BuiltInConditions.MethodContext;
import com.ibs.i18n.entity.CitySheet;
import com.ibs.i18n.entity.ProvinceSheet;
import com.ibs.i18n.entity.ResponseBody;
import com.ibs.i18n.i18n.ApiResultI18n;
import com.ibs.i18n.i18n.MessageResult;


@TransactionComponent
public class ProvinceService {
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private ProvinceService provinceService;
	
	@Autowired
	private InformationService informationService;
	
	  public MessageResult insert(ProvinceSheet provinceSheet) {
		MessageResult messageResult = new MessageResult();
		insertPro(provinceSheet,messageResult);
		return informationService.getMessageResult(messageResult, null);
		}

	 public void insertPro(ProvinceSheet provinceSheet,MessageResult messageResult) {
		  if(provinceSheet.getProvinceName()=="") {
			 messageResult.addValidation("api.response.code.notNull", provinceSheet);
		  }else{
			 Session session =  SimpleSessionContext.getSession();
			 try {
				session.getTableSession().save(provinceSheet);
				session.commit();
				messageResult.addData("api.response.code.success", provinceSheet);
			} catch (Exception e) {
				session.rollback();
				messageResult.addError("api.response.code.error", provinceSheet);
			}finally {
				session.close();
			}
		  }
	  }
	  
	  public void updatePro(ProvinceSheet provinceSheet,MessageResult messageResult) {
		  if(provinceSheet.getProvinceName()=="") {
			 messageResult.addValidation("api.response.code.notNull", provinceSheet);
		  }else{
			  Session session =  SimpleSessionContext.getSession();
				 try {
					session.getTableSession().update(provinceSheet);
					session.commit();
					messageResult.addData("api.response.code.success", provinceSheet);
				}catch (Exception e) {
					session.rollback();
					messageResult.addError("api.response.code.error", provinceSheet);
				}finally {
					session.close();
			 } 
		 }
	}

	public MessageResult update(ProvinceSheet provinceSheet) {
		 MessageResult messageResult = new MessageResult();
		 updatePro(provinceSheet,messageResult);
		 return informationService.getMessageResult(messageResult, null);
	}
	
	
	public MessageResult delete(String ids,String deleteChildNode) {
		Object obj = null;
	    Map<String, Object> map = null;
	    List<Map<String, Object>> list = null;
		MessageResult messageResult = new MessageResult();
		MessageResult Mr = null;
		String id[] = ids.split(",");
		for(String Id:id) {
		if(deleteChildNode=="false"||deleteChildNode==null) {
			deleteProvince(Id,messageResult);
		}else {
			    obj = cityService.query2(Id);
			    if(obj!=null) {
			    	list = (List<Map<String,Object>>)obj;
			    	for(Object object : list) {
			    		map = (Map<String, Object>) object;
			    		deleteCity(map.get("ID").toString(),messageResult);
			    	}
			    }else {
			    	deleteProvince(Id,messageResult);
				}
			}
		}
		Mr = informationService.getMessageResult(messageResult, null);
		return Mr;
	}
	
	public void deleteProvince(String Id,MessageResult messageResult) {
		Session session =  SimpleSessionContext.getSession();
		 try {
			session.getSqlSession().executeUpdate("delete from PROVINCE_SHEET where ID = '"+Id+"'");
			session.commit();
			messageResult.addData("api.response.code.success", Id);
		} catch (Exception e) {
			session.rollback();
			messageResult.addError("api.response.code.error", Id);
		}finally {
			session.close();
	   } 
	}
	
	public void deleteCity(String Id,MessageResult messageResult) {
		Session session =  SimpleSessionContext.getSession();
		 try {
			session.getSqlSession().executeUpdate("delete from CITY_SHEET where ID = '"+Id+"'");
			session.commit();
			messageResult.addData("api.response.code.success", Id);
		} catch (Exception e) {
			session.rollback();
			messageResult.addError("api.response.code.error", Id);
		}finally {
			session.close();
	   } 
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
			 Session session =  SimpleSessionContext.getSession();
			 try {
				session.getTableSession().save(citySheet);
				session.commit();
				messageResult.addData("api.response.code.success", citySheet);
			} catch (Exception e) {
				session.rollback();
				messageResult.addError("api.response.code.error", citySheet);
			}finally {
				session.close();
		 }  
	  }
	}
	
	@Transaction
	public MessageResult queryBtn(String clumes,List<Object> paramList,String tableName){
		Object obj = null;
		if(tableName.equals("Province_Sheet".toUpperCase())) {
			obj  = SessionContext.getTableSession().query(ProvinceSheet.class,"select "+Columns.getNames(ProvinceSheet.class)+" from "+tableName+" WHERE 1=1"+clumes,paramList);
		}else if(tableName.equals("City_Sheet".toUpperCase())){
			obj  = SessionContext.getTableSession().query(CitySheet.class,"select "+Columns.getNames(CitySheet.class)+" from "+tableName+" WHERE 1=1"+clumes,paramList);
		}
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

	@SuppressWarnings("unchecked")
	public MessageResult updateMany(List<Map<String, Object>> list) {
		MessageResult messageResult = new MessageResult();
		MessageResult mr = new MessageResult();
		ProvinceSheet provinceSheet = null;
		ProvinceSheet ps = null;
		CitySheet citySheet = null;
		List<LinkedHashMap<String ,Object>> linkedmap = null;
		Map mm = null;
		Object obj = null;
		for(Map map:list){
			//将map转为class对象
			provinceSheet = ConverterUtil.mapToClass(map, ProvinceSheet.class);
			//做验证
			updatePro(provinceSheet,messageResult);
			   if(map.get("child")!=null){
					obj = map.get("child");
					if(obj instanceof java.util.LinkedHashMap) {
					   mm = (Map)obj;
					   mm.put("pId",provinceSheet.getId());
					   citySheet = ConverterUtil.mapToClass(mm, CitySheet.class);
					   updateCity(citySheet,messageResult);
					}else {
						linkedmap = (List<LinkedHashMap<String ,Object>>)obj;
						for(LinkedHashMap linked:linkedmap) {
							citySheet = ConverterUtil.mapToClass((Map)linked, CitySheet.class);
							updateCity(citySheet,messageResult);
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
	
	public void updateCity(CitySheet citySheet,MessageResult messageResult){
		 if(citySheet.getCityName()=="") {
			 messageResult.addValidation("api.response.code.notNull", citySheet);
		 }else{
			 Session session =  SimpleSessionContext.getSession();
			 try {
				session.getTableSession().update(citySheet);
				session.commit();
				messageResult.addData("api.response.code.success", citySheet);
			} catch (Exception e) {
				session.rollback();
				messageResult.addError("api.response.code.error", citySheet);
			}finally {
				session.close();
		   } 
		}
	}
    
	@Transaction
	public MessageResult dynamicQuery(HttpServletRequest request, String tableName) {
		MessageResult messageResult = new MessageResult();
		ResponseBody res = (ResponseBody)request.getAttribute("ResponseBody");
		try {
			MethodContext methodContext = new MethodContext(res.getRequestBuiltinParams(),res.getRequestResourceParams(),res.getRequestParentResourceParams(),tableName);
			messageResult.setData(methodContext.getDatas());
		} catch (Exception e) {
			messageResult.setError(e.toString());
		}
		return messageResult;
	}
}
