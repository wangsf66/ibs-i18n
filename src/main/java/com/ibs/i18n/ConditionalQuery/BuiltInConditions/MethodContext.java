package com.ibs.i18n.ConditionalQuery.BuiltInConditions;


import java.util.Map;

public class MethodContext {
    //排序
	private OrderByMethod orderByMethod;
	//分页
    private PageMethod pageMethod;
    //递归查询
    private RecursiveMethod recursiveMethod;
    //常规查询
    private RoutineQueryMethod routineQueryMethod;
    

	public MethodContext(Map<String,String> requestBuiltinParams,Map<String,String> requestResourceParams,Map<String,String> requestParentResourceParams,String tablename) {
		setOrderByMethod(requestBuiltinParams);
		setPageMethod(requestBuiltinParams,requestResourceParams,tablename);
		setRecursiveMethod(requestBuiltinParams,tablename,requestResourceParams,requestParentResourceParams);
		setRoutineQueryMethod(requestResourceParams,tablename);
	}
	
	
	private void setRoutineQueryMethod(Map<String, String> requestResourceParams, String tablename) {
		 routineQueryMethod = new RoutineQueryMethod(requestResourceParams,tablename);
		 if(pageMethod!=null) {
			 routineQueryMethod.setPageMethod(pageMethod);
			 }
		 if(orderByMethod!=null) {
			 routineQueryMethod.setOrderByMethod(orderByMethod);
		 }
	 }


	public void setPageMethod(Map<String,String> requestBuiltinParams,Map<String,String> requestResourceParams,String tablename) {
		String rows = requestBuiltinParams.get(PageMethod._rowsKey);
		String pageKey = requestBuiltinParams.get(PageMethod._pageKey);
		if(rows!=""&&rows!=null&&pageKey!=""&&pageKey!=null) {
			int row = Integer.parseInt(rows);
			int page = Integer.parseInt(pageKey);
			if(row>0 && page>0) {
				 pageMethod = new PageMethod(row,page);
			}
		}
	}
	
	
	public void setRecursiveMethod(Map<String,String> requestBuiltinParams,String tablename,Map<String,String> requestResourceParams,Map<String,String> requestParentResourceParams) {
		String recursive = requestBuiltinParams.get(RecursiveMethod._recursive);
		String pcName = requestBuiltinParams.get(RecursiveMethod._pcName);
		String  deep = requestBuiltinParams.get(RecursiveMethod._deepKey);
		String Id = requestParentResourceParams.get(RecursiveMethod._Id);
		if(recursive!=null) {
			if(recursive.equals("true")) {
				recursiveMethod = new RecursiveMethod(Integer.parseInt(deep),pcName,tablename,requestResourceParams,requestParentResourceParams,Id);
				if(orderByMethod!=null) {
					recursiveMethod.setOrderByMethod(orderByMethod);
				}
				if(pageMethod!=null) {
					recursiveMethod.setPageMethod(pageMethod);
				}
			}
		}else {
			recursiveMethod  = null;
		}
	}
	
	public void setOrderByMethod(Map<String,String> requestBuiltinParams) {
		String sort = requestBuiltinParams.get(OrderByMethod._sortKey);
		if(!"".equals(sort)) {
			orderByMethod = new OrderByMethod(sort);
		}
	}

	public Object getDatas() {
		if(recursiveMethod!=null) {
			return recursiveMethod.getDatas();
		}
		if(recursiveMethod==null) {
			return routineQueryMethod.getDatas();
		}
		return null;
	}
}
