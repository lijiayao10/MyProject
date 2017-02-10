package com.cjy.code.nosql.redis;

import redis.clients.jedis.Jedis;

public class RedisClientTest {
    public static void main(String[] args) {
        try {
            Jedis jr = new Jedis("127.0.0.1", 6379); //redis服务地址和端口号
            String key = "mKey";
            jr.set(key, "hello,redis!");
            String v = new String(jr.get(key));
            String k2 = "count";
            jr.incr(k2);
            jr.incr(k2);
            System.out.println("--------");
            System.out.println(v);
            System.out.println(new String(jr.get(k2)));
            
            jr.sadd("set", new String[]{"1","2"});
            
            
            System.out.println(jr.lpop("set"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
