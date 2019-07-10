package com.ibs.i18n.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 事物组件扫描的自动配置
 * @author DougLei
 */
@Configuration // 标明这是一个配置类
@Import(TransactionComponentAutoRegistry.class)
public class TransactionComponentScanAutoConfiguration {

}

