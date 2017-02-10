package com.cjy.code.mina;

import com.cjy.code.mina.manage.ServerManager;

public class MinaTimeServer {

    public static void main(String[] args) throws Exception {

        ServerManager serverManager = new ServerManager();

        serverManager.start();

        //        // 创建服务端监控线程
        //        IoAcceptor acceptor = new NioSocketAcceptor();
        //        acceptor.getSessionConfig().setReadBufferSize(2048);
        //        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        //        // 设置日志记录器
        //        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        //        // 设置编码过滤器
        //        acceptor.getFilterChain().addLast("codec",
        //                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        //        // 指定业务逻辑处理器
        //        acceptor.setHandler(new TimeServerHandler());
        //        // 设置端口号
        //        acceptor.bind(new InetSocketAddress(PORT));
        //        // 启动监听线程
        //        acceptor.bind();

    }
}
