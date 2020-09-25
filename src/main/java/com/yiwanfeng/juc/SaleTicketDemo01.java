package com.yiwanfeng.juc;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Project Name: javabase
 * Class Name: SaleTicketDemo01
 * Author: YiwanFeng
 * Create Time: 2020/7/21
 * Simple Description:
 */

class Ticket {
    private int number = 30;
    Lock lock = new ReentrantLock();
    public void sale() {
        lock.lock();
        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName()+"\t卖出第："+(number --)+"\t还剩："+number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}

public class SaleTicketDemo01 {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();},"A").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();},"B").start();
        new Thread(() -> {for (int i = 0; i < 40; i++) ticket.sale();},"C").start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 40; i++) {
//                    ticket.sale();
//                }
//            }
//        },"B").start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 40; i++) {
//                    ticket.sale();
//                }
//            }
//        },"C").start();
    }
}
