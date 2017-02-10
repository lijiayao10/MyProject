 package com.cjy.code.path;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class PathTest2 {

    /**
     * @param args
     */
    public static void main(String[] args) {
        Path dir = Paths.get("e:\\opt\\logs\\cmf\\app");
        
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.log")){
            System.out.println("1");
            for (Path path : stream) {
                System.out.println(path.getFileName());
            }
            
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
