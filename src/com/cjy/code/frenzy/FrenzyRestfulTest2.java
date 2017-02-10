package com.cjy.code.frenzy;

import com.manyi.iw.fin.frenzy.restful.common.BaseFacade;
import com.manyi.iw.fin.frenzy.restful.request.Requestable;
import com.manyi.iw.fin.frenzy.restful.response.Responseable;

public class FrenzyRestfulTest2 extends BaseFacade {

    protected FrenzyRestfulTest2(String baseURL) {
        super(baseURL);
    }

    @Override
    protected String getPathInfoPrefix() {
        return "";
    }
    
    
    protected <RESPONSE extends Responseable> RESPONSE get(final String requestMapping, final Requestable req, final Class<RESPONSE> resClazz)
    {
        return super.get(requestMapping, req, resClazz);
     }
    
    protected <RESPONSE extends Responseable> RESPONSE post(final String requestMapping, final Requestable req, final Class<RESPONSE> resClazz)
    {
        return super.post(requestMapping, req, resClazz);
     }

}
