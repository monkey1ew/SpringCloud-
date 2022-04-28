package com.fqh.springcloud.dao;

import com.fqh.springcloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Mapper
public interface OrderDao {

    //1.新建订单
    void create(Order order);

    //2.修改订单状态 0->1
    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
