package com.yiwanfeng.netty.nio;

import java.nio.ByteBuffer;

/**
 * Project Name: javabase
 * Class Name: ReadOnlyBuffer
 * Author: YiwanFeng
 * Create Time: 2020/5/27
 * Simple Description:
 */
public class ReadOnlyBuffer {
    public static void main(String[] args) {
        // 创建一个 buffer
        ByteBuffer buffer = ByteBuffer.allocate(64);
        for (int i = 0; i < 64; i++) {
            buffer.put((byte) i);
        }
        // 读取
        buffer.flip();
        // 得到一个只读的 Buffer
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println(readOnlyBuffer.getClass().getName());

        // 读取
        while (readOnlyBuffer.hasRemaining()) {
            System.out.println(readOnlyBuffer.get());
        }
    }
}
