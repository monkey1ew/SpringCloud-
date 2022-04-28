package com.fqh.springcloud.service;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * @author 海盗狗
 * @version 1.0
 */
public interface AccountService {

    void decrease(Long userId, BigDecimal money);

}
