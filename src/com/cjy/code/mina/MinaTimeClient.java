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
        // 创建客户端连接器.
        NioSocketConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        // 设置连接超时检查时间
        connector.setConnectTimeoutCheckInterval(30);
        connector.setHandler(new TimeClientHandler());

        // 建立连接
        ConnectFuture cf = connector.connect(new InetSocketAddress("127.0.0.1", 6488));
        // 等待连接创建完成
        cf.awaitUninterruptibly();

        /**
         * 请求接口
         */
        long time = System.currentTimeMillis();

        Event event = new Event(QueryTradeService.class.getName(), "queryList", new Class[] { String.class },
                new Object[] { "---------" });

        for (int i = 0; i < 10000; i++) {
            cf.getSession().write(event);
        }
        // 等待连接断开
        cf.getSession().getCloseFuture().awaitUninterruptibly();

        System.out.println("time:" + (System.currentTimeMillis() - time));
        // 释放连接
        connector.dispose();
    }

    public class MinaTha implements Runnable {

        @Override
        public void run() {

        }

    }
}
