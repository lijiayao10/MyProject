package com.cjy.code;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cjy.code.common.CommonException;
import com.google.common.collect.Lists;
import com.ibm.wsdl.util.StringUtils;

public class Test6 {

    public static void main(String[] args) {
//        System.out.println(fetchChannelErrorCode("返回码：T6]"));
        
        ArrayList<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        
        List<String> list2 = list.subList(0, 5);
        
        for (Iterator iterator = list.iterator(); iterator.hasNext();) {
            System.out.println("1");
            iterator.next();
            iterator.remove();
        }
        
        
        

    }

    public static String fetchChannelErrorCode(String message) {
        if(message!=null) {
            int index = message.indexOf("返回码：");
            if (index != -1) {
                String temp = message.substring(index + 4);
                index = temp.indexOf("]");
                if (index != -1) {
                    return temp.substring(0, index);
                } else {
                    return temp;
                }
            }
        
        }
        
        List<Long> list = Lists.<Long> newArrayList();
        return null;
        
    }
    
    

}

