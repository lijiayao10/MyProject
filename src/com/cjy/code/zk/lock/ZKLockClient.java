package com.cjy.code.zk.lock;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ZKLockClient {
    
    private static Logger logger = Logger.getLogger(ZookeeperServer.class);
    
    private static String connectString = "localhost:2181";
    
    private static String rootPath = "/zklockRoot";
    
    private ZooKeeper zkClient;
    
    public ZKLockClient() {
        try {
            zkClient = new ZooKeeper(connectString, 300000, new Watcher() {
                
                @Override
                public void process(WatchedEvent event) {
                    logger.info("节点变更");
                }
            });
            zkClient.create(rootPath, "root".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (IOException | KeeperException | InterruptedException e) {
            logger.error("创建zk客服端异常", e);
            e.printStackTrace();
        }
    }

    public ZooKeeper getZkClient() {
        return zkClient;
    }

    public void setZkClient(ZooKeeper zkClient) {
        this.zkClient = zkClient;
    }
    
    public List<String> getChildren() throws KeeperException, InterruptedException{
        return zkClient.getChildren(rootPath, false);
    }
    
    public boolean existsNode(String node,Watcher watcher) throws KeeperException, InterruptedException{
        return zkClient.exists(rootPath+"/"+node, watcher) != null;
    }
    
    public void createNode(String node) throws KeeperException, InterruptedException{
        zkClient.create(rootPath+"/"+node, "node".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    
    public void deleteNode(String node) throws KeeperException, InterruptedException{
        zkClient.delete(rootPath+"/"+node, -1);
    }
    
}
