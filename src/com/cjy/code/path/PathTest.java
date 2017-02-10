 package com.cjy.code.path;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {

    public static void main(String[] args) {
        //获得path方法一,e:/logs/access.log  
        Path path = FileSystems.getDefault().getPath("e:/logs", "access.log");  
        System.out.println(path.getNameCount());  
        //获得path方法二，用File的toPath()方法获得Path对象  
        File file = new File("e:/logs/access.log");  
        Path pathOther = file.toPath();  
        //0,说明这两个path是相等的  
        System.out.println(path.compareTo(pathOther));  
        //获得path方法三  
        Path path3 = Paths.get("e:/logs", "access.log");  
        System.out.println(path3.toString());  
          
        //join two paths  
        Path path4 = Paths.get("e:/logs");  
        System.out.println("path4: " + path4.resolve("access.log"));  
        System.out.println("--------------分割线---------------");  
        try {  
            if(Files.isReadable(path)){  
                //注意此处的newBufferedRead的charset参数，如果和所要读取的文件的编码不一致，则会抛出异常  
                //java的新特性，不用自己关闭流  
                BufferedReader br = Files.newBufferedReader(path, Charset.defaultCharset());//new BufferedReader(new FileReader(new File("e:/logs/access.log")));//  
                String line = "";  
                while((line = br.readLine()) != null){  
                    System.out.println(line);  
                }  
            }else{  
                System.err.println("cannot readable");  
            }  
        } catch (IOException e) {  
            System.err.println("error charset");  
        }  
        
    }

}
