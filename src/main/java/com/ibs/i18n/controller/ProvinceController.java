package com.ibs.i18n.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.entity.ProvinceSheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.service.ProvinceService;

@RestController
@RequestMapping("/province")
public class ProvinceController {
	
	@Autowired
	private ProvinceService provinceService;
	
	@RequestMapping("/insert")
	public MessageResult insert(@RequestBody ProvinceSheet provinceSheet ) {
		return provinceService.insert(provinceSheet);
	}
	
	@RequestMapping("/insertMany")
	public MessageResult insertMany(@RequestBody List<Map<String,Object>> list){
		return 	provinceService.insertMany(list);
	}
	
	@RequestMapping("/updateMany")
	public MessageResult updateMany(@RequestBody List<Map<String,Object>> list){
		return 	provinceService.updateMany(list);
	}
	
	@RequestMapping("/update")
	public MessageResult update(@RequestBody ProvinceSheet provinceSheet) {
		return provinceService.update(provinceSheet);
	}
	
	@RequestMapping("/delete")
	public MessageResult delete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
		String deleteChildNode = request.getParameter("deleteChildNode");//FALSE OR YES
		return provinceService.delete(ids, deleteChildNode);
	}
	
	@RequestMapping("/query")
	public MessageResult query(){
		return provinceService.queryA();
	}
	
	//多条件查询
	@RequestMapping("/queryCondition/{tableName}")
	public MessageResult queryCondition(HttpServletRequest request,@PathVariable(name = "tableName") String tableName){
		return provinceService.dynamicQuery(request,tableName);
	}
	
	
	@RequestMapping("/page")
	public MessageResult page(HttpServletRequest request) {
		int pageNum =Integer.parseInt(request.getParameter("pageNum"));
		int pageSize =Integer.parseInt(request.getParameter("pageSize"));
		return provinceService.Page(pageNum, pageSize);
	}
}
