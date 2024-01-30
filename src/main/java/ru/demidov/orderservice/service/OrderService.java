package ru.demidov.orderservice.service;

import org.springframework.http.ResponseEntity;
import ru.demidov.orderservice.dto.OrderFormDto;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.Product;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    List<Product> findProductsByIdOrder(Long id);
    ResponseEntity<Order> create(OrderFormDto form);
    ResponseEntity<Order> update(Long id, OrderFormDto form);
    ResponseEntity<?> delete(Long id);
}
