package com.fqh.springcloud.service;

import com.fqh.springcloud.bean.CommonResult;
import com.fqh.springcloud.bean.Payment;
import org.springframework.stereotype.Component;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Component
public class PaymentFallbackService implements PaymentService{

    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(4444,
                "服务降级返回, -----PaymentFallbackService",
                new Payment(id, "errorSerial"));
    }
}
