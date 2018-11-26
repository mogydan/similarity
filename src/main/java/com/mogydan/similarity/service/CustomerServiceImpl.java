package com.mogydan.similarity.service;

import com.mogydan.similarity.exception.ResourceNotFoundException;
import com.mogydan.similarity.model.Customer;
import com.mogydan.similarity.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void addCustomers(List<Customer> customers) {
        customerRepository.save(customers);
    }

    @Override
    public Customer getCustomer(String customerId) {
        return customerRepository.findById(Long.parseLong(customerId))
                .orElseThrow(() -> new ResourceNotFoundException("Customer with id = {0} was not found", customerId));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public List<Long> getAllCustomersIds() {
        return getAllCustomers().stream()
                .map(Customer::getId)
                .collect(Collectors.toList());
    }

    @Override
    public void updateCustomer(Customer updates, String customerId) {
        Customer customer = getCustomer(customerId)
                .setName(updates.getName())
                .setEmail(updates.getEmail());
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(String customerId) {
        customerRepository.delete(getCustomer(customerId));
    }

    @Override
    public void clear() {
        customerRepository.deleteAll();
    }
}
