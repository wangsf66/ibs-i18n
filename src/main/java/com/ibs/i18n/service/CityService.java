package com.ibs.i18n.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.SimpleSessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.core.validate.ValidateException;
import com.douglei.orm.sessions.Session;
import com.douglei.tools.utils.StringUtil;
import com.douglei.tools.utils.naming.column.Columns;
import com.ibs.i18n.entity.CitySheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.response.ResponseContext;
@TransactionComponent
public class CityService {
	
	@Autowired
	private InformationService informationService;
	
	
	 public void insert(CitySheet citySheet) {
		   insertCity(citySheet);
		}
	 
	 public void update(CitySheet citySheet) {
		 updateCity(citySheet);
	}
	
		public void insertCity(CitySheet citySheet){
			 if("".equals(citySheet.getCityName())||citySheet.getCityName()==null) {
				 ResponseContext.addValidation(citySheet, "cityName", "api.response.code.notNull");
			 }else if("".equals(citySheet.getpId())||citySheet.getpId()==null) {
				 ResponseContext.addValidation(citySheet, "pId", "api.response.code.notNull");
			 }else if(StringUtil.computeStringLength(citySheet.getCityName())>50) {
				 ResponseContext.addValidation(citySheet, "cityName", "api.response.code.overLength");
			 }else{
				 Session session =  SimpleSessionContext.getSession();
				 try {
					session.getTableSession().save(citySheet);
					session.commit();
					ResponseContext.addData(citySheet);
				}catch (Exception e) {
					session.rollback();
					ResponseContext.addError(citySheet, "api.response.code.error");
				}finally {
					session.close();
			    }  	
			 }
		}
		
		
	
	  public void updateCity(CitySheet citySheet){
		   if("".equals(citySheet.getCityName())||citySheet.getCityName()==null) {
				 ResponseContext.addValidation(citySheet, "cityName", "api.response.code.notNull");
			 }else if("".equals(citySheet.getpId())||citySheet.getpId()==null) {
				 ResponseContext.addValidation(citySheet, "pId", "api.response.code.notNull");
			 }else if(StringUtil.computeStringLength(citySheet.getCityName())>50) {
				 ResponseContext.addValidation(citySheet, "cityName", "api.response.code.overLength");
			 }else{
				 Session session =  SimpleSessionContext.getSession();
				 try {
					session.getTableSession().update(citySheet);
					session.commit();
					ResponseContext.addData(citySheet);
				}catch (Exception e) {
					session.rollback();
					ResponseContext.addError(citySheet, "api.response.code.error");
				}finally {
					session.close();
			    }  	
				 
			 }
		}
	
	
	
	
	public MessageResult delete(String ids) {
		MessageResult messageResult = new MessageResult();
		Session session =  SimpleSessionContext.getSession();
		 try {
			session.getSqlSession().executeUpdate("delete from CITY_SHEET where ID in ("+ids+")");
			session.commit();
			messageResult.addData("api.response.code.success", ids);
		} catch (Exception e) {
			session.rollback();
			messageResult.addError("api.response.code.error", ids);
		}finally {
			session.close();
	   } 
		return informationService.getMessageResult(messageResult, null);
	}
	
	@Transaction
	public MessageResult query(int pId) {
		Object obj  = SessionContext.getTableSession().query(CitySheet.class,"select "+Columns.getNames(CitySheet.class)+" from CITY_SHEET WHERE 	PID='"+pId+"'");
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(obj);
		return mr;
	}
	
	@Transaction
	public Object query2(String pId) {
		Object obj  = SessionContext.getTableSession().query(CitySheet.class,"select "+Columns.getNames(CitySheet.class)+" from CITY_SHEET WHERE 	PID='"+pId+"'");
		return obj;
	}
	@Transaction
	public Object query(CitySheet citySheet) {
		Object obj = null;
		obj = SessionContext.getTableSession().query(CitySheet.class,"select "+Columns.getNames(CitySheet.class)+" from CITY_SHEET");
		return obj;
	}
	@Transaction
	public Object query3(String id) {
		Object obj  = SessionContext.getTableSession().query(CitySheet.class,"select "+Columns.getNames(CitySheet.class)+" from CITY_SHEET WHERE 	PID="+id);
		return obj;
	}
	@Transaction
	public MessageResult queryA() {
		Object obj  = SessionContext.getTableSession().query(CitySheet.class,"select "+Columns.getNames(CitySheet.class)+" from CITY_SHEET ");
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(obj);
		return mr;
	}
	
	public void insertMany(List<CitySheet> list) {
		for(CitySheet city:list) {
			insertCity(city);
		 }
	}
	
	public void updateMany(List<CitySheet> list) {
		for(CitySheet city:list) {
			updateCity(city);
		 }
	}
	
	@Transaction
	public MessageResult Page(int pageNum,int pageSize) {
		PageResult<CitySheet> page = SessionContext.getTableSession().pageQuery(CitySheet.class,pageNum, pageSize, "select "+Columns.getNames(CitySheet.class)+" from CITY_SHEET");
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(page);
		return mr;
	}
	
	@Transaction
	public Object PageByPid(int pageNum,int pageSize,String id) {
		PageResult<CitySheet> page =SessionContext.getTableSession().pageQuery(CitySheet.class, pageNum, pageSize,"select "+Columns.getNames(CitySheet.class)+" from CITY_SHEET where PID='"+id+"'");
		return page;
	}
	
	@Transaction
	public MessageResult queryBtn(String clumes,List<Object> paramList) {
		Object obj  = SessionContext.getTableSession().query(CitySheet.class,"select "+Columns.getNames(CitySheet.class)+" from CITY_SHEET WHERE 1=1"+clumes,paramList);
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(obj);
		return mr;
	}
}
