package com.yiwanfeng.netty.nio;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Project Name: javabase
 * Class Name: NIOClient
 * Author: YiwanFeng
 * Create Time: 2020/5/31
 * Simple Description:
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        // 获得一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 提供服务器端的 ip 和 端口号
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("因为连接需要时间，客户端不会阻塞，可以做其他工作。。。");
            }
        }
        // 如果连接成功，就发送数据
        String str = "hello, Netty!";
        // Wraps a byte array into a buffer
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        // 发送数据，将 buffer 数据写入 channel
        socketChannel.write(buffer);
        System.in.read();

    }
}
