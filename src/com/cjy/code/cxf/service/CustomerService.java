package com.cjy.code.cxf.service;

import java.util.List;

import javax.websocket.server.PathParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.cjy.code.cxf.domain.Customer;
import com.cjy.code.cxf.domain.Me;
import com.cjy.code.cxf.domain.Page;
import com.cjy.code.cxf.domain.ServiceException;

@Path(value = "/customer")
@Produces({ "application/xml", "application/json" })
public interface CustomerService {

    @GET
    @Path(value = "/{id}/info")
    Customer findCustomerById(@PathParam("id") String id);

    @GET
    @Path(value = "/search")
    Customer findCustomerByName(@QueryParam("name") String name);

    @POST
    @Path(value = "/all")
    List<Customer> findAllCustomer();

    @POST
    @Path(value = "/page")
    Page<Customer> findPageCustomer() throws ServiceException;

    @POST
    @Path(value = "/pageMe")
    Page<Me> findPage();
}
