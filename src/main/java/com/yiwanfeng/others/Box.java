package com.yiwanfeng.others;

/***
 * 定义一个泛型类
 * @param <T>
 */
public class Box<T> {
    private T t;

    public void add(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<>();
        Box<String> stringBox = new Box<>();

        integerBox.add(new Integer(10));
        stringBox.add(new String("Hello"));

        System.out.printf("整型值为：%d\n\n", integerBox.get());
        System.out.printf("字符串为：%s\n", stringBox.get());
    }
}
