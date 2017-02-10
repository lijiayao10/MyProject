package com.cjy.code.socket.udp;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UDP客户端 类UDPEchoClientTimeout.java的实现描述：TODO 类实现描述
 * 
 * @author Administrator 2016年1月25日 下午6:21:44
 */
public class UDPEchoClientTimeout {

    private static final int    TIMEOUT  = 3000;
    private static final int    MAXTRIES = 5;

    private static final String server   = "localhost";
    private static final int    port     = 6001;
    private static final String content  = "内容内容内容:1111111111111111111111";

    public static void main(String[] args) throws Exception {
        InetAddress serverAddress = InetAddress.getByName(server);
        byte[] bytesTosend = content.getBytes();
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT);

        DatagramPacket sendPacket = new DatagramPacket(bytesTosend, bytesTosend.length,
                serverAddress, port);

        DatagramPacket receivePacket = new DatagramPacket(new byte[bytesTosend.length],
                bytesTosend.length);

        int tries = 0;

        boolean receiveResponse = false;
        do {
            socket.send(sendPacket);
            try {
                socket.receive(receivePacket);

                if (!receivePacket.getAddress().equals(serverAddress)) {
                    throw new IOException("error IO");
                }
                receiveResponse = true;
            } catch (InterruptedIOException e) {
                tries += 1;
                System.out.println("Time out," + (MAXTRIES - tries) + "more tries...");
            }
        } while ((!receiveResponse) && (tries < MAXTRIES));

        if (receiveResponse) {
            System.out.println("Received: " + new String(receivePacket.getData()));
        } else {
            System.out.println("No response -- giving up.");
        }

        socket.close();
    }

}
