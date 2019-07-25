package com.ibs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

import com.ibs.log.core.filter.HttpServletRequest4Log;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ServletComponentScan(basePackageClasses = {HttpServletRequest4Log.class})
public class IbsI18nApplication {
	public static void main(String[] args) {
		SpringApplication.run(IbsI18nApplication.class, args);
	}
}
