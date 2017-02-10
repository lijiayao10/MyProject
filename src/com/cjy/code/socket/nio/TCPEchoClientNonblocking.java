package com.cjy.code.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 类TCPEchoClientNonblocking.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2016年1月29日 下午12:28:47
 */
public class TCPEchoClientNonblocking {

    private static int    port    = 6004;

    private static String Content = "内容啊啊啊啊啊, num:";

    private static String server  = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        byte[] argument = Content.getBytes();

        //创建Channel
        SocketChannel clntChan = SocketChannel.open();
        //设置通道的阻塞模式,  false:非阻塞
        clntChan.configureBlocking(false);

        //建立链接,  不代表立即创建
        if (!clntChan.connect(new InetSocketAddress(server, port))) {
            //坚持完成套接字通道的连接过程
            while (!clntChan.finishConnect()) {
                System.out.println(".");
            }
        }

        //创建读写缓冲区
        //些缓冲区,带入自己byte数组的引用
        ByteBuffer writeBuf = ByteBuffer.wrap(argument);
        //创建一个规定长度的缓冲区
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
        int totalBytesRcvd = 0;
        int bytesRcvd;
        while (totalBytesRcvd < argument.length) {
            //检查是否有元素然后在数据
            if (writeBuf.hasRemaining()) {
                System.out.println("写数据");
                clntChan.write(writeBuf);
            }
            //读取数据
            if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
                throw new SocketException("error!!!!!");
            }
            totalBytesRcvd += bytesRcvd;
            System.out.println(".");
        }

        System.out.println("Received: " + new String(readBuf.array(), 0, totalBytesRcvd));
        clntChan.close();
    }
}
