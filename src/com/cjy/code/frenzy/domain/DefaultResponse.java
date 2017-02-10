package com.cjy.code.frenzy.domain;

import java.util.List;

import com.manyi.iw.fin.frenzy.restful.response.Responseable;

public class DefaultResponse implements Responseable {

    /**
     * 
     */
    private static final long serialVersionUID = 486029847207634461L;
    
    
    private String name;
    
    private String code;
    
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
