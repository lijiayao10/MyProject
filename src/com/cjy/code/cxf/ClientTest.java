package com.cjy.code.cxf;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.cjy.code.cxf.domain.Customer;
import com.cjy.code.cxf.domain.Me;
import com.cjy.code.cxf.domain.Page;
import com.cjy.code.cxf.service.CustomerService;

public class ClientTest {

    private static List<Object> getJacksonJsonProvider() {
        List<Object> providers = new LinkedList<Object>();
        providers.add(new ServiceExceptionMapper());
        JacksonJsonProvider provider = new JacksonJsonProvider();
        provider.configure(
                org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        providers.add(provider);
        return providers;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        try {
            Page<Me> pages = getServiceInstance(CustomerService.class).findPage();
            for (Me u : pages.getResult()) {
                System.out.println(u.getName());
            }

            Page<Customer> page = getServiceInstance(CustomerService.class).findPageCustomer();
            for (Customer u : page.getResult()) {
                System.out.println(u.getName());
            }
        } catch (WebApplicationException e) {
            if (e instanceof WebApplicationException) {
                WebApplicationException we = (WebApplicationException) e;
                System.out.println(we.getMessage());
                //System.out.println(we.getCause().getMessage());
            }
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<Class<?>, Object> repos = new HashMap<Class<?>, Object>();

    private static String                baseUrl;

    static {
        baseUrl = "http://localhost:9000/ws/jaxrs";
    }

    @SuppressWarnings("unchecked")
    public static <T> T getServiceInstance(Class<T> clazz) {
        T t = (T) repos.get(clazz);
        if (t == null) {
            synchronized (clazz) {
                t = (T) repos.get(clazz);
                if (t == null) {

                    t = JAXRSClientFactory.create(baseUrl, clazz, getJacksonJsonProvider());
                    Client client = WebClient.client(t);
                    WebClient.getConfig(client).getInInterceptors().add(new LoggingInInterceptor());
                    WebClient.getConfig(client).getInFaultInterceptors()
                            .add(new LoggingInInterceptor());
                    WebClient.getConfig(client).getOutFaultInterceptors()
                            .add(new LoggingOutInterceptor());
                    WebClient.getConfig(client).getOutInterceptors()
                            .add(new LoggingOutInterceptor());
                    client.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                            .acceptEncoding("UTF-8");
                    repos.put(clazz, t);
                }
            }
        }
        return t;
    }
}
