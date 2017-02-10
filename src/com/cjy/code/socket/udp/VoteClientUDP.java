package com.cjy.code.socket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import com.cjy.code.socket.vote.VoteMsg;
import com.cjy.code.socket.vote.VoteMsgCoder;
import com.cjy.code.socket.vote.VoteMsgTextCoder;

public class VoteClientUDP {

    private static int      port        = 6002;

    public static final int CANDIDATEID = 888;

    private static String   server      = "127.0.0.1";

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.connect(InetAddress.getByName(server), port);

        VoteMsg vote = new VoteMsg(false, false, CANDIDATEID, 0);

        VoteMsgCoder coder = new VoteMsgTextCoder();

        byte[] encodedVote = coder.toWire(vote);
        System.out.println("send Txt:" + encodedVote.length);
        System.out.println(vote);
        DatagramPacket message = new DatagramPacket(encodedVote, encodedVote.length);
        socket.send(message);

        message = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],
                VoteMsgTextCoder.MAX_WIRE_LENGTH);
        socket.receive(message);
        encodedVote = Arrays.copyOfRange(message.getData(), 0, message.getLength());

        System.out.println("received txt:" + encodedVote.length);
        vote = coder.fromWire(encodedVote);
        System.out.println(vote);
    }

}
