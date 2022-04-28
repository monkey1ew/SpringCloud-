package com.fqh.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author 海盗狗
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Storage {

    private Long id;
    private Long productId;
    private Integer total;
    private Integer used;
    private Integer residue;
}
