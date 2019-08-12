package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public class LeMethod extends pdUtil {
	public static String toDBScriptStatement(String params,String column,String para) {
		if(isInversion(params)){
			  return  " and "+column+" is not null  and "+ column +" > '"+para+"'";
		}else {
			   return  " and "+column+" is not null  and "+ column +" <= '"+para+"'";   
		}  
	}
}
