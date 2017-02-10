package com.cjy.code.mina2;

import java.io.Serializable;

public class MyRequestObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String            name;

    private String            value;

    public MyRequestObject(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Request [name: " + name + ", value: " + value + "]");
        return sb.toString();
    }
}
