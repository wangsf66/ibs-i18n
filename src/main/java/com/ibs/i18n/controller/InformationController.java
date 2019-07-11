package com.ibs.i18n.controller;


import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ibs.i18n.entity.InformationSheet;
import com.ibs.i18n.i18n.MessageResult;
import com.ibs.i18n.service.InformationService;
import com.ibs.i18n.util.TransactionComponentAutoRegistry;

@RestController
@RequestMapping("/information")
public class InformationController {
	@Autowired
	private InformationService informationService;
	
	@RequestMapping("/insert")
	public InformationSheet insert(@RequestBody InformationSheet informationSheet ) {
		MessageResult messageResult = new  MessageResult();
		messageResult.setSuccess(true);
		messageResult.setStatus("200");
		messageResult.addObject(1000, "wwwwwww");
	    messageResult.addObject(1111, "sssssss");
		String language = "zh_CN";
		MessageResult message = informationService.getMessageResult(messageResult,language);
		System.out.println(message.getData());
		Map<String, String> mapA = TransactionComponentAutoRegistry.mapList.get("zh_CN");
		System.out.println(mapA.size());
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
