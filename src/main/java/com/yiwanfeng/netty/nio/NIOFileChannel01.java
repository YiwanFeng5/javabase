package com.yiwanfeng.netty.nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Project Name: javabase
 * Class Name: NIOFileChannel01
 * Author: YiwanFeng
 * Create Time: 2020/5/14
 * Simple Description:
 */
public class NIOFileChannel01 {
    public static void main(String[] args) throws Exception {
        String str = "Hello, NIO";
        // 创建一个输出流 channel
        FileOutputStream fileOutputStream = new FileOutputStream("src\\file01.txt");
        // 通过 fileOutputStream 获取对应的 FileChannle
        // 这个fileChannel 真实类型是 FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();
        // 创建一个缓冲区 ByteBuffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        // 将str 放入到 byteBuffer
        byteBuffer.put(str.getBytes());
        // 对 byteBuffer 进行 flip
        byteBuffer.flip();
        // 将 byteBuffer 数据写入到 fileChannel
        fileChannel.write(byteBuffer);
        fileOutputStream.close();
    }
}
