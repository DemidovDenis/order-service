package ru.demidov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.demidov.orderservice.entity.Customer;

public interface UserCustomRepository extends JpaSpecificationExecutor<Customer> {
}
