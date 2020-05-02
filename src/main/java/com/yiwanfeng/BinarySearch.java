package com.yiwanfeng;

import java.util.Arrays;
import java.util.Random;

/**
 * Project Name: javabase
 * Class Name: BinarySearch
 * Author: YiwanFeng
 * Create Time: 2020/4/6
 * Simple Description: 二分查找 时间复杂度O(log2n);空间复杂度O(1)
 */
public class BinarySearch {

    private static void binarySearch(int[] data, int left, int right, int findVal) {
        int midIndex = (left + right)/2;
        if (left < right) {
            if (data[midIndex] == findVal) {
                System.out.println("找到元素，其下表为：" + midIndex);
            } else if (findVal < data[midIndex]) {
                binarySearch(data, left, midIndex, findVal);
            } else if (findVal > data[midIndex]) {
                binarySearch(data, midIndex, right, findVal);
                // 找到后方返回下标
            } else {
                return ;
            }
        }
        return ;
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] data = new int[10];
        for (int i = 0; i < 10; i++) {
            int num = random.nextInt(100);
            data[i] = num;
        }
        System.out.println("生成序列为：\n"+Arrays.toString(data));
        Arrays.sort(data);
        System.out.println("排序后的序列为：\n"+Arrays.toString(data));
        // 随机选择一个下标作为需要查找的数字
        int needCheckIndex = random.nextInt(10);
        int needCheckNum = data[needCheckIndex];
        System.out.println("需要查找的元素为：" + needCheckNum);
        binarySearch(data, 0, data.length-1, needCheckNum);
    }
}
