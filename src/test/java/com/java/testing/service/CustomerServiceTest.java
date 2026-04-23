package com.java.testing.service;

import com.java.testing.exception.CustomerAlreadyExistsException;
import com.java.testing.exception.CustomerNotFoundException;
import com.java.testing.model.Customer;
import org.junit.jupiter.api.*;

import java.util.List;
import static junit.framework.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerServiceTest {

    private CustomerService customerService;
    private List<Customer> customers;

    @BeforeEach
    public void setUp() {
        customers = List.of(
                Customer.builder().id(1).name("Vignesh").email("Vignesh@gmail.com").balance(100.0).build(),
                Customer.builder().id(2).name("Surya").email("Surya@gmail.com").balance(200.0).build()
        );

        customerService = new CustomerService(customers);
    }

    @Test
    void shouldAddCustomerWhenDataIsValid() {
        Customer customer = new Customer(3, "Yogesh", "Yogesh@test.com", 500);

        Customer result = customerService.addCustomer(customer);

        assertEquals("Yogesh", result.getName());
        assertEquals(3, customerService.getAllCustomers().size());
    }

    @Test
    void shouldThrowExceptionWhenAddingDuplicateCustomer() {
        Customer duplicate = new Customer(1, "Test", "test@test.com", 500);

        assertThrows(CustomerAlreadyExistsException.class,
                () -> customerService.addCustomer(duplicate));
    }

    @Test
    void shouldReturnCustomerWhenValidId() {
        Customer customer = customerService.getCustomerById(1);

        assertEquals("Vignesh", customer.getName());
    }

    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomerById(100));
    }

    @Test
    void shouldDeleteCustomerWhenValidId() {
        boolean result = customerService.deleteCustomer(1);

        assertTrue(result);
        assertEquals(1, customerService.getAllCustomers().size());
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingCustomer() {
        assertThrows(CustomerNotFoundException.class,
                () -> customerService.deleteCustomer(999));
    }

    @Test
    void shouldReturnAllCustomers() {
        assertEquals(2, customerService.getAllCustomers().size());
    }

    @Test
    void shouldReturnTotalBalance() {
        double total = customerService.getTotalBalance();

        assertEquals(300.0, total);
    }

    @AfterEach
    void tearDown() {
        customerService = null;
        customers = null;
    }
}