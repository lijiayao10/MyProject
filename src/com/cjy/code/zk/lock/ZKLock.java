package com.cjy.code.zk.lock;

import java.util.Arrays;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;

import static com.cjy.code.zk.lock.ZookeeperServer.*;

public class ZKLock {

    static sun.awt.Mutex  mutex = new sun.awt.Mutex();
    static String myZnode = "none";
    
    static String node = "none";

    static void getLock() throws KeeperException, InterruptedException {
        List<String> list = zk.getChildren(rootPath, false);
        String[] nodes = list.toArray(new String[list.size()]);
        Arrays.sort(nodes);
        if ((rootPath+"/"+myZnode).equals(rootPath + "/" + nodes[0])) {
            buildTask().execute();
        } else {
            waitForLock(nodes[0]);
        }
    }

    static void waitForLock(String lower) throws InterruptedException, KeeperException {
        Stat stat = zk.exists(rootPath + "/" + lower, true);
        if (stat != null) {
            mutex.wait();
        } else {
            getLock();
        }
    }

    private static LockTask buildTask() {
        return new LockTask() {

            @Override
            public <T> T execute() {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("我拿到lock了");
                return null;
            }
        };
    }
    
    public static void main(String[] args) throws KeeperException, InterruptedException {
        for (int i = 0; i <100; i++) {
            zk.create(rootPath+"/"+node+i, ("node"+i).getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        
        for (;;) {
            myZnode = node + 32;
            ZKLock.getLock();
            
        }
    }

}
