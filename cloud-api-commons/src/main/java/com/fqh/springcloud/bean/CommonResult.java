package com.fqh.springcloud.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 海盗狗
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommonResult<T> {

//    404 not_found
    private Integer code;
    private String message;
    private T data;

    public CommonResult(Integer code, String message) {
//        this.code = code;
//        this.message = message;
        this(code, message, null);
    }
}

