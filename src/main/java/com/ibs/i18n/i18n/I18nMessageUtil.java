package com.ibs.i18n.i18n;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.douglei.tools.instances.scanner.FileScanner;
import com.douglei.tools.utils.CloseUtil;

public class I18nMessageUtil {
	
    private static Map<String,Map<String,String>> mapList = new HashMap<String,Map<String,String>>();
    
    private I18nMessageUtil(){
    
    }
    
    @SuppressWarnings("unchecked")
	private static void initMessageSourceAccessor(String language) throws IOException {
    	//扫描所有以.i18n为后缀名的文件
        Map<String, String> map  = null;
    	FileScanner  sc = new FileScanner(".i18n");
    	List<String> list = sc.scan(true, "i18n/");
    	Properties p  = null;
    	for(String file:list) {
			p = new Properties(); 
			InputStream in  = null;
			try {
				in = FileScanner.readByScanPath(file);
				p.load(in);
				map = new HashMap<String, String>((Map) p);
				File tempFile =new File(file.trim());  
				int lastIndex = tempFile.getName().lastIndexOf(".");
		        String baseName = tempFile.getName().substring(0,lastIndex);
				mapList.put(baseName, map);
			}catch(IOException e){
				 e.printStackTrace();
			}finally {
				CloseUtil.closeIO(in);
			}
    	}
    	sc.destroy();
    }

    public static String getMessage(String language,int code) throws IOException {
        initMessageSourceAccessor(language);
        return I18nMessageUtil.getMessage(language,code,mapList);
    }
    
    public static String getMessage(String language,int code,Map<String,Map<String,String>> mapList){
        String message = "";
        Map<String, String> map  = mapList.get(language);
        message = map.get(code+"");
        return message;
    }
}
