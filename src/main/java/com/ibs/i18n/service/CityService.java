package com.ibs.i18n.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.tools.utils.naming.column.Columns;
import com.ibs.i18n.entity.CitySheet;
import com.ibs.i18n.i18n.MessageResult;
@TransactionComponent
public class CityService {
	
	@Autowired
	private InformationService informationService;
	
	
	@Transaction
	 public MessageResult insert(CitySheet citySheet) {
		 MessageResult messageResult = new MessageResult();
		 insertCity(citySheet,messageResult);
		 return informationService.getMessageResult(messageResult, null);
		}
	
		public void insertCity(CitySheet citySheet,MessageResult messageResult){
			 if(citySheet.getCityName()==""||citySheet.getpId()=="") {
				 messageResult.addValidation("api.response.code.notNull", citySheet);
			 }else{
				 SessionContext.getTableSession().save(citySheet);
				 messageResult.addData("api.response.code.success", citySheet);	
			 }
		}
		
	
	  public void updateCity(CitySheet citySheet,MessageResult messageResult){
			 if(citySheet.getCityName()=="") {
				 messageResult.addValidation("api.response.code.notNull", citySheet);
			 }else{
				 SessionContext.getTableSession().update(citySheet);
				 messageResult.addData("api.response.code.success", citySheet);	
			 }
		}
	
	
	@Transaction
	public MessageResult update(CitySheet citySheet) {
		 MessageResult messageResult = new MessageResult();
		 updateCity(citySheet,messageResult);
		 return informationService.getMessageResult(messageResult, null);
	}
	
	@Transaction
	public MessageResult delete(String ids) {
		MessageResult messageResult = new MessageResult();
		SessionContext.getSqlSession().executeUpdate("delete from CITY_SHEET where ID in ("+ids+")");
		messageResult.addData("api.response.code.success", ids);
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
	
	@Transaction
	public MessageResult insertMany(List<CitySheet> list) {
		MessageResult messageResult = new MessageResult();
		for(CitySheet city:list) {
			insertCity(city,messageResult);
		 }
		 return informationService.getMessageResult(messageResult, null);
	}
	
	@Transaction
	public MessageResult updateMany(List<CitySheet> list) {
		MessageResult messageResult = new MessageResult();
		for(CitySheet city:list) {
			updateCity(city,messageResult);
		 }
		 return informationService.getMessageResult(messageResult, null);
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
