package com.cjy.code.mina;

import com.cjy.code.mina.manage.ServerManager;

public class MinaTimeServer {

    public static void main(String[] args) throws Exception {

        ServerManager serverManager = new ServerManager();

        serverManager.start();

        //        // ��������˼���߳�
        //        IoAcceptor acceptor = new NioSocketAcceptor();
        //        acceptor.getSessionConfig().setReadBufferSize(2048);
        //        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        //        // ������־��¼��
        //        acceptor.getFilterChain().addLast("logger", new LoggingFilter());
        //        // ���ñ��������
        //        acceptor.getFilterChain().addLast("codec",
        //                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        //        // ָ��ҵ���߼�������
        //        acceptor.setHandler(new TimeServerHandler());
        //        // ���ö˿ں�
        //        acceptor.bind(new InetSocketAddress(PORT));
        //        // ���������߳�
        //        acceptor.bind();

    }
}
