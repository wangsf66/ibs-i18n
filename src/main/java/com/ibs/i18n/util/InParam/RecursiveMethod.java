package com.ibs.i18n.util.InParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.SimpleSessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.orm.sessions.Session;
import com.douglei.tools.utils.naming.column.Columns;
import com.ibs.i18n.entity.ProvinceSheet;
import com.ibs.i18n.service.ProvinceService;
import com.ibs.i18n.util.pdUtil;

public  class RecursiveMethod{
	
	public static final  String _deepKey = "_deep";
	
	public static final  String _pcName = "_pcName";
	
	public static final  String _recursive = "_recursive";
	
	public static final  String _Id = "ID";
	
	private String Id;
	
	private int deep;
	
	private String  pcName;
	
	//是否是第一次调用，默认是false
	private boolean executeFirst = false;
	
	private String tablename;
	
	private Map<String,String> requestResourceParams;
	
	private Map<String,String> requestParentResourceParams;

	public RecursiveMethod(int deep, String pcName,String tablename,Map<String,String> requestResourceParams,Map<String,String> requestParentResourceParams,String Id) {
		super();
		this.deep = deep;
		this.pcName = pcName==null?"PARENT_ID":pcName.toUpperCase();
		this.tablename  = tablename;
		this.requestResourceParams = requestResourceParams;
		this.requestParentResourceParams = requestParentResourceParams;
		this.Id = Id;
	}

	private OrderByMethod orderByMethod;
	public void setOrderByMethod(OrderByMethod orderByMethod) {
		this.orderByMethod = orderByMethod;
	}
	
	private PageMethod pageMethod;
	public void setPageMethod(PageMethod pageMethod) {
		this.pageMethod = pageMethod;
	}

	public Object getDatas() {
		List<Object> paramlist = new ArrayList<Object>();
		List<Map<String, Object>> rootlist = null;
		PageResult<Map<String, Object>> pageObject = null;
		if(pageMethod==null) {
			rootlist = SessionContext.getSqlSession().query(getSql(paramlist,null),paramlist);
			paramlist.clear();
			recursiveQuery(rootlist,paramlist);
			return rootlist;
		}else {
			pageObject = SessionContext.getSqlSession().pageQuery(pageMethod.getRows(), pageMethod.getRows(),getSql(paramlist,null),paramlist);
			paramlist.clear();
			recursiveQuery(pageObject.getResultDatas(),paramlist);
			return pageObject;
		}
	}
	
	private void recursiveQuery(List<Map<String, Object>> list,List<Object> paramlist) {
		if(list.size()==0||this.deep==0) {
			return;
		}
		this.deep--;
		paramlist.clear();
		List<Map<String, Object>> childlist = SessionContext.getSqlSession().query(getSql(paramlist,list),paramlist);
		if(childlist.size()>0 && this.deep>0) {
	    	recursiveQuery(childlist,paramlist);
	    	addchild(childlist,list);
	    }
	}
	
	public void addchild(List<Map<String, Object>> childlist,List<Map<String, Object>> list) {
		List<Object> listObject = null;
		Object obj = null;
		for(int j=0;j<list.size();j++) {
    		for(int i=0;i<childlist.size();i++) {
    			if(list.get(j).get("ID").equals(childlist.get(i).get(pcName))) {
    				if(obj==null) {
    					obj = childlist.get(i);
    					list.get(j).put("children",obj);
    				}else {
    					Map map = (Map)obj;
    					if(childlist.get(i).get(pcName).equals(map.get(pcName))) {
    						listObject = new ArrayList();
    						listObject.add(obj);
    						listObject.add(childlist.get(i));
    						list.get(j).put("children",listObject);
    					}else {
    						obj = childlist.get(i);
    						list.get(j).put("children",obj);
    					}
    				}
    			}
    		}
    	}
	}
	
	private String getSql(List<Object> paramlist,List<Map<String, Object>> list) {
		if(executeFirst) {
		    return getNextSql(list,paramlist);
		}
		return getFirstSql(paramlist);
	}

	private String getNextSql(List<Map<String, Object>> list,List<Object> paramlist){
		if(orderByMethod==null) {
		    return "Select * from "+tablename+" where 1=1 "+pdUtil.czSql(requestResourceParams, paramlist)+" and "+pcName+" in("+getParam(list,paramlist)+") ";
		}else {
			return "Select * from "+tablename+" where 1=1 "+pdUtil.czSql(requestResourceParams, paramlist)+" and "+pcName+" in("+getParam(list,paramlist)+") "+orderByMethod.getSql();
		}
		
	}

	private String getFirstSql(List<Object> paramlist) {
		String sql = "";
		if(Id==null) {
			sql = "Select * from "+tablename+" where 1=1 "+pdUtil.czSql(requestParentResourceParams, paramlist)+" and ("+pcName+" is null or "+pcName+" ='') "+orderByMethod.getSql();
		}else {
			sql = "Select * from "+tablename+" where 1=1 "+pdUtil.czSql(requestParentResourceParams, paramlist)+"  "+orderByMethod.getSql();
		}	
		executeFirst = true;
		return sql;
	}
	
	public String getParam(List<Map<String, Object>> list,List<Object> paramlist) {
		String sql = "";
		for(int i=0;i<list.size();i++) {
			if(i==list.size()-1) {
				sql+= " ?";
			}else {
				sql+= "? ,";
			}
			paramlist.add(list.get(i).get("ID"));
		}	
		return  sql;
	}
}