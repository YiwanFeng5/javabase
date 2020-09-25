package com.yiwanfeng.netty.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * Project Name: javabase
 * Class Name: GroupChatServer
 * Author: YiwanFeng
 * Create Time: 2020/6/3
 * Simple Description:
 */
public class GroupChatServer {
    // 定义属性
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    // 构造器
    // 初始化工作
    public GroupChatServer() {
        try {
            // 得到选择器
            selector = Selector.open();
            // 初始化 ServerSocketChannel
            listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞模式
            listenChannel.configureBlocking(false);
            // 将该 listenChannel 注册到selector
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 监听
    public void listen() {
        try {
            while (true) {
                int count = selector.select();
                if (count > 0) { // 有相关的Channel需要处理
                    // 遍历得到 selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        // 取出 selectionKey
                        SelectionKey key = iterator.next();
                        // 监听到 ACCEPT
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            // 将 sc 注册到 selector 上
                            sc.register(selector, SelectionKey.OP_READ);
                            // 提示
                            System.out.println(sc.getRemoteAddress() + "上线了");
                        }
                        if (key.isReadable()) { // 通道发生Read事件，即通道是可读的状态
                            // 处理读 （专门写方法）
                            readData(key);
                        }
                        // 当前的key，删除，房子重复处理
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待……");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }

    // 读取客户端消息
    private void readData(SelectionKey key) {
        // 定义一个socketChannel
        SocketChannel channel = null;
        try {
            // 取到关联的channel
            channel = (SocketChannel) key.channel();
            // 创建 Buffer
            ByteBuffer buffer = ByteBuffer.allocate(2014);
            int count = channel.read(buffer);
            // 根据 count 的值做处理
            if (count > 0) {
                // 把缓冲区的数据转成字符串
                String msg = new String(buffer.array());
                // 输出消息
                System.out.println("from 客户端：" + msg);
                // 像其它的客户端转发消息（去掉自己）,专门写一个方法来处理
                sendInfoToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了。。。");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // 转发消息给其它客户（通道）
    private void sendInfoToOtherClients(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中……");
        // 遍历所有注册到 selector 上的 SocketChannel，并排除 self
        for (SelectionKey key : selector.keys()) {
            // 通过key取出对应的 SocketChannel
            SelectableChannel targetChannel = key.channel();
            // 排除自己
            if(targetChannel instanceof  SocketChannel && targetChannel != self) {
                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将 msg 存储到 buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                // 将 buffer 的数据写入通道
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        // 创建服务器对象
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
