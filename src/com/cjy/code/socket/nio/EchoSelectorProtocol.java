package com.cjy.code.socket.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoSelectorProtocol implements TCPProtocol {

    private int bufSize;

    public EchoSelectorProtocol(int bufSize) {
        this.bufSize = bufSize;
    }

    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        //获得信道
        SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
        //非阻塞
        clntChan.configureBlocking(false);
        //注册可读操作的选择器,然后给定新缓存
        clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        //获得信道
        SocketChannel clntChan = (SocketChannel) key.channel();
        //获得缓冲区
        ByteBuffer buf = (ByteBuffer) key.attachment();
        //信道中拿数据,获得读取长度
        long bytesRead = clntChan.read(buf);
        //返回-1表示关闭
        if (bytesRead == -1) {
            clntChan.close();
        } else if (bytesRead > 0) {
            //标记为可读可写
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        //获得缓冲区
        ByteBuffer buf = (ByteBuffer) key.attachment();
        //准备缓冲区的写操作
        buf.flip();
        //获得信道
        SocketChannel clntChan = (SocketChannel) key.channel();
        //向信道写数据
        clntChan.write(buf);
        //如果缓冲区为空表示不能写数据
        if (!buf.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ);
        }
        //压缩缓冲区,整理缓冲区
        buf.compact();
    }

}
