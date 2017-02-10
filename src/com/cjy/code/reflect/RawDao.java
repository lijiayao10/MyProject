/*
 * @author caojiayao 2017年1月17日 下午4:20:17
 */
package com.cjy.code.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * <p><p>
 * @author caojiayao 
 * @version $Id: RawDao.java, v 0.1 2017年1月17日 下午4:20:17 caojiayao Exp $
 */
public class RawDao<T> {
    protected Class<T> clazz;

    @SuppressWarnings("unchecked")
    public RawDao() {
        @SuppressWarnings("rawtypes")
        Class clazz = getClass();

        while (clazz != Object.class) {
            Type t = clazz.getGenericSuperclass();
            if (t instanceof ParameterizedType) {
                Type[] args = ((ParameterizedType) t).getActualTypeArguments();
                if (args[0] instanceof Class) {
                    this.clazz = (Class<T>) args[0];
                    break;
                }
            }
            clazz = clazz.getSuperclass();
        }
        System.out.println(clazz);
        System.out.println(this.clazz);
    }
    
    public static void main(String[] args) {
        new RawDao<HashMap<String, String>>();
    }
}