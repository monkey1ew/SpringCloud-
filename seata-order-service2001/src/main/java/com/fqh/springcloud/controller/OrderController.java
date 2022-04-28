package com.fqh.springcloud.controller;

import com.fqh.springcloud.domain.CommonResult;
import com.fqh.springcloud.domain.Order;
import com.fqh.springcloud.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 海盗狗
 * @version 1.0
 */
@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/create")
    public CommonResult commonResult(Order order) {
        orderService.create(order);
        return new CommonResult(200, "订单创建成功");
    }
}
