package com.ibs.i18n.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.SimpleSessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.sessionfactory.sessions.Session;
import com.douglei.tools.utils.IdentityUtil;
import com.douglei.tools.utils.naming.column.Columns;
import com.ibs.components.response.ResponseContext;
import com.ibs.i18n.entity.OfficeSheet;
import com.ibs.i18n.i18n.MessageResult;
@TransactionComponent
public class OfficeService {
	
	@Autowired
	private InformationService informationService;
	
	
	 public MessageResult insert(OfficeSheet officeSheet) {
		 MessageResult messageResult = new MessageResult();
		 officeSheet.setID(IdentityUtil.get32UUID());
		 insertOffice(officeSheet,messageResult);
		 return informationService.getMessageResult(messageResult, null);
		}
	
		public void insertOffice(OfficeSheet officeSheet,MessageResult messageResult){
			 if(officeSheet.getOFFICENAME()==""||officeSheet.getPID()=="") {
				 messageResult.addValidation("api.response.code.notNull", officeSheet);
			 }else{
				 Session session =  SimpleSessionContext.getSession();
				 try {
					session.getTableSession().save(officeSheet);
					session.commit();
					messageResult.addData("api.response.code.success", officeSheet);
				} catch (Exception e) {
					session.rollback();
					messageResult.addError("api.response.code.error", officeSheet);
				}finally {
					session.close();
			    }  	
			 }
		}
		
	
	  public void updateOffice(OfficeSheet officeSheet,MessageResult messageResult){
			 if(officeSheet.getOFFICENAME()=="") {
				 messageResult.addValidation("api.response.code.notNull", officeSheet);
			 }else{
				 Session session =  SimpleSessionContext.getSession();
				 try {
					session.getTableSession().update(officeSheet);
					session.commit();
					messageResult.addData("api.response.code.success", officeSheet);
				} catch (Exception e) {
					session.rollback();
					messageResult.addError("api.response.code.error", officeSheet);
				}finally {
					session.close();
			    }  	
			 }
		}
	
	
	public MessageResult update(OfficeSheet officeSheet) {
		 MessageResult messageResult = new MessageResult();
		 updateOffice(officeSheet,messageResult);
		 return informationService.getMessageResult(messageResult, null);
	}
	
	public MessageResult delete(String ids) {
		Map<String,Object> idsMap = new HashMap();
		idsMap.put("ids", ids);
		MessageResult messageResult = new MessageResult();
		Session session =  SimpleSessionContext.getSession();
		 try {
			session.getSqlSession().executeUpdate("delete from OFFICE_SHEET where ID in ("+ids+")");
			session.commit();
			messageResult.addData("api.response.code.success", idsMap);
		} catch (Exception e) {
			session.rollback();
			messageResult.addError("api.response.code.error", idsMap);
		}finally {
			session.close();
	   } 
		return informationService.getMessageResult(messageResult, null);
	}

	@Transaction
	public MessageResult Page(int pageNum,int pageSize) {
		PageResult<OfficeSheet> page = SessionContext.getTableSession().pageQuery(OfficeSheet.class,pageNum, pageSize, "select "+Columns.getNames(OfficeSheet.class)+" from OFFICE_SHEET");
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(page);
		return mr;
	}
	
	
	@Transaction
	public MessageResult queryBtn(String clumes,List<Object> paramList,String tableName) {
		Object obj  = SessionContext.getTableSession().query(OfficeSheet.class,"select "+Columns.getNames(OfficeSheet.class)+" from "+tableName+" WHERE 1=1"+clumes,paramList);
		MessageResult mr = new MessageResult();
		mr.setStatus("200");
		mr.setData(obj);
		return mr;
	}

	public void insert(String tableName, Map<String, Object> map) {
		insertOfficeMap(tableName,map);
	}
	
	
	public void insertOfficeMap(String tableName, Map<String, Object> map){
		Session session =  SimpleSessionContext.getSession();
		 try {
			session.getTableSession().save(tableName, map);
			session.commit();
			ResponseContext.addData(map);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			ResponseContext.addError(map,e);
		}finally {
			session.close();
	    }  		 
	 }

	public void insertMany(String tableName, List<Map<String, Object>> maplist) {
		for(Map<String, Object> map:maplist) {
			insertOfficeMap(tableName,map);
		}
	}
	
	public void update(String tableName, Map<String, Object> map) {
		updateOfficeMap(tableName,map);
	}
	
	
	public void updateOfficeMap(String tableName, Map<String, Object> map){
		Session session =  SimpleSessionContext.getSession();
		 try {
			session.getTableSession().update(tableName, map);
			session.commit();
			ResponseContext.addData(map);
		} catch (Exception e) {
			e.printStackTrace();
			session.rollback();
			ResponseContext.addError(map,e);
		}finally {
			session.close();
	    }  		 
	 }

	public void updateMany(String tableName, List<Map<String, Object>> maplist) {
		for(Map<String, Object> map:maplist) {
			updateOfficeMap(tableName,map);
		}
	}

	public void deleteObject(String tableName, String ids) {
		 Map<String,Object> idsMap = new HashMap();
		 idsMap.put("ids", ids);
		 Session session =  SimpleSessionContext.getSession();
		 try {
			session.getSqlSession().executeUpdate("delete from "+tableName+" where ID in ("+getParam(ids)+")");
			session.commit();
			ResponseContext.addData(idsMap);
		} catch (Exception e) {
			session.rollback();
			ResponseContext.addError(idsMap,e);
		}finally {
			session.close();
	   } 
	}
	
	public String getParam(String ids) {
		String id[] = ids.split(",");
		String sql = "";
		for(int i=0;i<id.length;i++) {
			if(i==id.length-1) {
				sql+= "'"+id[i]+"'";
			}else {
				sql+= "'"+id[i]+"' ,";
			}
		}	
		return  sql;
	}	
}
