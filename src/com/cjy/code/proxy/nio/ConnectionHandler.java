package com.cjy.code.proxy.nio;

import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.net.SocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.SelectionKey;  
import java.nio.channels.Selector;  
import java.nio.channels.SocketChannel;  
import java.util.Arrays;  
import java.util.concurrent.ArrayBlockingQueue;  
import java.util.concurrent.BlockingQueue;  
import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
  
/** 
 * Connection handler. 
 * Need to finish. 
 * @author Jim-Zhang 
 */  
public class ConnectionHandler{  
      
    /** Singleton */  
    private static final ConnectionHandler instance = new ConnectionHandler();  
      
    /** Scheduled worker */  
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(Consts.THREAD_COUNT);  
      
    /**  
     * Add channel. 
     * @param socketChannel SocketChannel to handle  
     * */  
    public void add(SocketChannel socketChannel){  
          
        // One thread per request's SocketChannel  
        executorService.execute(new HandlerThread(socketChannel));  
    }  
      
    /**  
     * Get {@link ConnectionHandler} object. 
     * @return {@link ConnectionHandler} Instance 
     * */  
    public static ConnectionHandler getInstance(){  
        return instance;  
    }  
      
    /** Thread for handling socket channel. */  
    private class HandlerThread extends Thread{  
          
        /** Reqeust SocketChannel */  
        private SocketChannel socketChannel;  
          
        private final BlockingQueue<String> sourceRequestData = new ArrayBlockingQueue<String>(1000);  
          
        /** Target */  
        private SocketChannel targetSocketChannel;  
          
        /** Constructor */  
        public HandlerThread(SocketChannel socketChannel){  
            HandlerThread.this.socketChannel = socketChannel;  
            setDaemon(true);  
        }  
          
        @Override  
        public void run() {  
              
            // Use SocketChannel to read http data  
            // final Socket socket = socketChannel.socket();  
            String resourceUrl = null;  
            try {  
                // set non-block  
                socketChannel.configureBlocking(false);  
                Selector selector = Selector.open();  
                SelectionKey sourceKey = socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
                  
                // for reading line   
                StringBuilder lineBuilder = new StringBuilder();  
                ByteBuffer buffer = ByteBuffer.allocate(1024);  
                String line = null; // null for no data  
                  
                // for target  
                Selector targetSelector = null;  
                SelectionKey targetSelectionKey = null;  
                  
                // Read request data  
                while (true){   
                      
                    Thread.sleep(20);  
                      
                    buffer.clear();  
                      
                    // Read request data  
                    selector.selectNow();  
                      
                    if (sourceKey.isValid() && sourceKey.isReadable()){  
                        int readLen = socketChannel.read(buffer);  
                        if (readLen > 0){  
                            byte[] datas = new byte[readLen];  
                            buffer.rewind();  
                            buffer.get(datas);  
                            lineBuilder.append(new String(datas.clone()));  
                            byte[] dataEnd = new byte[]{datas[datas.length -2],datas[datas.length -1]};  
                            if (endof(dataEnd)){  
                                line = lineBuilder.toString();  
                                break;  
                            }  
                        }  
                          
                        if (readLen == -1){  
                            line = lineBuilder.toString();  
                            break;  
                        }  
                    }  
                      
                }  
                  
                  
                if (line == null || line.isEmpty()){  
                    return;  
                }  
                  
                if (line != null){  
                      
                    // Push request data to Queue to be handled later  
                    String requestStr = line.replace(HttpHeader.PROXY_CONNECTION, "Connection");  
                    sourceRequestData.add(requestStr);  
                }  
                  
                // Handle target channel  
                while(true && socketChannel.isOpen()){  
                      
                    String lineToHandle = sourceRequestData.poll();  
                    // GET http://xx/ss/dd HTTP/1.1   
                    // Immediately open the SocketChannel  
                    if (targetSocketChannel == null   
                            && lineToHandle != null   
                            && lineToHandle.indexOf("HTTP/1.1") != -1){  
                        String targetHostStr = lineToHandle.split(" ")[1];  
                        targetHostStr = targetHostStr.substring(targetHostStr.indexOf("//")+2);  
                        targetHostStr = targetHostStr.substring(0,targetHostStr.indexOf("/"));   
                                  
                        // use SocketChannel to request data  
                        String[] hostParams = targetHostStr.split(HttpHeader.SPLIT_CHAR);  
                        String host = hostParams[0].trim();  
                        int port = hostParams.length == 1 ?  80 : Integer.valueOf(hostParams[1].trim());  
                          
                        SocketAddress targetAddress = new InetSocketAddress(host, port);  
                              
                        targetSocketChannel =  SocketChannel.open(targetAddress);  
                        targetSocketChannel.configureBlocking(false);  
                          
                        targetSelector = Selector.open();  
                        targetSelectionKey = targetSocketChannel.register(targetSelector,  
                                    SelectionKey.OP_READ | SelectionKey.OP_WRITE);  
                          
                        resourceUrl = lineToHandle;  
                    }  
                      
                    if(targetSocketChannel != null){  
                      
                        targetSelector.select();  
                        if (targetSelectionKey.isWritable()){  
                            String toTargetData = lineToHandle;  
                            if (toTargetData != null){  
                                byte[] datas = toTargetData.getBytes();  
                                ByteBuffer buffers = ByteBuffer.wrap(datas);  
                                // buffers.flip();  
                                targetSocketChannel.write(buffers);  
                                  
                                targetSocketChannel.write(ByteBuffer.wrap(Consts.LINE_END));  
                                targetSocketChannel.write(ByteBuffer.wrap(Consts.LINE_END));  
                                // targetSocketChannel.write(ByteBuffer.wrap(Consts.LINE_END));  
                                  
                                // line end  
                                // targetSocketChannel.write(ByteBuffer.wrap(Consts.LINE_END));  
                            }else{  
                                // targetSocketChannel.write(ByteBuffer.wrap(Consts.STREAM_END));  
                                // targetSocketChannel.write(ByteBuffer.wrap(Consts.LINE_END));  
                            }  
                        }  
                          
                        if (targetSelectionKey.isReadable()){  
                              
                            ByteBuffer buffers = ByteBuffer.allocate(1024);  
                              
                            int len = targetSocketChannel.read(buffers);  
                            if (len == 0){  
                                continue;  
                            }  
                              
                            if (len == -1){  
                                break;  
                            }  
                              
                            buffers.flip();  
                            socketChannel.write(buffers);  
                            buffers.compact();  
                        }  
                    }  
                      
                    Thread.sleep(20);  
                }  
                  
                  
            } catch (Throwable e) {  
            	e.printStackTrace();
                  
                try {  
                    System.out.println(resourceUrl);  
                    socketChannel.close();  
                    targetSocketChannel.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
                e.printStackTrace();  
            }  
        }  
          
        /**  
         * End of stream or line. 
         * @param data Data 
         * @return true-end of line or stream 
         * */  
        private boolean endof(byte[] datas){  
            return Arrays.equals(Consts.LINE_END,datas)   
                        || Arrays.equals(Consts.STREAM_END,datas);  
        }  
    }  
} 