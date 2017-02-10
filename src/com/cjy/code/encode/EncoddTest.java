package com.cjy.code.encode;

import java.io.UnsupportedEncodingException;

public class EncoddTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String content = "的地方打发打发第三方的发大水";
        byte[] strs = content.getBytes("GBK");
        System.out.println(new String(strs, "GBK"));
    }

}
