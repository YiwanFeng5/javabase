package com.yiwanfeng.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Project Name: javabase
 * Class Name: NIOServer
 * Author: YiwanFeng
 * Create Time: 2020/5/31
 * Simple Description:
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        // 创建 ServerSocketChannel -> ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 得到一个 Selector 对象
        Selector selector = Selector.open();
        // 绑定一个端口 6666 ，在服务器端监听
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 把 serverSocketChannel 注册到 Selector 关心 事件 OPACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // 循环等待客户端连接
        while (true) {
            // 这里我们等待 1 秒，如果没有事件发生，返回
            if (selector.select(1000) == 0) { // 没有事件发生
                System.out.println("服务器等待了 1 秒，无连接");
                continue;
            }
            // 如果返回 >0，就获取到相关的 selectionKey 集合
            // 1. 如果返回的 >0，表示已经获取到关注的时间
            // 2. selector.selectedKeys() 返回关注事件的集合
            // 通过 selectionKeys 反向获取通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 遍历 Set<SelectionKey>，使用迭代器遍历
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                // 获取到 SelectionKey
                SelectionKey key = keyIterator.next();
                // 根据 key，对应的通道发生的事件做相应处理
                if (key.isAcceptable()) { // 如果是 OP_ACCEPT，有新客户端连接
                    // 给该客户端生成一个 SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个socketChannel " + socketChannel.hashCode());
                    // 将 socketChannel 设置为非阻塞的
                    socketChannel.configureBlocking(false);
                    // 将 socketChannel 注册到 selector
                    // 关联一个 Buffer
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) { // 发生 OP_READ
                    // 通过 key 反向获取到对应 channel
                    SocketChannel channel = (SocketChannel) key.channel();
                    // 获取到该 channel 关联的 buffer
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    channel.read(buffer);
                    System.out.println("form 客户端 " + new String(buffer.array()));
                }
                // 手动从集合中移除当前 的selectionKey，防止重复操作
                keyIterator.remove();
            }
        }
    }
}
