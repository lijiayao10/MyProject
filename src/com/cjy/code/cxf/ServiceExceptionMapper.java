package com.cjy.code.cxf;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.ResponseExceptionMapper;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.cjy.code.cxf.domain.ServiceException;
import com.fasterxml.jackson.core.JsonParseException;

public class ServiceExceptionMapper implements ResponseExceptionMapper<ServiceException> {

    /**
     * (non-Javadoc)
     * 
     * @see org.apache.cxf.jaxrs.client.ResponseExceptionMapper#fromResponse(javax.ws.rs.core.Response)
     */
    @Override
    public ServiceException fromResponse(Response r) {
        Object obj = r.getEntity();
        ObjectMapper mapper = new ObjectMapper();

        mapper.configure(
                org.codehaus.jackson.map.DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,
                false);
        try {
            return mapper.readValue(obj.toString(), ServiceException.class);
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return new ServiceException(obj.toString());
    }

}
