package com.cjy.code;
public enum MaYun {
    himself; //定义一个枚举的元素，就代表MaYun的一个实例
    private String anotherField;

    MaYun() {
        anotherField = "2";
    }

    public void splitAlipay() {
        System.out.println("Alipay是我的啦！看你丫Yahoo绿眉绿眼的望着。。。");
    }

    public static void main(String[] args) {
        System.out.println(MaYun.himself.anotherField);
    }
}
