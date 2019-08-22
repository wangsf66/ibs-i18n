package com.ibs.i18n.util.InParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.ibs.i18n.util.pdUtil;

public  class PageMethod{
	
	public static final  String  _rowsKey = "_rows";
	
	public static final  String  _pageKey = "_page";
	
	private int rows;
	
	private int  page;
	
	private String tablename;
	
    private Map<String,String> requestResourceParams;
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	
	

	public PageMethod(int rows, int page) {
		super();
		this.rows = rows;
		this.page = page;
	}
	

	public PageMethod() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}