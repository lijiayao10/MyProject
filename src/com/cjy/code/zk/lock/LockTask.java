package com.cjy.code.zk.lock;
public interface LockTask {
    
    public <T> T execute();

}
