package com.cjy.code.zk.lock;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.google.common.collect.ImmutableMap;

import jodd.util.StringTemplateParser;

public class DefaultZKLock implements IZKLock {

    private ZKLockClient        zk          = new ZKLockClient();

    private final static String NODE_FORMAT = "${host}-${name}-${index}";
    
    private  String index = "1";
    
    public DefaultZKLock(String index) {
        this.index = index;
    }

    @Override
    public void lock() {

        try {
            List<String> list = zk.getChildren();
            String[] nodes = list.toArray(new String[list.size()]);
            Arrays.sort(nodes);
            if (nodes.length > 0 && getMyNodeName().equals(nodes[0])) {
                isLock.set(true);
                System.out.print("执行中...");
                for (int i = 0; i < 1000; i++) {
                    System.out.print(".");
                    Thread.currentThread().sleep(100l);
                }
                System.out.print("执行完成!");
                unlock();
            } else {
                isLock.set(false);
                System.out.println("为获取到锁");
                createAndLock();
            }

        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }

    }
    
    private void createAndLock(){
        try {
            if(zk.existsNode(getMyNodeName(),getWatcher())){
                while(!isLock.get()){
                    Thread.yield();
                    Thread.currentThread().sleep(10l);
                }
            }else{
                zk.createNode(getMyNodeName());
            }
            
            lock();
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private AtomicBoolean isLock = new AtomicBoolean(false);
    
    private Watcher getWatcher(){
        return new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                isLock.set(true);
            }
        };
    }
    
    @Override
    public void unlock() {
        try {
            zk.deleteNode(getMyNodeName());
            System.out.print("释放分布式锁");
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getMyNodeName() {
        try {
            return new StringTemplateParser().parse(NODE_FORMAT,
                StringTemplateParser.createMapMacroResolver(ImmutableMap.<String, String> builder()
                    .put("host", InetAddress.getLocalHost().getHostAddress()).put("name", InetAddress.getLocalHost().getHostName()).put("index", index).build()));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        
        return "";
    }
    
    public static void main(String[] args) {
        IZKLock zklock =new DefaultZKLock("0");
        zklock.lock();
    }

}
