package com.fqh.springcloud.service;

import com.fqh.springcloud.domain.Order;

/**
 * @author 海盗狗
 * @version 1.0
 */
public interface OrderService {

    void create(Order order);

    void update(Long userId, Integer status);
}
