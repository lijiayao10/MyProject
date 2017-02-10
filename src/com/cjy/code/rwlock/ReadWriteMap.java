package com.cjy.code.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteMap<K, V> {
    private final Map<K, V>     map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock          r    = lock.readLock();
    private final Lock          w    = lock.writeLock();

    public ReadWriteMap(Map<K, V> map) {
        this.map = map;
    }

    public V put(K key, V value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public V get(K key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

    public static void main(String[] args) {
        final ReadWriteMap<String, String> readWriteMap = new ReadWriteMap<>(new HashMap<String, String>());

        Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

        for (int i = 0; i < 10000; i++) {
            final int tmp = i;

            executor.execute(new Runnable() {

                @Override
                public void run() {
                    String str = "number:" + tmp;
                    if (tmp % 2 == 0) {
                        readWriteMap.put(str, str);
                    } else {
                        readWriteMap.get(str);
                    }
                }
            });

        }

    }

}
