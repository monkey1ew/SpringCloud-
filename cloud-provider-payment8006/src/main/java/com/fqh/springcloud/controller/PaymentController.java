package com.fqh.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Slf4j
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/consul")
    public String paymentZk() {
        log.info("有人来call我了............");
        return "springcloud with consul :" + serverPort
                + "\t" + UUID.randomUUID().toString();
    }
}
