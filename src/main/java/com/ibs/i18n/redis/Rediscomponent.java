package com.ibs.i18n.redis;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class Rediscomponent implements BeanPostProcessor{

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if(bean instanceof RedisUtil) {
		  ((RedisUtil)bean).getRedisUtil();
		}
		return bean;
	}
}
