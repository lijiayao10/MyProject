package com.cjy.code.singleton;
public class SingletonGC {
    private byte[] bytes = new byte[10240];;
    public static final SingletonGC SINGLETON_GC = new SingletonGC();
    
    
    private SingletonGC(){}
    
    

    public byte[] getBytes() {
        return bytes;
    }


    @Override
    protected void finalize() throws Throwable {
        System.out.println("huishou");
        super.finalize();
    }
    
    
}
