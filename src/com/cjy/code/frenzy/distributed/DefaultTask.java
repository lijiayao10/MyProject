package com.cjy.code.frenzy.distributed;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.curator.test.TestingServer;

import com.manyi.iw.fin.frenzy.distributed.leaderelection.TaskCallback;
import com.manyi.iw.fin.frenzy.distributed.leaderelection.TaskTemplate;
import com.manyi.iw.fin.frenzy.util.zookeeper.CuratorResource;


public class DefaultTask extends TaskTemplate {
    
    static String connect = "127.0.0.1:2181";
    static String namespace = null; 
    
    
    static CuratorResource curatorResource = new CuratorResource(connect, namespace, 60*1000, 15*1000, null);
    
    public static void main(String[] args) throws Exception {
//        CuratorFramework framework = CuratorManagerFramework.build(curatorResource).current();
//        framework.start();
        TestingServer server = new TestingServer();
        for (int i = 0; i < 10; i++) {
            CuratorResource curatorResource = new CuratorResource(server.getConnectString(), namespace, 60*1000, 15*1000, null);
            
            DefaultTask task = new DefaultTask();
            task.setCuratorResource(curatorResource);
            task.setNamespace("/select2/leader2");
            //手动初始化
            task.afterPropertiesSet();
            
            TaskCallback<String> taskCallback =  new TaskCallback<String>() {

                @Override
                public String doTask() {
                    System.out.println("执行1111");
                    return "123456";
                }
            };
            
            for (int j = 0; j < 100; j++) {
                System.out.println(task.execute(taskCallback));;
            }
            
            System.out.println("添加");
        }
        

        
        System.out.println("Press enter/return to quit\n");
        new BufferedReader(new InputStreamReader(System.in)).readLine();
        
    }

}
