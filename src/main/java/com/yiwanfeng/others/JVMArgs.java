package com.yiwanfeng.others;

import java.util.concurrent.TimeUnit;

/**
 * Project Name: javabase
 * Class Name: JVMArgs
 * Author: YiwanFeng
 * Create Time: 2020/7/12
 * Simple Description:
 */
public class JVMArgs {
    public static void main(String[] args) {
        System.out.println("Hello, world!");
        try {
            TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
