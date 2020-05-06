package com.yiwanfeng.juc;

/**
 * Project Name: javabase
 * Class Name: TestVolatile
 * Author: YiwanFeng
 * Create Time: 2020/5/3
 * Simple Description: 使用 volatile 关键字来解决多线程操作
 *                     共享数据时的不可见问题，相较于 synchronized 是一种
 *                     较为轻量级的同步策略
 * 注意：
 *   1. volatile 不具备“互斥性”
 *   2. volatile 不能保证变量的“原子性”
 */
public class TestVolatile {
    public static void main(String[] args) {
        ThreadDemo td = new ThreadDemo();
        new Thread(td).start();
        while(true) {
            // 使用synchronized
//            synchronized (td) {
//                if(td.isFlag()) {
//                    System.out.println("---------------------------");
//                    break;
//                }
//            }
            // 使用了 volatile 关键字
            if(td.isFlag()) {
                System.out.println("---------------------------");
                break;
            }
        }
    }
}

class ThreadDemo implements Runnable {
    //使用synchronized
//    private boolean flag = false;
    // 使用 volatile 关键字来修饰多线程共享的变量
    private volatile boolean flag = false;

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
        System.out.println("flag = " + isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
