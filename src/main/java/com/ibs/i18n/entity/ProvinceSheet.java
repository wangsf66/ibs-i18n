package com.ibs.i18n.entity;

import java.util.Date;
import java.util.List;

import com.douglei.tools.utils.naming.column.Column;

public class ProvinceSheet {
	@Column("ID")
	private String id;
	@Column("PROVINCENAME")
	private String provinceName;
	@Column("AREACODE")
    private int areaCode;
	@Column("DIRECTLYUNDER")
	private int directlyUnder;
	@Column("CREATEDATE")
    private Date createDate;
	@Column("POPULATIONSIZE")
	private float populationSize;
	@Column
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	private List<CitySheet> list;
	
	public List<CitySheet> getList() {
		return list;
	}
	public void setList(List<CitySheet> list) {
		this.list = list;
	}
	public int getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(int areaCode) {
		this.areaCode = areaCode;
	}
	public int getDirectlyUnder() {
		return directlyUnder;
	}
	public void setDirectlyUnder(int directlyUnder) {
		this.directlyUnder = directlyUnder;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public float getPopulationSize() {
		return populationSize;
	}
	public void setPopulationSize(float populationSize) {
		this.populationSize = populationSize;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

}
