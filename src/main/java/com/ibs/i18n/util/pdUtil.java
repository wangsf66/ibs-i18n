package com.ibs.i18n.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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
	public static String czSql(HttpServletRequest request) {
		String urlParameter=request.getQueryString();//网址中的参数
		String param = "";
		if(urlParameter!=null&&!"".equals(urlParameter)){
            try {
            	param=URLDecoder.decode(urlParameter,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }else {
        	param="";
        }
		if(param!="") {
			//在正式操作中，这个数组是除以“_”开头的内置参数外的资源数组，在request中进行处理，这块只负读取
			//？号后面的参数组，以&号分割
			String parameterSet[] = param.split("&");
			return executeSql(parameterSet);
		}else {
			return "";
		}
	}
	
	//拼接sql
	public static String executeSql(String parameterSet[]){
		String sql = "";
		String params[] = {};
		String column = null;
	    String value = null;
		for(String parameter:parameterSet) {
			//将一对参数分割为列名和值 ，以"="分割
		    params = parameter.split("="); 
		    column = params[0];
		    value = params[1];
		    //如果参数中存在“（”，则参数是内置的方法 ，否则即为普通的值
		    if(params[1].contains("(")) {
		    	//获取方法中的参数
				String para = value.substring(value.indexOf("(")+1,value.indexOf(")"));
				//参数为空
				if(valueIsNullStr(para)) {
					sql += " and "+ column +" is null"; 
				}else{
					sql += montageSql(value,column,para,sql);			
				}	
			}else {
			    sql += " and "+ column +" = "+ params;
			}
		}
		return sql;
    }	
	//参数为一个时
	//eq("")，ge("")，gt("")，le("")，lt("")，ne("")，in("")，like("")，btn("")
	//参数为多个时
	//in("",""),btn("","")
	public static String montageSql(String value,String column,String para,String sql) {
		//将参数分割为数组，以“，”号分割
		//isInversion(value) 值为true表示取反 false，反之
		String param[] = para.split(",");
		if(getMethodName(value).equals("eq")) {
			if(isInversion(value)){
				sql = NeMethod.toDBScriptStatement(column,para); 
			}else {
				sql = EqMethod.toDBScriptStatement(column,para); 
			}
	    }else if(getMethodName(value).equals("ne")) {
	    	if(isInversion(value)){
	    		sql = EqMethod.toDBScriptStatement(column,para); 
			}else {
				sql = NeMethod.toDBScriptStatement(column,para); 
			}
	    }else if(getMethodName(value).equals("gt")) {
	    	if(isInversion(value)){
	    		sql = LtMethod.toDBScriptStatement(column,para); 
			}else {
				sql = GtMethod.toDBScriptStatement(column,para); 
			}
	    }else if(getMethodName(value).equals("ge")) {
	    	if(isInversion(value)){
	    		sql = LeMethod.toDBScriptStatement(column,para); 
			}else {
				sql = GeMethod.toDBScriptStatement(column,para); 
			}
	    }else if(getMethodName(value).equals("le")) {
	    	if(isInversion(value)){
	    		sql = GeMethod.toDBScriptStatement(column,para); 
			}else {
				sql = LeMethod.toDBScriptStatement(column,para); 
			}
	    }else if(getMethodName(value).equals("lt")) {
	    	if(isInversion(value)){
	    		sql = GtMethod.toDBScriptStatement(column,para); 
			}else {
				sql = LtMethod.toDBScriptStatement(column,para); 
			}
	    }else if(getMethodName(value).equals("btn")||getMethodName(value).equals("between")) {
	    	//between当传递俩个参数时是一个段，当只传一个值时，取值为0~参数的数据
	    	if(param.length ==1) {
	    		if(isInversion(value)){
	    			sql = GeMethod.toDBScriptStatement(column,para); 
	    		}else {
	    			sql = LtMethod.toDBScriptStatement(column,para); 
	    		}
	    	}else {
	    		isInversion(value);
	    		sql = BtnMethod.toDBScriptStatement(column,param);
	    	}
	    }else if(getMethodName(value).equals("in")) {
	    	if(param.length ==1) {
	    		if(isInversion(value)){
	    			sql = NeMethod.toDBScriptStatement(column,para); 
	    		}else {
	    			sql = EqMethod.toDBScriptStatement(column,para); 
	    		}
	    	}else {
	    		isInversion(value);
	    		sql = InMethod.toDBScriptStatement(column,param);
	    	}
	    }else if(getMethodName(value).equals("ctn")||getMethodName(value).equals("contains")) {
	    	isInversion(value);
	    	sql = LikeMethod.toDBScriptStatement(column, para);
	    }
		return sql;
	}
}
