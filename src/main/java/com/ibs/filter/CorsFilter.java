package com.ibs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter{

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		response.addHeader("Access-Control-Allow-Origin", "*");  
		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");  
		response.addHeader("Access-Control-Max-Age", "3600");  
		response.addHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, _token, _projectId, token"); // TODO _projectId临时添加，适应触摸屏
		
		String requestMethod = request.getMethod();
		if("options".equalsIgnoreCase(requestMethod)){
			return;
		}
		chain.doFilter(req, resp);
		
	}
}
