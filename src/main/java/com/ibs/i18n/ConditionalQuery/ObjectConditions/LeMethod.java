package com.ibs.i18n.ConditionalQuery.ObjectConditions;


public class LeMethod extends SplicingConditionsUtil {
	public static String toDBScriptStatement(String column) {
		return  " and "+ column +" <=  ?";    
	}
}
