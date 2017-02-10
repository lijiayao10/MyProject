package com.cjy.code.socket.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * ��TCPEchoClientNonblocking.java��ʵ��������TODO ��ʵ������
 * 
 * @author Administrator 2016��1��29�� ����12:28:47
 */
public class TCPEchoClientNonblocking {

    private static int    port    = 6004;

    private static String Content = "���ݰ���������, num:";

    private static String server  = "127.0.0.1";

    public static void main(String[] args) throws IOException {

        byte[] argument = Content.getBytes();

        //����Channel
        SocketChannel clntChan = SocketChannel.open();
        //����ͨ��������ģʽ,  false:������
        clntChan.configureBlocking(false);

        //��������,  ��������������
        if (!clntChan.connect(new InetSocketAddress(server, port))) {
            //�������׽���ͨ�������ӹ���
            while (!clntChan.finishConnect()) {
                System.out.println(".");
            }
        }

        //������д������
        //Щ������,�����Լ�byte���������
        ByteBuffer writeBuf = ByteBuffer.wrap(argument);
        //����һ���涨���ȵĻ�����
        ByteBuffer readBuf = ByteBuffer.allocate(argument.length);
        int totalBytesRcvd = 0;
        int bytesRcvd;
        while (totalBytesRcvd < argument.length) {
            //����Ƿ���Ԫ��Ȼ��������
            if (writeBuf.hasRemaining()) {
                System.out.println("д����");
                clntChan.write(writeBuf);
            }
            //��ȡ����
            if ((bytesRcvd = clntChan.read(readBuf)) == -1) {
                throw new SocketException("error!!!!!");
            }
            totalBytesRcvd += bytesRcvd;
            System.out.println(".");
        }

        System.out.println("Received: " + new String(readBuf.array(), 0, totalBytesRcvd));
        clntChan.close();
    }
}
