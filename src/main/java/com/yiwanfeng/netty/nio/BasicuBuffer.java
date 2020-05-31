package com.yiwanfeng.netty.nio;

import java.nio.IntBuffer;

/**
 * Project Name: javabase
 * Class Name: BasicuBuffer
 * Author: YiwanFeng
 * Create Time: 2020/5/7
 * Simple Description:
 */
public class BasicuBuffer {
    public static void main(String[] args) {
        // 举例说明 Buffer 的使用（简单说明）
        // 创建一个 Buffer，大小为 5，即可以存放 5 个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i * 2);
        }
        // 如何从 Buffer 读取数据
        // 将 Buffer 转换，读写切换
        intBuffer.flip();

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
