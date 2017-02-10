package com.cjy.code.laoa.chapter04.nio.client;

import static com.cjy.code.laoa.chapter04.socket.Commons.DEFAULT_MESSAGE_CHARSET;
import static com.cjy.code.laoa.chapter04.socket.Commons.println;

import java.io.FileOutputStream;
import java.io.IOException;

import com.cjy.code.laoa.chapter04.file.FileUtils;
import com.cjy.code.laoa.chapter04.socket.SocketWrapper;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 50; i++) {
            final String num = String.valueOf(i);
            new Thread(String.valueOf(i)) {
                public void run() {
                    SocketWrapper socketWrapper = null;
                    FileOutputStream stream = null;
                    try {
                        stream = new FileOutputStream("d:/tmp/aaa" + num + ".pdf");
                        socketWrapper = new SocketWrapper("localhost", 8888);

                        println("已经连接上服务器端.....");
                        socketWrapper.write("我是线程：" + num, DEFAULT_MESSAGE_CHARSET);
                        byte[] buffer = new byte[8192];
                        int len = socketWrapper.read(buffer);
                        while (len > 0) {
                            println("我是线程：" + num + " 我接受到数据，长度为：" + len);
                            stream.write(buffer, 0, len);
                            len = socketWrapper.read(buffer);
                        }
                        //System.in.read();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        FileUtils.closeStream(stream, socketWrapper);
                    }
                }
            }.start();
        }

    }
}
