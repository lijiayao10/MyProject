package com.cjy.code.cache.mycache;

public class BeanHash<K,V> {

    
    
    public int getHashByKey(K key){
        return 0;
    }
    
    
    public int getHashByValue(V v){
        return 0;
    }
    
    /**
     * 1.8
     * @param key
     * @return
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    
}
