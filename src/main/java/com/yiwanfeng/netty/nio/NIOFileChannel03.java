package com.yiwanfeng.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Project Name: javabase
 * Class Name: NIOFileChannel03
 * Author: YiwanFeng
 * Create Time: 2020/5/26
 * Simple Description:
 */
public class NIOFileChannel03 {
    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("src\\1.txt");
        FileChannel fileChannel01 = fileInputStream.getChannel();

        FileOutputStream fileOutputStream = new FileOutputStream("src\\2.txt");
        FileChannel fileChannel02 = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (true) {
            byteBuffer.clear(); // 清空 buffer
            int read = fileChannel01.read(byteBuffer);
            if (read == -1) { // 表示读完
                break;
            }
            // 将 buffer 中的数据写入到 fileChannel02 -- 2.txt
            byteBuffer.flip();
            fileChannel02.write(byteBuffer);
        }
        // 关闭相关的流
        fileInputStream.close();
        fileOutputStream.close();
    }
}
