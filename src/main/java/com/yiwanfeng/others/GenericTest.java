package com.yiwanfeng.others;

import java.util.ArrayList;
import java.util.List;

/***
 * 类型通配符一般是使用?代替具体的类型参数。例如 List<?> 在逻辑上是List<String>,List<Integer> 等所有List<具体类型实参>的父类。
 */
public class GenericTest {
    public static void main(String[] args) {
        List<String> name = new ArrayList<>();
        List<Integer> age =  new ArrayList<Integer>();
        List<Number> number = new ArrayList<Number>();
        name.add("icon");
        age.add(18);
        number.add(314);
//        getData(name);
//        getData(age);
//        getData(number);
//        getUperNumber(name); // 编译会报错
        getUperNumber(age);
        getUperNumber(number);
    }

    public static void getData(List<?> data) {
        System.out.println("data: " + data.get(0));
    }

    public static void getUperNumber(List<? extends Number> data) {
        System.out.println("data: " + data.get(0));
    }
}
