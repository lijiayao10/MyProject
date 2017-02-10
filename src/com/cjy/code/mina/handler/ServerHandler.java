package com.cjy.code.mina.handler;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.util.CopyOnWriteMap;

import com.cjy.code.mina.common.Event;
import com.cjy.code.mina.service.QueryTradeService;
import com.cjy.code.mina.service.impl.QueryTradeServiceImpl;

public class ServerHandler extends IoHandlerAdapter {

    /**
     * 本地服务Map 以后直接从springbeans中获取
     */
    private static Map<String, Object> applicationBeans = new CopyOnWriteMap<>();

    public ServerHandler() {
        //获取本地注册对象以后需要改
        QueryTradeService tradeService = new QueryTradeServiceImpl();
        applicationBeans.put(QueryTradeService.class.getName(), tradeService);

    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        session.close(true);
    }

    /**
     * 消息接收事件
     */
    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        if (null == message) {
            throw new NullPointerException("请求对象不能为空!");
        }

        System.out.println(message.toString());

        if (!message.getClass().equals(Event.class)) {
            throw new ClassCastException(message.getClass().getName() + " is not Class:com.cjy.code.mina.common.Event");
        }

        Event event = (Event) message;

        Object bean = applicationBeans.get(event.getClassPath());

        if (null == bean) {
            throw new NullPointerException("不能为空!");
        }

        Class<? extends Object> c = bean.getClass();

        Method method = c.getMethod(event.getEventMethod().getMethodName(), event.getEventMethod().getParamTypes());

        //        Class returnType = method.getReturnType();

        Object returnObj = method.invoke(bean, event.getEventMethod().getParams());

        // 返回消息字符串
        session.write(returnObj);
        System.out.println(returnObj.toString());
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
    }

}
