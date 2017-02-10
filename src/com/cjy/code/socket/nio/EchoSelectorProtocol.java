package com.cjy.code.socket.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class EchoSelectorProtocol implements TCPProtocol {

    private int bufSize;

    public EchoSelectorProtocol(int bufSize) {
        this.bufSize = bufSize;
    }

    @Override
    public void handleAccept(SelectionKey key) throws IOException {
        //����ŵ�
        SocketChannel clntChan = ((ServerSocketChannel) key.channel()).accept();
        //������
        clntChan.configureBlocking(false);
        //ע��ɶ�������ѡ����,Ȼ������»���
        clntChan.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    }

    @Override
    public void handleRead(SelectionKey key) throws IOException {
        //����ŵ�
        SocketChannel clntChan = (SocketChannel) key.channel();
        //��û�����
        ByteBuffer buf = (ByteBuffer) key.attachment();
        //�ŵ���������,��ö�ȡ����
        long bytesRead = clntChan.read(buf);
        //����-1��ʾ�ر�
        if (bytesRead == -1) {
            clntChan.close();
        } else if (bytesRead > 0) {
            //���Ϊ�ɶ���д
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    @Override
    public void handleWrite(SelectionKey key) throws IOException {
        //��û�����
        ByteBuffer buf = (ByteBuffer) key.attachment();
        //׼����������д����
        buf.flip();
        //����ŵ�
        SocketChannel clntChan = (SocketChannel) key.channel();
        //���ŵ�д����
        clntChan.write(buf);
        //���������Ϊ�ձ�ʾ����д����
        if (!buf.hasRemaining()) {
            key.interestOps(SelectionKey.OP_READ);
        }
        //ѹ��������,��������
        buf.compact();
    }

}
