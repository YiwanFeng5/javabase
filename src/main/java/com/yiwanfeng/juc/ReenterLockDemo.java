package com.yiwanfeng.juc;

/**
 * Project Name: javabase
 * Class Name: ReenterLockDemo
 * Author: YiwanFeng
 * Create Time: 2020/7/2
 * Simple Description:
 */

class Phone {
    public synchronized void sendSms() throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t sendSms");
        sendEmail();
    }
    public synchronized void sendEmail() throws Exception {
        System.out.println(Thread.currentThread().getName()+"\t sendEmail");
    }

}
public class ReenterLockDemo {
    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(() ->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() ->{
            try {
                phone.sendSms();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }
}
