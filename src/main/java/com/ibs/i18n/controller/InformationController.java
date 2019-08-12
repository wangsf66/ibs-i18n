package com.ibs.i18n.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.service.InformationService;

@RestController
@RequestMapping("/information")
public class InformationController {
	@Autowired
	private InformationService informationService;
	
	@RequestMapping("/insert")
	public MessageResult insert(@RequestBody InformationSheet informationSheet ) {
		MessageResult messageResult = new  MessageResult();
		messageResult.setSuccess(1);
		messageResult.setStatus("200");
		messageResult.addData("1000", "wwwwwww");
	    messageResult.addData("1111", "sssssss");
		String language = "zh_CN";
		MessageResult message = informationService.getMessageResult(messageResult,language);
		System.out.println(message.getData());
		return message;
	}
	
	@RequestMapping("/MessageResult")
	public MessageResult insertMessageResult(@RequestBody MessageResult messageResult ) {
		String language = "zh_CN";
		MessageResult message = informationService.getMessageResult(messageResult,language);
		System.out.println(message.getData());
		return message;
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
