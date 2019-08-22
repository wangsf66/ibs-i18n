package com.ibs.i18n.util.InParam;


public  class OrderByMethod{
	
	public static final  String _sortKey = "_sort";
	
	private String sort;

	public OrderByMethod(String sort) {
		super();
		this.sort = sort;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public OrderByMethod() {
		super();
	}
	
	public String getSql() {
		if(this.sort==null) {
			return "";
		}
		String sort[] = this.sort.split(",");
		String sql = " order by ";
		for(int i=0;i<sort.length;i++) {
			if(i==sort.length-1) {
				sql+= sort[i];
			}else {
				sql+= sort[i]+",";
			}
		}
		return  sql;
	}
}