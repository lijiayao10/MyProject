package com.cjy.code.patterns.template;
public class DefaultTemplateService extends AbstractTemplateService {

    @Override
    public void processMethod() {
        System.out.println("processMethod !!!");
    }
    
    public static void main(String[] args) {
        DefaultTemplateService service = new DefaultTemplateService();
        service.doService();
        
        (new AbstractTemplateService() {
            
            @Override
            public void processMethod() {
                System.out.println("processMethod`````");
            }
        }).doService();;
    }

}
