package com.fqh.springcloud.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 海盗狗
 * @version 1.0
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Payment implements Serializable {

    private Long id;
    private String serial;
}
