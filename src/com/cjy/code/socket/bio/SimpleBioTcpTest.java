package com.cjy.code.socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * 基于BIO的Socket通信测试
 * 
 * @author shirdrn
 */
public class SimpleBioTcpTest {

    static int threadCount = 5000;

    /**
     * 基于BIO的Socket服务端进程
     * 
     * @author shirdrn
     */
    static class SocketServer extends Thread {

        /** 服务端口号 */
        private int        port     = 8888;
        /** 为客户端分配编号 */
        private static int sequence = 0;

        public SocketServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            Socket socket = null;
            int counter = 0;
            try {
                ServerSocket serverSocket = new ServerSocket(this.port);
                boolean flag = false;
                Date start = null;
                while (true) {
                    socket = serverSocket.accept(); // 监听  
                    // 有请求到来才开始计时  
                    if (!flag) {
                        start = new Date();
                        flag = true;
                    }
                    this.handleMessage(socket); // 处理一个连接过来的客户端请求  
                    if (++counter == threadCount) {
                        Date end = new Date();
                        long last = end.getTime() - start.getTime();
                        System.out.println(threadCount + " requests cost " + last + " ms.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 处理一个客户端socket连接
         * 
         * @param socket 客户端socket
         * @throws IOException
         */
        private void handleMessage(Socket socket) throws IOException {
            InputStream in = socket.getInputStream(); // 流：客户端->服务端（读）  
            OutputStream out = socket.getOutputStream(); // 流：服务端->客户端（写）  
            int receiveBytes;
            byte[] receiveBuffer = new byte[128];
            String clientMessage = "";
            if ((receiveBytes = in.read(receiveBuffer)) != -1) {
                clientMessage = new String(receiveBuffer, 0, receiveBytes);
                if (clientMessage.startsWith("I am the client")) {
                    String serverResponseWords = "I am the server, and you are the " + (++sequence) + "th client.";
                    out.write(serverResponseWords.getBytes());
                }
            }
            out.flush();
            System.out.println("Server: receives clientMessage->" + clientMessage);
        }
    }

    /**
     * 基于BIO的Socket客户端线程
     * 
     * @author shirdrn
     */
    static class SocketClient implements Runnable {

        private String ipAddress;
        private int    port;
        /** 待发送的请求数据 */
        private String data;

        public SocketClient(String ipAddress, int port) {
            this.ipAddress = ipAddress;
            this.port = port;
        }

        @Override
        public void run() {
            this.send();
        }

        /**
         * 连接Socket服务端，并模拟发送请求数据
         */
        public void send() {
            Socket socket = null;
            OutputStream out = null;
            InputStream in = null;
            try {
                socket = new Socket(this.ipAddress, this.port); // 连接  
                // 发送请求  
                out = socket.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                // 接收响应  
                in = socket.getInputStream();
                int totalBytes = 0;
                int receiveBytes = 0;
                byte[] receiveBuffer = new byte[128];
                if ((receiveBytes = in.read(receiveBuffer)) != -1) {
                    totalBytes += receiveBytes;
                }
                String serverMessage = new String(receiveBuffer, 0, receiveBytes);
                System.out.println("Client: receives serverMessage->" + serverMessage);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // 发送请求并接收到响应，通信完成，关闭连接  
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static void main(String[] args) throws Exception {
        SocketServer server = new SocketServer(1983);
        server.start();

        Thread.sleep(3000);

        for (int i = 0; i < threadCount; i++) {
            SocketClient client = new SocketClient("localhost", 1983);
            client.setData("I am the client " + (i + 1) + ".");
            new Thread(client).start();
            Thread.sleep(0, 1);
        }
    }
}
