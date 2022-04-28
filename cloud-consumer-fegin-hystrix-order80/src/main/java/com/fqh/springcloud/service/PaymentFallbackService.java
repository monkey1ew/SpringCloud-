package com.fqh.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService{

    @Override
    public String paymentInfo_OK(Integer id) {
        return "--------PaymentFallbackService fall back(paymentInfo_OK)/(ㄒoㄒ)/~~";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "--------PaymentFallbackService fall back(paymentInfo_TimeOut)/(ㄒoㄒ)/~~";
    }
}
