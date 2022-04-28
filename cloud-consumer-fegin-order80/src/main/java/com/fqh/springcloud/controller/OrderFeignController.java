package com.fqh.springcloud.controller;

import com.fqh.springcloud.bean.CommonResult;
import com.fqh.springcloud.bean.Payment;
import com.fqh.springcloud.service.PaymentFeignService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Slf4j
@RestController
public class OrderFeignController {

    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping(value = "/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id) {
        return paymentFeignService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/feign/timeout")
    public String paymentFeignTimeout() {
//        openfegin-ribbon, 客户端默认等待1s
        return paymentFeignService.paymentFeignTimeout();
    }

}
