package com.fqh.springcloud.service.impl;

import com.fqh.springcloud.dao.OrderDao;
import com.fqh.springcloud.domain.Order;
import com.fqh.springcloud.service.AccountService;
import com.fqh.springcloud.service.OrderService;
import com.fqh.springcloud.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;


    @Override
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("===============>开始新建订单");
        //1. 新建订单
        orderDao.create(order);

        log.info("===============>订单微服务开始调用库存扣减Count");
        //2. 扣减库存
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("===============>订单微服务开始调用库存，做扣减end");

        log.info("===============>订单微服务开始调用账户服务扣钱Money");
        //3. 扣用户的钱
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("==============>订单微服务开始调用账户服务，做扣钱end");

        //4. 修改订单的状态 0 -> 1
        log.info("==============>修改订单状态开始");
        orderDao.update(order.getUserId(), 0);
        log.info("=============>修改订单状态结束");

        log.info("==========>下单结束");
    }

    @Override
    public void update(Long userId, Integer status) {

    }
}
