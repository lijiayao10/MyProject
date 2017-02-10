package com.cjy.code.proxy3;

public class TestCglib {  
    
    public static void main(String[] args) {  
        BookFacadeCglib cglib=new BookFacadeCglib();  
        BookFacade bookCglib=(BookFacade)cglib.getInstance(new BookFacadeImpl1());  
        bookCglib.addBook();  
//        bookCglib.outMsg();
    }  
}
