package com.yiwanfeng.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Project Name: javabase
 * Class Name: VolatileDemo
 * Author: YiwanFeng
 * Create Time: 2020/7/1
 * Simple Description:
 */
public class VolatileDemo {
    public static void main(String[] args) {
//        visibility();
        VolatileClass volatileClass = new VolatileClass();
        for (int i = 1; i <= 20; i++) {
            new Thread(()->{
                for (int j = 1; j <= 1000; j++) {
                    volatileClass.addOne();
                    volatileClass.atomicAddOne();
                }
            },String.valueOf(i)).start();
        }
        while (Thread.activeCount()>2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName()+" get result num is " + volatileClass.num);
        System.out.println(Thread.currentThread().getName()+" get atomic result num is " + volatileClass.number);
    }

    private static void visibility() {
        VolatileClass volatileClass = new VolatileClass();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "\tget num is " + volatileClass.num);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                volatileClass.alterNum();
                System.out.println(Thread.currentThread().getName() + "\tget added num is " + volatileClass.num);
            }
        }, "AAA").start();
        while (volatileClass.num == 0) {

        }
        System.out.println(Thread.currentThread().getName() + "\t get num is " + volatileClass.num);
    }
}

class VolatileClass {
    volatile int num = 0;
    AtomicInteger number = new AtomicInteger();
    public void alterNum() {
        this.num = 60;
    }
    public void addOne() {
        this.num++;
    }
    public void atomicAddOne() {
        number.getAndIncrement();
    }
}
