package com.java.testing.service;

import com.java.testing.exception.CustomerAlreadyExistsException;
import com.java.testing.exception.CustomerNotFoundException;
import com.java.testing.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    private List<Customer> customers;

    public CustomerService(List<Customer> customers) {
        this.customers = new ArrayList<>(customers);   // FIXED
    }

    public Customer addCustomer(Customer customer) {

        boolean exists = customers.stream()
                .anyMatch(c -> c.getId() == customer.getId());

        if (exists) {
            throw new CustomerAlreadyExistsException(
                    "Customer already exists with ID: " + customer.getId());
        }

        customers.add(customer);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public Customer getCustomerById(int id) {

        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with ID: " + id));
    }

    public boolean deleteCustomer(int id) {

        boolean removed = customers.removeIf(c -> c.getId() == id);

        if (!removed) {
            throw new CustomerNotFoundException(
                    "Cannot delete. Customer not found with ID: " + id);
        }

        return true;
    }

    public double getTotalBalance() {

        return customers.stream()
                .mapToDouble(Customer::getBalance)
                .sum();
    }
}