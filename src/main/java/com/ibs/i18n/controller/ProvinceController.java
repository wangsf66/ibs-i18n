package com.ibs.i18n.controller;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.entity.CitySheet;
import com.ibs.i18n.entity.ProvinceSheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.service.ProvinceService;
import com.ibs.i18n.util.pdUtil;
import com.ibs.i18n.util.impl.EqMethod;
import com.ibs.i18n.util.impl.NeMethod;

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
	@RequestMapping("/queryCondition")
	public MessageResult queryCondition(HttpServletRequest request){
		return provinceService.queryBtn(pdUtil.czSql(request));
	}
	
	
	@RequestMapping("/page")
	public MessageResult page(HttpServletRequest request) {
		int pageNum =Integer.parseInt(request.getParameter("pageNum"));
		int pageSize =Integer.parseInt(request.getParameter("pageSize"));
		return provinceService.Page(pageNum, pageSize);
	}
}
