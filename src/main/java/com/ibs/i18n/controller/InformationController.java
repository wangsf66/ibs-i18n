package com.ibs.i18n.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.i18n.ApiResultI18n;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.service.InformationService;

@RestController
@RequestMapping("/information")
public class InformationController {
	@Autowired
	private InformationService informationService;
	
	@RequestMapping("/insert")
	public InformationSheet insert(@RequestBody InformationSheet informationSheet ) {
//		String  str = informationService.getMessage(1001);
//	    System.out.print(str);
		MessageResult messageResult = new  MessageResult();
		messageResult.setSuccess(true);
		messageResult.setStatus("200");
		ApiResultI18n apiResultI18n = new ApiResultI18n();
		apiResultI18n.setCode(1000);
		apiResultI18n.setData("wwwwwww");
		ApiResultI18n apiResultI18nA = new ApiResultI18n();
		apiResultI18nA.setCode(1111);
		apiResultI18nA.setData("sssssss");
		ArrayList<ApiResultI18n> list = new ArrayList<ApiResultI18n>();
		list.add(apiResultI18n);
		list.add(apiResultI18nA);
		messageResult.setData(list);
		String language = "zh_CN";
		MessageResult message = informationService.getMessageResult(messageResult,language);
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
