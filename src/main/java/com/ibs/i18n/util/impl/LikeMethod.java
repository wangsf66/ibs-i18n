package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public  class LikeMethod extends pdUtil{
	
	public static String toDBScriptStatement(String params,String column,String para) {
		if(isInversion(params)){
			 return  " and "+column+" is not null  and "+ column +" not like '"+para+"'";
		}else {
			 return  " and "+column+" is not null  and "+ column +" like '"+para+"'";   
		}  	
    }
}