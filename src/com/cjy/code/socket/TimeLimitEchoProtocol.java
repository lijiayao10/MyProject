package com.cjy.code.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.slf4j.Logger;

public class TimeLimitEchoProtocol implements Runnable {

    private static final int    BUFSIZE       = 32;
    private static final String TIMELIMIT     = "10000";
    private static final String TIMELIMITPROP = "Timelimit";
    private static int          timelimit;
    private Socket              clientSocket;
    private Logger              logger;

    public TimeLimitEchoProtocol(Socket sock, Logger logger) {
        this.clientSocket = sock;
        this.logger = logger;

        timelimit = Integer.parseInt(System.getProperty(TIMELIMITPROP, TIMELIMIT));
    }

    public void handleEchoClient(Socket sock, Logger logger) {
        try {
            InputStream in = sock.getInputStream();
            OutputStream out = sock.getOutputStream();

            int recvMsgSize;
            int totalBytesEchoed = 0;
            byte[] echoBuffer = new byte[BUFSIZE];
            long endTime = System.currentTimeMillis() + timelimit;
            int timeBoundMillis = timelimit;
            sock.setSoTimeout(timeBoundMillis);
            while ((timeBoundMillis > 0) && (recvMsgSize = in.read(echoBuffer)) != -1) {
                out.write(echoBuffer, 0, recvMsgSize);
                totalBytesEchoed += recvMsgSize;
                timeBoundMillis = (int) (endTime - System.currentTimeMillis());
                sock.setSoTimeout(timeBoundMillis);
            }
            Thread.sleep(2000l);
            logger.info(
                    "client:" + sock.getRemoteSocketAddress() + "," + totalBytesEchoed + " bytes");
        } catch (IOException e) {
            logger.error("error", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                sock.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void run() {
        this.handleEchoClient(clientSocket, logger);
    }

}
