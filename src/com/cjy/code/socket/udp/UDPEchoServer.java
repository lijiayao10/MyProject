package com.cjy.code.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPEchoServer {

    private static final int    ECHOMAX = 255;

    private static final String server  = "localhost";
    private static final int    port    = 6001;

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);
        DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

        while (true) {
            socket.receive(packet);
            System.out.println("client at " + packet.getAddress().getHostAddress() + " on port "
                    + packet.getPort());

            System.out.println("content: " + new String(packet.getData()));

            socket.send(packet);
            packet.setLength(ECHOMAX);

        }
    }

}
