package com.cjy.code.lang3.tuple;

import org.apache.commons.lang3.tuple.Pair;

public class PairTest {
    
    public static void main(String[] args) {
        Pair<String, String> pair = Pair.of("L", "R");
        
        System.out.println("L:"+pair.getLeft());
        System.out.println("R:"+pair.getRight());
        System.out.println("KEY:"+pair.getKey());
        System.out.println("VALUE:"+pair.getValue());
    }

}
