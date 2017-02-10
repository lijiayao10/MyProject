/*
 * @author caojiayao 2017年1月17日 下午3:25:43
 */
package com.cjy.code.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * <p><p>
 * @author caojiayao 
 * @version $Id: GenericParent.java, v 0.1 2017年1月17日 下午3:25:43 caojiayao Exp $
 */
public class GenericParent<T,K> {
    public Class<T> entityClass;

    public GenericParent(){
        TypeVariable[] tValue = getClass().getTypeParameters();
        System.out.println(tValue[0].getName());
        System.out.println(getClass().getName());
        Type t = getClass().getGenericSuperclass();
        System.out.println(t);
        System.out.println(t.getClass().getName());
        entityClass = (Class<T>)((ParameterizedType)t).getActualTypeArguments()[0];
        System.out.println(entityClass.getName());
    }

    public static void main(String[] args){
        GenericParent<String,Integer> obj = new GenericParent<String,Integer>();
    }
}
