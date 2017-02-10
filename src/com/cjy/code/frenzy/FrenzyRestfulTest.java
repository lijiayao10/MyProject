package com.cjy.code.frenzy;

import java.util.ArrayList;
import java.util.List;

import com.cjy.code.frenzy.domain.DefaultRequest;
import com.cjy.code.frenzy.domain.DefaultResponse;
import com.manyi.iw.fin.frenzy.restful.common.BaseFacade;

public class FrenzyRestfulTest {
    
    
    public static void main(String[] args) {
        
        DefaultRequest request =  new DefaultRequest();
        request.setName("name");
        request.setCode("code");
        List<String> lists=new ArrayList<String>();
        lists.add("aaaaa");
        lists.add("aaaaa");
        lists.add("aaaaa");
        request.setLists(lists);
        DefaultResponse response = new FrenzyRestfulTest2("http://localhost:8080/json").post("", request, DefaultResponse.class);
    
        System.out.println(response.getName());
        System.out.println(response.getCode());
        System.out.println(response.getLists());
    }

}

