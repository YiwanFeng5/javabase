package com.yiwanfeng.nio;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Project Name: javabase
 * Class Name: TestChannel
 * Author: YiwanFeng
 * Create Time: 2020/5/5
 * Simple Description:
 */
public class TestChannel {

    // 字符集
    @Test
    public void test6() throws IOException {
        Charset gbk = Charset.forName("GBK");
        // 获取编码器
        CharsetEncoder charsetEncoder = gbk.newEncoder();
        // 获取解码器
        CharsetDecoder charsetDecoder = gbk.newDecoder();
        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("中华人民共和国");
        charBuffer.flip();
        // 编码
        ByteBuffer encode = charsetEncoder.encode(charBuffer);
        for (int i = 0; i < 12; i++) {
            System.out.println(encode.get());

        }
        // 解码
        encode.flip();
        CharBuffer decode = charsetDecoder.decode(encode);
        System.out.println(decode.toString());
        System.out.println("----------------------");
    }

    @Test
    public void test5() {
        SortedMap<String, Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = map.entrySet();
        for (Map.Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }

    @Test
    public void test4() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");
        // 1. 获取通道
        FileChannel channel1 = raf1.getChannel();
        // 2. 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        // 3. 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("--------------------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        // 4. 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);

    }

    // 通道之间的数据传输（直接缓冲区）
    @Test
    public void test3() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);

//        inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());
        inChannel.close();
        outChannel.close();
    }

    // 使用直接缓冲区完成文件的复制（内存映射文件）
    @Test
    public void test2() throws IOException {
        FileChannel inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        // 直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.get(dst);
        inChannel.close();
        outChannel.close();
    }

    // 利用通道完成文件的复制
    @Test
    public void test1() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("1.jpg");
            fos = new FileOutputStream("2.jpg");

            // 1. 获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            // 2. 分配指定的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // 将通道中的数据存入缓冲区中
            while(inChannel.read(buf) != -1) {
                buf.flip(); // 切换成读取数据模式
                // 4. 将缓冲区中的数据写入同道中
                outChannel.write(buf);
                buf.clear(); //清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
