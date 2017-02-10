package com.cjy.code.cxf.service;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.cjy.code.cxf.domain.Customer;
import com.cjy.code.cxf.domain.Me;
import com.cjy.code.cxf.domain.Page;
import com.cjy.code.cxf.domain.ServiceException;

public class CustomerServiceImpl implements CustomerService {

    public Customer findCustomerById(String id) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(id);
        customer.setBirthday(Calendar.getInstance().getTime());
        return customer;
    }

    public Customer findCustomerByName(String name) {
        Customer customer = new Customer();
        customer.setId(name);
        customer.setName(name);
        customer.setBirthday(Calendar.getInstance().getTime());
        return customer;
    }

    /**
     * (non-Javadoc)
     * 
     * @see edu.xdev.restful.CustomerService#findAllCustomer()
     */
    @Override
    public List<Customer> findAllCustomer() {
        List<Customer> tar = new LinkedList<Customer>();
        Customer customer = new Customer();
        customer.setId("e24234");
        customer.setName("張三");
        customer.setBirthday(Calendar.getInstance().getTime());
        tar.add(customer);
        customer = new Customer();
        customer.setId("324324");
        customer.setName("李四");
        customer.setBirthday(Calendar.getInstance().getTime());
        tar.add(customer);
        return tar;
    }

    /**
     * (non-Javadoc)
     * 
     * @see edu.xdev.restful.CustomerService#findPageCustomer()
     */
    public Page<Customer> findPageCustomer() throws ServiceException {

        List<Customer> tar = new LinkedList<Customer>();
        Customer customer = new Customer();
        customer.setId("e24234");
        customer.setName("張三");
        customer.setBirthday(Calendar.getInstance().getTime());
        tar.add(customer);
        customer = new Customer();
        customer.setId("324324");
        customer.setName("李四");
        customer.setBirthday(Calendar.getInstance().getTime());
        tar.add(customer);
        Page<Customer> page = new Page<Customer>(1, 2, 1, tar);
        if (1 == 1) {
            throw new ServiceException("abcd");
        }
        return page;
    }

    /**
     * (non-Javadoc)
     * 
     * @see edu.xdev.restful.CustomerService#findPage()
     */
    public Page<Me> findPage() {
        List<Me> tar = new LinkedList<Me>();
        Me m = new Me();
        m.setName("中文");
        tar.add(m);
        m = new Me();
        m.setName("English");
        tar.add(m);

        Page<Me> page = new Page<Me>(1, 2, 1, tar);
        return page;
    }
}
