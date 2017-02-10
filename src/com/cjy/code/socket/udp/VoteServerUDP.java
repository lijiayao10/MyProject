package com.cjy.code.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;

import com.cjy.code.socket.vote.VoteMsg;
import com.cjy.code.socket.vote.VoteMsgCoder;
import com.cjy.code.socket.vote.VoteMsgTextCoder;
import com.cjy.code.socket.vote.VoteService;

public class VoteServerUDP {

    private static int port = 6002;

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(port);

        byte[] inBuffer = new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH];
        VoteMsgCoder coder = new VoteMsgTextCoder();
        VoteService service = new VoteService();
        while (true) {
            DatagramPacket packet = new DatagramPacket(inBuffer, inBuffer.length);
            socket.receive(packet);
            byte[] encodedMsg = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
            System.out.println("handling request from " + packet.getSocketAddress());
            try {
                VoteMsg msg = coder.fromWire(encodedMsg);
                msg = service.handleRequest(msg);
                packet.setData(coder.toWire(msg));
                System.out.println("Sending response (" + packet.getLength() + ")");
                System.out.println(msg);
                socket.send(packet);
            } catch (IOException ioe) {
                System.err.println("error");
            }
        }
    }

}
