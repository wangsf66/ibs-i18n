package com.ibs.i18n.controller;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.entity.CitySheet;
import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.entity.ProvinceSheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.service.CityService;
import com.ibs.i18n.service.InformationService;
import com.ibs.i18n.service.ProvinceService;
import com.ibs.response.Response;
import com.ibs.response.ResponseContext;

@RestController
@RequestMapping("/city")
public class CityController {
	
	
	@Autowired
	private CityService cityService;
	
	@RequestMapping("/insert")
	public Response insert(@RequestBody CitySheet citySheet ) {
		cityService.insert(citySheet);
		return ResponseContext.getFinalResponse();
	}
	
	@RequestMapping("/insertMany")
	public Response insertMany(@RequestBody List<CitySheet> list){
		cityService.insertMany(list);
		//判断是否为数组
	 	return ResponseContext.getFinalResponse(true);
	}
	
	@RequestMapping("/updateMany")
	public Response updateMany(@RequestBody List<CitySheet> list){
		 cityService.updateMany(list);
		 return ResponseContext.getFinalResponse(true);
	}
	
	@RequestMapping("/update")
	public Response update(@RequestBody CitySheet citySheet ) {
		cityService.update(citySheet);
		return ResponseContext.getFinalResponse();
	}
	@RequestMapping("/delete")
	public MessageResult delete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
	    return cityService.delete(ids);
	}
	
	@RequestMapping("/query")
	public MessageResult query() {
		return cityService.queryA();
	}
	
	@RequestMapping("/page")
	public MessageResult page(HttpServletRequest request) {
		int pageNum =Integer.parseInt(request.getParameter("pageNum"));
		int pageSize =Integer.parseInt(request.getParameter("pageSize"));
		return cityService.Page(pageNum, pageSize);
	}
	
	//多条件查询
//	@RequestMapping("/queryCondition")
//	public MessageResult queryCondition(HttpServletRequest request){
//		List<Object> paramList = new ArrayList();
//		return cityService.queryBtn(pdUtil.czSql(request,paramList),paramList);
//	}	
		
}
