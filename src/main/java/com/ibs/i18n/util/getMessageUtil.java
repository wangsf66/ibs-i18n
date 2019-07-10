package com.ibs.i18n.util;

import java.util.Locale;
import com.ibs.i18n.i18n.ApiResultI18n;

public class getMessageUtil {
	
     public static String getMessage(int code) {
		 //获取本地系统语言
	     Locale locale = Locale.getDefault();
	     String language =  locale.getLanguage()+"_"+locale.getCountry();
	     ApiResultI18n apiResultI18n = ApiResultI18n.failure(code,language);
	     return apiResultI18n.getMsg();  
	}
    
    public static String getLocalLanguage(){
		 //获取本地系统语言
	     Locale locale = Locale.getDefault();
	     String language =  locale.getLanguage()+"_"+locale.getCountry();
	     return language;  
	}
}
