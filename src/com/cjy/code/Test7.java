package com.cjy.code;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;

public class Test7 {

    public static void main(String[] args) {
        
        
        double sum =2.2 * 100;
        
//        System.out.println(sum);
//        
//        System.out.println(JSON.toJSONString(new String[]{"1","2"}));
//        
//        System.out.println((String[])JSON.parse("[\"1\",\"2\"]"));
//        
        Map<String, String> map = Test7.<String>get();
        map.put("1", "2");
        
        System.out.println(map);
        
        
        System.out.println(JSONArray.parseArray("[]").isEmpty());
        
        

    }
    
    
    public static <T> Map<String,T> get(){
        return new HashMap<String,T>();
    }

    
    

}

