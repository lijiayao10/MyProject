package com.cjy.code.cache;

/**
 * Created by Administrator on 2016/1/7.
 * @author caojiayao
 */
public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
