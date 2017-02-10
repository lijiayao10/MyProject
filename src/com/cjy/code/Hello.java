package com.cjy.code;

import java.io.IOException;
import java.net.ConnectException;

public class Hello {
    public static void main(String[] args) {
        try {
            print();
        } catch ( IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void print() throws IOException, ClassNotFoundException,ConnectException {

    }
}
