package ru.demidov.orderservice.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.Product;

import java.util.List;
import java.util.Optional;

public interface OrderRepository{
    List<Order> findAll();
    List<Product> findProductsByIdOrder(Long id);
    Order save(Order order);
    Order update(Order order);
    Optional<Order> findById(Long id);
    void delete(Order order);
}
