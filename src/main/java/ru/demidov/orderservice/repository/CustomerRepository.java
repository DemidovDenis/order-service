package ru.demidov.orderservice.repository;

import ru.demidov.orderservice.entity.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findByUserName(String username);
    Customer save(Customer customer);
}
