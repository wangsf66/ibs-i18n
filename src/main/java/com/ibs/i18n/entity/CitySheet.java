package com.ibs.i18n.entity;

import java.util.Date;

import com.ibs.parent.code.entity.column.Column;

public class CitySheet {
	@Column("ID")
	private int id;
	@Column("CITYNAME")
	private String cityName;
	@Column("PID")
	private String pId;
	@Column("ZIPCODE")
	private int zipCode;
	@Column("DIRECTLYUNDER")
	private int directlyUnder;
	@Column("CREATEDATE")
    private Date createDate;
	@Column("POPULATIONSIZE")
	private float populationSize;
	
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	public CitySheet(int id, String pId, String cityName) {
		super();
		this.id = id;
		this.cityName = cityName;
		this.pId = pId;
	}
	public CitySheet() {
		super();
	}
	
	public CitySheet(int id, String cityName, String pId, int zipCode, int directlyUnder, Date createDate,
			float populationSize) {
		super();
		this.id = id;
		this.cityName = cityName;
		this.pId = pId;
		this.zipCode = zipCode;
		this.directlyUnder = directlyUnder;
		this.createDate = createDate;
		this.populationSize = populationSize;
	}
	
}
