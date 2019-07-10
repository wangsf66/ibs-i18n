package com.ibs.i18n.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import com.douglei.orm.spring.TransactionComponentRegister2Spring;
import com.douglei.tools.instances.scanner.FileScanner;
import com.douglei.tools.utils.CloseUtil;

/**
 * 
 * @author DougLei
 */
public class TransactionComponentAutoRegistry extends TransactionComponentRegister2Spring implements BeanFactoryAware, ImportBeanDefinitionRegistrar{
	public static Map<String,Map<String,String>> mapList = new HashMap<String,Map<String,String>>();
	
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		Map<String, String> map  = null;
    	FileScanner  sc = new FileScanner(".i18n.properties");
    	List<String> list = sc.scan(true, "i18n/");
    	Properties p  = null;
    	for(String file:list) {
			p = new Properties(); 
			InputStream in  = null;
			try {
				in = FileScanner.readByScanPath(file);
				p.load(in);
				map = new HashMap<String, String>((Map)p);
				File tempFile =new File(file.trim());  
				int firstIndex = tempFile.getName().indexOf(".");
		        String baseName = tempFile.getName().substring(0,firstIndex);
				mapList.put(baseName, map);
			}catch(IOException e){
				 e.printStackTrace();
			}finally {
				 CloseUtil.closeIO(in);
			}
    	}
    	sc.destroy();
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
	}
}
