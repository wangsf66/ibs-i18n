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
	
	//判断会否取反
	public static boolean isInversion(String params) {
			if(params.startsWith("!")) {
				return true;
			}
			return false;
	}
		
		//判断会否为空
	public static boolean valueIsNullStr(String params) {
			if(params.equals(null)) {
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
			    sql += " and "+ column +" = '"+ params+"'";
			}
		}
		return sql;
    }	
	
	public static String montageSql(String value,String column,String para,String sql) {
		//将参数分割为数组，以“，”号分割
		String param[] = para.split(",");
		//参数为一个时
		//eq("")，ge("")，gt("")，le("")，lt("")，ne("")，in("")，like("")，btn("")
		//参数为多个时
		//in("",""),btn("","")
		if(getMethodName(value).equals("eq")) {
			sql = EqMethod.toDBScriptStatement(value,column,para);
	    }else if(getMethodName(value).equals("ne")) {
	    	sql = NeMethod.toDBScriptStatement(value,column,para);
	    }else if(getMethodName(value).equals("gt")) {
	    	sql = GtMethod.toDBScriptStatement(value,column,para);
	    }else if(getMethodName(value).equals("ge")) {
	    	sql = GeMethod.toDBScriptStatement(value,column,para);
	    }else if(getMethodName(value).equals("le")) {
	    	sql = LeMethod.toDBScriptStatement(value,column,para);
	    }else if(getMethodName(value).equals("lt")) {
	    	sql = LtMethod.toDBScriptStatement(value,column,para);
	    }else if(getMethodName(value).equals("btn")||getMethodName(value).equals("between")) {
	    	sql = BtnMethod.toDBScriptStatement(value,column,param);
	    }else if(getMethodName(value).equals("in")) {
	    	sql = InMethod.toDBScriptStatement(value,column,param);
	    }else if(getMethodName(value).equals("ctn")||getMethodName(value).equals("contains")) {
	    	sql = LikeMethod.toDBScriptStatement(value,column,para);
	    }
		return sql;
	}
}
