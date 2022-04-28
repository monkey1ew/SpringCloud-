package com.fqh.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.ServerSocket;
import java.util.concurrent.TimeUnit;

/**
 * @author 海盗狗
 * @version 1.0
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA() {
        return "--------------testA";
    }

    @GetMapping("/testB")
    public String testB() {
        return "--------------testB";
    }

    @GetMapping("/testD")
    public String testD() {
//        try {
//            线程休眠几秒
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        log.info("testD 测试RT");
//    }
//            异常比例
        log.info("testD 测试异常比例");
        int age = 10 / 0;
        return "--------------testD";
    }

//    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
//        int age = 10 / 0; //这里运行时异常, sentinel可不管(只管控制台的相关配置), 页面直接500错误
        return "--------------testHotKey";
    }

    public String deal_testHotKey(String p1, String p2, BlockException blockException) {
//        sentinel的默认提示: Blocked by sentinel limiting...
        return "--------------testHotKey, deal_HotKey/(ㄒoㄒ)/~~";
    }
}
