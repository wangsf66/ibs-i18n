package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public  class EqMethod extends pdUtil{
	
	public static String toDBScriptStatement(String column) {
		return  " and "+ column +" = ?";   
    }
}