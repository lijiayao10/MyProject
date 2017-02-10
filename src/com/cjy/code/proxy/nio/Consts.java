package com.cjy.code.proxy.nio;

/** 
 * Constants for proxy.Should not instatiate. 
 * @author Jim-Zhang 
 * 
 */  
public class Consts {  
      
    // private constructor  
    private Consts(){throw new UnsupportedOperationException("Can not instatiate.");}  
      
    /** The number of threads handle requests. */  
    public static final int THREAD_COUNT = 200;  
      
    /** Default listening port. */  
    public static final int LISTEN_PORT = 4186;  
      
    /** Listening port cmd argument */  
    public final static String CMD_ARG_PORT = "-p";  
      
    /** Line end */  
    public final static String LINE_END_S = "\r\n";  
      
    /** Line end */  
    public final static byte[] LINE_END = "\r\n".getBytes();  
      
    /** Stream end */  
    public static byte[] STREAM_END = new byte[]{0,0};  
}  