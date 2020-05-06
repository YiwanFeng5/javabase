package com.yiwanfeng.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Project Name: javabase
 * Class Name: TestAtomicDemo
 * Author: YiwanFeng
 * Create Time: 2020/5/3
 * Simple Description: 关于原子性操作
 * 一、i++ 的原子性问题，i++ 的操作实际上分为三个步骤“读 - 改 - 写”
 *      int i = 10;
 *      i = i++;//10
 *      相当于：
 *      int temp = i;
 *      i = i + 1;
 *      i = temp;
 * 二、原子变量：jdk1.5+ java.util.concurrent.atomic 包下提供了原子变量，底层：
 *      1. volatile 保证内存可见性
 *      2. CAS（Compare And Swap） 算法保证数据的原子性
 *          CAS 算法是硬件对于并发操作共享数据的支持
 *          内存值 V
 *          预估值 A
 *          更新值 B
 *          当且仅当 V == A 时，V = B
 *          否则将不做任何操作
 */
public class TestAtomicDemo {
    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();

        }
    }
}

class AtomicDemo implements Runnable {

//    private int serialNumber = 0;
    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {

        }
        System.out.println(getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }

    public void setSerialNumber(AtomicInteger serialNumber) {
        this.serialNumber = serialNumber;
    }
}
