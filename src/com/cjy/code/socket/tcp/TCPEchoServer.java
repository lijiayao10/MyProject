package com.cjy.code.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.cjy.code.socket.Framer;
import com.cjy.code.socket.LengthFramer;

public class TCPEchoServer {

    private static int port    = 6001;

    private static int BUFSIZE = 32;

    public static void main(String[] args) throws InterruptedException {
        try {
            //            ServerSocket serverSocket = new ServerSocket(port);
            ServerSocket serverSocket = new ServerSocket();

            InetSocketAddress inetSocketAddress = new InetSocketAddress("localhost", port);
            serverSocket.bind(inetSocketAddress, 1);
            int recvMsgsize = 0;
            byte[] receivBuf = new byte[BUFSIZE];

            while (true) {
                Socket clientSocket = serverSocket.accept();

                System.out.println("address : " + clientSocket.getRemoteSocketAddress());

                InputStream in = clientSocket.getInputStream();
                OutputStream out = clientSocket.getOutputStream();

                Framer framer = new LengthFramer(in);
                receivBuf = framer.nextMsg();
                out.write(receivBuf);

                //                while ((recvMsgsize = in.read(receivBuf)) != -1) {
                //                    String receivedData = new String(receivBuf.toString());
                //                    out.write(receivBuf, 0, recvMsgsize);
                //                    Thread.sleep(1000l);
                //                }

                System.out.println("Msg:" + new String(receivBuf));
                clientSocket.close();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
