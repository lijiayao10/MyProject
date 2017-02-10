package com.cjy.code.asm3;

public interface Comparable extends Mesurable {
    public static final int LESS    = 0;

    public static final int EQUAL   = 0;

    public static final int GREATER = 0;

    public abstract int compareTo(Object object);
}
