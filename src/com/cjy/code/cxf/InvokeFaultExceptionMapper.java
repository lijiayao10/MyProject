package com.cjy.code.cxf;

import java.util.Locale;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.cjy.code.cxf.domain.ServiceException;

@Provider
public class InvokeFaultExceptionMapper implements ExceptionMapper<ServiceException> {

    /**
     * (non-Javadoc)
     * 
     * @see javax.ws.rs.ext.ExceptionMapper#toResponse(java.lang.Throwable)
     */
    @Override
    public Response toResponse(ServiceException ex) {
        ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
        rb.type("application/json;charset=UTF-8");
        rb.entity(ex);
        rb.language(Locale.SIMPLIFIED_CHINESE);
        Response r = rb.build();
        return r;

    }

}
