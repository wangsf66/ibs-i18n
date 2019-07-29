package com.ibs.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author DougLei
 */
@Configuration
public class CorsFilterRegistry {

	@Bean
	public CorsFilter corsFilter() {
		return new CorsFilter();
	}
	
	/**
	 * 注册log过滤器
	 * @return
	 */
	@Bean
	public FilterRegistrationBean<CorsFilter> CorsFilter(){
		FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<CorsFilter>();
		registration.setFilter(corsFilter());
		registration.addUrlPatterns("/*");
		registration.setName("cors-Douglei");
		registration.setOrder(1);
		return registration;
	}
}
