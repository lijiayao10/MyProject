package com.cjy.code.zk;

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

public class ZookeeperClient {
    
    static Logger logger = Logger.getLogger(ZookeeperClient.class);
    
    private static String connectString = "localhost:2181";
    
    
    public static String rootPath = "/consumers";
    
    public static String childPath1 = "/childPath1";
    public static String childPath2 = "/childPath2";
    
    public static ZooKeeper zk = null;
    
    static {
        try {
            zk = new ZooKeeper(connectString, 6000, new Watcher() {
                
                @Override
                public void process(WatchedEvent arg0) {
                    System.out.println("事件被触发:"+arg0.getType());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) throws KeeperException, InterruptedException {
        // 创建一个目录节点
        zk.create(rootPath, "testRootData".getBytes(), Ids.OPEN_ACL_UNSAFE,
          CreateMode.PERSISTENT);
        // 关闭连接
        zk.close(); 
        
    }
    
    
    void findLeader() throws InterruptedException { 
        byte[] leader = null; 
        try { 
            leader = zk.getData(rootPath + "/leader", true, null); 
        } catch (Exception e) { 
            logger.error(e); 
        } 
        if (leader != null) { 
//            following(); 
        } else { 
            String newLeader = null; 
            try { 
                byte[] localhost = InetAddress.getLocalHost().getAddress(); 
                newLeader = zk.create(rootPath + "/leader", localhost, 
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL); 
            } catch (Exception e) { 
                logger.error(e); 
            } 
            if (newLeader != null) { 
//                leading(); 
            } else { 
                mutex.wait(); 
            } 
        } 
    }
    
    Mutex mutex;
    String myZnode="";
    
    void getLock() throws KeeperException, InterruptedException{ 
           List<String> list = zk.getChildren(rootPath, false); 
           String[] nodes = list.toArray(new String[list.size()]); 
           Arrays.sort(nodes); 
           if(myZnode.equals(rootPath+"/"+nodes[0])){ 
//               doAction(); 
               
           } 
           else{ 
               waitForLock(nodes[0]); 
           } 
       } 
       void waitForLock(String lower) throws InterruptedException, KeeperException {
           Stat stat = zk.exists(rootPath + "/" + lower,true); 
           if(stat != null){ 
               mutex.wait(); 
           } 
           else{ 
               getLock(); 
           } 
       } 
    
    
}
