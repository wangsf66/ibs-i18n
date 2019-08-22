package com.ibs.i18n.controller;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.entity.CitySheet;
import com.ibs.i18n.entity.OfficeSheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.service.CityService;
import com.ibs.i18n.service.OfficeService;
import com.ibs.i18n.util.pdUtil;

@RestController
@RequestMapping("/office")
public class OfficeController {
	
	
	@Autowired
	private OfficeService officeService;
	
	@RequestMapping("/insert")
	public MessageResult insert(@RequestBody  OfficeSheet officeSheet ) {
		return officeService.insert(officeSheet);
	}
	
	@RequestMapping("/update")
	public MessageResult update(@RequestBody OfficeSheet officeSheet ) {
		return officeService.update(officeSheet);
	}
	@RequestMapping("/delete")
	public MessageResult delete(HttpServletRequest request) {
		String ids = request.getParameter("ids");
	    return officeService.delete(ids);
	}
	
	
	@RequestMapping("/page")
	public MessageResult page(HttpServletRequest request) {
		int pageNum =Integer.parseInt(request.getParameter("pageNum"));
		int pageSize =Integer.parseInt(request.getParameter("pageSize"));
		return officeService.Page(pageNum, pageSize);
	}
	
	//多条件查询
	@RequestMapping("/queryCondition/{tableName}")
	public MessageResult queryCondition(HttpServletRequest request,@PathVariable(name = "tableName") String tableName){
		List<Object> paramList = new ArrayList();
		return officeService.queryBtn(pdUtil.czSql(request,paramList,tableName),paramList,tableName);
	}
		
}
