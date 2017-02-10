package com.cjy.code.queue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.util.internal.ThreadLocalBoolean;

public class ArraysTaskQueue {

    public ArraysTaskQueue() {
        // TODO Auto-generated constructor stub
    }

    /** 出款处理中机构订单队列 */
    private BlockingQueue<Long> queryInstOrdersQueue = new LinkedBlockingQueue<>();

    /**
     * 查询各个渠道出款处理中订单
     * @return
     */
    private List<Long> queryInstOrders() {
        List<Long> instOrderIdList = new ArrayList<Long>();
        int i = 0;
        while (i++ < 3)
            instOrderIdList.add(Long.valueOf(i));
        return instOrderIdList;
    }

    private boolean firstLoad = true;

    /**
     * 获取数据
     * @return
     */
    private List<Long> loadTaskQueue(int batchSize) {
        if (queryInstOrdersQueue.size() == 0 && firstLoad) {
            firstLoad = false;
            queryInstOrdersQueue.addAll(queryInstOrders());
        }
        
        new LinkedBlockingQueue().isEmpty();

        List<Long> tasks = new ArrayList<Long>();

        int i = 0;
        try {
            while (i < batchSize && !queryInstOrdersQueue.isEmpty()) {
                Long task = queryInstOrdersQueue.poll(3, TimeUnit.SECONDS);
                if (task != null) {
                    tasks.add(task);
                }
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //数据队列数据为空,可以重新加载
        if (tasks.size() == 0 && !firstLoad) {
            firstLoad = true;
        }

        return tasks;
    }

    public static void main(String[] args) throws InterruptedException {
        final ArraysTaskQueue taskQueue = new ArraysTaskQueue();

        new Thread(new Runnable() {

            @Override
            public void run() {
                int i = 0;

                int count = 0;

                while (true) {
                    List<Long> tasks = taskQueue.loadTaskQueue(100);
                    if (tasks.size() == 0) {
                        break;
                    } else {
                        System.out.println(i++ + "tasks count:" + tasks.size());
                        count += tasks.size();
                    }
                }

                System.out.println(count);
            }
        }).start();
        
        
        


    }

}
