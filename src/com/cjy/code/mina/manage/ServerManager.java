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
     * 本地服务Map 以后直接从springbeans中获取
     */
    private static Map<String, Object> applicationBeans = new CopyOnWriteMap<>();

    private AtomicBoolean              isStart          = new AtomicBoolean(false);

    private int                        PORT             = 6488;

    private BlockingQueue<Runnable>    workQueue        = new ArrayBlockingQueue<Runnable>(100);

    public void start() throws Exception {
        if (!isStart.compareAndSet(false, true)) {
            throw new Exception("ServerManager is Start!!");
        }

        //获取本地注册对象以后需要改
        QueryTradeService tradeService = new QueryTradeServiceImpl();
        applicationBeans.put(QueryTradeService.class.getName(), tradeService);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 300l, TimeUnit.SECONDS, workQueue);
        // 创建服务端监控线程
        IoAcceptor acceptor = new NioSocketAcceptor();
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        // 设置日志记录器
        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        //        // 线程
        //        acceptor.getFilterChain().addLast("executor", new ExecutorFilter(2, 10));
        // 设置编码过滤器
        acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        // 指定业务逻辑处理器
        acceptor.setHandler(new ServerHandler());
        // 设置端口号
        acceptor.bind(new InetSocketAddress(PORT));
        // 启动监听线程
        acceptor.bind();
    }

}
