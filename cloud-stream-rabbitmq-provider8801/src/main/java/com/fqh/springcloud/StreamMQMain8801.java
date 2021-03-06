package com.fqh.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author 海盗狗
 * @version 1.0
 */
@EnableEurekaClient
@SpringBootApplication
public class StreamMQMain8801 {

    public static void main(String[] args) {

        SpringApplication.run(StreamMQMain8801.class, args);
    }
}
