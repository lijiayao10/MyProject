package com.cjy.code.proxy3;

public class BookFacadeImpl1 implements BookFacade {
	private String msg;
	
	public void addBook() {  
        System.out.println("增加图书的普通方法...");  
    }
	
	
	public BookFacadeImpl1(){
		System.out.println("实例化了!!!!!!!!");
	}
	
	
//	public BookFacadeImpl1(String msg){
//		this.msg = msg;
//	}
	
	
	
	public void outMsg() {  
        System.out.println("msg:"+msg);  
    }


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}
}
