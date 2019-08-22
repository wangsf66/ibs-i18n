package com.ibs.i18n.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.SimpleSessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.sessions.Session;
import com.douglei.tools.utils.IdentityUtil;
import com.douglei.tools.utils.naming.column.Columns;
import com.ibs.i18n.entity.OfficeSheet;
import com.ibs.i18n.i18n.MessageResult;
@TransactionComponent
public class OfficeService {
	
	@Autowired
	private InformationService informationService;
	
	
	 public MessageResult insert(OfficeSheet officeSheet) {
		 MessageResult messageResult = new MessageResult();
		 insertOffice(officeSheet,messageResult);
		 return informationService.getMessageResult(messageResult, null);
		}
	
		public void insertOffice(OfficeSheet officeSheet,MessageResult messageResult){
			 if(officeSheet.getOfficeName()==""||officeSheet.getpId()=="") {
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
			 if(officeSheet.getOfficeName()=="") {
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
		MessageResult messageResult = new MessageResult();
		Session session =  SimpleSessionContext.getSession();
		 try {
			session.getSqlSession().executeUpdate("delete from OFFICE_SHEET where ID in ("+ids+")");
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
}
