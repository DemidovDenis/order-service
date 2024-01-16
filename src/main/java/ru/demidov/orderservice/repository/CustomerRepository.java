package ru.demidov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.demidov.orderservice.entity.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> findByUserName(String username);
    Customer save(Customer customer);
}
