package com.ibs.i18n.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.service.InformationService;
import com.ibs.i18n.util.getMessageUtil;

@RestController
@RequestMapping("/information")
public class InformationController {
	@Autowired
	private InformationService informationService;
	
	@RequestMapping("/insert")
	public InformationSheet insert(@RequestBody InformationSheet informationSheet ) {
		String  str = informationService.getMessage(1111);
	    System.out.print(str);
		return informationSheet = informationService.insert(informationSheet);
	}

	@RequestMapping("/update")
	public InformationSheet update(@RequestBody InformationSheet informationSheet ) {
		return informationService.update(informationSheet);
	}
	@RequestMapping("/delete")
	public InformationSheet delete(@RequestBody InformationSheet informationSheet ) {
		return informationService.delete(informationSheet);
	}
	@RequestMapping("/query")
	public Object query(@RequestBody InformationSheet informationSheet ) {
		return informationService.query(informationSheet);
	}
}
