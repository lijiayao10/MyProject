package com.cjy.code.socket.tcp;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import com.cjy.code.socket.Framer;
import com.cjy.code.socket.LengthFramer;
import com.cjy.code.socket.vote.VoteMsg;
import com.cjy.code.socket.vote.VoteMsgBinCoder;
import com.cjy.code.socket.vote.VoteMsgCoder;

public class VoteClientTCP2 {
    public static final int CANDIDATEID = 888;

    private static int      port        = 6002;

    private static String   Countent    = "内容啊啊啊啊啊, num:";

    private static String   server      = "127.0.0.1";

    private static Socket   socket;

    private static Framer   framer;

    static {
        try {
            socket = new Socket(server, port);
            framer = new LengthFramer(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception, IOException {

        System.out.println("connected ...");

        for (int i = 0; i < 1000; i++) {
            sendVoteMsg(i % 2 == 0);
        }

        while (true) {
            try {
                outResult();
            } catch (NullPointerException e) {
                break;
            }
        }

        socket.close();
    }

    public static void sendVoteMsg(boolean isInquiry) throws IOException {
        OutputStream out = socket.getOutputStream();
        VoteMsgCoder coder = new VoteMsgBinCoder();

        VoteMsg msg = new VoteMsg(false, isInquiry, CANDIDATEID, 0);

        //将对象构建成byte[]
        byte[] encodedMsg = coder.toWire(msg);

        //Send request
        System.out.println("1 sending Inquiry(" + encodedMsg.length + "bytes):");
        System.out.println(msg);
        //发送数据
        framer.feameMsg(encodedMsg, out);
    }

    public static void outResult() throws IOException {
        VoteMsgCoder coder = new VoteMsgBinCoder();
        //Receive vote response
        //拿到byte[]数据
        byte[] encodedMsg = framer.nextMsg();
        if (encodedMsg == null) {
            throw new NullPointerException("没了!!");
        }
        //将byte[]解析成对象
        VoteMsg msg = coder.fromWire(encodedMsg);
        System.out.println("4 Received Response (" + encodedMsg.length + "bytes):");
        System.out.println(msg);
    }
}
