package com.ibs.i18n.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.i18n.entity.OfficeSheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.service.OfficeService;

@RestController
@RequestMapping("/office")
public class OfficeController {
	
	
	@Autowired
	private OfficeService officeService;
	
	@RequestMapping("/insert")
	public Response insert(@RequestBody  OfficeSheet officeSheet ) {
		 officeService.insert(officeSheet);
		 return ResponseContext.getFinalResponse();
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
		int pageNum =Integer.parseInt(request.getParameter("_page"));
		int pageSize =Integer.parseInt(request.getParameter("_rows"));
		return officeService.Page(pageNum, pageSize);
	}
	
	
	@RequestMapping("/insert/{tableName}")
	public Response insertA(@RequestBody  Map<String,Object> map,@PathVariable(name = "tableName") String tableName ) {
		 officeService.insert(tableName,map);
		 return ResponseContext.getFinalResponse();
	}
	
	@RequestMapping("/insertMany/{tableName}")
	public Response insertMany(@RequestBody  List<Map<String,Object>> maplist,@PathVariable(name = "tableName") String tableName ) {
		 officeService.insertMany(tableName,maplist);
		 return ResponseContext.getFinalResponse(true);
	}
	
	@RequestMapping("/update/{tableName}")
	public Response updateA(@RequestBody  Map<String,Object> map,@PathVariable(name = "tableName") String tableName ) {
		 officeService.update(tableName,map);
		 return ResponseContext.getFinalResponse();
	}
	
	@RequestMapping("/updateMany/{tableName}")
	public Response updateMany(@RequestBody  List<Map<String,Object>> maplist,@PathVariable(name = "tableName") String tableName ) {
		 officeService.updateMany(tableName,maplist);
		 return ResponseContext.getFinalResponse(true);
	}
	
	@RequestMapping("/delete/{tableName}")
	public Response deleteObject(HttpServletRequest request,@PathVariable(name = "tableName") String tableName ) {
		String ids = request.getParameter("ids");
	    officeService.deleteObject(tableName,ids);
	    return ResponseContext.getFinalResponse();
	}

}
