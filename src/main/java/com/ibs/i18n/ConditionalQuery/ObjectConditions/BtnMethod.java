package com.ibs.i18n.ConditionalQuery.ObjectConditions;


public  class BtnMethod extends SplicingConditionsUtil{
	
	public static String toDBScriptStatement(String column) {
		return  " and "+ column +" "+notOperator+" BETWEEN  ?  AND  ?";  	
    }
}