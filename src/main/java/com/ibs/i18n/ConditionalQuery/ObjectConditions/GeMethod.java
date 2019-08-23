package com.ibs.i18n.ConditionalQuery.ObjectConditions;


public class GeMethod extends SplicingConditionsUtil {
	public static String toDBScriptStatement(String column) {
	    return  " and "+ column +" > ?";   
	}
}
