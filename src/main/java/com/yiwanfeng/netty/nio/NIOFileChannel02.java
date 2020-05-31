package com.yiwanfeng.netty.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Project Name: javabase
 * Class Name: NIOFileChannel02
 * Author: YiwanFeng
 * Create Time: 2020/5/23
 * Simple Description:
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        // 创建文件的输入流
        File file = new File("src\\file01.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        // 通过 fileInputStream 获取对应的 FileChannel -> 实际类型 FileChannelImpl
        FileChannel fileChannel = fileInputStream.getChannel();

        // 创建缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());

        // 将通道的数据读入到 Buffer
        fileChannel.read(byteBuffer);

        // 将 byteBuffer 的字节数据转成string
        System.out.println(new String(byteBuffer.array()));
        fileInputStream.close();

    }
}
