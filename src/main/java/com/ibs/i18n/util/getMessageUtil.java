package com.ibs.i18n.util;

import java.util.Locale;
import com.ibs.i18n.i18n.ApiResultI18n;

public class getMessageUtil {
	
     public static String getMessage(String code) {
	     ApiResultI18n apiResultI18n = ApiResultI18n.failure(code,getLocalLanguage());
	     return apiResultI18n.getMessage();  
	}
    
    public static String getLocalLanguage(){
		 //获取本地系统语言
	     Locale locale = Locale.getDefault();
	     String language =  locale.getLanguage()+"_"+locale.getCountry();
	     return language;  
	}
    
    public static String getMessageA(String code,String language){
	     ApiResultI18n apiResultI18n = ApiResultI18n.failure(code,language);
	     return apiResultI18n.getMessage();  
	}
}
