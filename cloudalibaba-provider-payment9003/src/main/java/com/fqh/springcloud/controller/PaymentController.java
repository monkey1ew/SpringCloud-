package com.fqh.springcloud.controller;

import cn.hutool.Hutool;
import com.alibaba.nacos.common.util.UuidUtils;
import com.fqh.springcloud.bean.CommonResult;
import com.fqh.springcloud.bean.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author 海盗狗
 * @version 1.0
 */
@RestController
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    public static HashMap<Long, Payment> hashMap = new HashMap<>();
    static {
        hashMap.put(1L, new Payment(1L, "28ada1w21xada231321da"));
        hashMap.put(2L, new Payment(2L, "b22sadahu2iqe1eqe121e"));
        hashMap.put(3L, new Payment(3L, "cua2131asdhue21addsad"));
    }
    @GetMapping(value = "/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id) {
        Payment payment = hashMap.get(id);
        CommonResult<Payment> result = new CommonResult<>(200, "from mysql, serverPort: " + serverPort, payment);
        return result;
    }
}
