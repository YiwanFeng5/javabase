package com.yiwanfeng.juc;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Project Name: javabase
 * Class Name: ArrayListDemo
 * Author: YiwanFeng
 * Create Time: 2020/7/1
 * Simple Description:
 */
public class ArrayListDemo {
    public static void main(String[] args) {
        List<Object> list = Collections.synchronizedList(new ArrayList<>());
        new HashSet<>();
        new HashMap<>();
//        Vector<Integer> integers = new Vector<>();
//        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            }, String.valueOf(i)).start();

        }
    }
}
