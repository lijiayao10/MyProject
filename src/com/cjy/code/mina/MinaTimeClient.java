package com.cjy.code.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.cjy.code.mina.common.Event;
import com.cjy.code.mina.service.QueryTradeService;

public class MinaTimeClient {

    public static void main(String[] args) {
        // �����ͻ���������.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        // �������ӳ�ʱ���ʱ��
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new TimeClientHandler());

        // ��������
        ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 6488));
        // �ȴ����Ӵ������
        cf.awaitUninterruptibly();

        /**
         * ����ӿ�
         */
        long time = System.currentTimeMillis();

        Event event = new Event(QueryTradeService.class.getName(), "queryList", new Class[] { String.class },
                new Object[] { "---------" });

        for (int i = 0; i < 10000; i++) {
            cf.getSession().write(event);
        }
        // �ȴ����ӶϿ�
        cf.getSession().getCloseFuture().awaitUninterruptibly();

        System.out.println("time:" + (System.currentTimeMillis() - time));
        // �ͷ�����
        connector.dispose();
    }

    public class MinaTha implements Runnable {

        @Override
        public void run() {

        }

    }
}
