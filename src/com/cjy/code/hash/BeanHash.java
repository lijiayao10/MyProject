package com.cjy.code.hash;

import java.util.HashMap;

import org.junit.Test;

import com.mongodb.util.Hash;

public class BeanHash<K,V> {

    
    
    public int getHashByKey(K key){
        return 0;
    }
    
    
    public int getHashByValue(V v){
        return 0;
    }
    
    /**
     * 简单hash
     * @param crs
     * @param length
     * @return
     */
    public static int getHashIndex(char[] crs,int length){
        int nHash = 0;
        
        for (char cr : crs) {
            nHash = (nHash << 5) + nHash + cr;
        }
        
        return (nHash % length);
    }
    
    /**
     * HashMap jdk1.6 哈希
     * @param crs
     * @param length
     * @return
     */
    public static int getHashIndex2(String str,int length){
        return indexFor(hash(str.hashCode()), length);
    }
    
    
    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * Returns index for hash code h.
     */
    static int indexFor(int h, int length) {
        return h & (length-1);
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
    
    @Test
    public void test1() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) {
            Math.abs(BeanHash.getHashIndex(("给对方更多"+i).toCharArray(), 16));
        }
        System.out.println("times :"+(System.currentTimeMillis() - time));
    }
    
    @Test
    public void test2() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) {
            BeanHash.getHashIndex2(("给对方更多"+i), 16);
        }
        
        System.out.println("times :"+(System.currentTimeMillis() - time));
    }
    
    
    @Test
    public void test3() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) {
            int h =Hash.hashCode("给对方更多"+i); 
            int index = h & (16 -1);
        }
        
        System.out.println("times :"+(System.currentTimeMillis() - time));
    }
    
    @Test
    public void test4() {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 1000_0000; i++) {
            hash(("给对方更多"+i));
        }
        
        System.out.println("times :"+(System.currentTimeMillis() - time));
    }
}
