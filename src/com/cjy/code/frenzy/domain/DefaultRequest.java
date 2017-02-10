package com.cjy.code.frenzy.domain;

import java.util.List;

import com.manyi.iw.fin.frenzy.restful.request.Requestable;

public class DefaultRequest implements Requestable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    
    private String name ;
    
    private String code ;
    
    private List<String> lists;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getLists() {
        return lists;
    }

    public void setLists(List<String> lists) {
        this.lists = lists;
    }

    

    

}
