package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public  class BtnMethod extends pdUtil{
	
	public static String toDBScriptStatement(String params,String column,String para[]) {
		//取反
		if(isInversion(params)){
			if(para.length==1) {
				return  " and "+column+" is not null  and "+ column +" > '"+para[0]+"'";
			}else {
				return  " and "+column+" is not null  and "+ column +"not BETWEEN '"+para[0]+"' AND '"+para[1]+"'";  
			}
		}else {
			//不取反
            if(para.length==1) {
            	return  " and "+column+" is not null  and "+ column +" < '"+para[0]+"'";  
			}else {
				return  " and "+column+" is not null  and "+ column +" BETWEEN '"+para[0]+"' AND '"+para[1]+"'";   
			}
		}  	
    }
}