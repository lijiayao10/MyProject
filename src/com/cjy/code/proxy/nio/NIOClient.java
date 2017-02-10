package com.cjy.code.proxy.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class NIOClient {
    /*��ʶ����*/  
    private static int flag = 0;  
    /*��������С*/  
    private static int BLOCK = 4096;  
    /*�������ݻ�����*/  
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(BLOCK);  
    /*�������ݻ�����*/  
    private static ByteBuffer receiveBuffer = ByteBuffer.allocate(BLOCK);  
    /*�������˵�ַ*/  
    private final static InetSocketAddress SERVER_ADDRESS = new InetSocketAddress(  
            "0.0.0.0", Consts.LISTEN_PORT);  
  
    private final static String sendContent="HTTP/1.1 Request URL:http://0.0.0.0:"+Consts.LISTEN_PORT+"/"+Consts.LINE_END_S;
    
    
    public static void main(String[] args) throws IOException {  
        // ��socketͨ��  
        SocketChannel clientChannel = SocketChannel.open();  
        // ����Ϊ��������ʽ  
        clientChannel.configureBlocking(false);  
        // ��ѡ����  
        Selector selector = Selector.open();  
        // ע�����ӷ����socket����  
        clientChannel.register(selector, SelectionKey.OP_CONNECT);  
        // ����  
        clientChannel.connect(SERVER_ADDRESS);  
    
        SocketChannel socketChannel;
        Set<SelectionKey> selectionKeys;    
        String receiveText;  
        String sendText;  
        int count=0;  
  
        while (true) {  
            //ѡ��һ���������Ӧ��ͨ����Ϊ I/O ����׼��������  
            //�������ע��� channel ����������ע��� IO �������Խ���ʱ���ú������أ�������Ӧ�� SelectionKey ���� selected-key set 
            selector.select();  
            //���ش�ѡ��������ѡ�������  
            selectionKeys = selector.selectedKeys();  
            //System.out.println(selectionKeys.size());  
            for(SelectionKey selectionKey:selectionKeys){ 
                //�ж��Ƿ�Ϊ�������ӵ��¼�
                if (selectionKey.isConnectable()) {  
                    System.out.println("client connect");  
                    socketChannel = (SocketChannel) selectionKey.channel();  //
                    // �жϴ�ͨ�����Ƿ����ڽ������Ӳ�����  
                    // ����׽���ͨ�������ӹ��̡�  
                    if (socketChannel.isConnectionPending()) { 
                        //������ӵĽ�����TCP�������֣�
                        socketChannel.finishConnect();  
                        System.out.println("�������!");  
                        sendBuffer.clear();  
                        sendBuffer.put(sendContent.getBytes());  
                        sendBuffer.flip();  
                        socketChannel.write(sendBuffer);  
                    }  
                    socketChannel.register(selector, SelectionKey.OP_READ);  
                } else if (selectionKey.isReadable()) {  
                    socketChannel = (SocketChannel) selectionKey.channel();  
                    //������������Ա��´ζ�ȡ  
                    receiveBuffer.clear();  
                    //��ȡ�����������������ݵ���������  
                    count=socketChannel.read(receiveBuffer);  
                    if(count>0){  
                        receiveText = new String( receiveBuffer.array(),0,count);  
                        System.out.println("�ͻ��˽��ܷ�����������--:"+receiveText);  
                        socketChannel.register(selector, SelectionKey.OP_WRITE);  
                    }  
  
                } else if (selectionKey.isWritable()) {  
                    sendBuffer.clear();  
                    socketChannel = (SocketChannel) selectionKey.channel();  
                    sendText = "message from client--" + (flag++);  
                    sendBuffer.put(sendText.getBytes());  
                     //������������־��λ,��Ϊ������put�����ݱ�־���ı�Ҫ����ж�ȡ���ݷ��������,��Ҫ��λ  
                    sendBuffer.flip();  
                    socketChannel.write(sendBuffer);  
                    System.out.println("�ͻ�����������˷�������--��"+sendText);  
                    socketChannel.register(selector, SelectionKey.OP_READ);  
                }  
            }  
            selectionKeys.clear();  
        }  
    }  
}
