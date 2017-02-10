package com.cjy.code.zk.lock;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import com.sun.corba.se.impl.orbutil.concurrent.Mutex;

public class ZookeeperServer {
    
    static Logger logger = Logger.getLogger(ZookeeperServer.class);
    
    private static String connectString = "localhost:2181";
    
    
    public static String rootPath = "/rootPathLock";
    
    public static String childPath1 = "/childPath1";
    public static String childPath2 = "/childPath2";
    
    public static ZooKeeper zk = null;
    
    static {
        try {
            zk = new ZooKeeper(connectString, 30000, new Watcher() {
                
                @Override
                public void process(WatchedEvent arg0) {
//                    try {
//                        ZKLock.getLock();
//                    } catch (KeeperException | InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            });
            
//            zk.create(rootPath, "root".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
