package com.ibs.i18n.ConditionalQuery.ObjectConditions;


public  class LikeMethod extends SplicingConditionsUtil{

	public static String toDBScriptStatement(String column) {
		return  " and "+ column +" "+notOperator+" like  ?";
	}
}