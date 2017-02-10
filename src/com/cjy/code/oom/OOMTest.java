package com.cjy.code.oom;

import java.util.HashMap;
import java.util.Map;

public class OOMTest {

    public final static Map<String, String> map = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(10000l);

        for (int i = 0;; i++) {
            map.put("key:" + i, "value:" + i);
            //            System.out.println("============:" + i);
        }
    }

}
