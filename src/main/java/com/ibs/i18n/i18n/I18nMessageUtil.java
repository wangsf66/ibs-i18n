package com.ibs.i18n.i18n;

import java.io.IOException;
import java.util.Map;
import com.ibs.i18n.util.TransactionComponentAutoRegistry;

public class I18nMessageUtil {
	
    private I18nMessageUtil(){
    
    }
    
    public static String getMessage(String language,int code) throws IOException {
       //initMessageSourceAccessor(language);
        return I18nMessageUtil.getMessage(language,code,TransactionComponentAutoRegistry.mapList);
    }
 
    public static String getMessage(String language,int code,Map<String,Map<String,String>> mapList){
        String message = "";
        Map<String, String> map  = mapList.get(language);
        message = map.get(code+"");
        return message;
    }
}
