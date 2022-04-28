package com.fqh.springcloud.service;

/**
 * @author 海盗狗
 * @version 1.0
 */
public interface StorageService {

    void decrease(Long productId, Integer count);
}
