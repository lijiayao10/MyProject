package com.cjy.code.socket.compress;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.zip.GZIPOutputStream;

import org.slf4j.Logger;

public class CompressProtocol implements Runnable {

    public static final int BUFSIZE = 1024;

    private Socket          clntSock;
    private Logger          logger;

    public CompressProtocol(Socket clntSock, Logger logger) {
        this.clntSock = clntSock;
        this.logger = logger;
    }

    public void handleCompressClient(Socket clntSock, Logger logger) {
        try {
            InputStream in = clntSock.getInputStream();
            GZIPOutputStream out = new GZIPOutputStream(clntSock.getOutputStream());
            byte[] buffer = new byte[BUFSIZE];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.finish();

            logger.info("client " + clntSock.getRemoteSocketAddress());

        } catch (IOException e) {
            logger.error("error ", e);
        } finally {
            try {
                clntSock.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        this.handleCompressClient(clntSock, logger);
    }

}
