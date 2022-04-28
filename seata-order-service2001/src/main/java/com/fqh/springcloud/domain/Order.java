package com.fqh.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

/**
 * @author 海盗狗
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {

    private Long id;
    private Long userId;
    private Long productId;
    private Integer count;
    private BigDecimal money;
    /**
     * 订单状态: 0-创建中, 1-已完成
     */
    private Integer status;
}
