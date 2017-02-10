package com.cjy.code.socket.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.cjy.code.socket.Framer;
import com.cjy.code.socket.LengthFramer;
import com.cjy.code.socket.vote.VoteMsg;
import com.cjy.code.socket.vote.VoteMsgBinCoder;
import com.cjy.code.socket.vote.VoteMsgCoder;

public class VoteClientTCP {
    public static final int CANDIDATEID = 888;

    private static int      port        = 6002;

    private static String   Countent    = "���ݰ���������, num:";

    private static String   server      = "127.0.0.1";

    public static void main(String[] args) throws Exception, IOException {
        Socket socket = new Socket(server, port);

        System.out.println("connected ...");

        OutputStream out = socket.getOutputStream();

        VoteMsgCoder coder = new VoteMsgBinCoder();
        Framer framer = new LengthFramer(socket.getInputStream());

        VoteMsg msg = new VoteMsg(false, true, CANDIDATEID, 0);

        //�����󹹽���byte[]
        byte[] encodedMsg = coder.toWire(msg);

        //Send request
        System.out.println("1 sending Inquiry(" + encodedMsg.length + "bytes):");
        System.out.println(msg);
        //��������
        framer.feameMsg(encodedMsg, out);

        //Now send a vote
        msg.setInquiry(false);
        //�����󹹽���byte[]
        encodedMsg = coder.toWire(msg);
        System.out.println("2 Sending Vote (" + encodedMsg.length + " bytes):");
        //��������
        framer.feameMsg(encodedMsg, out);

        //Receive inquiry response
        //�õ�byte[]����
        encodedMsg = framer.nextMsg();
        //��byte[]�����ɶ���
        msg = coder.fromWire(encodedMsg);
        System.out.println("3 Received Response (" + encodedMsg.length + "bytes):");

        System.out.println(msg);

        //Receive vote response
        //��byte[]�����ɶ���
        msg = coder.fromWire(framer.nextMsg());
        System.out.println("4 Received Response (" + encodedMsg.length + "bytes):");
        System.out.println(msg);

        socket.close();
    }
}
