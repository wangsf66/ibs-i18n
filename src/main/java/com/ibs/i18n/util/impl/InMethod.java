package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public  class InMethod extends pdUtil{
	
	public static String toDBScriptStatement(String column,String para[]) {
		return  " and "+ column +" "+notOperator+" IN ("+arrayToString(para)+")";  
    }
	
	public static String arrayToString(String para[]) {
		String param ="";
		for(int i=0;i<para.length;i++) {
			if(i==para.length-1) {
				param += para[i];	
			}else {
				param += para[i]+",";
			}
		}
		return param;
	}
}