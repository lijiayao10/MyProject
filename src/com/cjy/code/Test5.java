package com.cjy.code;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test5 {

    public static void main(String[] args) {

        //        Map<String, ?> map = new HashMap<String, String>();
        //
        //        Map<String, String> map2 = new HashMap<>();
        //
        //        Map<String, ?> map3 = new HashMap<>();
        //
        //        ((Map<String, String>) map).putAll(map2);

        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        System.out.println(list);
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
            if (iterator.next().equals("2")) {
                iterator.remove();
            }
        }

        System.out.println(list);

        Object obj = null;
        System.out.println((String) obj);
        
        
        String cvv2 = "0819";
        if(cvv2 != null && cvv2.length() ==4){
        String cvv2_YMM = cvv2.substring(2, 4) + cvv2.substring(0, 2);
        //cvv2
        System.out.println(cvv2_YMM);
        }
    }

}
