package com.fqh.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 海盗狗
 * @version 1.0
 *
 * 1.CAS 是什么 ？ ======> compareAndSet
 *  比较,交换
 */
public class CASDemo {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(5);

//        main Thread do things
        System.out.println(atomicInteger.compareAndSet(5, 2019) + "\t current data: " + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1024) + "\t current data: " + atomicInteger.get());

    }
}
