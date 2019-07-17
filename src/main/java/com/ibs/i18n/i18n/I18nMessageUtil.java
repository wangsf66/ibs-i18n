package com.ibs.i18n.i18n;

import java.io.IOException;
import java.util.Map;
import com.ibs.i18n.util.TransactionComponentAutoRegistry;

public class I18nMessageUtil {
	
    private I18nMessageUtil(){
    
    }
    
    public static String getMessage(String language,String code) throws IOException {
        return I18nMessageUtil.getMessage(language,code,TransactionComponentAutoRegistry.mapList);
    }
 
    public static String getMessage(String language,String code,Map<String,Map<String,String>> mapList){
        Map<String, String> map  = mapList.get(language);
        String message  = map.get(code);
        if(message ==""||message ==null) {
        	return "";
        }else {
        	return message;
        } 
    }
}
