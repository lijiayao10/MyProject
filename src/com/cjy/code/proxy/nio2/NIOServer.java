package com.cjy.code.proxy.nio2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.nio.channels.SocketChannel;

public class NIOServer {
    /*��־����*/
    private static int flag = 0;
    /*���建������С*/
    private static int block = 4096;
    /*���ջ�����*/
    private static ByteBuffer receiveBuffer = ByteBuffer.allocate(block);
    /*���ͻ�����*/
    private static ByteBuffer sendBuffer = ByteBuffer.allocate(block);
    /*����Selector*/
    private Selector selector;
    
    public NIOServer(int port) throws IOException{
        //�򿪷������׽���ͨ��
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //����������Ϊ������
        serverSocketChannel.configureBlocking(false);
        //������˷������׽���ͨ���������׽���
        ServerSocket serverSocket = serverSocketChannel.socket();
        //���з���İ�
        serverSocket.bind(new InetSocketAddress(port));
        //ͨ��open()�����ҵ�Selector
        selector = Selector.open();
        //ע�ᵽselector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("Server Start -----8888:");
    }
    //����
    public void listen() throws IOException{
        while(true){
            //�������ע��� channel ����������ע��� IO �������Խ���ʱ���ú������أ�������Ӧ�� SelectionKey ���� selected-key set
            selector.select();
            //Selected-key set ����������ͨ�� select() ������⵽���Խ��� IO ������ channel ��������Ͽ���ͨ�� selectedKeys() �õ�
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                handleKey(selectionKey);
                iterator.remove();
            }
        }
        
    }
    //��������
    public void handleKey(SelectionKey selectionKey) throws IOException{
        //��������
        ServerSocketChannel serverSocketChannel = null;
        SocketChannel socketChannel = null;
        String receiveText;
        String sendText;
        int count;
        //���Դ˼���ͨ���Ƿ�׼���ý����µ��׽�������
        if(selectionKey.isAcceptable()){
            //���ش����˼���ͨ��
            serverSocketChannel = (ServerSocketChannel)selectionKey.channel();
            //���ܿͻ��˽������ӵ����󣬲����� SocketChannel ����
            socketChannel = serverSocketChannel.accept();
            //����Ϊ������
            socketChannel.configureBlocking(false);
            //ע�ᵽselector
            socketChannel.register(selector, SelectionKey.OP_READ);
        }else if(selectionKey.isReadable()){
            //����Ϊ֮�����˼���ͨ��
            socketChannel = (SocketChannel)selectionKey.channel();
            //����������գ��Ա��´ζ�ȡ
            receiveBuffer.clear();
            //�������������ݶ�ȡ��������
            
            count = socketChannel.read(receiveBuffer);
        
            
            if(count>0){
                receiveText = new String(receiveBuffer.array(),0,count);
                System.out.println("�������˽��ܵ�������---"+receiveText);
                socketChannel.register(selector, SelectionKey.OP_WRITE);
            }
        }else if (selectionKey.isWritable()) {  
            //������������Ա��´�д��  
            sendBuffer.clear();  
            // ����Ϊ֮�����˼���ͨ����  
            socketChannel = (SocketChannel) selectionKey.channel();  
            sendText="message from server--" + flag++;  
            //�򻺳�������������  
            sendBuffer.put(sendText.getBytes());  
             //������������־��λ,��Ϊ������put�����ݱ�־���ı�Ҫ����ж�ȡ���ݷ��������,��Ҫ��λ  
            sendBuffer.flip();  
            //�����ͨ��  
            socketChannel.write(sendBuffer);  
            System.out.println("����������ͻ��˷�������--��"+sendText);  
            socketChannel.register(selector, SelectionKey.OP_READ);  
        }  
        
    }
    public static void main(String[] args) throws IOException {
        int port = 8888; 
        NIOServer server = new NIOServer(port);
        server.listen();
    }

}
