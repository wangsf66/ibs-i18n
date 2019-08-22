package com.ibs.i18n.Filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import com.ibs.i18n.entity.ResponseBody;

@WebFilter(filterName = "ParamsFilter",urlPatterns = "/*")
public class ParamsFilter implements Filter{
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("----------------------->过滤器被创建");
	}
	
	public void destroy() {
		System.out.println("----------------------->过滤器被销毁");
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		Map<String, String> urlParams = new HashMap<String, String>(16);
		HttpServletRequest request = (HttpServletRequest) req;
		ResponseBody res = new  ResponseBody();
    	Enumeration<String> parameterNames = request.getParameterNames();
    	if(parameterNames != null && parameterNames.hasMoreElements()){
			String key = null;
			while(parameterNames.hasMoreElements()){
				key = parameterNames.nextElement();// 获取key
				if(key.equals("_")){
					continue;
				}
				urlParams.put(key, request.getParameter(key).trim());
			}
		}
    	res.setRequestBuiltinParams(analysisBuiltinParams(urlParams));
    	res.setRequestParentResourceParams(requestParentResourceParams(urlParams));
    	res.setRequestResourceParams(urlParams); 
    	request.setAttribute("ResponseBody", res);
		chain.doFilter(req, resp);
	}
	
	 /**
		 * 解析出内置的url参数
		 * @param urlParams
		 * @return
		 */
	    private Map<String, String> analysisBuiltinParams(Map<String, String> urlParams) {
	    	String str[] = {"_focusedId",  "_rows", "_page", "_resultType", "_sort", 
	    			  "_recursive", "_deep", "_pcName", 
	    			  "_simpleModel", "_psRefPropName",
	    			  "_subResourceName", "_subListRefPropName", "_subSort"
	    			 	   };
	    	Map<String, String> builtinParams = null;
			if(urlParams.size() > 0){
				builtinParams = new HashMap<String, String>(urlParams.size());
				String builtinParamValue = null;
				for (String bufp : str) {
					builtinParamValue = urlParams.remove(bufp);
					if(builtinParamValue!=null){
						builtinParams.put(bufp, builtinParamValue);
					}
				}
			}else{
				builtinParams = Collections.emptyMap();
			}
			return builtinParams;
		}
	    
	    
	    //解析出父级查询条件
	    private Map<String, String> requestParentResourceParams(Map<String, String> urlParams) {
	    	Map<String, String> parentResourceParams = null;
			if(urlParams.size() > 0){
				parentResourceParams = new HashMap<String, String>(urlParams.size());
				Iterator<Map.Entry<String, String>> iter = urlParams.entrySet().iterator();
		        while (iter.hasNext()) {
		        	Map.Entry<String, String> entry = iter.next();
		            String key = entry.getKey();
		            String value = entry.getValue();
		            if (key.startsWith("_root.")) {
		            	parentResourceParams.put(key.replace("_root.", ""), value);
		                iter.remove();
		                urlParams.remove(key);
		            }
		        }
			}else{
				parentResourceParams = Collections.emptyMap();
			}
			return parentResourceParams;
		}
}
