package com.cjy.code.cxf;

import java.util.LinkedList;
import java.util.List;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import com.cjy.code.cxf.service.CustomerServiceImpl;

public class Server {

    /**
     * @param args
     */
    public static void main(String[] args) {

        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.getInInterceptors().add(new LoggingInInterceptor());
        factoryBean.getOutInterceptors().add(new LoggingOutInterceptor());
        factoryBean.setResourceClasses(CustomerServiceImpl.class);
        List<Object> list = new LinkedList<Object>();
        list.add(new org.codehaus.jackson.jaxrs.JacksonJsonProvider());
        list.add(new InvokeFaultExceptionMapper());
        factoryBean.setProviders(list);

        factoryBean.setAddress("http://localhost:9000/ws/jaxrs");
        factoryBean.create();
    }

}
