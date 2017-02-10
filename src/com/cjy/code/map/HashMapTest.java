package com.cjy.code.map;

import java.util.HashMap;

public class HashMapTest {

    public static void main(String[] args) {

        int size = 100000;

        long time = System.currentTimeMillis();

        HashMap<String, String> hashMap = new HashMap<>(size, 1f);

        for (int i = 0; i < size; i++) {
            hashMap.put(i + "12132", i + "jkgjhjkh");
        }

        for (int i = 0; i < size; i++) {
            hashMap.get(i + "12132");
        }

        for (int i = 0; i < size; i++) {
            hashMap.containsKey(i + "12132");
        }

        System.out.println("time:" + (System.currentTimeMillis() - time));
    }

}
