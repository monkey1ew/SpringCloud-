package com.fqh.springcloud.service.impl;

import com.fqh.springcloud.dao.StorageDao;
import com.fqh.springcloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Slf4j
@Service
public class StorageServiceImpl implements StorageService {

    @Resource
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("===========>storage-service中扣减库存开始");
        storageDao.decrease(productId, count);
        log.info("===========>storage-service扣减库存结束");
    }
}
