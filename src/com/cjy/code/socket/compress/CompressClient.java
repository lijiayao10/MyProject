package com.cjy.code.socket.compress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class CompressClient {

    private static int         port     = 6003;

    private static String      server   = "127.0.0.1";

    public static final int    BUFSIZE  = 256;

    public static final String fileName = "d://tmp//socket.txt";

    public static void main(String[] args) throws IOException {
        FileInputStream fileIn = new FileInputStream(fileName);
        FileOutputStream fileOut = new FileOutputStream(fileName + ".gz");

        Socket sock = new Socket(server, port);
        sendBytes(sock, fileIn);

        InputStream sockIn = sock.getInputStream();
        int bytesRead;
        byte[] buffer = new byte[BUFSIZE];
        while ((bytesRead = sockIn.read(buffer)) != -1) {
            fileOut.write(buffer, 0, bytesRead);
            System.out.print("R");
        }
        System.out.println();
        sock.close();
        fileIn.close();
        fileOut.close();

    }

    private static void sendBytes(Socket sock, InputStream filein) throws IOException {
        OutputStream sockOut = sock.getOutputStream();
        int bytesRead;
        byte[] buffer = new byte[BUFSIZE];
        while ((bytesRead = filein.read(buffer)) != -1) {
            sockOut.write(buffer, 0, bytesRead);
        }
        sock.shutdownOutput();
    }

}
