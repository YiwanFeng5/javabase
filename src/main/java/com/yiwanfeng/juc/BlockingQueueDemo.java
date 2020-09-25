package com.yiwanfeng.juc;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Project Name: javabase
 * Class Name: BlockingQueueDemo
 * Author: YiwanFeng
 * Create Time: 2020/7/3
 * Simple Description:
 */
public class BlockingQueueDemo {
    public static void main(String[] args) {
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        String remove = blockingQueue.remove();
        System.out.println(remove );
        System.out.println(blockingQueue.add("x"));
    }
}
