package com.cjy.code.socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ����BIO��Socket��������
 * 
 * @author shirdrn
 */
public class SimpleBioTcpServer extends Thread {

    /** ����˿ں� */
    private int        port     = 8888;
    /** Ϊ�ͻ��˷����� */
    private static int sequence = 0;

    public SimpleBioTcpServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            while (true) {
                socket = serverSocket.accept(); // ����  
                this.handleMessage(socket); // ����һ�����ӹ����Ŀͻ�������  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����һ���ͻ���socket����
     * 
     * @param socket �ͻ���socket
     * @throws IOException
     */
    private void handleMessage(Socket socket) throws IOException {
        InputStream in = socket.getInputStream(); // �����ͻ���->����ˣ�����  
        OutputStream out = socket.getOutputStream(); // ���������->�ͻ��ˣ�д��  
        int receiveBytes;
        byte[] receiveBuffer = new byte[128];
        String clientMessage = "";
        if ((receiveBytes = in.read(receiveBuffer)) != -1) {
            clientMessage = new String(receiveBuffer, 0, receiveBytes);
            if (clientMessage.startsWith("I am the client")) {
                String serverResponseWords = "I am the server, and you are the " + (++sequence) + "th client.";
                out.write(serverResponseWords.getBytes());
            }
        }
        out.flush();
        System.out.println("Server: receives clientMessage->" + clientMessage);
    }

    public static void main(String[] args) {
        SimpleBioTcpServer server = new SimpleBioTcpServer(1983);
        server.start();
    }
}
