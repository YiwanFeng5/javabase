package com.yiwanfeng.netty.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Project Name: javabase
 * Class Name: NIOFileChannel04
 * Author: YiwanFeng
 * Create Time: 2020/5/27
 * Simple Description:
 */
public class NIOFileChannel04 {
    public static void main(String[] args) throws IOException {
        // 创建相关流
        FileInputStream fileInputStream = new FileInputStream("src\\a1.jpg");
        FileOutputStream fileOutputStream = new FileOutputStream("src\\a2.jpg");

        // 获取各个流对应的 fileChannel
        FileChannel sourceCh = fileInputStream.getChannel();
        FileChannel destCh = fileOutputStream.getChannel();

        // 使用每个 transferFrom 完成拷贝
        destCh.transferFrom(sourceCh, 0, sourceCh.size());

        // 关闭相关通道和流
        sourceCh.close();
        destCh.close();
        fileInputStream.close();
        fileOutputStream.close();
    }
}
