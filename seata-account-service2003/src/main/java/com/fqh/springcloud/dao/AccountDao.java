package com.fqh.springcloud.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.MatchesPattern;
import java.math.BigDecimal;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Mapper
public interface AccountDao {

    void decrease(@Param("userId") Long userId, @Param("money")BigDecimal money);

}
