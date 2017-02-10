package com.cjy.code.mina.manage;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.apache.mina.util.CopyOnWriteMap;

import com.cjy.code.mina.handler.ServerHandler;
import com.cjy.code.mina.service.QueryTradeService;
import com.cjy.code.mina.service.impl.QueryTradeServiceImpl;

public class ServerManager extends IoHandlerAdapter {

    /**
     * ���ط���Map �Ժ�ֱ�Ӵ�springbeans�л�ȡ
     */
    private static Map<String, Object> applicationBeans = new CopyOnWriteMap<>();

    private AtomicBoolean              isStart          = new AtomicBoolean(false);

    private int                        PORT             = 6488;

    private BlockingQueue<Runnable>    workQueue        = new ArrayBlockingQueue<Runnable>(100);

    public void start() throws Exception {
        if (!isStart.compareAndSet(false, true)) {
            throw new Exception("ServerManager is Start!!");
        }

        //��ȡ����ע������Ժ���Ҫ��
        QueryTradeService tradeService = new QueryTradeServiceImpl();
        applicationBeans.put(QueryTradeService.class.getName(), tradeService);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 300l, TimeUnit.SECONDS, workQueue);
        // ��������˼���߳�
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        // ������־��¼��
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        //        // �߳�
        //        acceptor.getFilterChain().addLast("executor", new ExecutorFilter(2, 10));
        // ���ñ��������
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // ָ��ҵ���߼�������
        acceptor.setHandler(new ServerHandler());
        // ���ö˿ں�
        acceptor.bind(new InetSocketAddress(PORT));
        // ���������߳�
        acceptor.bind();
    }

}
