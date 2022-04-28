package com.fqh.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 海盗狗
 * @version 1.0
 */
@MapperScan({"com.fqh.springcloud.dao"})
@Configuration
public class MyBatisConfig {
}
