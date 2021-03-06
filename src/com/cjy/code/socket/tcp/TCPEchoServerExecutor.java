package com.cjy.code.socket.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjy.code.socket.TimeLimitEchoProtocol;

public class TCPEchoServerExecutor {

    private static int             port   = 6001;

    private static ExecutorService exec   = Executors.newCachedThreadPool();

    private static Logger          logger = LoggerFactory.getLogger(TCPEchoServerExecutor.class);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            exec.execute(new TimeLimitEchoProtocol(clientSocket, logger));
        }

    }
}
