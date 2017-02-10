package com.cjy.code.common;
public class CommonException extends Exception{
    
    public CommonException(String msg,Throwable throwable){
        super(msg,throwable);
    }
    
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg=msg;
    }
    
}
