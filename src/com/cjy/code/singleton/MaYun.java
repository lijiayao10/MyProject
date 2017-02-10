/*
 * @author caojiayao 2017年1月25日 下午7:07:47
 */
package com.cjy.code.singleton;

/**
 * <p><p>
 * @author caojiayao 
 * @version $Id: MaYun.java, v 0.1 2017年1月25日 下午7:07:47 caojiayao Exp $
 */
public enum MaYun {
                   himself; //定义一个枚举的元素，就代表MaYun的一个实例
    private String anotherField;

    MaYun() {
        //MaYun诞生要做的事情
        //这个方法也可以去掉。将构造时候需要做的事情放在instance赋值的时候：
        /** himself = MaYun() {
        * //MaYun诞生要做的事情
        * }
        **/
    }

    public void splitAlipay() {
        System.out.println("Alipay是我的啦！看你丫Yahoo绿眉绿眼的望着。。。");
    }
    
    
}
