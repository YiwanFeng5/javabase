package com.yiwanfeng.juc;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project Name: javabase
 * Class Name: NoSafeDemo03
 * Author: YiwanFeng
 * Create Time: 2020/7/21
 * Simple Description:
 */
public class NoSafeDemo03 {
    public static void main(String[] args) {
        List<String> list = new CopyOnWriteArrayList<>();//Collections.synchronizedList(new ArrayList<>());//new Vector<>();//new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
