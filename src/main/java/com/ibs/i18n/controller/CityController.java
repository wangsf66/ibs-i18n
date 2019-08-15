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
import com.ibs.i18n.util.pdUtil;

@RestController
@RequestMapping("/city")
public class CityController {
	
	
	@Autowired
	private CityService cityService;
	
	@RequestMapping("/insert")
	public MessageResult insert(@RequestBody CitySheet citySheet ) {
		return cityService.insert(citySheet);
	}
	
	@RequestMapping("/insertMany")
	public MessageResult insertMany(@RequestBody List<CitySheet> list){
		return 	cityService.insertMany(list);
	}
	
	@RequestMapping("/updateMany")
	public MessageResult updateMany(@RequestBody List<CitySheet> list){
		return 	cityService.insertMany(list);
	}
	
	@RequestMapping("/update")
	public MessageResult update(@RequestBody CitySheet citySheet ) {
		return cityService.update(citySheet);
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
	@RequestMapping("/queryCondition")
	public MessageResult queryCondition(HttpServletRequest request){
		List<Object> paramList = new ArrayList();
		return cityService.queryBtn(pdUtil.czSql(request,paramList),paramList);
	}	
		
}
