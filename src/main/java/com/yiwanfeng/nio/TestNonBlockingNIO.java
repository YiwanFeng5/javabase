package com.yiwanfeng.nio;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Project Name: javabase
 * Class Name: TestNonBlockingNIO
 * Author: YiwanFeng
 * Create Time: 2020/5/6
 * Simple Description:
 */
public class TestNonBlockingNIO {
    // 客户端
    @Test
    public void client() throws IOException {
        // 1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        // 2. 切换非阻塞模式
        socketChannel.configureBlocking(false);
        // 3. 分配指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 4. 发送数据给服务端
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()) {
            String str = scanner.next();
            buf.put((new Date().toString() + ":\t"+ str).getBytes());
            buf.flip();
            socketChannel.write(buf);
            buf.clear();
        }
        // 5. 关闭通道
        socketChannel.close();
    }
    // 服务端
    @Test
    public void server() throws IOException {
        // 1. 获取通道
        ServerSocketChannel ssChannel = ServerSocketChannel.open();
        // 2. 切换非阻塞模式
        ssChannel.configureBlocking(false);
        // 3. 绑定连接
        ssChannel.bind(new InetSocketAddress(9898));
        // 4. 获取选择器
        Selector selector = Selector.open();
        // 5. 将通道注册到选择器上，并且指定“监听接收时间”
        ssChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 6. 轮询式的获取选择器上已经“准备就绪的时间”
        while(selector.select() > 0) {
            // 7. 获取当前选择其中注册的“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                // 8. 获取准备“就绪”的事件
                SelectionKey sk = it.next();
                // 9. 判断具体是什么事件准备就绪
                if(sk.isAcceptable()) {
                    // 10. 若“接收就绪”，获取客户端连接
                    SocketChannel sChannel = ssChannel.accept();
                    // 11. 切换非阻塞模式
                    sChannel.configureBlocking(false);
                    // 12. 将通道注册到选择器上
                    sChannel.register(selector,SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    // 13. 获取当前选择器上的“读就绪”状态的通道
                    SocketChannel sChannel = (SocketChannel) sk.channel();
                    // 14. 读取数据
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    int len = 0;
                    while((len = sChannel.read(buf)) > 0) {
                        buf.flip();
                        System.out.println(new String(buf.array(), 0, len));
                        buf.clear();
                    }
                }
                // 15. 取消选择键
                it.remove();
            }
        }
    }

}
