package com.cjy.code.cache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.Lists;

public class CacheBuilderTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        LoadingCache<Long, Integer> cache = CacheBuilder.newBuilder().maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.SECONDS).removalListener(new RemovalListener<Long, Integer>(){
                    @Override
                    public void onRemoval(RemovalNotification<Long, Integer> rn) {
                        System.out.println(rn.getKey()+"被移除");  
                        
                    }}).build(new CacheLoader<Long, Integer>() {
                    public Integer load(Long key) throws Exception {
                        return -1;
                    }
                });
        
        cache.put(1l, 1);
        cache.put(2l, 2);
        cache.put(3l, 3);
        cache.put(4l, 4);
        
        System.out.println("map size:"+cache.size());
        
        System.out.println(cache.get(2l));
        
        Thread.sleep(2*1000);
        
        System.out.println("map size:"+cache.size());
        
//        if(cache.get(2l) == -1){
//            System.out.println("new add");
//            cache.put(2l, 2);
//        }
        System.out.println(cache.get(1l));
        System.out.println(cache.get(2l));
        System.out.println(cache.get(3l));
        System.out.println(cache.get(4l));
        
//        while(true){
//            if(1!=1){
//                break;
//            }
//            if(cache.size() < 4)
//            System.out.println("map size:"+cache.size());
//        }
        
       Lists.transform(null, new Function<String, String>() {

        @Override
        public String apply(String input) {
            return null;
        }
    });
    }

}
