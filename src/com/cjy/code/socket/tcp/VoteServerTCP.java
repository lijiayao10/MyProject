package com.cjy.code.socket.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.cjy.code.socket.Framer;
import com.cjy.code.socket.LengthFramer;
import com.cjy.code.socket.vote.VoteMsg;
import com.cjy.code.socket.vote.VoteMsgBinCoder;
import com.cjy.code.socket.vote.VoteMsgCoder;
import com.cjy.code.socket.vote.VoteService;

public class VoteServerTCP {

    private static int port = 6002;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);

        VoteMsgCoder coder = new VoteMsgBinCoder();
        VoteService service = new VoteService();

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("address : " + clientSocket.getRemoteSocketAddress());
            Framer framer = new LengthFramer(clientSocket.getInputStream());
            try {
                byte[] req;
                while ((req = framer.nextMsg()) != null) {
                    System.out.println("Received message (" + req.length + " bytes)");
                    //处理投票
                    VoteMsg responseMsg = service.handleRequest(coder.fromWire(req));
                    System.out.println("接收内容:" + responseMsg);
                    framer.feameMsg(coder.toWire(responseMsg), clientSocket.getOutputStream());
                }
            } catch (IOException e) {
                System.err.println("error 1");
                e.printStackTrace();
            } finally {
                System.out.println("guanbi la ");
                clientSocket.close();
            }
        }
    }

}
