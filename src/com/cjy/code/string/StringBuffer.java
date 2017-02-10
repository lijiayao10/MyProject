package com.cjy.code.string;

import java.util.Arrays;

public class StringBuffer {

    char[] vars;

    int    count;

    public StringBuffer(String str) {
        int len = str.length();
        vars = new char[len];
        append(str);
    }

    public void dilatation(int len) {
        int newCount = count + len;
        vars = Arrays.copyOf(vars, newCount);
    }

    public StringBuffer append(String str) {
        int len = str.length();
        dilatation(len);
        str.getChars(0, len, vars, count);
        count += len;
        return this;
    }

    @Override
    public String toString() {
        return new String(vars, 0, count);
    }

    public static void main(String[] args) {
        System.out.println(new StringBuffer("abc").append("def").append("gh"));
    }
}
