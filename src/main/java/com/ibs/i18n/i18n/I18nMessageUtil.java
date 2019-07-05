package com.ibs.i18n.i18n;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.springframework.context.support.MessageSourceAccessor;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.douglei.tools.instances.scanner.FileScanner;
import com.ibs.i18n.util.ReloadableResourceBundleMessageSource;

public class I18nMessageUtil {
	private static MessageSourceAccessor accessor;
    //private static MessageSource messageSource;
    private static final String PATH_PARENT = "classpath:i18n/";
    private static final String SUFFIX = ".i18n";
    private static ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

    private I18nMessageUtil(){
    }

    /**
     * 初始化资源文件的存储器
     * 加载指定语言配置文件
     *
     * @param language 语言类型(文件名即为语言类型,eg: en_us 表明使用 美式英文 语言配置)
     */
    private static void initMessageSourceAccessor(String language) throws IOException {
        /**
         * 获取配置文件名
         */
    	//扫描所有以.i18n为后缀名的文件
    	FileScanner  sc = new FileScanner(".i18n");
    	List<String> list = sc.scan(true, "i18n/");
    	Properties p  = null;
    	for(String file:list) {
			p = new Properties(); 
			p.load(FileScanner.readByScanPath(file));
    		System.out.print(file);
    	}
    	sc.destroy();

        Resource resource = resourcePatternResolver.getResource(PATH_PARENT + language + SUFFIX);
        String fileName = resource.getURL().toString();
        int lastIndex = fileName.lastIndexOf(".");
        String baseName = fileName.substring(0,lastIndex);
        /**
         * 读取配置文件
         */
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename(baseName);
        reloadableResourceBundleMessageSource.setCacheSeconds(5);
        accessor = new MessageSourceAccessor(reloadableResourceBundleMessageSource);
       // messageSource = reloadableResourceBundleMessageSource;
    }

    /**
     * 获取一条语言配置信息
     *
     * @param language 语言类型,zh_cn: 简体中文, en_us: 英文
     * @param message 配置信息属性名,eg: api.response.code.user.signUp
     * @param defaultMessage 默认信息,当无法从配置文件中读取到对应的配置信息时返回改信息
     * @return
     * @throws IOException
     */
    public static String getMessage(String language, String defaultMessage,int code) throws IOException {
        initMessageSourceAccessor(language);
        String message = "";
        try {
        	message = accessor.getMessage(code+"");
        }catch(Exception e) {
        	message  = "";
        }
        return message;
    }
}
