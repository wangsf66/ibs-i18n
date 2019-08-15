package com.ibs.i18n.util.impl;

import com.ibs.i18n.util.pdUtil;

public  class InMethod extends pdUtil{
	
	public static String toDBScriptStatement(String column,String para[]) {
		StringBuilder hql = new StringBuilder();
		hql.append(" and "+ column +" "+notOperator+" IN (");
		for(int i=0;i<para.length;i++) {
			if(para[i].equals("null")) {
				returnHql(i,para,hql);
			}else {
				hql.append("? ,");
				returnHql(i,para,hql);
			}		
		}
		return hql.toString();  
    }
	
	//当元素为最后一个字符串时做的拼接操作
	public static StringBuilder returnHql(int i,String para[],StringBuilder hql) {
		if(i==para.length-1) {
			hql.deleteCharAt(hql.length()-1);
			hql.append(")");
		}
		return hql;
	}
}