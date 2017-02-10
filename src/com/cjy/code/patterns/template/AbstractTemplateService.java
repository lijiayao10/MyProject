package com.cjy.code.patterns.template;

/**
 * 模版方法模式<br>
 * 1.封装算法<br>
 * 2.提供泛型处理方法<br>
 * 3.提供钩子方法处理具体子类不同情况<br>
 * @author caojiayao 2016年5月25日 下午1:54:19
 */
public abstract class AbstractTemplateService {
    
    
    final public void doService(){
        this.firstMethod();
        this.processMethod();
        this.lastMethod();
        if(this.isOther())
            this.otherMethod();
    }
    
    public void firstMethod(){
        System.out.println("firstMethod...");
    }
    
    public abstract void processMethod();
    
    public void lastMethod(){
        System.out.println("lastMethod...");
    }
    
    
    final public void otherMethod(){
        System.out.println("otherMethod...");
    }
    
    /**
     * 钩子方法
     * @return
     */
    public boolean isOther(){
        return true;
    }
    
    
}
