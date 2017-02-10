/*
 * @author caojiayao 2017年1月25日 下午4:10:16
 */
package com.cjy.code.refactor;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.commons.lang3.StringUtils;

/**
 * <p><p>
 * @author caojiayao 
 * @version $Id: RefactorAnonymousClass.java, v 0.1 2017年1月25日 下午4:10:16 caojiayao Exp $
 */
public class RefactorAnonymousClass implements Map {

    /**
     * <p><p>
     * @author caojiayao 
     * @version $Id: RefactorAnonymousClass.java, v 0.1 2017年1月25日 下午4:12:24 caojiayao Exp $
     */
    private final class MyThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return MyThread1(r);
        }

        private Thread MyThread1(Runnable r) {
            Thread t = new Thread(r);
            t.setName("Worker thread");
            t.setPriority(Thread.MIN_PRIORITY);
            t.setDaemon(true);
            return t;
        }
    }

    ExecutorService threadPool = null;

    void createPool() {
        threadPool = Executors.newFixedThreadPool(1, new MyThreadFactory());
    }
    
    public Object getObject(){
        return StringUtils.EMPTY;
    }
    
    
    public Object getObject2(){
        java.util.Map<String, String> map = new HashMap<String, String>();
        
        map.put(StringUtils.EMPTY, StringUtils.EMPTY);
        
        return (String)getObject();
    }

}
