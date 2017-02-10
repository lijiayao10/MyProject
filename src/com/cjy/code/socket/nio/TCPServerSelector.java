package com.cjy.code.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class TCPServerSelector {

    private static int       port    = 6004;

    private static final int TIMEOUT = 3000;

    private static int       BUFSIZE = 256;

    public static void main(String[] args) throws IOException {

        args = new String[] { "6004", "6005", "6006" };

        Selector selector = Selector.open();

        for (String arg : args) {
            //创建信道实例
            ServerSocketChannel listNChannel = ServerSocketChannel.open();
            //绑定端口
            listNChannel.socket().bind(new InetSocketAddress(Integer.parseInt(arg)));
            //非阻塞
            listNChannel.configureBlocking(false);
            //信道注册选择器且信道可以进行ACCEPT操作
            listNChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

        TCPProtocol protocol = new EchoSelectorProtocol(BUFSIZE);

        while (true) {
            if (selector.select(TIMEOUT) == 0) {
                System.out.println(".");
                continue;
            }
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while (keyIter.hasNext()) {
                SelectionKey key = keyIter.next();
                System.out.println(key.readyOps());
                if (key.isAcceptable()) {
                    //如果监听到对象讲对象设成可读
                    protocol.handleAccept(key);
                }

                if (key.isReadable()) {
                    //如果数据读完了,  将对象设置成可写
                    protocol.handleRead(key);
                }

                if (key.isValid() && key.isWritable()) {
                    //
                    protocol.handleWrite(key);
                }
                keyIter.remove();
            }
        }

    }

}
