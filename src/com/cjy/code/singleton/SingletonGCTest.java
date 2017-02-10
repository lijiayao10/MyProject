package com.cjy.code.singleton;
public class SingletonGCTest {

    public static void main(String[] args) throws InterruptedException {
        SingletonGCTest gcTest = new SingletonGCTest();
        gcTest.test2();
        
        gcTest=null;
        
        byte[][] bs = new byte[4096][4096];
        int i =0;
        while(true){
            byte[] bytes = new byte[4096];
            
            bs[i++] = bytes;
            
            Thread.sleep(1);
            
            if(i>4000){
                break;
            }
        }
        
        bs = null;
        
        Thread.sleep(10000000);
    }
    
    private void test2(){
        SingletonGCReference gcReference = new SingletonGCReference();
        gcReference.getSINGLETON_GC();
        
        gcReference = null;
    }
    
    
    

}
