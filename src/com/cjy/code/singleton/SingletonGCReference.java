package com.cjy.code.singleton;
public class SingletonGCReference {
    private SingletonGC SINGLETON_GC = SingletonGC.SINGLETON_GC;

    public SingletonGC getSINGLETON_GC() {
        return SINGLETON_GC;
    }

    public void setSINGLETON_GC(SingletonGC sINGLETON_GC) {
        SINGLETON_GC = sINGLETON_GC;
    }
}
