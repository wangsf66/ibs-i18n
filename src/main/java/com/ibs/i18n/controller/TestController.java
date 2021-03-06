package com.ibs.i18n.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.entity.ProvinceSheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.redis.RedisUtil;
import com.ibs.i18n.service.CityService;
import com.ibs.i18n.service.InformationService;
import com.ibs.i18n.service.ProvinceService;

@RestController
@RequestMapping("/information")
public class TestController{
	
	public static ArrayList<InformationSheet> list = new ArrayList<InformationSheet>();

	
	static {
		InformationSheet informationSheet = new InformationSheet("1110","model error","zh_CN");
		InformationSheet inf = new InformationSheet("1110","model","zh_CN");
		InformationSheet infor = new InformationSheet("1110","1","");
		InformationSheet inforB = new InformationSheet("134340","3","");
		InformationSheet inforD = new InformationSheet("13erwr","5","");
		InformationSheet inforA = new InformationSheet("123234","2","en_US");
		InformationSheet inforC = new InformationSheet("12eret","4","zh_CN");
		list.add(informationSheet); 
		list.add(inf);
		
		list.add(infor);
		list.add(inforB);
		list.add(inforD);

		list.add(inforC);
		list.add(inforA);
	
	}
	
	@Autowired
	private InformationService informationService;
		//单条插入
		@RequestMapping("/test")
	    public MessageResult test(HttpServletRequest request){	
		 String state = request.getParameter("state");
		 InformationSheet informationSheet = new InformationSheet("1213","1","zh_CN");
		 MessageResult messageResult = new MessageResult();
		 MessageResult Mr = null;
		 if(informationSheet.getLanguage()=="") {
			 if(state.equals("")||state.equals("V")||state.equals("DV")||state.equals("VM")||state.equals("DVM")) {
			     messageResult.addValidation("api.response.code.phoneIllegal", informationSheet);
			 }
		 }else{
			try {
				informationService.insert(informationSheet);
				if(state.equals("")||state.equals("D")||state.equals("DV")||state.equals("DM")||state.equals("DVM")) {
				    messageResult.addData("api.response.code.success", informationSheet);
				}
			}catch(Exception e) {
				if(state.equals("")||state.equals("M")||state.equals("DM")||state.equals("VM")||state.equals("DVM")) {
				    messageResult.addError("api.response.code.inertError", informationSheet);
				}
			} 
		 }
		 Mr = informationService.getMessageResult(messageResult, null);
		 return Mr;
		  
	}
		
		@RequestMapping("/test2")
	    public MessageResult test2(@RequestBody JSONObject jsonObject){
		 String state=jsonObject.get("state").toString();	
		 MessageResult messageResult = new MessageResult();
		 MessageResult Mr = null;
		 for(InformationSheet obj:list){
			 if(obj.getLanguage()==""){
				 if(state.equals("")||state.equals("V")||state.equals("DV")||state.equals("VM")||state.equals("DVM")) {
					 messageResult.addValidation("api.response.code.phoneIllegal", obj);
				 }
			 }else{
				try {
					informationService.insert(obj);
					if(state.equals("")||state.equals("D")||state.equals("DV")||state.equals("DM")||state.equals("DVM")) {
					   messageResult.addData("api.response.code.success", obj);
					}
				}catch(Exception e) {
					if(state.equals("")||state.equals("M")||state.equals("DM")||state.equals("VM")||state.equals("DVM")) {
					  messageResult.addError("api.response.code.inertError", obj);
					}
				} 
			 } 
		 }
		 Mr = informationService.getMessageResult(messageResult, null);
		 return Mr;  
	}
	
	 //多条插入
	 @RequestMapping("/testList")
	 public MessageResult testList(HttpServletRequest request){
		 String state = request.getParameter("state");
		 //D、M、V、DM、DV、VM、DVM
		 MessageResult messageResult = new MessageResult();
		 MessageResult Mr = null;
		 for(InformationSheet obj:list){
			 if(obj.getLanguage()==""){
				 if(state.equals("")||state.equals("V")||state.equals("DV")||state.equals("VM")||state.equals("DVM")) {
					 messageResult.addValidation("api.response.code.phoneIllegal", obj);
				 }
			 }else{
				try {
					informationService.insert(obj);
					if(state.equals("")||state.equals("D")||state.equals("DV")||state.equals("DM")||state.equals("DVM")) {
					   messageResult.addData("api.response.code.success", obj);
					}
				}catch(Exception e) {
					if(state.equals("")||state.equals("M")||state.equals("DM")||state.equals("VM")||state.equals("DVM")) {
					  messageResult.addError("api.response.code.inertError", obj);
					}
				} 
			 } 
		 }
		 Mr = informationService.getMessageResult(messageResult, null);
		 return Mr;
	}
	
	@Autowired
	private RedisUtil redisUtil;
	
	@RequestMapping("/test1")
	public Map<Object,Object>  test() {
	     String key = "hash";
	     redisUtil.set(key, "111"); 
	     return redisUtil.hmget("en_US");
	}
	
	@RequestMapping("/page")
	public MessageResult page(HttpServletRequest request) {
		int pageNum =Integer.parseInt(request.getParameter("_page"));
		int pageSize =Integer.parseInt(request.getParameter("_rows"));
		return informationService.Page(pageNum, pageSize);
	}
	
	@RequestMapping("/pagepost")
	public MessageResult page2(@RequestBody JSONObject jsonObject) {
		int pageNum =Integer.parseInt(jsonObject.get("pageNum").toString());
		int pageSize =Integer.parseInt(jsonObject.get("pageSize").toString());
		return informationService.Page(pageNum, pageSize);
	}
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private ProvinceService provinceService;
	
	//根据父级id查询子数据
	@RequestMapping("/selectCityByPid")
	public MessageResult city(HttpServletRequest request) {
		int pId =Integer.parseInt(request.getParameter("pId"));
		return cityService.query(pId);
	}
	
	//查询全部数据
	@RequestMapping("/selectAllProvinceWithCity")
	public MessageResult city2() {
		return provinceService.query();
	}
	
	//根据父级id查询父级信息以及子数据
	@RequestMapping("/selectAllByProvinceId")
	public MessageResult city3(HttpServletRequest request) {
		String id = request.getParameter("id");
		return provinceService.query(id);
	}
	
	//根据父级id查询city数据并分页
	@RequestMapping("/selectCityByProvinceIdPage")
	public MessageResult city4(HttpServletRequest request) {
		String id =request.getParameter("pid");
		int pageNum =Integer.parseInt(request.getParameter("_page"));
		int pageSize =Integer.parseInt(request.getParameter("_rows"));
		return provinceService.query(id,pageNum,pageSize);
	}
}
