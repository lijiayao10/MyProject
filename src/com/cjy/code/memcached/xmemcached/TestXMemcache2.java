package com.cjy.code.memcached.xmemcached;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;  
   
   
   
import net.rubyeye.xmemcached.utils.AddrUtil;  
   
import net.rubyeye.xmemcached.MemcachedClient;  
   
import net.rubyeye.xmemcached.MemcachedClientBuilder;  
   
import net.rubyeye.xmemcached.XMemcachedClientBuilder;  
   
import net.rubyeye.xmemcached.exception.MemcachedException;  
   
   
   
public class TestXMemcache2 {

    public static void main(String[] args) {

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil

            .getAddresses("127.0.0.1:11211"));

        MemcachedClient memcachedClient;

        try {

            memcachedClient = builder.build();

            memcachedClient.set("hello", 0, "Hello,xmemcached");

            for (int i = 0; i < 10_0000; i++) {
                memcachedClient.set("hello" + i, 0, "Hello,xmemcached" + i);
            }
            
            for (int i = 0; i < 10_0000; i++) {
                memcachedClient.set("helle" + i, 0, "Helle,xmemcached321321312" + i);
            }

            //            new BufferedReader(new InputStreamReader(System.in)).readLine();

            memcachedClient.shutdown();

        } catch (MemcachedException e) {

            System.err.println("MemcachedClient operation fail");

            e.printStackTrace();

        } catch (TimeoutException e) {

            System.err.println("MemcachedClient operation timeout");

            e.printStackTrace();

        } catch (InterruptedException e) {

            // ignore  

        } catch (IOException e) {

            System.err.println("Shutdown MemcachedClient fail");

            e.printStackTrace();

        }

    }

}