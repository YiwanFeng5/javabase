package com.yiwanfeng.others;

/**
 * Project Name: javabase
 * Class Name: MySearch
 * Author: YiwanFeng
 * Create Time: 2020/8/12
 * Simple Description:
 */
public class MySearch {

    public static void mysearch(int data[], int left, int right, int k) {
        int mid = (left + right) / 2;
        if (left < right) {
            if (data[mid] == k) {
                System.out.println("查找到k值，下表为："+mid);
            } else if (k < data[mid]) {
                mysearch(data, left, mid, k);
            } else if (k > data[mid]) {
                mysearch(data, right, mid, k);
            }
        }
        return ;
    }

    public static void main(String[] args) {

    }
}
