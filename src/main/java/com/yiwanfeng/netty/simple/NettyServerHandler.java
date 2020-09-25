package com.yiwanfeng.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;


/**
 * Project Name: javabase
 * Class Name: NettyServerHandler
 * Author: YiwanFeng
 * Create Time: 2020/6/7
 * Simple Description:
 * 1. 我们自定义一个Handler 需要继承 Netty 规定好的某个 HandlerAdpater
 * 2. 这时我们自定义一个 Handler，才能称为一个 Handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 读取数据实际（这里我们可以读取客户端发送的消息）
     * 1. ChannelHandlerContext ctx: 上下文对象，含有管道 pipeline，通道 channel， 地址
     * 2. Object msg: 就是客户端发送的数据默认Object
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程 " + Thread.currentThread().getName());
        System.out.println("server ctx = " + ctx);
        System.out.println("看看 channel 和 pipline的关系");
        Channel channel = ctx.channel();
        ChannelPipeline pipeline = ctx.pipeline(); // 本质是以这个双向链表，出栈入栈
        // 将msg 转成一个 ByteBuffer
        // ByteBuf 是 Netty 提供的，不是 NIO 的 ByteBuffer
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址：" + ctx.channel().remoteAddress());
    }

    /**
     * 数据读取完毕
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // writeAndFlush 是 write + flush
        // 数据写入到缓存，并刷新
        // 一搬讲，我们对这个发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello, 客户端 ~ ", CharsetUtil.UTF_8));
    }

    /**
     * 处理异常，一搬需要关闭通道
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
