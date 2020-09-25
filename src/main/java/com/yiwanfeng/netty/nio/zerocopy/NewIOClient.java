package com.yiwanfeng.netty.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * Project Name: javabase
 * Class Name: NewIOClient
 * Author: YiwanFeng
 * Create Time: 2020/6/4
 * Simple Description:
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 7001));
        String file = "src\\rainmeterskin.zip";
        // 得到一个文件 channel
        FileChannel fileChannel = new FileInputStream(file).getChannel();
        // 准备发送
        long startTime = System.currentTimeMillis();
        // 在 linux 下一个 transferTo 方法就可以完成传输
        // 在windows 下一次调用 transferTo 只能发送 8MB ，就需要分段传输文件，而且要注意传输时的位置
//        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);//transferTo 底层使用到零拷贝
        long transferCount = 0;
        int perSize = 8 * 1024 * 1024;
        long pages = Math.floorDiv(fileChannel.size(), perSize);
        for (int i = 0; i <= pages; i++) {
            transferCount += fileChannel.transferTo(i * perSize, perSize, socketChannel);
        }
        System.out.println("发送的总字节数：" + transferCount + "，耗时：" + (System.currentTimeMillis() - startTime));
        // 关闭
        fileChannel.close();
    }
}
