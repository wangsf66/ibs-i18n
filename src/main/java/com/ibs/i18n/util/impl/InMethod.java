package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public  class InMethod extends pdUtil{
	
	public static String toDBScriptStatement(String params,String column,String para[]) {
		//取反
		if(isInversion(params)){
			if(para.length==1) {
				return  " and "+column+" is not null  and "+ column +" != '"+para[0]+"'";
			}else {
				return  " and "+column+" is not null  and "+ column +"not IN ("+arrayToString(para)+")";  
			}
		}else {
			//不取反
            if(para.length==1) {
            	return  " and "+column+" is not null  and "+ column +" = '"+para[0]+"'";  
			}else {
				return  " and "+column+" is not null  and "+ column + " IN ("+arrayToString(para)+")";  
			}
		}  	
    }
	
	public static String arrayToString(String para[]) {
		String param ="";
		for(int i=0;i<para.length;i++) {
			if(i==para.length-1) {
				param +="'"+para[i]+"'";	
			}else {
				param +="'"+para[i]+"'"+",";
			}
		}
		return param;
	}
}