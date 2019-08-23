package com.ibs.i18n.ConditionalQuery.BuiltInConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.ibs.i18n.ConditionalQuery.ObjectConditions.SplicingConditionsUtil;


public class RoutineQueryMethod {

	 //排序组件
	private OrderByMethod orderByMethod;
	
	public void setOrderByMethod(OrderByMethod orderByMethod) {
		this.orderByMethod = orderByMethod;
	}
	//分页组件
	private PageMethod pageMethod;
	public void setPageMethod(PageMethod pageMethod) {
		this.pageMethod = pageMethod;
	}
	
    private String tablename;
	
	private Map<String,String> requestResourceParams;
	
	public RoutineQueryMethod(Map<String, String> requestResourceParams, String tablename) {
		this.tablename  = tablename;
		this.requestResourceParams = requestResourceParams;
	}

	public Object getDatas() {
		List<Object> paramlist = new ArrayList<Object>();
		List<Map<String, Object>> rootlist = null;
		PageResult<Map<String, Object>> pageObject = null;
		if(pageMethod==null) {
			return SessionContext.getSqlSession().query(getSql(paramlist),paramlist);
		}else {
			return  SessionContext.getSqlSession().pageQuery(pageMethod.getRows(), pageMethod.getRows(),getSql(paramlist),paramlist);
		}
	}

	private String getSql(List<Object> paramlist) {
		if(orderByMethod==null) {
		    return "Select * from "+tablename+" where 1=1 "+SplicingConditionsUtil.czSql(requestResourceParams, paramlist);
		}else {
			return "Select * from "+tablename+" where 1=1 "+SplicingConditionsUtil.czSql(requestResourceParams, paramlist)+"  "+orderByMethod.getSql();
		}
	}
}
