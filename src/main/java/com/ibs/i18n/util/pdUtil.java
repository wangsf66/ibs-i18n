package com.ibs.i18n.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ibs.i18n.util.impl.BtnMethod;
import com.ibs.i18n.util.impl.EqMethod;
import com.ibs.i18n.util.impl.GeMethod;
import com.ibs.i18n.util.impl.GtMethod;
import com.ibs.i18n.util.impl.InMethod;
import com.ibs.i18n.util.impl.LeMethod;
import com.ibs.i18n.util.impl.LikeMethod;
import com.ibs.i18n.util.impl.LtMethod;
import com.ibs.i18n.util.impl.NeMethod;

public class pdUtil{
	
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
	public static String czSql(HttpServletRequest request,List<Object> paramList) {
		String urlParameter=request.getQueryString();//网址中的参数
		StringBuilder param = null;
		if(urlParameter!=null&&!"".equals(urlParameter)){
            try {
            	param = new StringBuilder();
            	param.append(URLDecoder.decode(urlParameter,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
		if(param!=null) {
			//在正式操作中，这个数组是除以“_”开头的内置参数外的资源数组，在request中进行处理，这块只负读取
			//？号后面的参数组，以&号分割
			String parameterSet[] = param.toString().split("&");
			return executeSql(parameterSet,paramList);
		}else {
			return "";
		}
	}
	
	//拼接sql
	public static String executeSql(String parameterSet[],List<Object> paramList){
		StringBuilder sql = new StringBuilder();
		String params[] = {};
		String column = null;
	    String value = null;
		for(String parameter:parameterSet) {
			//将一对参数分割为列名和值 ，以"="分割
		    params = parameter.split("="); 
		    column = params[0];
		    //trim()去掉字符串两端的空格
		    value = params[1].trim();
		    //如果参数中存在“（”，则参数是内置的方法 ，否则即为普通的值
		    if(params[1].contains("(")) {
		    	//获取方法中的参数
				String para = value.substring(value.indexOf("(")+1,value.indexOf(")"));
				//参数为空
				if(valueIsNullStr(para)) {
					sql.append(" and "+ column +" is null"); 
				}else{
					sql.append(montageSql(value,column,para,sql,paramList)); 	
				}	
			}else {
				sql.append(" and "+ column +" = "+ params); 
			}
		}
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
