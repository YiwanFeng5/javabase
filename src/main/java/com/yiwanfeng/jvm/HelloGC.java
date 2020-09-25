package com.yiwanfeng.jvm;

/**
 * Project Name: javabase
 * Class Name: HelloGC
 * Author: YiwanFeng
 * Create Time: 2020/7/13
 * Simple Description:
 */
public class HelloGC {
    public static void main(String[] args) {
        long totalMemory = Runtime.getRuntime().totalMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("TOTAL_MEMORY(-Xms) = " + totalMemory + "(字节)、" + (totalMemory / (double)1024 /1024)+ "MB");
        System.out.println("MAX_MEMORY(-Xmx) = " + maxMemory + "(字节)、" + (maxMemory / (double)1024 /1024)+ "MB");
    }
}
