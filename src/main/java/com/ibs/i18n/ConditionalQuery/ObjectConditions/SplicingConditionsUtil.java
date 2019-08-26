package com.ibs.i18n.ConditionalQuery.ObjectConditions;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.ibs.i18n.entity.ResponseBody;

public class SplicingConditionsUtil{
	
	public static String notOperator = "";
	
	//判断会否取反
	public static Boolean isInversion(String params) {
			if(params.startsWith("!")) {
				notOperator = "not";
				return true;
			}
			notOperator = "";
			return false;
	}
		
		//判断会否为空
	public static boolean valueIsNullStr(String params) {
			if(params.equals(null)){
				return true;
			}
			return false;
	}
	
	//获取方法名
	public static String getMethodName(String params) {
		if(isInversion(params)) {
			return params.substring(1,params.indexOf("("));
		}else {
			return params.substring(0,params.indexOf("("));
		}
	}
	
	//解析URL
	public static String czSql(Map<String, String> requestResourceParams, List<Object> paramlist) {
		String sql = "";
		if(requestResourceParams.size()!= 0) {
			sql +=  executeObjectSql(requestResourceParams,paramlist);
		}
		return sql;
	}
	
   
	//解析URL
	public static String csSql(HttpServletRequest request,List<Object> paramList,String tableName) {
		String sql = "";
		ResponseBody res = (ResponseBody)request.getAttribute("ResponseBody");
		if(res.getRequestResourceParams().size()!= 0) {
			Map paramMap = res.getRequestResourceParams(); 
			sql +=  executeObjectSql(paramMap,paramList);
		}
		return sql;
	}
	
	//拼接对象条件sql
	public static String executeObjectSql(Map<?, ?> paramMap,List<Object> paramList){
		StringBuilder sql = new StringBuilder();
	    paramMap.forEach((key, value) -> {
	    	String param = value.toString().trim();
	    	String column = key.toString().trim();
            if(param.contains("(")) {
		    	//获取方法中的参数
				String para = param.substring(param.indexOf("(")+1,param.indexOf(")"));
				//参数为空
				if(valueIsNullStr(para)) {
					sql.append(" and "+ column +" is null"); 
				}else{
					sql.append(montageSql(param,column,para,sql,paramList)); 	
				}	
			}else {
				sql.append(" and "+ column +" =  ? "); 
				paramList.add(param);
			}
        });
		return sql.toString();
    }	
	

	//参数为一个时
	//eq("")，ge("")，gt("")，le("")，lt("")，ne("")，in("")，like("")，btn("")
	//参数为多个时
	//in("",""),btn("","")
	public static String montageSql(String value,String column,String para,StringBuilder sql,List<Object> paramList) {
		String sqls = sql.toString();
		//将参数分割为数组，以“，”号分割
		//isInversion(value) 值为true表示取反 false，反之
		String param[] = para.split(",");
		for(String str:param) {
			if(!str.equals("null")){
				paramList.add(str);
			}
		}
		//将函数名全部转为小写
		switch (getMethodName(value).toLowerCase()) {
		        case "eq":
		        	if(param.length ==1) {
			    		if(isInversion(value)){
			    			sqls = NeMethod.toDBScriptStatement(column); 
			    		}else {
			    			sqls = EqMethod.toDBScriptStatement(column); 
			    		}
			    	}else {
			    		isInversion(value);
			    		sqls = InMethod.toDBScriptStatement(column,param);
			    	}
		            break;
	            case "ne":
	            	if(param.length ==1) {
	            		if(isInversion(value)){
		    	    		sqls = EqMethod.toDBScriptStatement(column); 
		    			}else {
		    				sqls = NeMethod.toDBScriptStatement(column); 
		    			}
			    	}else {
			    		isInversion(value);
			    		sqls = InMethod.toDBScriptStatement(column,param);
			    	}
		             break;
		        case "gt":
		        	if(isInversion(value)){
			    		sqls = LtMethod.toDBScriptStatement(column); 
					}else {
						sqls = GtMethod.toDBScriptStatement(column); 
					}
		            break;
		        case "ge":
		        	if(isInversion(value)){
			    		sqls = LeMethod.toDBScriptStatement(column); 
					}else {
						sqls = GeMethod.toDBScriptStatement(column); 
					}
		             break;
	            case "le":
	            	if(isInversion(value)){
	    	    		sqls = GeMethod.toDBScriptStatement(column); 
	    			}else {
	    				sqls = LeMethod.toDBScriptStatement(column); 
	    			}
		             break;
		        case "lt":
		        	if(isInversion(value)){
			    		sqls = GtMethod.toDBScriptStatement(column); 
					}else {
						sqls = LtMethod.toDBScriptStatement(column); 
					}
		            break;
		        case "in":
		        	if(param.length ==1) {
			    		if(isInversion(value)){
			    			sqls = NeMethod.toDBScriptStatement(column); 
			    		}else {
			    			sqls = EqMethod.toDBScriptStatement(column); 
			    		}
			    	}else {
			    		isInversion(value);
			    		sqls = InMethod.toDBScriptStatement(column,param);
			    	}
		            break;
	            case "btn":
	            	if(param.length ==1) {
	    	    		if(isInversion(value)){
	    	    			sqls = GeMethod.toDBScriptStatement(column); 
	    	    		}else {
	    	    			sqls = LtMethod.toDBScriptStatement(column); 
	    	    		}
	    	    	}else {
	    	    		isInversion(value);
	    	    		sqls = BtnMethod.toDBScriptStatement(column);
	    	    	}
		             break;
		        case "between":
		        	if(param.length ==1) {
			    		if(isInversion(value)){
			    			sqls = GeMethod.toDBScriptStatement(column); 
			    		}else {
			    			sqls = LtMethod.toDBScriptStatement(column); 
			    		}
			    	}else {
			    		isInversion(value);
			    		sqls = BtnMethod.toDBScriptStatement(column);
			    	}
		            break; 
		         case "ctn":
		        	 isInversion(value);
		 	    	 sqls = LikeMethod.toDBScriptStatement(column);
			         break;
		         case "contains":
		        	 isInversion(value);
		 	    	 sqls = LikeMethod.toDBScriptStatement(column);
			         break;	
		}
		return sqls;
	}	
}
