package com.mogydan.similarity.service;

import com.mogydan.similarity.model.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    void addCustomers(List<Customer> customers);

    Customer getCustomer(String customerId);

    List<Customer> getAllCustomers();

    List<Long> getAllCustomersIds();

    void updateCustomer(Customer customer, String customerId);

    void deleteCustomer(String customerId);

    void clear();
}
