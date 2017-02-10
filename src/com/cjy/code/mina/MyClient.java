package com.cjy.code.mina;

import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cjy.code.mina.common.Event;
import com.cjy.code.mina.service.impl.QueryTradeServiceImpl;

public class MyClient {

    private static final Logger logger = LoggerFactory.getLogger(MyClient.class);

    public static void main(String[] args) {
        IoConnector connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(10 * 1000);

        connector.getFilterChain().addLast("logger", new LoggingFilter());
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));

        connector.setHandler(new IoHandlerAdapter() {

            @Override
            public void sessionCreated(IoSession session) throws Exception {
            }

            @Override
            public void sessionOpened(IoSession session) throws Exception {
                Event event = new Event(QueryTradeServiceImpl.class.getName(), "queryList",
                        new Class[] { String.class }, new Object[] { "---------" });
                session.write(event);
            }

            @Override
            public void sessionClosed(IoSession session) throws Exception {
            }

            @Override
            public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
            }

            @Override
            public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
                logger.error(cause.getMessage(), cause);
                session.close(true);
            }

            @Override
            public void messageReceived(IoSession session, Object message) throws Exception {
                Event myResObj = (Event) message;
                logger.info("Received " + myResObj);
                session.close(true);
            }

            @Override
            public void messageSent(IoSession session, Object message) throws Exception {
                logger.info("Sent " + message);
            }
        });

        IoSession session = null;
        try {
            ConnectFuture future = connector.connect(new InetSocketAddress("localhost", 10000));
            future.awaitUninterruptibly();
            session = future.getSession();
        } catch (RuntimeIoException e) {
            logger.error(e.getMessage(), e);
        }

        session.getCloseFuture().awaitUninterruptibly();
        connector.dispose();
    }
}
