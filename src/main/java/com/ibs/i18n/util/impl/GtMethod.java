package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public class GtMethod extends pdUtil {
	public static String toDBScriptStatement(String column,String para) {
	   return  " and "+ column +" >= '"+para+"'";   
	}
}
