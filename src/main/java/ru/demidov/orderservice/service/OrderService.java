package ru.demidov.orderservice.service;

import ru.demidov.orderservice.dto.OrderProductDto;
import ru.demidov.orderservice.entity.Order;
import ru.demidov.orderservice.entity.Product;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    List<Product> findProductsByIdOrder(Long id);
    Order create(Order order);
    Order updateOrder(Long id, List<OrderProductDto> formDtos);
    Order deleteOrder(Long id);
}
