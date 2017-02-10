package com.cjy.code.proxy.nio;

import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.net.SocketAddress;  
import java.nio.channels.ServerSocketChannel;  
import java.nio.channels.SocketChannel;  
  
/*** 
 * Start up the proxy. 
 * @author Jim-Zhang 
 * 
 */  
public class ProxyBootstrap {  
  
    /** 
     * Entry. 
     * @param args arguments 
     */  
    public static void main(String[] args) {  
          
        // Check arguments  
          
        // If the first argument is '-help',print usage.  
        if (args == null || args.length == 0){  
              
            // Start the server  
            startServer(Consts.LISTEN_PORT);  
            return;  
        }  
          
        // Check '-p'.  
        for (int i = 0; i <args.length; i ++){  
            String arg = args[i];  
            if (!arg.equals(Consts.CMD_ARG_PORT)){  
                printHelp();  
                return;  
            }  
              
            if (i == args.length -1){  
                printHelp();  
                return;  
            }  
              
            // Check port  
            String sport = args[i+1].trim();  
            if (sport.length() == 0){ // invalid  
                printHelp();  
                return;  
            }  
              
            try{  
                int usePort = Integer.valueOf(sport);  
                  
                // start the server  
                startServer(usePort);  
            }catch(NumberFormatException e){  
                printHelp();  
                e.printStackTrace();  
                return;  
            }  
        }  
          
    }  
      
    /** 
     * Start up the server on specific port. 
     * @param port Listen port 
     */  
    private static void startServer(int port){  
          
        try {  
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();  
            SocketAddress socketAddress = new InetSocketAddress(port);  
              
            // Bind serverSocketChannel  
            serverSocketChannel.bind(socketAddress);  
              
            System.out.println("Listening on port:"+port+".");  
            while(true){  
                  
                // block mode  
                SocketChannel socketChannel = serverSocketChannel.accept();  
                  
                ConnectionHandler.getInstance().add(socketChannel);  
                  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * Print help info. 
     */  
    private static void printHelp(){  
        System.out.println("Usage:");  
        System.out.println("-p port");  
        System.out.println("For example: -p 8200 will listen on port 8200.");  
    }  
}  