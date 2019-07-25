package com.ibs.i18n.i18n;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.ibs.i18n.redis.RedisUtil;

@Component
public class I18nMessageUtil implements ApplicationContextAware {
	
	private static RedisUtil redisUtil;
	
	private static ApplicationContext applicationContext;
	
    public static String getMessage(String language,String code) throws IOException {
    	if(redisUtil==null) {
    		redisUtil = applicationContext.getBean(RedisUtil.class);
    	}
        return getMessageA(language,code);
    }
 
    private static String getMessageA(String language,String code){
        Map<Object, Object> map  = redisUtil.hmget(language);
        String message  =(String)map.get(code);
        if(message ==""||message ==null) {
        	return "";
        }else {
        	return message;
        } 
    }

	@Override
	public void setApplicationContext(ApplicationContext ac) throws BeansException {
		applicationContext = ac;
	}
}
