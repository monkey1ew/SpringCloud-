package com.fqh.springcloud.service;

import com.fqh.springcloud.bean.Payment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author 海盗狗
 * @version 1.0
 */

public interface PaymentService {

    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
