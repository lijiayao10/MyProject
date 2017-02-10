package com.cjy.code.socket.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPEchoClient {

    private static int    port     = 6001;

    private static String Countent = "内容啊啊啊啊啊, num:";

    private static String server   = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        byte[] data = Countent.getBytes();

        for (int i = 0; i < 10; i++) {
            sendSocket((Countent + i).getBytes());
        }
    }

    private static void sendSocket(byte[] data)
            throws UnknownHostException, IOException, SocketException {
        //        InetSocketAddress address = InetSocketAddress.createUnresolved("localhost", port);
        //
        //        System.out.println(address.getHostName());

        Socket socket = new Socket(server, port);

        System.out.println("connected ...");

        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();

        out.write(data);
        //就关闭OUT
        socket.shutdownOutput();

        //增加长度帧头
        //        Framer framer = new LengthFramer(in);
        //        framer.feameMsg(data, out);

        int totalBytesRcvd = 0;
        int bytesRcvd;
        while (totalBytesRcvd < data.length) {
            if ((bytesRcvd = in.read(data, totalBytesRcvd, data.length - totalBytesRcvd)) == -1) {
                throw new SocketException("error");
            }
            totalBytesRcvd += bytesRcvd;
        }
        //关闭读取的
        socket.shutdownInput();
        System.out.println("Received: " + new String(data));
        socket.close();
    }
}
