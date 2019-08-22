package com.ibs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ServletComponentScan(basePackages = "com.ibs.i18n.Filter")
public class IbsI18nApplication {
	public static void main(String[] args) {
		SpringApplication.run(IbsI18nApplication.class, args);
	}	
}
