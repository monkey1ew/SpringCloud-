package com.fqh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 海盗狗
 * @version 1.0
 */
@EnableFeignClients
@SpringBootApplication
public class OrderFeignMain80 {

    public static void main(String[] args) {

        SpringApplication.run(OrderFeignMain80.class, args);
    }
}
