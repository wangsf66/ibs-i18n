package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public  class InMethod extends pdUtil{
	
	public static String toDBScriptStatement(String column,String para[]) {
		StringBuilder hql = new StringBuilder();
		hql.append(" and "+ column +" "+notOperator+" IN (");
		for(int i=0;i<para.length;i++) {
			if(i==para.length-1) {
				hql.append("? )");	
			}else if(para[i].equals(null)) {
				hql.append(" ");	
			}else {
				hql.append("? ,");	
			}
		}
		return hql.toString();  
    }
}