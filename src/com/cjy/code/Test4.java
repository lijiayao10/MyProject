package com.cjy.code;

public class Test4 {

    public static void main(String[] args) {
        String amount = "-0.01";
        System.out.println(amount.trim().replaceAll("-", ""));

        String name1 = "name1";
        String[] name1s = new String[] { "name1" };

        if (name1s[0] == "name1".trim()) {
            System.out.println("OK");
        }
    }

}
