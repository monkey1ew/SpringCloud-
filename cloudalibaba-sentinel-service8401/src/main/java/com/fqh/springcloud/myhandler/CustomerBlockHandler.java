package com.fqh.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.fqh.springcloud.bean.CommonResult;
import com.fqh.springcloud.bean.Payment;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author 海盗狗
 * @version 1.0
 */
public class CustomerBlockHandler {

    public static CommonResult handleException1(BlockException exception) {
        return new CommonResult(4444, "按客户自定义,global------1 ", new Payment(2020L, "serial003"));
    }
    public static CommonResult handleException2(BlockException exception) {
        return new CommonResult(4444, "按客户自定义,global------2", new Payment(2020L, "serial003"));
    }
}
