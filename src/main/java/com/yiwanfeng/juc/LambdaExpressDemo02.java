package com.yiwanfeng.juc;

import java.util.ArrayList;

/**
 * Project Name: javabase
 * Class Name: LambdaExpressDemo02
 * Author: YiwanFeng
 * Create Time: 2020/7/21
 * Simple Description:
 */

interface Foo {
    public void sayHello();
}

public class LambdaExpressDemo02 {
    public static void main(String[] args) {
//        Foo foo = new Foo(){
//
//            @Override
//            public void sayHello() {
//                System.out.println("****hello 1205");
//            }
//        };
//        foo.sayHello();
        Foo foo = () -> {System.out.println("****hello 1205");};
        foo.sayHello();
    }
}
