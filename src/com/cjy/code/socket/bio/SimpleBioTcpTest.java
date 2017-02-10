package com.cjy.code.socket.bio;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * ����BIO��Socketͨ�Ų���
 * 
 * @author shirdrn
 */
public class SimpleBioTcpTest {

    static int threadCount = 5000;

    /**
     * ����BIO��Socket����˽���
     * 
     * @author shirdrn
     */
    static class SocketServer extends Thread {

        /** ����˿ں� */
        private int        port     = 8888;
        /** Ϊ�ͻ��˷����� */
        private static int sequence = 0;

        public SocketServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            Socket socket = null;
            int counter = 0;
            try {
                ServerSocket serverSocket = new ServerSocket(this.port);
                boolean flag = false;
                Date start = null;
                while (true) {
                    socket = serverSocket.accept(); // ����  
                    // ���������ſ�ʼ��ʱ  
                    if (!flag) {
                        start = new Date();
                        flag = true;
                    }
                    this.handleMessage(socket); // ����һ�����ӹ����Ŀͻ�������  
                    if (++counter == threadCount) {
                        Date end = new Date();
                        long last = end.getTime() - start.getTime();
                        System.out.println(threadCount + " requests cost " + last + " ms.");
                    }
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
    }

    /**
     * ����BIO��Socket�ͻ����߳�
     * 
     * @author shirdrn
     */
    static class SocketClient implements Runnable {

        private String ipAddress;
        private int    port;
        /** �����͵��������� */
        private String data;

        public SocketClient(String ipAddress, int port) {
            this.ipAddress = ipAddress;
            this.port = port;
        }

        @Override
        public void run() {
            this.send();
        }

        /**
         * ����Socket����ˣ���ģ�ⷢ����������
         */
        public void send() {
            Socket socket = null;
            OutputStream out = null;
            InputStream in = null;
            try {
                socket = new Socket(this.ipAddress, this.port); // ����  
                // ��������  
                out = socket.getOutputStream();
                out.write(data.getBytes());
                out.flush();
                // ������Ӧ  
                in = socket.getInputStream();
                int totalBytes = 0;
                int receiveBytes = 0;
                byte[] receiveBuffer = new byte[128];
                if ((receiveBytes = in.read(receiveBuffer)) != -1) {
                    totalBytes += receiveBytes;
                }
                String serverMessage = new String(receiveBuffer, 0, receiveBytes);
                System.out.println("Client: receives serverMessage->" + serverMessage);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    // �������󲢽��յ���Ӧ��ͨ����ɣ��ر�����  
                    out.close();
                    in.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void setData(String data) {
            this.data = data;
        }
    }

    public static void main(String[] args) throws Exception {
        SocketServer server = new SocketServer(1983);
        server.start();

        Thread.sleep(3000);

        for (int i = 0; i < threadCount; i++) {
            SocketClient client = new SocketClient("localhost", 1983);
            client.setData("I am the client " + (i + 1) + ".");
            new Thread(client).start();
            Thread.sleep(0, 1);
        }
    }
}
